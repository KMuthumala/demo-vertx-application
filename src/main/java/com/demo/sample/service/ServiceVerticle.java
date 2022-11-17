package com.demo.sample.service;

import com.demo.sample.definitions.IAddressStore;
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

    eb.<JsonObject>consumer(IAddressStore.USER_CREATION_SERVICE_LEVEL, message -> {
      eb.request(IAddressStore.USER_CREATION_REPOSITORY_LEVEL, message.body().copy(), reply -> {
        if (reply.succeeded()) {
          message.reply(reply.result().body());
        } else {
          message.fail(500, "Internal Server Error");
        }
      });
    });

    eb.<JsonObject>consumer(IAddressStore.ALL_USER_GETTING_SERVICE_LEVEL, message -> {
      eb.request(IAddressStore.ALL_USER_GETTING_REPOSITORY_LEVEL, "", reply -> {
        if (reply.succeeded()) {
          message.reply(reply.result().body());
        } else {
          message.fail(500, "Internal Server Error");
        }
      });
    });


  }


}
