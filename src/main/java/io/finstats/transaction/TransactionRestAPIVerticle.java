package io.finstats.transaction;

import io.finstats.http.HttpStatus;
import io.finstats.metrics.CircularMetricsStorage;
import io.finstats.metrics.InvalidTimestampException;
import io.finstats.metrics.MetricsStorage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionRestAPIVerticle extends AbstractVerticle {
  private final static Logger LOGGER = LoggerFactory.getLogger(TransactionRestAPIVerticle.class);

  private MetricsStorage metricsStorage;

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);
    metricsStorage = new CircularMetricsStorage();
  }

  @Override
  public void start() {
    Router api = Router.router(vertx);
    api.post("/transactions").handler(this::registerTransaction);
  }

  private void registerTransaction(RoutingContext context) {
    final Transaction transaction = getTransaction(context);
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

  private Transaction getTransaction(RoutingContext context) {
    String json = context.getBodyAsString();
    Transaction transaction = null;
    try {
      transaction = Json.decodeValue(json, Transaction.class);
    } catch (RuntimeException e) {
      LOGGER.error("Failed to parse json '{}'", json, e.getMessage());
    }
    return transaction;
  }
}
