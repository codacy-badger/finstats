package io.finstats.transaction;

public final class Transaction {

  private final double amount;
  private final long timestamp;

  public Transaction(double amount, long timestamp) {
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public double getAmount() {
    return amount;
  }

  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return String.format("Transaction {amount=%f, timestamp=%d}", amount, timestamp);
  }
}


