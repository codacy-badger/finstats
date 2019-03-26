package io.finstats.api;

import static io.finstats.api.HttpStatus.BAD_REQUEST;
import static io.finstats.api.HttpStatus.CREATED;
import static io.finstats.api.HttpStatus.NO_CONTENT;
import io.finstats.statistics.Statistics;
import io.finstats.statistics.StatisticsService;
import io.finstats.storage.InvalidTimestampException;
import io.finstats.transaction.Transaction;
import static io.finstats.utils.JsonConverter.fromJson;
import static io.finstats.utils.JsonConverter.toJson;
import io.finstats.utils.ResponseHelper;
import static io.finstats.utils.ResponseHelper.sendResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FinStatsRestApiVerticle extends AbstractVerticle {

  private static final String API_TRANSACTIONS = "/transactions";
  private static final String API_GET_STATISTICS = "/statistics";
  private static final int API_PORT = 9000;

  private final StatisticsService statisticsService;

  public FinStatsRestApiVerticle(StatisticsService statisticsService) {
    this.statisticsService = statisticsService;
  }

  @Override
  public void start() throws Exception {
    super.start();

    final Router router = Router.router(vertx);
    router.post(API_TRANSACTIONS)
        .handler(BodyHandler.create())
        .handler(this::transactions);

    router.get(API_GET_STATISTICS).handler(this::getStatistics);

    log.info("Staring on http://localhost:{}", API_PORT);

    vertx.createHttpServer()
        .requestHandler(router)
        .listen(API_PORT);
  }

  private void transactions(RoutingContext context) {
    try {
      final Transaction transaction = fromJson(context.getBodyAsString(), Transaction.class);
      statisticsService.registerTransaction(transaction);
      sendResponse(CREATED, "Transaction processed", context);

    } catch (InvalidTimestampException e) {
      sendResponse(NO_CONTENT, e, context);

    } catch (RuntimeException e) {
      sendResponse(BAD_REQUEST, e, context);
    }
  }

  private void getStatistics(RoutingContext context) {
    Statistics statistics = statisticsService.getStatistics();
    ResponseHelper.sendResponse(toJson(statistics), context);
  }
}
