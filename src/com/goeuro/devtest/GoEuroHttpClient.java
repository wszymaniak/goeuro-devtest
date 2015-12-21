package com.goeuro.devtest;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeuro.devtest.beans.Location;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Client used to access GoEuro APIs.
 */
public class GoEuroHttpClient {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final String baseSuggestUrl;
    private final HttpClient httpClient;

    private static final String DEFAULT_BASE_SUGGEST_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
    private static final Logger log = LogManager.getLogger(GoEuroHttpClient.class);

    public GoEuroHttpClient() {
        this(DEFAULT_BASE_SUGGEST_URL, HttpClients.createDefault());

    }

    public GoEuroHttpClient(String baseSuggestUrl) {
        this(baseSuggestUrl, HttpClients.createDefault());
    }

    public GoEuroHttpClient(String baseSuggestUrl, HttpClient httpClient) {
        this.baseSuggestUrl = baseSuggestUrl;
        this.httpClient = httpClient;
    }

    public List<Location> suggestLocations(String cityName) throws IOException, GoEuroHttpException {
        String encodedCityName = URLEncoder.encode(cityName,"UTF-8");
        String resourceUri = baseSuggestUrl + encodedCityName;
        log.debug("Getting suggestions for uri: " + resourceUri);
        return list(baseSuggestUrl + encodedCityName, Location.class);
    }

    protected <T>List<T> list(String resourceUri, Class<T> target) throws IOException, GoEuroHttpException {
        HttpGet getSuggestions = new HttpGet(resourceUri);
        HttpResponse response = httpClient.execute(getSuggestions);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 400) {
                String reasonPhrase = response.getStatusLine().getReasonPhrase();
                log.warn("List request statusCode: " + statusCode + " reason: " + reasonPhrase);
                throw new GoEuroHttpException(reasonPhrase);

            }
            HttpEntity entity = response.getEntity();
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, target);
            return objectMapper.readValue(entity.getContent(), type);
        } finally {
            if (response instanceof Closeable){
                ((Closeable) response).close();
            }
        }
    }
}