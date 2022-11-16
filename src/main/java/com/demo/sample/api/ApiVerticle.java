package com.demo.sample.api;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class ApiVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    Router router = Router.router(vertx);
    router.post("/api/v1/register");
    router.post("/api/v1/users");
    router.post("/api/v1/user/:name");

    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "Application/json")
        .end("Hello from Vert.x!");
    }).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  @Override
  public void stop() throws Exception {

  }
}
