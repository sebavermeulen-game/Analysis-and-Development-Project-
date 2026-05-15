package be.howest.adria.domain;

public class SubscriptionDetails {
    private final String subscriptionType;
    private final String subscriptionPlan;
    private final int hours;
    private final int range;
    private final Intensity intensity;

    public SubscriptionDetails(String subscriptionType, String subscriptionPlan, int hours, int range, Intensity intensity) {
        this.subscriptionType = subscriptionType;
        this.subscriptionPlan = subscriptionPlan;
        this.hours = hours;
        this.range = range;
        this.intensity = intensity;
    }

    protected String getSubscriptionType() {
        return subscriptionType;
    }

    protected String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    protected int getHours() {
        return hours;
    }

    protected int getRange() {
        return range;
    }

    protected Intensity getIntensity() {
        return intensity;
    }
}