package io.finstats.statistics;

import io.finstats.api.Controller;
import spark.Route;
import spark.RouteGroup;
import static spark.Spark.get;

public class StatisticsController implements Controller {

  @Override
  public String getPath() {
    return "/statistics";
  }

  @Override
  public RouteGroup getRoutes() {
    return () -> get("", APPLICATION_JSON, getStatistics());
  }

  private Route getStatistics() {
    return null;
  }
}
