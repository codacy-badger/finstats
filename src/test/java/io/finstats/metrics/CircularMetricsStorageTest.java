package io.finstats.metrics;

import io.finstats.transaction.Transaction;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class CircularMetricsStorageTest {

  private static final int TEST_INTERVAL = 10;
  private static final long NOW = System.currentTimeMillis();
  private final static CircularMetricsStorage storage = createTestStorage();

  @Test
  public void shouldGenerateCorrectIndex() {
    for (int i = 1; i <= 10; i++) {
      storage.addMetric(new Transaction(100, secondsAgo(i)));
    }
  }

  private long secondsAgo(int seconds) {
    return NOW - TimeUnit.SECONDS.toMillis(seconds);
  }

  private static CircularMetricsStorage createTestStorage() {
    return new CircularMetricsStorage(() -> NOW, Duration.of(TEST_INTERVAL, ChronoUnit.SECONDS));
  }
}