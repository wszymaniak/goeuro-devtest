package com.goeuro.devtest;


import com.goeuro.devtest.beans.Location;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GoEuroHttpClientTest {
    private static final String LOCATION_NAME = "Berlin";

    @Mock
    private HttpClient mockHttpClient;

    @InjectMocks
    private GoEuroHttpClient goEuroHttpClient;

    @Test
    public void testHappyCase() throws Exception {
        HttpResponse response = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("http", 1, 1), 200, ""));
        HttpEntity mockEntity = mock(HttpEntity.class);
        when(mockEntity.getContent()).thenReturn(buildResponseStream());
        response.setEntity(mockEntity);
        when(mockHttpClient.execute(any(HttpUriRequest.class))).thenReturn(response);
        List<Location> locations = goEuroHttpClient.suggestLocations(LOCATION_NAME);

        assertEquals(3, locations.size());
        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(mockHttpClient).execute(requestCaptor.capture());
        HttpUriRequest request = requestCaptor.getValue();
        Assert.assertEquals("GET", request.getMethod());
        Assert.assertNotNull(request.getURI().getPath());
        Assert.assertTrue(request.getURI().getPath().contains(LOCATION_NAME));

    }

    private InputStream buildResponseStream() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(TestDataBuilder.LOCATION_JSON);
        builder.append(",");
        builder.append(TestDataBuilder.LOCATION_JSON);
        builder.append(",");
        builder.append(TestDataBuilder.LOCATION_JSON);
        builder.append("]");

        return new ByteArrayInputStream(builder.toString().getBytes());
    }

    @Test(expected = GoEuroHttpException.class)
    public void test404() throws Exception {
        HttpResponse response = mock(HttpResponse.class);
        StatusLine statusLine404 = new BasicStatusLine(new ProtocolVersion("http", 1, 1), 404, "");
        when(response.getStatusLine()).thenReturn(statusLine404);
        when(mockHttpClient.execute(any(HttpUriRequest.class))).thenReturn(response);
        goEuroHttpClient.suggestLocations(LOCATION_NAME);
    }
}
