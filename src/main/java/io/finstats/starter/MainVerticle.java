package io.finstats.starter;

import io.finstats.api.FinStatsRestApiVerticle;
import io.finstats.transaction.TransactionService;
import io.finstats.transaction.TransactionServiceImpl;
import static io.finstats.utils.LoggingUtils.configureLogging;
import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start() {
    configureLogging();

    TransactionService transactionService = new TransactionServiceImpl();
    vertx.deployVerticle(new FinStatsRestApiVerticle(transactionService));
  }
}
