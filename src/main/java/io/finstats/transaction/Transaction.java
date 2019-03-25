package io.finstats.transaction;

import lombok.Data;

@Data
public final class Transaction {

  private double amount;
  private long timestamp;

  public Transaction() {
    throw new NullPointerException();
  }

  public Transaction(double amount, long timestamp) {
    this.amount = amount;
    this.timestamp = timestamp;
  }
}


