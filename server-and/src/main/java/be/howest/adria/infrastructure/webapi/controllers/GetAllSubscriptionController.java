package be.howest.adria.infrastructure.webapi.controllers;

import be.howest.adria.application.contracts.usecases.UseCase;
import be.howest.adria.infrastructure.shared.contracts.Controller;
import be.howest.adria.infrastructure.webapi.shared.Request;

public class GetAllSubscriptionController implements Controller<Request> {

    private final UseCase<Void> getAllSubscriptions;

    public GetAllSubscriptionController(UseCase<Void> getAllSubscriptions) {
        this.getAllSubscriptions = getAllSubscriptions;
    }

    @Override
    public void handle(Request request) {
        getAllSubscriptions.execute(null);
    }
}

