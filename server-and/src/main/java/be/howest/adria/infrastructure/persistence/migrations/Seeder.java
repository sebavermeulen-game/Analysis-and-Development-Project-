package be.howest.adria.infrastructure.persistence.migrations;

import be.howest.adria.application.contracts.repositories.SubscriptionRepository;
import be.howest.adria.domain.Intensity;
import be.howest.adria.domain.Subscription;
import be.howest.adria.domain.SubscriptionDetails;

import java.math.BigDecimal;
import java.util.List;

public class Seeder {
    private static final String INDIVIDUAL_USER = "Individual User";
    private static final String BUSINESSES = "Businesses";
    private static final String GOVERNMENT_LARGE_ORGANISATIONS = "Government & Large Organisations";
    private static final String BASIC_PLAN = "Basic Plan";
    private static final String PREMIUM_SUPPORT = "Premium Support";
    private static final String SUPPORT_24_24 = "24/24 Support";
    private static final String BASIC_SUBSCRIPTION = "Basic Subscription";

    private final SubscriptionRepository subscriptionRepository;

    public Seeder(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void seed() {
        List<Subscription> subscriptions = List.of(
                new Subscription(0, new SubscriptionDetails(INDIVIDUAL_USER, BASIC_PLAN, 30, 50, Intensity.LOW), "Suitable for personal use", "Request light for small locations", BASIC_SUBSCRIPTION, BigDecimal.valueOf(500.00)),
                new Subscription(0, new SubscriptionDetails(INDIVIDUAL_USER, "Standard Plan", 100, 100, Intensity.MEDIUM), "Adjustable intensity.", "Accept/Refuse light requests sent by others.", PREMIUM_SUPPORT, BigDecimal.valueOf(1500.00)),
                new Subscription(0, new SubscriptionDetails(INDIVIDUAL_USER, "Premium Plan", -1, 150, Intensity.HIGH), "Customized scheduling and intensity control.", "Full control over light requests, including large spaces.", SUPPORT_24_24, BigDecimal.valueOf(3000.00)),
                new Subscription(0, new SubscriptionDetails(BUSINESSES, "Starter Plan", 150, 50, Intensity.MEDIUM), "Automated scheduling", "Ideal for small businesses.", BASIC_SUBSCRIPTION, BigDecimal.valueOf(5000.00)),
                new Subscription(0, new SubscriptionDetails(BUSINESSES, "Pro Plan", 400, 100, Intensity.HIGH), "Advanced scheduling and multi-location management", "Suitable for small operations", PREMIUM_SUPPORT, BigDecimal.valueOf(15000.00)),
                new Subscription(0, new SubscriptionDetails(BUSINESSES, "Enterprise Plan", -1, 500, Intensity.ULTRA_HIGH), "Full control over range and intensity.", "Request light for large locations", SUPPORT_24_24, BigDecimal.valueOf(30000.00)),
                new Subscription(0, new SubscriptionDetails(GOVERNMENT_LARGE_ORGANISATIONS, "GLO Basic Plan", 400, 750, Intensity.MEDIUM), "Supports multiple operations at once", "Suitable for large areas", BASIC_SUBSCRIPTION, BigDecimal.valueOf(50000.00)),
                new Subscription(0, new SubscriptionDetails(GOVERNMENT_LARGE_ORGANISATIONS, "GLO Plus Plan", 500, 1000, Intensity.HIGH), "Full control over range and intensity.", "Suitable for larger operations.", PREMIUM_SUPPORT, BigDecimal.valueOf(150000.00)),
                new Subscription(0, new SubscriptionDetails(GOVERNMENT_LARGE_ORGANISATIONS, "GLO Unlimited Plan", -1, 2000, Intensity.ULTRA_HIGH), "Full control over range and intensity for large industrial operations.", "Full control over light requests, including large spaces.", SUPPORT_24_24, BigDecimal.valueOf(300000.00))
        );
        subscriptionRepository.saveAll(subscriptions);
    }
}