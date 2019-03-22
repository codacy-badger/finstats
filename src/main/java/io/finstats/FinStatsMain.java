package io.finstats;

import io.vertx.core.Vertx;

public class FinStatsMain {

  public static void main(String[] args) {
    Vertx.vertx()
        .deployVerticle(new FinStatsVerticle());
  }
}
