package be.howest.adria.infrastructure.webapi.responsehandlers;

import be.howest.adria.domain.LocationWithOrderData;
import be.howest.adria.infrastructure.webapi.shared.ApiResult;
import be.howest.adria.infrastructure.webapi.shared.ResponseHandler;
import io.vertx.ext.web.RoutingContext;

import java.util.Deque;

public class CreateGetAllLocationsResponseHandler implements ResponseHandler<Deque<LocationWithOrderData>> {

    private final RoutingContext ctx;

    public CreateGetAllLocationsResponseHandler(RoutingContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void handle(Deque<LocationWithOrderData> data) {
        ApiResult.ok(ctx, data);
    }
}