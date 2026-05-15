package be.howest.adria.infrastructure.webapi.presenters;

import be.howest.adria.application.contracts.usecases.OutputPort;
import be.howest.adria.application.taskly.GetAllSubscriptionsOutput;
import be.howest.adria.domain.Subscription;
import be.howest.adria.infrastructure.webapi.shared.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllSubscriptionsPresenter implements OutputPort<GetAllSubscriptionsOutput> {

    private final ResponseHandler<Map<String, List<Subscription>>> responseHandler;

    public GetAllSubscriptionsPresenter(ResponseHandler<Map<String, List<Subscription>>> responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void present(GetAllSubscriptionsOutput data) {
        Map<String, List<Subscription>> subscriptions = new HashMap<>();

        for (Subscription subscription : data.getSubscriptions()) {
            if (subscriptions.containsKey(subscription.getSubscriptionType())) {
                subscriptions.get(subscription.getSubscriptionType()).add(subscription);
            } else {
                List<Subscription> newSubscriptionList = new ArrayList<>();
                newSubscriptionList.add(subscription);
                subscriptions.put(subscription.getSubscriptionType(), newSubscriptionList);
            }
        }
        responseHandler.handle(subscriptions);
    }
}
