package io.finstats.storage;

public class InvalidTimestampException extends RuntimeException {
  private InvalidTimestampException(String message) {
    super(message);
  }

  static void check(boolean condition, String message) {
    if (!condition) {
      throw new InvalidTimestampException(message);
    }
  }
}
