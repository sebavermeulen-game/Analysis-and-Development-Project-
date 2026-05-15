package be.howest.adria.infrastructure.webapi.responsehandlers;

import be.howest.adria.infrastructure.webapi.shared.ApiResult;
import be.howest.adria.infrastructure.webapi.shared.ResponseHandler;
import io.vertx.ext.web.RoutingContext;

public class CreateUserSubscriptionResponseHandler implements ResponseHandler<String> {

    private final RoutingContext ctx;

    public CreateUserSubscriptionResponseHandler(RoutingContext ctx) {
        this.ctx = ctx;
    }
    @Override
    public void handle(String subscriptionId) {
        ApiResult.createdWithBody(ctx, subscriptionId);
    }
}
