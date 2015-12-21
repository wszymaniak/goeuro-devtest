package com.goeuro.devtest;

import com.goeuro.devtest.beans.Location;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static com.goeuro.devtest.TestDataBuilder.buildLocation;
import static org.junit.Assert.assertEquals;

public class LocationCsvWriterTest {
    private static final String LOCATION_FILE_PREFIX = "location_file";

    @Test
    public void testWriteToCsv() throws Exception {
        File file = File.createTempFile(LOCATION_FILE_PREFIX, null);
        LocationCsvWriter writer = new LocationCsvWriter();
        String absolutePath = file.getAbsolutePath();
        List<Location> locations = Arrays.asList(buildLocation(), buildLocation(), buildLocation());
        writer.writeAll(absolutePath, locations);
        List<String> lines = Files.readAllLines(file.toPath());
        assertEquals(3, lines.size());

        String expectedLinePrefix = String.format("%s,%s,%s", TestDataBuilder.IDENTIFIER, TestDataBuilder.NAME, TestDataBuilder.TYPE);
        Assert.assertTrue(lines.get(0).startsWith(expectedLinePrefix));
        Assert.assertTrue(lines.get(0).contains(String.format("%.1f", TestDataBuilder.POS_LATITUDE)));
        Assert.assertTrue(lines.get(0).contains(String.format("%.1f", TestDataBuilder.POS_LONGITUDE)));
    }

}