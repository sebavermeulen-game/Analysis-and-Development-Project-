package be.howest.adria.infrastructure.persistence.repositories.mappers;

import be.howest.adria.domain.LocationWithOrderData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LocationWithOrderDataMapper implements ResultSetMapper<LocationWithOrderData> {

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String END_TIME = "endTime";

    @Override
    public LocationWithOrderData map(ResultSet resultSet) throws SQLException {
        double latitude = resultSet.getDouble(LATITUDE);
        double longitude = resultSet.getDouble(LONGITUDE);
        LocalDateTime endTime = resultSet.getTimestamp(END_TIME).toLocalDateTime();
        int intensity = resultSet.getInt("intensity");
        int range = resultSet.getInt("range");

        return new LocationWithOrderData(latitude, longitude, endTime, intensity, range);
    }
}