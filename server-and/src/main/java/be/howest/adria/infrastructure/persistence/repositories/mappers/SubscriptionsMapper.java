package be.howest.adria.infrastructure.persistence.repositories.mappers;

import be.howest.adria.domain.Intensity;
import be.howest.adria.domain.Subscription;
import be.howest.adria.domain.SubscriptionDetails;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionsMapper implements ResultSetMapper<Subscription> {

    private static final String SUBSCRIPTION_ID = "subscriptionId";
    private static final String SUBSCRIPTION_TYPE = "subscriptionType";
    private static final String SUBSCRIPTION_PLAN = "subscriptionPlan";
    private static final String HOURS = "hours";
    private static final String RANGE = "range";
    private static final String INTENSITY = "intensity";
    private static final String EXTRA_INFORMATION = "extraInformation";
    private static final String REQUEST_INFORMATION = "requestInformation";
    private static final String SUBSCRIPTION_SUPPORT = "subscriptionSupport";
    private static final String PRICE = "price";

    @Override
    public Subscription map(ResultSet resultSet) throws SQLException {
        if (resultSet.getString(SUBSCRIPTION_ID) == null)
            return null;

        int subscriptionId = resultSet.getInt(SUBSCRIPTION_ID);
        String subscriptionType = resultSet.getString(SUBSCRIPTION_TYPE);
        String subscriptionPlan = resultSet.getString(SUBSCRIPTION_PLAN);
        int hours = resultSet.getInt(HOURS);
        int range = resultSet.getInt(RANGE);
        Intensity intensity = Intensity.getIntensityLevel(resultSet.getString(INTENSITY));
        String extraInformation = resultSet.getString(EXTRA_INFORMATION);
        String requestInformation = resultSet.getString(REQUEST_INFORMATION);
        String subscriptionSupport = resultSet.getString(SUBSCRIPTION_SUPPORT);
        BigDecimal price = resultSet.getBigDecimal(PRICE);

        SubscriptionDetails details = new SubscriptionDetails(subscriptionType, subscriptionPlan, hours, range, intensity);

        return new Subscription(subscriptionId, details, extraInformation, requestInformation, subscriptionSupport, price);
    }
}