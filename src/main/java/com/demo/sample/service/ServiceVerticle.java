package com.demo.sample.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

import java.util.logging.Logger;

public class ServiceVerticle extends AbstractVerticle {

  private UserService service;
  private final static Logger LOGGER = Logger.getLogger(ServiceVerticle.class.getName());

  @Override
  public void start() throws Exception {
    System.out.println("Started ServiceVerticle ");

    service = UserService.create();
    EventBus eb = vertx.eventBus();

    eb.<JsonObject>consumer("user-create-service", message -> {
      eb.request("user-create-repository", message.body().copy(), reply -> {
        if (reply.succeeded()) {
          message.reply(reply.result().body());
        } else {
          message.fail(500, "Internal Server Error");
        }
      });
    });

    eb.<JsonObject>consumer("users-get-service", message -> {
      eb.request("users-get-repository", "", reply -> {
        if (reply.succeeded()) {
          message.reply(reply.result().body());
        } else {
          message.fail(500, "Internal Server Error");
        }
      });
    });


  }


}
