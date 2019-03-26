package io.finstats.storage;

import static io.finstats.storage.Metric.getOrIncrement;
import io.finstats.transaction.Transaction;
import java.time.Duration;
import static java.time.Duration.ofMillis;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MetricsStorageImpl implements MetricsStorage {
  private static final int STORAGE_SIZE = 64;
  private static final Duration INTERVAL = Duration.ofMinutes(1);

  private final AtomicReferenceArray<MetricEntry> metrics;
  private final Duration interval;
  private final Supplier<Long> now;

  public MetricsStorageImpl() {
    this(System::currentTimeMillis, INTERVAL);
  }

  MetricsStorageImpl(Supplier<Long> now, Duration interval) {
    this.now = now;
    this.interval = interval;
    metrics = new AtomicReferenceArray<>(STORAGE_SIZE);
  }

  @Override
  public void storeMetric(Transaction transaction) {
    getEntry(transaction.getTimestamp())
        .update(transaction.getAmount());
  }

  @Override
  public Stream<Metric> getMetricsStream() {
    long moment = now.get();
    long start = getMinimalIndex(moment);
    long end = getCurrentIndex(moment);

    return LongStream.rangeClosed(start, end)
        .mapToObj(index -> {
          MetricEntry entry = metrics.get(offset(index));
          return entry != null && index == entry.index ? entry.getMetric() : null;
        })
        .filter(Objects::nonNull);
  }

  private MetricEntry getEntry(long timestamp) {
    long index = getIndex(timestamp);
    int offset = offset(index);

    log.debug("offset={}, index={} for timestamp={}", offset, index, new Date(timestamp));

    return metrics.updateAndGet(offset, value -> getOrCreate(index, value));
  }

  private long getIndex(long timestamp) {
    long moment = now.get();
    long start = getMinimalIndex(moment);
    long end = getCurrentIndex(moment);

    long index = ofMillis(timestamp).getSeconds();

    InvalidTimestampException.check(start <= index, "timestamp is too old");
    InvalidTimestampException.check(index <= end, "timestamp must not be greater than current time");

    return index;
  }

  private long getCurrentIndex(long moment) {
    return ofMillis(moment).getSeconds();
  }

  private long getMinimalIndex(long moment) {
    return ofMillis(moment).minus(interval).getSeconds();
  }

  private int offset(long index) {
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

    void update(double amount) {
      metric.updateAndGet(metric -> getOrIncrement(metric, amount));
    }

    @Override
    public String toString() {
      return "MetricEntry{" +
          "index=" + index +
          ", metric=" + metric +
          '}';
    }

    Metric getMetric() {
      return metric.get();
    }
  }
}
