package com.demo.sample.service;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class Service {

  private static Service service;

  private Service() {

  }

  public static Service create() {
    if (service == null) {
      return new Service();
    } else {
      return service;
    }

  }


//  public User createNewUser(JsonObject jsonObject) {
//    System.out.println("Inside create new user in service");
//    return
//  }
}
