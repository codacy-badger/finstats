package io.finstats.statistics;

import io.finstats.transaction.Transaction;

public interface StatisticsService {
  void registerTransaction(Transaction transaction);
  Statistics getStatistics();
}
