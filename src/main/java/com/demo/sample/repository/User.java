package com.demo.sample.repository;

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
