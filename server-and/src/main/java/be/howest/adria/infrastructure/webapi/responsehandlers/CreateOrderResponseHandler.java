package be.howest.adria.infrastructure.webapi.responsehandlers;

import be.howest.adria.infrastructure.webapi.shared.ApiResult;
import be.howest.adria.infrastructure.webapi.shared.ResponseHandler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class CreateOrderResponseHandler implements ResponseHandler<String> {
    private final RoutingContext ctx;

    public CreateOrderResponseHandler(RoutingContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void handle(String result) {
        JsonObject responseBody = new JsonObject();
        responseBody.put("orderId", result);
        ApiResult.createdWithBody(ctx, responseBody);
    }
}