package be.howest.adria.main.factories;

import be.howest.adria.application.contracts.EventNotifier;
import be.howest.adria.application.contracts.repositories.*;
import be.howest.adria.application.taskly.*;
import be.howest.adria.infrastructure.persistence.repositories.*;
import be.howest.adria.infrastructure.pushnotifications.server.PushServer;
import be.howest.adria.infrastructure.shared.contracts.Controller;
import be.howest.adria.infrastructure.webapi.controllers.*;
import be.howest.adria.infrastructure.webapi.presenters.*;
import be.howest.adria.infrastructure.webapi.responsehandlers.*;
import be.howest.adria.infrastructure.webapi.shared.Request;
import io.vertx.ext.web.RoutingContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WebApiControllerFactory {
    private final EventNotifier event = PushServer.instance();
    private static final WebApiControllerFactory instance = new WebApiControllerFactory();
    private static final Logger LOGGER = Logger.getLogger(WebApiControllerFactory.class.getName());
    private final SubscriptionRepository subscriptionRepository = SqliteGetAllSubscriptions.instance();
    private final UserRepository userRepository = SqliteUserRepository.instance();
    private final OrderRepository orderRepository = SqliteOrderRepository.instance();
    private final CreateUserSubscriptionRepository userSubscriptionRepository = SqliteUserSubscriptionRepository.instance();
    private final LocationsRepository locationsRepository = SqliteGetAllLocationsRepository.instance();

    public static WebApiControllerFactory instance() {
        return instance;
    }

    private WebApiControllerFactory() {}

    public Controller<Request> createController(String operationId, RoutingContext ctx) {
        LOGGER.log(Level.INFO, "Creating controller for operationId: {0}", operationId);
        return switch (operationId) {
            case "GetAllSubscriptions" -> createGetAllSubscriptionsController(ctx);
            case "CreateUser" -> createCreateUserController(ctx);
            case "CreateOrder" -> createCreateOrderController(ctx);
            case "CreateSubscription" -> createCreateUserSubscriptionController(ctx);
            case "GetAllLocations" -> createGetAllLocationsController(ctx);
            default -> {
                LOGGER.log(Level.SEVERE, "Unknown operationId: {0}", operationId);
                throw new IllegalArgumentException("Unknown operationId: " + operationId);
            }
        };
    }

    private Controller<Request> createCreateUserController(RoutingContext ctx) {
        LOGGER.log(Level.INFO, "Creating CreateUserController");
        CreateUserResponseHandler responseHandler = new CreateUserResponseHandler(ctx);
        CreateUserPresenter outputPort = new CreateUserPresenter(responseHandler);
        CreateUser useCase = new CreateUser(userRepository, outputPort);
        return new CreateUserController(useCase);
    }

    private Controller<Request> createGetAllSubscriptionsController(RoutingContext ctx) {
        LOGGER.log(Level.INFO, "Creating GetAllSubscriptionController");
        GetSubscriptionsResponseHandler responseHandler = new GetSubscriptionsResponseHandler(ctx);
        GetAllSubscriptionsPresenter outputPort = new GetAllSubscriptionsPresenter(responseHandler);
        GetAllSubscriptions useCase = new GetAllSubscriptions(subscriptionRepository, outputPort, event);
        return new GetAllSubscriptionController(useCase);
    }

    private Controller<Request> createCreateOrderController(RoutingContext ctx) {
        LOGGER.log(Level.INFO, "Creating CreateOrderController");

        LOGGER.log(Level.INFO, "Creating CreateOrderResponseHandler");
        CreateOrderResponseHandler responseHandler = new CreateOrderResponseHandler(ctx);

        LOGGER.log(Level.INFO, "Creating CreateOrderPresenter");
        CreateOrderPresenter outputPort = new CreateOrderPresenter(responseHandler);

        LOGGER.log(Level.INFO, "Creating CreateOrder use case");
        CreateOrder useCase = new CreateOrder(orderRepository, outputPort);

        LOGGER.log(Level.INFO, "Returning new CreateOrderController");
        return new CreateOrderController(useCase);
    }

    private Controller<Request> createCreateUserSubscriptionController(RoutingContext ctx) {
        LOGGER.log(Level.INFO, "Creating CreateUserSubscriptionController");

        LOGGER.log(Level.INFO, "Creating CreateUserSubscriptionResponseHandler");
        CreateUserSubscriptionResponseHandler responseHandler = new CreateUserSubscriptionResponseHandler(ctx);

        LOGGER.log(Level.INFO, "Creating CreateUserSubscriptionPresenter");
        CreateUserSubscriptionPresenter outputPort = new CreateUserSubscriptionPresenter(responseHandler);

        LOGGER.log(Level.INFO, "Creating CreateUserSubscription use case");
        CreateUserSubscription useCase = new CreateUserSubscription(userRepository, userSubscriptionRepository, outputPort);

        LOGGER.log(Level.INFO, "Returning new CreateUserSubscriptionController");
        return new CreateUserSubscriptionController(useCase);
    }

    private Controller<Request> createGetAllLocationsController(RoutingContext ctx) {
        LOGGER.log(Level.INFO, "Creating GetAllLocationsController");
        CreateGetAllLocationsResponseHandler responseHandler = new CreateGetAllLocationsResponseHandler(ctx);
        CreateGetAllLocationsPresenter outputPort = new CreateGetAllLocationsPresenter(responseHandler);
        GetAllLocations useCase = new GetAllLocations(locationsRepository, outputPort);
        return new GetAllLocationsController(useCase);
    }
}