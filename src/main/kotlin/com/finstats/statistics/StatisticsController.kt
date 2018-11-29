package com.finstats.statistics

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticsController {

  /**
   * Should execute in O(1) time and space
   *
   * @return [Statistics] for last 60 seconds
   */
  @GetMapping
  fun statistics(): Statistics {
    return Statistics()
  }
}