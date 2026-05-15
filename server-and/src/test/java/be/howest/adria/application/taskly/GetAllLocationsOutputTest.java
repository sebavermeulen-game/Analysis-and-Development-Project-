package be.howest.adria.application.taskly;

import be.howest.adria.domain.LocationWithOrderData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetAllLocationsOutputTest {

    private Deque<LocationWithOrderData> locations;
    private LocationWithOrderData location1;
    private LocationWithOrderData location2;

    @BeforeEach
    void setUp() {
        location1 = new LocationWithOrderData(1.0, 1.0, LocalDateTime.now(), 5, 10);
        location2 = new LocationWithOrderData(2.0, 2.0, LocalDateTime.now(), 6, 12);
        locations = new ArrayDeque<>();
        locations.add(location1);
        locations.add(location2);
    }

    @Test
    void testConstructor() {
        // Act
        GetAllLocationsOutput output = new GetAllLocationsOutput(locations);

        // Assert
        assertNotNull(output, "Output should not be null");
    }

    @Test
    void testGetLocationsNotNull() {
        // Act
        GetAllLocationsOutput output = new GetAllLocationsOutput(locations);

        // Assert
        Deque<LocationWithOrderData> resultLocations = output.getLocations();
        assertNotNull(resultLocations, "Locations should not be null");
    }

    @Test
    void testGetLocationsSize() {
        // Act
        GetAllLocationsOutput output = new GetAllLocationsOutput(locations);

        // Assert
        Deque<LocationWithOrderData> resultLocations = output.getLocations();
        assertEquals(2, resultLocations.size(), "Locations should contain 2 elements");
    }

    @Test
    void testFirstLocation() {
        // Act
        GetAllLocationsOutput output = new GetAllLocationsOutput(locations);

        // Assert
        Deque<LocationWithOrderData> resultLocations = output.getLocations();
        assertEquals(location1, resultLocations.getFirst(), "First location should match");
    }

    @Test
    void testSecondLocation() {
        // Act
        GetAllLocationsOutput output = new GetAllLocationsOutput(locations);

        // Assert
        Deque<LocationWithOrderData> resultLocations = output.getLocations();
        assertEquals(location2, resultLocations.getLast(), "Second location should match");
    }
}