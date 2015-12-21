package com.goeuro.devtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeuro.devtest.beans.Location;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static com.goeuro.devtest.TestDataBuilder.*;

public class JsonParserTest {

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerializer() throws Exception {
        Location location = buildLocation();
        String json = mapper.writeValueAsString(location);
        assertEquals(LOCATION_JSON, json);
    }

    @Test
    public void testDeserialize() throws Exception {
        Location location = mapper.readValue(LOCATION_JSON, Location.class);
        assertEquals(IDENTIFIER, location.getId());
        assertEquals(NAME, location.getName());
        assertEquals(TYPE, location.getType());
        assertEquals(POS_LATITUDE, location.getPosition().getLatitude(), 1e-9);
        assertEquals(POS_LONGITUDE, location.getPosition().getLongitude(), 1e-9);
    }
}
