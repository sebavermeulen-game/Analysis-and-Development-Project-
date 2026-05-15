package be.howest.adria.infrastructure.webapi.responsehandlers;

import be.howest.adria.domain.Subscription;
import be.howest.adria.infrastructure.webapi.shared.ApiResult;
import be.howest.adria.infrastructure.webapi.shared.ResponseHandler;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Map;

public class GetSubscriptionsResponseHandler implements ResponseHandler<Map<String, List<Subscription>>> {

    private final RoutingContext ctx;

    public GetSubscriptionsResponseHandler(RoutingContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void handle(Map<String, List<Subscription>> data) {
        ApiResult.ok(ctx, data);
    }
}