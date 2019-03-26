package io.finstats.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Statistics {
  private double sum;
  private double avg;
  private double max;
  private double min;
  private long count;
}
