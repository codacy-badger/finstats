package io.finstats.api;

import static io.finstats.api.HttpStatus.CREATED;
import static io.finstats.api.HttpStatus.NO_CONTENT;
import io.finstats.storage.InvalidTimestampException;
import io.finstats.transaction.Transaction;
import io.finstats.transaction.TransactionService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FinStatsRestApiVerticle extends AbstractVerticle {

  private static final String API_TRANSACTIONS = "/transactions";
  private static final String API_GET_STATISTICS = "/statistics";
  private static final int DEFAULT_PORT = 9000;

  private final TransactionService transactionService;

  public FinStatsRestApiVerticle(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @Override
  public void start() throws Exception {
    super.start();

    final Router router = Router.router(vertx);
    router.post(API_TRANSACTIONS).handler(this::transactions);
    router.get(API_GET_STATISTICS).handler(this::getStatistics);

    int port = config().getInteger("service.port", DEFAULT_PORT);

    log.info("Staring on http://localhost:{}", port);

    vertx.createHttpServer()
        .requestHandler(router)
        .listen(port);
  }

  private void transactions(RoutingContext context) {
    log.debug("transactions");
    final Transaction transaction = getTransaction(context);
    try {
      transactionService.registerTransaction(transaction);
      context.response()
          .setStatusCode(CREATED.getCode())
          .end();
    } catch (InvalidTimestampException e) {
      context.response()
          .setStatusCode(NO_CONTENT.getCode())
          .end();
    }
  }

  private void getStatistics(RoutingContext context) {
    log.debug("getStatistics");
  }

  private Transaction getTransaction(RoutingContext context) {
    String json = context.getBodyAsString();
    Transaction transaction = null;
    try {
      transaction = Json.decodeValue(json, Transaction.class);
    } catch (RuntimeException e) {
      log.error("Failed to parse json '{}'", json, e.getMessage());
    }
    return transaction;
  }
}
