package com.finstats.statistics

/**
 * Transaction statistics for last 60 seconds
 */
data class Statistics(
  /**
   * total sum of transaction value
   */
  val sum: Double = 0.0,

  /**
   * average amount of transaction value
   */
  val avg: Double = 0.0,

  /**
   * max transaction value
   */
  val max: Double = 0.0,

  /**
   * min transaction value
   */
  val min: Double = 0.0,

  /**
   * total number of transactions happened in the last 60 seconds
   */
  val count: Long = 0)