package io.finstats.statistics;

public final class Statistics {
  /**
   * Total sum of transaction value
   */
  private final double sum;

  /**
   * Average amount of transaction value
   */
  private final double avg;

  /**
   * max transaction value
   */
  private final double max;

  /**
   * min transaction value
   */
  private final double min;

  /**
   * total number of transactions happened in the last 60 seconds
   */
  private final long count;

  Statistics() {
    this(0D, 0D, 0D, 0D, 0L);
  }

  private Statistics(double sum, double avg, double max, double min, long count) {
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
