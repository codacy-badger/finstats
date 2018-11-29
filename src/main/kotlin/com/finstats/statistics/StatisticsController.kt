package com.finstats.statistics

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticsController {

  @GetMapping
  fun statistics(): Statistics {
    return Statistics()
  }
}