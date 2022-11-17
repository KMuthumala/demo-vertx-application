package com.demo.sample.repository;

import io.vertx.core.Future;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.Tuple;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserRepository {

  private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());

  private static final Function<Row, User> MAPPER = (row) ->
    User.of(
      row.getUUID("id"),
      row.getString("name"),
      row.getString("email")
    );

  private final PgPool client;

  private UserRepository(PgPool _client) {
    this.client = _client;
  }

  public static UserRepository create(PgPool client) {
    return new UserRepository(client);
  }

  public Future<List<User>> findAll() {
    return client.query("SELECT * FROM users ORDER BY id ASC")
      .execute()
      .map(rs -> StreamSupport.stream(rs.spliterator(), false)
        .map(MAPPER)
        .collect(Collectors.toList())
      ).onSuccess(res -> {
        LOGGER.info("users get successfully");
      }).onFailure(failure -> {
        LOGGER.severe(failure.getCause().getMessage());
      });
  }

  public Future<UUID> save(User data) {

    return client.preparedQuery("INSERT INTO users (name, email) VALUES ($1, $2) RETURNING (id)")
      .execute(Tuple.of(data.getName(), data.getEmail()))
      .map(rs -> rs.iterator().next().getUUID("id"))
      .onSuccess(res -> {
        LOGGER.info("user created successfully");
      }).onFailure(failure -> {
        LOGGER.severe(failure.getMessage());
      });

  }

}
