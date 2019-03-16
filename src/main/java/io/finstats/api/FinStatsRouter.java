package io.finstats.api;

import com.google.inject.Inject;
import java.util.Set;
import spark.RouteGroup;
import spark.Spark;

public class FinStatsRouter implements Router {

  private final Set<Controller> controllers;

  @Inject
  public FinStatsRouter(Set<Controller> controllers) {
    this.controllers = controllers;
  }

  @Override
  public String getPath() {
    return "";
  }

  @Override
  public RouteGroup getRoutes() {
    return () -> controllers.forEach(it -> Spark.path(it.getPath(), it.getRoutes()));
  }
}
