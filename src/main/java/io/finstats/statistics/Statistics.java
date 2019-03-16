package io.finstats.statistics;

public final class Statistics {
  /**
   * Total sum of transaction value
   */
  private final Double sum;

  /**
   * Average amount of transaction value
   */
  private final Double avg;

  /**
   * max transaction value
   */
  private final Double max;

  /**
   * min transaction value
   */
  private final Double min;

  /**
   * total number of transactions happened in the last 60 seconds
   */
  private final Long count;

  Statistics() {
    this(0D, 0D, 0D, 0D, 0L);
  }

  private Statistics(Double sum, Double avg, Double max, Double min, Long count) {
    this.sum = sum;
    this.avg = avg;
    this.max = max;
    this.min = min;
    this.count = count;
  }

  public Double getSum() {
    return sum;
  }

  public Double getAvg() {
    return avg;
  }

  public Double getMax() {
    return max;
  }

  public Double getMin() {
    return min;
  }

  public Long getCount() {
    return count;
  }
}
