package be.howest.adria.domain;

import java.math.BigDecimal;

public class Subscription {
    private int subscriptionId;
    private String subscriptionType;
    private String subscriptionPlan;
    private int hours;
    private int range;
    private Intensity intensity;
    private String extraInformation;
    private String requestInformation;
    private String subscriptionSupport;
    private BigDecimal price;

    public Subscription(int subscriptionId, SubscriptionDetails details, String extraInformation,
                        String requestInformation, String subscriptionSupport, BigDecimal price) {
        this.subscriptionId = subscriptionId;
        this.subscriptionType = details.getSubscriptionType();
        this.subscriptionPlan = details.getSubscriptionPlan();
        this.hours = details.getHours();
        this.range = details.getRange();
        this.intensity = details.getIntensity();
        this.extraInformation = extraInformation;
        this.requestInformation = requestInformation;
        this.subscriptionSupport = subscriptionSupport;
        this.price = price;
    }

    public Subscription(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public String getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public int getHours() {
        return hours;
    }

    public int getRange() {
        return range;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public String getExtraInformation() {
        return extraInformation;
    }

    public String getRequestInformation() {
        return requestInformation;
    }

    public String getSubscriptionSupport() {
        return subscriptionSupport;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isUnlimited() {
        return hours == -1;
    }
}