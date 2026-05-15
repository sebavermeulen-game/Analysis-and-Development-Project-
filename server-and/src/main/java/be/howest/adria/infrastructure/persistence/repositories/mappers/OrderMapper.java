package be.howest.adria.infrastructure.persistence.repositories.mappers;

import be.howest.adria.domain.Order;
import be.howest.adria.domain.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderMapper implements ResultSetMapper<Order> {

    private static final String ORDER_ID = "orderId";
    private static final String LOCATION_LATITUDE = "latitude";
    private static final String LOCATION_LONGITUDE = "longitude";
    private static final String INTENSITY = "intensity";
    private static final String START_TIME = "startingtime";
    private static final String END_TIME = "end_time"; // Assuming this column exists
    private static final String RANGE = "range";
    private static final String USER_ID = "userId"; // Assuming this column exists

    @Override
    public Order map(ResultSet resultSet) throws SQLException {
        if (resultSet.getString(ORDER_ID) == null)
            return null;

        double latitude = resultSet.getDouble(LOCATION_LATITUDE);
        double longitude = resultSet.getDouble(LOCATION_LONGITUDE);
        Location location = new Location(latitude, longitude);
        int intensity = resultSet.getInt(INTENSITY);
        LocalDateTime startTime = resultSet.getTimestamp(START_TIME).toLocalDateTime();
        LocalDateTime endTime = resultSet.getTimestamp(END_TIME).toLocalDateTime();
        int range = resultSet.getInt(RANGE);
        String userId = resultSet.getString(USER_ID);

        return new Order(location, intensity, startTime, endTime, range, userId);
    }
}