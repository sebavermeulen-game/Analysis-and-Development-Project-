package be.howest.adria.application.taskly;

import be.howest.adria.application.contracts.EventNotifier;
import be.howest.adria.application.contracts.repositories.SubscriptionRepository;
import be.howest.adria.application.contracts.usecases.OutputPort;
import be.howest.adria.application.contracts.usecases.UseCase;

public class GetAllSubscriptions implements UseCase<Void> {

    private final SubscriptionRepository subscriptionRepository;
    private final OutputPort<GetAllSubscriptionsOutput> outputPort;

    private final EventNotifier eventNotifier;


    public GetAllSubscriptions(SubscriptionRepository subscriptionRepository, OutputPort<GetAllSubscriptionsOutput> outputPort, EventNotifier eventNotifier) {
        this.subscriptionRepository = subscriptionRepository;
        this.outputPort = outputPort;
        this.eventNotifier = eventNotifier;
    }

    @Override
    public void execute(Void input) {
        outputPort.present(new GetAllSubscriptionsOutput(subscriptionRepository.findAll()));
        eventNotifier.publish("Prices will increase soon!");
    }
}