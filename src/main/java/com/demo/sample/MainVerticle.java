package com.demo.sample;

import com.demo.sample.api.ApiVerticle;
import com.demo.sample.repository.RepositoryVerticle;
import com.demo.sample.service.ServiceVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new ApiVerticle());
    vertx.deployVerticle(new ServiceVerticle());
    vertx.deployVerticle(new RepositoryVerticle());
  }
}
