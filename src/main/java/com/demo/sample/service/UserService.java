package com.demo.sample.service;

public class UserService {

  private static UserService service;

  private UserService() {

  }

  public static UserService create() {
    if (service == null) {
      return new UserService();
    } else {
      return service;
    }

  }


//  public User createNewUser(JsonObject jsonObject) {
//    System.out.println("Inside create new user in service");
//    return
//  }
}
