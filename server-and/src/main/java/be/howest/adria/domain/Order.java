package be.howest.adria.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final Location location;
    private final int intensity;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int range;
    private final String userId;

    public Order(Location location, int intensity, LocalDateTime startTime, LocalDateTime endTime, int range, String userId) {
        this.orderId = UUID.randomUUID().toString();
        this.location = location;
        this.intensity = intensity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.range = range;
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public Location getLocation() {
        return location;
    }

    public int getIntensity() {
        return intensity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getRange() {
        return range;
    }

    public String getUserId() {
        return userId;
    }

    public long getDurationSeconds() {
        return Duration.between(startTime, endTime).getSeconds();
    }

    protected boolean overlapsWith(Order other) {
        return startTime.isBefore(other.endTime) && endTime.isAfter(other.startTime);
    }
}