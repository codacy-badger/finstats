package io.finstats;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import io.finstats.api.Controller;
import io.finstats.api.FinStatsRouter;
import io.finstats.api.Router;
import io.finstats.metrics.CircularMetricsStorage;
import io.finstats.metrics.MetricsStorage;
import io.finstats.statistics.StatisticsController;
import io.finstats.transaction.TransactionController;

public class FinStatsModule extends AbstractModule {
  @Override
  protected void configure() {
    Multibinder<Controller> mb = Multibinder.newSetBinder(binder(), Controller.class);
    mb.addBinding().to(TransactionController.class);
    mb.addBinding().to(StatisticsController.class);

    bind(MetricsStorage.class).to(CircularMetricsStorage.class).asEagerSingleton();
    bind(Router.class).to(FinStatsRouter.class).asEagerSingleton();
  }
}
