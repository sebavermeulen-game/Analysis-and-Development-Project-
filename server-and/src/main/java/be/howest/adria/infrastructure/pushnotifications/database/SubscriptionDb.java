package be.howest.adria.infrastructure.pushnotifications.database;

import java.util.List;

import nl.martijndwars.webpush.Subscription;

public interface SubscriptionDb {
    void saveSubscriptions(List<Subscription> subscriptions);
    List<Subscription> getSubscriptions();
}
