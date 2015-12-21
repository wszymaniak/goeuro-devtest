package com.goeuro.devtest;

import com.goeuro.devtest.beans.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Main application class.
 */
public class DownloaderApplication {
    private GoEuroHttpClient goEuroHttpClient;
    private LocationCsvWriter locationsWriter;
    private static Logger log = LogManager.getLogger(DownloaderApplication.class);

    public DownloaderApplication() {
        goEuroHttpClient = new GoEuroHttpClient();
        locationsWriter = new LocationCsvWriter();
    }

    /**
     * Program entry point.
     * @param args the array of command line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Wrong number of arguments. Expected: 1, got: " + args.length);
            System.err.println("Expected usage: DownloaderApplication <CITY_NAME>");
            System.exit(-1);
        }

        try {
            new DownloaderApplication().processQuery(args[0]);
        } catch (IOException | GoEuroHttpException e){
            log.error("Caught error while processing query", e);
        }

    }

    void processQuery(String cityName) throws IOException, GoEuroHttpException {
        log.debug("Processing query for city: " + cityName);
        List<Location> cities = goEuroHttpClient.suggestLocations(cityName);
        String filename = cityName + ".csv";
        log.debug("Writing result file to: " + filename);
        locationsWriter.writeAll(filename, cities);
    }
}
