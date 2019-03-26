package io.finstats.storage;

import java.util.Objects;

public final class Metric {
  public static final Metric ZERO = new Metric(0, 0, 0, 0);

  private final double sum;
  private final double max;
  private final double min;
  private final long count;

  private Metric(double sum, double max, double min, long count) {
    this.sum = sum;
    this.max = max;
    this.min = min;
    this.count = count;
  }

  public Metric merge(Metric other) {
    return new Metric(
        sum + other.sum,
        Math.max(max, other.max),
        Math.min(min, other.min),
        count + other.count
    );
  }

  static Metric getOrIncrement(Metric metric, double amount) {
    return ZERO.equals(metric)
        ? new Metric(amount, amount, amount, 1)
        : metric.increment(amount);
  }

  private Metric increment(double amount) {
    return new Metric(
        sum + amount,
        Math.max(max, amount),
        Math.min(min, amount),
        count + 1);
  }

  public double getSum() {
    return sum;
  }

  public double getMax() {
    return max;
  }

  public double getMin() {
    return min;
  }

  public long getCount() {
    return count;
  }

  public double getAvg() {
    return sum > 0 ? sum / count : Double.NaN;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Metric metric = (Metric) o;
    return Double.compare(metric.sum, sum) == 0 &&
        Double.compare(metric.max, max) == 0 &&
        Double.compare(metric.min, min) == 0 &&
        count == metric.count;
  }

  @Override
  public int hashCode() {
    return Objects.hash(sum, max, min, count);
  }
}
