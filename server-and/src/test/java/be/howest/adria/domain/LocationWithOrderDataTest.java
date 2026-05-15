package be.howest.adria.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationWithOrderDataTest {

    private double latitude;
    private double longitude;
    private LocalDateTime endTime;
    private int intensity;
    private int range;
    private LocationWithOrderData locationWithOrderData;

    @BeforeEach
    void setUp() {
        latitude = 51.051;
        longitude = 3.717;
        endTime = LocalDateTime.of(2024, 12, 15, 10, 30);
        intensity = 5;
        range = 10;
        locationWithOrderData = new LocationWithOrderData(latitude, longitude, endTime, intensity, range);
    }

    @Test
    void testConstructor() {
        assertEquals(latitude, locationWithOrderData.getLatitude());
        assertEquals(longitude, locationWithOrderData.getLongitude());
        assertEquals(endTime, locationWithOrderData.getEndTime());
        assertEquals(intensity, locationWithOrderData.getIntensity());
        assertEquals(range, locationWithOrderData.getRange());
    }

    @Test
    void testGetLatitude() {
        assertEquals(latitude, locationWithOrderData.getLatitude());
    }

    @Test
    void testGetLongitude() {
        assertEquals(longitude, locationWithOrderData.getLongitude());
    }

    @Test
    void testGetEndTime() {
        assertEquals(endTime, locationWithOrderData.getEndTime());
    }

    @Test
    void testGetIntensity() {
        assertEquals(intensity, locationWithOrderData.getIntensity());
    }

    @Test
    void testGetRange() {
        assertEquals(range, locationWithOrderData.getRange());
    }
}