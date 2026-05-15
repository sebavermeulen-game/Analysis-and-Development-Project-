package be.howest.adria.application.contracts.repositories;

import be.howest.adria.domain.Order;
import be.howest.adria.domain.Subscription;

import java.util.UUID;

public interface OrderRepository {
    Subscription findSubscriptionByUserId(UUID userId);
    void save(Order order);
    int findOrderId(Order order);
}