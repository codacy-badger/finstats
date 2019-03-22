package io.finstats.metrics;

import io.finstats.transaction.Transaction;
import java.time.Duration;
import static java.time.Duration.ofMillis;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CircularMetricsStorage implements MetricsStorage {
  private static final int STORAGE_SIZE = 64;
  private static final Duration INTERVAL = Duration.ofMinutes(1);

  private final AtomicReferenceArray<MetricEntry> metrics;
  private final Duration interval;
  private final Supplier<Long> now;

  public CircularMetricsStorage() {
    this(System::currentTimeMillis, INTERVAL);
  }

  CircularMetricsStorage(Supplier<Long> now, Duration interval) {
    this.now = now;
    this.interval = interval;
    metrics = new AtomicReferenceArray<>(STORAGE_SIZE);
  }

  @Override
  public void addMetric(Transaction transaction) {
    final double amount = transaction.getAmount();
    getEntry(transaction.getTimestamp())
        .getMetric()
        .updateAndGet(metric -> metric.getOrIncrement(amount));
  }

  private MetricEntry getEntry(long timestamp) {
    long index = getIndex(timestamp);
    int offset = getOffset(index);

    log.debug("offset={}, index={} for timestamp={}", offset, index, new Date(timestamp));

    return metrics.updateAndGet(offset, value -> getOrCreate(index, value));
  }

  private long getIndex(long timestamp) {
    long moment = now.get();
    long start = ofMillis(moment).minus(interval).getSeconds();
    long end = ofMillis(moment).getSeconds();

    long index = ofMillis(timestamp).getSeconds();

    InvalidTimestampException.check(start <= index, "timestamp is too old");
    InvalidTimestampException.check(index <= end, "timestamp must not be greater than current time");

    return index;
  }

  private int getOffset(long index) {
    return (int) (index % metrics.length());
  }

  private MetricEntry getOrCreate(long index, MetricEntry current) {
    return current == null || current.index < index
        ? new MetricEntry(index, Metric.ZERO)
        : current;
  }

  private static class MetricEntry {
    private final long index;
    private final AtomicReference<Metric> metric;

    private MetricEntry(long index, Metric metric) {
      this.index = index;
      this.metric = new AtomicReference<>(metric);
    }

    @Override
    public String toString() {
      return "MetricEntry{" +
          "index=" + index +
          ", metric=" + metric +
          '}';
    }

    AtomicReference<Metric> getMetric() {
      return metric;
    }
  }
}
