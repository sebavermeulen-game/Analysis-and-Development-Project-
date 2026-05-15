package be.howest.adria.domain;

import be.howest.adria.infrastructure.shared.utils.IntensityException;

public enum Intensity {
    LOW(40), MEDIUM(60), HIGH(80), ULTRA_HIGH(100);

    private static final int MIN_PERCENTAGE = 1;
    private static final int MAX_PERCENTAGE = 100;

    private final int maxPercentage;

    Intensity(int maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public int getMaxPercentage() {
        return maxPercentage;
    }

    public static boolean isValidIntensity(int intensity) {
        return intensity >= MIN_PERCENTAGE && intensity <= MAX_PERCENTAGE;
    }

    public static boolean isWithinMaxIntensity(int intensity, Intensity level) {
        return intensity <= level.getMaxPercentage();
    }

    public static boolean isWithinMaxIntensityForSubscription(int intensity, Subscription subscription) {
        Intensity level = getIntensityLevel(subscription.getIntensity().toString());
        return isWithinMaxIntensity(intensity, level);
    }

    public static Intensity getIntensityLevel(String level) {
        return switch (level.toUpperCase()) {
            case "LOW" -> LOW;
            case "MEDIUM" -> MEDIUM;
            case "HIGH" -> HIGH;
            case "ULTRA_HIGH" -> ULTRA_HIGH;
            default -> throw new IntensityException("Invalid subscription level: " + level);
        };
    }
}