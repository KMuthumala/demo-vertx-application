package com.demo.sample.repository;

import com.demo.sample.definitions.IAddressStore;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

import java.util.logging.Logger;

public class RepositoryVerticle extends AbstractVerticle {

  private final static Logger LOGGER = Logger.getLogger(RepositoryVerticle.class.getName());
  private PgPool pgPool;

  private UserRepository userRepository;

  @Override
  public void stop() throws Exception {
    pgPool.close()
      .onSuccess(closed -> {
        LOGGER.info("DB Connection Closed Successfully");
      })
      .onFailure(failure -> {
        LOGGER.info("DB Connection Closed Error");
      });
  }

  @Override
  public void start() throws Exception {
    LOGGER.info("Repository Verticle Started ");

    pgPool = PgPool.pool(vertx, new PgConnectOptions()
      .setPort(5432)
      .setHost("localhost")
      .setUser("postgres")
      .setDatabase("postgres")
      .setPassword("postgres"), new PoolOptions().setMaxSize(5)
    );

    userRepository = UserRepository.create(pgPool);

    EventBus eb = vertx.eventBus();

    eb.<JsonObject>consumer(IAddressStore.USER_CREATION_REPOSITORY_LEVEL, message -> {
      LOGGER.info("Received New User Registration");
      userRepository.save(User.of(message.body().copy().getString("name"), message.body().copy().getString("email")))
        .onSuccess(success -> {
          message.reply(new JsonObject().put("id",success.toString()));
        })
        .onFailure(failure -> {
          message.fail(500, failure.getMessage());
        });
    });

    eb.<JsonObject>consumer(IAddressStore.ALL_USER_GETTING_REPOSITORY_LEVEL, message -> {

      userRepository.findAll()
        .onSuccess(success -> {
          message.reply(User.toJsonArray(success));
        })
        .onFailure(failure -> {
          message.fail(500, failure.getMessage());
        });
    });
  }


}
