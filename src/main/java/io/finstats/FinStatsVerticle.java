package io.finstats;

import io.finstats.http.HttpStatus;
import io.finstats.transaction.TransactionRestAPIVerticle;
import static io.finstats.utils.LoggingUtils.configureLogging;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinStatsVerticle extends AbstractVerticle {
  private final static Logger LOGGER = LoggerFactory.getLogger(FinStatsVerticle.class);
  private static final int PORT = 7000;

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);
    configureLogging();
  }

  @Override
  public void start() {
    //Router api = Router.router(vertx);

    //api.route("/").handler(this::index);
    //api.post("/transactions").handler(this::registerTransaction);
    //api.get("/statistics").handler(this::getStatistics);

    vertx.deployVerticle(new TransactionRestAPIVerticle());
    vertx
        .createHttpServer()
        .listen(PORT);
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
