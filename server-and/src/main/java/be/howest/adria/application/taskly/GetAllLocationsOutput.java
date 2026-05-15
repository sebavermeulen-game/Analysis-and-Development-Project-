package be.howest.adria.application.taskly;

import be.howest.adria.domain.LocationWithOrderData;

import java.util.Deque;

public class GetAllLocationsOutput {

    private final Deque<LocationWithOrderData> locations;

    public GetAllLocationsOutput(Deque<LocationWithOrderData> locations) {
        this.locations = locations;
    }

    public Deque<LocationWithOrderData> getLocations() {
        return locations;
    }
}