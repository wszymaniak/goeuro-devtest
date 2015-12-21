package com.goeuro.devtest;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.goeuro.devtest.beans.Location;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Class that writes locations in csv format to files.
 */
public class LocationCsvWriter {
    private CsvSchema schema;
    private CsvMapper mapper = new CsvMapper();

    public LocationCsvWriter() {
        schema = CsvSchema.builder()
            .addColumn("id")
            .addColumn("name")
            .addColumn("type")
            .addColumn("latitude")
            .addColumn("longitude")
            .build();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Location.class, new LocationToCsvSerializer());
        mapper.registerModule(module);

    }

    public void writeAll(String filename, List<Location> cities) throws IOException {
        mapper.writer(
                schema.withoutHeader()
                      .withLineSeparator(System.lineSeparator()))
                .writeValue(new File(filename), cities);
    }
}
