package io.finstats.transaction;

public final class Transaction {

  private final Double amount;

  private final Long timestamp;

  public Transaction(Double amount, Long timestamp) {
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public Double getAmount() {
    return amount;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "amount=" + amount +
        ", timestamp=" + timestamp +
        '}';
  }
}


