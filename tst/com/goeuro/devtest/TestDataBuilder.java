package com.goeuro.devtest;

import com.goeuro.devtest.beans.Location;
import com.goeuro.devtest.beans.Position;

public class TestDataBuilder {
    public static final int IDENTIFIER = 1;
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final double POS_LATITUDE = 10.0;
    public static final double POS_LONGITUDE = 10.0;
    public static final String LOCATION_JSON = "{\"name\":\"name\",\"type\":\"type\",\"_id\":1,\"geo_position\":{\"latitude\":10.0,\"longitude\":10.0}}";

    public static Location buildLocation() {
        Location location = new Location();
        location.setId(IDENTIFIER);
        location.setName(NAME);
        location.setType(TYPE);
        Position pos = new Position();
        pos.setLatitude(POS_LATITUDE);
        pos.setLongitude(POS_LONGITUDE);
        location.setPosition(pos);

        return location;
    }
}
