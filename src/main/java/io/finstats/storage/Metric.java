package io.finstats.storage;

import java.util.Objects;

public final class Metric {
  static final Metric ZERO = new Metric(0, 0, 0, 0);

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

  private static Metric from(double amount) {
    return new Metric(amount, amount, amount, 1);
  }

  private static Metric from(Metric other, double amount) {
    double max = Math.max(other.getMax(), amount);
    double min =  Math.min(other.getMin(), amount);
    return new Metric(other.getSum() + amount, max, min, other.getCount() + 1);
  }

  Metric getOrIncrement(double amount) {
    return ZERO.equals(this)
        ? Metric.from(amount)
        : Metric.from(this, amount);
  }

  double getSum() {
    return sum;
  }

  double getMax() {
    return max;
  }

  double getMin() {
    return min;
  }

  long getCount() {
    return count;
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
