package io.finstats.metrics;

import io.finstats.transaction.Transaction;

public interface MetricsStorage {
  void addMetric(Transaction transaction);
}
