package io.finstats.transaction;

import com.google.inject.Inject;
import io.finstats.api.Controller;
import io.finstats.api.HttpStatus;
import io.finstats.metrics.InvalidTimestampException;
import io.finstats.metrics.CircularMetricsStorage;
import java.util.function.Supplier;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.post;

public class TransactionController implements Controller {
  private final CircularMetricsStorage metricsStorage;

  @Inject
  public TransactionController(CircularMetricsStorage metricsStorage) {
    this.metricsStorage = metricsStorage;
  }

  @Override
  public String getPath() {
    return "/transactions";
  }

  @Override
  public RouteGroup getRoutes() {
    return () -> post("", APPLICATION_JSON, registerTransaction());
  }

  private Route registerTransaction() {
    return (request, response) -> {
      Transaction transaction = GSON.fromJson(request.body(), Transaction.class);
      Supplier<Long> now = System::currentTimeMillis;
      try {
        metricsStorage.addMetric(transaction);
        response.status(HttpStatus.CREATED.getCode());

      } catch (InvalidTimestampException e) {
        response.status(HttpStatus.NO_CONTENT.getCode());
      }
      return "";
    };
  }
}
