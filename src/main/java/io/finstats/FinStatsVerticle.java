package io.finstats;

import io.finstats.http.HttpStatus;
import io.finstats.metrics.CircularMetricsStorage;
import io.finstats.metrics.InvalidTimestampException;
import io.finstats.metrics.MetricsStorage;
import io.finstats.transaction.Transaction;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import static io.vertx.core.json.Json.decodeValue;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class FinStatsVerticle extends AbstractVerticle {
  private static final int PORT = 7000;
  private MetricsStorage metricsStorage;

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);
    metricsStorage = new CircularMetricsStorage();
  }

  @Override
  public void start() {
    Router api = Router.router(vertx);

    api.route("/").handler(this::index);
    api.post("/transactions").handler(this::registerTransaction);
    api.get("/statistics").handler(this::getStatistics);

    vertx
        .createHttpServer()
        .requestHandler(api)
        .listen(PORT);
  }

  private void registerTransaction(RoutingContext context) {
    final Transaction transaction = decodeValue(context.getBodyAsString(), Transaction.class);
    try {
      metricsStorage.addMetric(transaction);
      context.response()
          .setStatusCode(HttpStatus.CREATED.getCode())
          .end();
    } catch (InvalidTimestampException e) {
      context.response()
          .setStatusCode(HttpStatus.NO_CONTENT.getCode())
          .end();
    }
  }

  private void getStatistics(RoutingContext context) {
    context.response()
        .setStatusCode(HttpStatus.NO_CONTENT.getCode())
        .end();
  }

  private void index(RoutingContext context) {
    context.response()
        .putHeader("content-type", "text/html")
        .end("FinStats application");
  }
}
