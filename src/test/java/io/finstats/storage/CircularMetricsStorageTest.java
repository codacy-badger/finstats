package io.finstats.storage;

import io.finstats.transaction.Transaction;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class CircularMetricsStorageTest {

  private static final int TEST_INTERVAL = 10;
  private static final long NOW = System.currentTimeMillis();
  private final static MetricsStorageImpl storage = createTestStorage();

  @Test
  void shouldGenerateCorrectIndex() {
    for (int i = 1; i <= 10; i++) {
      storage.storeMetric(new Transaction(100, secondsAgo(i)));
    }
  }

  private long secondsAgo(int seconds) {
    return NOW - TimeUnit.SECONDS.toMillis(seconds);
  }

  private static MetricsStorageImpl createTestStorage() {
    return new MetricsStorageImpl(() -> NOW, Duration.of(TEST_INTERVAL, ChronoUnit.SECONDS));
  }
}