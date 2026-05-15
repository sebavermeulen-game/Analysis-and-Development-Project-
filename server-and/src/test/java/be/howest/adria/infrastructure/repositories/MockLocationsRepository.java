package be.howest.adria.infrastructure.repositories;

import be.howest.adria.application.contracts.repositories.LocationsRepository;
import be.howest.adria.domain.LocationWithOrderData;

import java.util.ArrayDeque;
import java.util.Deque;

public class MockLocationsRepository implements LocationsRepository {
    private final Deque<LocationWithOrderData> locations = new ArrayDeque<>();

    @Override
    public Deque<LocationWithOrderData> getAllLocations() {
        return new ArrayDeque<>(locations);
    }

    public void addLocation(LocationWithOrderData location) {
        locations.add(location);
    }
}