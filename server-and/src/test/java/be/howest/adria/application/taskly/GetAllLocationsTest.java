package be.howest.adria.application.taskly;

import be.howest.adria.domain.LocationWithOrderData;
import be.howest.adria.infrastructure.repositories.MockLocationsRepository;
import be.howest.adria.application.contracts.usecases.MockOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetAllLocationsTest {

    private MockLocationsRepository locationsRepository;
    private MockOutputPort<GetAllLocationsOutput> outputPort;
    private GetAllLocations getAllLocations;

    @BeforeEach
    void setUp() {
        locationsRepository = new MockLocationsRepository();
        outputPort = new MockOutputPort<>();
        getAllLocations = new GetAllLocations(locationsRepository, outputPort);
    }

    @Test
    void testExecute() {
        // Arrange
        LocationWithOrderData location1 = new LocationWithOrderData(1.0, 1.0, LocalDateTime.now(), 5, 10);
        LocationWithOrderData location2 = new LocationWithOrderData(2.0, 2.0, LocalDateTime.now(), 6, 12);

        locationsRepository.addLocation(location1);
        locationsRepository.addLocation(location2);

        // Act
        getAllLocations.execute(null);

        // Assert
        GetAllLocationsOutput result = outputPort.getOutput();
        assertNotNull(result, "Output should not be null");
        assertEquals(2, result.getLocations().size(), "Output should contain 2 locations");
    }

    @Test
    void testFirstLocationDetails() {
        // Arrange
        LocationWithOrderData location1 = new LocationWithOrderData(1.0, 1.0, LocalDateTime.now(), 5, 10);
        LocationWithOrderData location2 = new LocationWithOrderData(2.0, 2.0, LocalDateTime.now(), 6, 12);

        locationsRepository.addLocation(location1);
        locationsRepository.addLocation(location2);

        // Act
        getAllLocations.execute(null);

        // Assert
        GetAllLocationsOutput result = outputPort.getOutput();
        Deque<LocationWithOrderData> locations = new ArrayDeque<>(result.getLocations());
        assertEquals(location1, locations.getFirst(), "First location should match");
    }

    @Test
    void testSecondLocationDetails() {
        // Arrange
        LocationWithOrderData location1 = new LocationWithOrderData(1.0, 1.0, LocalDateTime.now(), 5, 10);
        LocationWithOrderData location2 = new LocationWithOrderData(2.0, 2.0, LocalDateTime.now(), 6, 12);

        locationsRepository.addLocation(location1);
        locationsRepository.addLocation(location2);

        // Act
        getAllLocations.execute(null);

        // Assert
        GetAllLocationsOutput result = outputPort.getOutput();
        Deque<LocationWithOrderData> locations = new ArrayDeque<>(result.getLocations());
        assertEquals(location2, locations.getLast(), "Second location should match");
    }

    @Test
    void testExecuteEmptyRepository() {
        // Act
        getAllLocations.execute(null);

        // Assert
        GetAllLocationsOutput result = outputPort.getOutput();
        assertNotNull(result, "Output should not be null");
        assertTrue(result.getLocations().isEmpty(), "Output should contain no locations");
    }

    @Test
    void testSaveAll() {
        // Arrange
        LocationWithOrderData location1 = new LocationWithOrderData(1.0, 1.0, LocalDateTime.now(), 5, 10);
        LocationWithOrderData location2 = new LocationWithOrderData(2.0, 2.0, LocalDateTime.now(), 6, 12);

        locationsRepository.addLocation(location1);
        locationsRepository.addLocation(location2);

        // Act
        getAllLocations.execute(null);

        // Assert
        GetAllLocationsOutput result = outputPort.getOutput();
        Deque<LocationWithOrderData> locations = new ArrayDeque<>(result.getLocations());
        assertEquals(2, locations.size(), "Repository should contain 2 locations");
        assertEquals(location1, locations.getFirst(), "First location should match");
        assertEquals(location2, locations.getLast(), "Second location should match");
    }
}