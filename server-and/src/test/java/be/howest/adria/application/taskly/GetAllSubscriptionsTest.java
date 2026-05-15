package be.howest.adria.application.taskly;

import be.howest.adria.application.contracts.usecases.MockOutputPort;
import be.howest.adria.infrastructure.repositories.MockSubscriptionRepository;
import be.howest.adria.domain.Intensity;
import be.howest.adria.domain.Subscription;
import be.howest.adria.domain.SubscriptionDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetAllSubscriptionsTest {

    private MockSubscriptionRepository mockSubscriptionRepository;
    private MockOutputPort<GetAllSubscriptionsOutput> outputPort;
    private GetAllSubscriptions getAllSubscriptions;

    @BeforeEach
    void setup() {
        mockSubscriptionRepository = new MockSubscriptionRepository();
        outputPort = new MockOutputPort<>();
        getAllSubscriptions = new GetAllSubscriptions(mockSubscriptionRepository, outputPort, System.out::println);
    }

    @Test
    void testExecute() {
        // Arrange
        SubscriptionDetails details1 = new SubscriptionDetails("TypeA", "PlanA", 10, 100, Intensity.HIGH);
        Subscription subscription1 = new Subscription(1, details1, "Extra1", "Request1", "Support1", BigDecimal.valueOf(99.99));

        SubscriptionDetails details2 = new SubscriptionDetails("TypeB", "PlanB", 20, 200, Intensity.MEDIUM);
        Subscription subscription2 = new Subscription(2, details2, "Extra2", "Request2", "Support2", BigDecimal.valueOf(199.99));

        mockSubscriptionRepository.addSubscription(subscription1);
        mockSubscriptionRepository.addSubscription(subscription2);

        // Act
        getAllSubscriptions.execute(null);

        // Assert
        GetAllSubscriptionsOutput result = outputPort.getOutput();
        assertNotNull(result, "Output should not be null");
        assertEquals(2, result.getSubscriptions().size(), "Output should contain 2 subscriptions");
    }

    @Test
    void testFirstSubscriptionDetails() {
        // Arrange
        SubscriptionDetails details1 = new SubscriptionDetails("TypeA", "PlanA", 10, 100, Intensity.HIGH);
        Subscription subscription1 = new Subscription(1, details1, "Extra1", "Request1", "Support1", BigDecimal.valueOf(99.99));

        SubscriptionDetails details2 = new SubscriptionDetails("TypeB", "PlanB", 20, 200, Intensity.MEDIUM);
        Subscription subscription2 = new Subscription(2, details2, "Extra2", "Request2", "Support2", BigDecimal.valueOf(199.99));

        mockSubscriptionRepository.addSubscription(subscription1);
        mockSubscriptionRepository.addSubscription(subscription2);

        // Act
        getAllSubscriptions.execute(null);

        // Assert
        GetAllSubscriptionsOutput result = outputPort.getOutput();
        assertEquals(subscription1, result.getSubscriptions().get(0), "First subscription should match");
    }

    @Test
    void testSecondSubscriptionDetails() {
        // Arrange
        SubscriptionDetails details1 = new SubscriptionDetails("TypeA", "PlanA", 10, 100, Intensity.HIGH);
        Subscription subscription1 = new Subscription(1, details1, "Extra1", "Request1", "Support1", BigDecimal.valueOf(99.99));

        SubscriptionDetails details2 = new SubscriptionDetails("TypeB", "PlanB", 20, 200, Intensity.MEDIUM);
        Subscription subscription2 = new Subscription(2, details2, "Extra2", "Request2", "Support2", BigDecimal.valueOf(199.99));

        mockSubscriptionRepository.addSubscription(subscription1);
        mockSubscriptionRepository.addSubscription(subscription2);

        // Act
        getAllSubscriptions.execute(null);

        // Assert
        GetAllSubscriptionsOutput result = outputPort.getOutput();
        assertEquals(subscription2, result.getSubscriptions().get(1), "Second subscription should match");
    }

    @Test
    void testExecuteEmptyRepository() {
        // Act
        getAllSubscriptions.execute(null);

        // Assert
        GetAllSubscriptionsOutput result = outputPort.getOutput();
        assertNotNull(result, "Output should not be null");
        assertTrue(result.getSubscriptions().isEmpty(), "Output should contain no subscriptions");
    }

    @Test
    void testSaveAll() {
        // Arrange
        SubscriptionDetails details1 = new SubscriptionDetails("TypeA", "PlanA", 10, 100, Intensity.HIGH);
        Subscription subscription1 = new Subscription(1, details1, "Extra1", "Request1", "Support1", BigDecimal.valueOf(99.99));

        SubscriptionDetails details2 = new SubscriptionDetails("TypeB", "PlanB", 20, 200, Intensity.MEDIUM);
        Subscription subscription2 = new Subscription(2, details2, "Extra2", "Request2", "Support2", BigDecimal.valueOf(199.99));

        List<Subscription> subscriptions = List.of(subscription1, subscription2);

        // Act
        mockSubscriptionRepository.saveAll(subscriptions);

        // Assert
        List<Subscription> savedSubscriptions = mockSubscriptionRepository.findAll();
        assertEquals(2, savedSubscriptions.size(), "Repository should contain 2 subscriptions");
        assertEquals(subscription1, savedSubscriptions.get(0), "First subscription should match");
        assertEquals(subscription2, savedSubscriptions.get(1), "Second subscription should match");
    }
}