package io.finstats;

import io.finstats.api.FinStatsRestApiVerticle;
import io.finstats.statistics.StatisticsService;
import io.finstats.statistics.StatisticsServiceImpl;
import io.finstats.storage.MetricsStorage;
import io.finstats.storage.MetricsStorageImpl;
import static io.finstats.utils.LoggingUtils.configureLogging;
import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    configureLogging();

    final MetricsStorage storage = new MetricsStorageImpl();
    final StatisticsService statisticsService = new StatisticsServiceImpl(storage);

    vertx.deployVerticle(new FinStatsRestApiVerticle(statisticsService));
  }
}
