package be.howest.adria.application.contracts.repositories;

import be.howest.adria.domain.LocationWithOrderData;
import java.util.Deque;

public interface LocationsRepository {
    Deque<LocationWithOrderData> getAllLocations();
}