package io.finstats.storage;

import io.finstats.transaction.Transaction;
import java.util.stream.Stream;

public interface MetricsStorage {
  void storeMetric(Transaction transaction);
  Stream<Metric> getMetricsStream();
}
