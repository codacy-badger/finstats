package io.finstats.statistics;

import io.finstats.storage.Metric;
import io.finstats.storage.MetricsStorage;
import io.finstats.transaction.Transaction;

public class StatisticsServiceImpl implements StatisticsService {

  private final MetricsStorage storage;

  public StatisticsServiceImpl(MetricsStorage storage) {
    this.storage = storage;
  }

  @Override
  public void registerTransaction(Transaction transaction) {
    storage.storeMetric(transaction);
  }

  @Override
  public Statistics getStatistics() {
    final Metric metric = storage
        .getMetricsStream()
        .reduce(Metric.ZERO, Metric::merge);

    return new Statistics(metric.getSum(), metric.getAvg(), metric.getMax(), metric.getMin(), metric.getCount());
  }
}
