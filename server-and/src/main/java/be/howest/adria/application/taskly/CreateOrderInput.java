package be.howest.adria.application.taskly;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateOrderInput {
    private final UUID userId;
    private final double latitude;
    private final double longitude;
    private final int intensity;
    private final int range;
    private final int duration;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public CreateOrderInput(UUID userId, double latitude, double longitude, int intensity, LocalDateTime startTime, int range, int duration) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.intensity = intensity;
        this.range = range;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(duration);
    }

    public UUID getUserId() {
        return userId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
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

    public int getDuration() {
        return duration;
    }
}