package io.finstats.transaction;

import io.finstats.api.Controller;
import io.finstats.api.HttpStatus;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.post;

public class TransactionController implements Controller {

  @Override
  public String getPath() {
    return "/transactions";
  }

  @Override
  public RouteGroup getRoutes() {
    return () -> post("", APPLICATION_JSON, saveTransaction());
  }

  private Route saveTransaction() {
    return (request, response) -> {
      Transaction transaction = GSON.fromJson(request.body(), Transaction.class);
      System.out.println(transaction);

      response.status(HttpStatus.CREATED.getCode());
      return "";
    };
  }
}
