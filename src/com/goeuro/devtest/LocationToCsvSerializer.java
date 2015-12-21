package com.goeuro.devtest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.goeuro.devtest.beans.Location;

import java.io.IOException;

/**
 * Definition of serializer used when writing locations data to csv file.
 */
public class LocationToCsvSerializer extends JsonSerializer<Location> {
    @Override
    public void serialize(Location location, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", location.getId());
        jsonGenerator.writeStringField("name", location.getName());
        jsonGenerator.writeStringField("type", location.getType());
        if (location.getPosition() != null) {
            jsonGenerator.writeNumberField("latitude", location.getPosition().getLatitude());
            jsonGenerator.writeNumberField("longitude", location.getPosition().getLongitude());
        }
        jsonGenerator.writeEndObject();
    }
}
