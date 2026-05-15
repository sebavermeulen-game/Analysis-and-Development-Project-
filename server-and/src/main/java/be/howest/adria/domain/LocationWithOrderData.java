package be.howest.adria.domain;

import java.time.LocalDateTime;

public class LocationWithOrderData extends Location {
    private final LocalDateTime endTime;
    private final int intensity;
    private final int range;

    public LocationWithOrderData(double latitude, double longitude, LocalDateTime endTime, int intensity, int range) {
        super(latitude, longitude);
        this.endTime = endTime;
        this.intensity = intensity;
        this.range = range;
    }

    protected LocalDateTime getEndTime() {
        return endTime;
    }

    protected int getIntensity() {
        return intensity;
    }

    protected int getRange() {
        return range;
    }
}