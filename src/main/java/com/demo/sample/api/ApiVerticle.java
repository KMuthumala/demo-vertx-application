package com.demo.sample.api;

import com.demo.sample.definitions.IAddressStore;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.logging.Logger;

public class ApiVerticle extends AbstractVerticle {

  private final static Logger LOGGER = Logger.getLogger(ApiVerticle.class.getName());

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);
    router.post("/api/v1/register").produces("application/json").consumes("application/json").handler(BodyHandler.create()).handler(this::createNewUser);
    router.get("/api/v1/users").produces("application/json").handler(BodyHandler.create()).handler(this::getAllUsers);
    router.get("/api/v1/user/:name").produces("application/json"); // TODO

    vertx.createHttpServer().requestHandler(router).listen(8888)
      .onSuccess(server -> {
        startPromise.complete();
        LOGGER.info("HTTP server started on port " + server.actualPort());
      })
      .onFailure(event -> {
          startPromise.fail(event);
        LOGGER.severe("Failed to start HTTP server:" + event.getMessage());
        }
      );
  }

  private void createNewUser(RoutingContext routingContext) {
    LOGGER.info("Get all users request found...");
    vertx.eventBus().<JsonObject>request(IAddressStore.USER_CREATION_SERVICE_LEVEL, routingContext.body().asJsonObject(), reply -> {
      if (reply.succeeded()) {
        routingContext.request().response().setStatusCode(200).end(Json.encodePrettily(reply.result().body()));
      } else {
        routingContext.request().response().setStatusCode(500).end("Server Error");
      }
    });
  }

  private void getAllUsers(RoutingContext routingContext) {
    LOGGER.info("New user creation request found...");
    vertx.eventBus().<JsonObject>request(IAddressStore.ALL_USER_GETTING_SERVICE_LEVEL, "", reply -> {
      if (reply.succeeded()) {
        routingContext.request().response()
          .setStatusCode(200)
          .putHeader("content-type","application/json")
          .end(Json.encodePrettily(reply.result().body()));
      } else {
        routingContext.request().response().setStatusCode(500).end("Server Error");
      }
    });
  }

  @Override
  public void stop() throws Exception {

  }

}
