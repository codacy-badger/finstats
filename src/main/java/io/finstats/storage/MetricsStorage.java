package io.finstats.storage;

import io.finstats.transaction.Transaction;

public interface MetricsStorage {
  void addMetric(Transaction transaction);
}
