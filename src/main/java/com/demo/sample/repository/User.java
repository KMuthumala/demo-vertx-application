package com.demo.sample.repository;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.UUID;

public class User {
  UUID id;
  String name;
  String email;


  public static User of(String name, String email) {
    return of(null, name, email);
  }

  public static User of(UUID id, String title, String content) {
    User data = new User();
    data.setId(id);
    data.setName(title);
    data.setEmail(content);
    return data;
  }

  public static JsonObject toJsonObject(User user) {
    JsonObject jsonObject = new JsonObject();
    jsonObject.put("id", user.getId().toString());
    jsonObject.put("name", user.getName());
    jsonObject.put("email", user.getEmail());
    return jsonObject;
  }

  public static JsonArray toJsonArray(List<User> userList) {
    JsonArray jsonArray = new JsonArray();
    userList.forEach(user -> {
      jsonArray.add(User.toJsonObject(user));
    });
    return jsonArray;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  @Override
  public String toString() {
    return "Post{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      ", email='" + email + '\'' +
      '}';
  }

}
