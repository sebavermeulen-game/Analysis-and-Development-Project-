package be.howest.adria.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {
    @Test
    void testLocationCreation() {
        Location location = new Location(51.0, 4.0);
        assertEquals(51.0, location.getLatitude());
        assertEquals(4.0, location.getLongitude());
    }
}
