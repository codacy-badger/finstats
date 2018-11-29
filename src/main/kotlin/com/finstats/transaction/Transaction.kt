package com.finstats.transaction

data class Transaction(
  /**
   * Transaction amount
   */
  val amount: Float,

  /**
   * Transaction time in epoch in millis in UTS time zone
   */
  val timestamp: Long
)
