package be.howest.adria.infrastructure.webapi.presenters;

import be.howest.adria.application.contracts.usecases.OutputPort;
import be.howest.adria.application.taskly.CreateUserSubscriptionOutput;
import be.howest.adria.infrastructure.webapi.shared.ResponseHandler;

public class CreateUserSubscriptionPresenter implements OutputPort<CreateUserSubscriptionOutput> {

    private final ResponseHandler<String> responseHandler;

    public CreateUserSubscriptionPresenter(ResponseHandler<String> responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void present(CreateUserSubscriptionOutput data) {
        responseHandler.handle(data.getSubscriptionId().toString());
    }
}
