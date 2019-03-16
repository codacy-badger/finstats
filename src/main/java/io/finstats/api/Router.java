package io.finstats.api;

import spark.RouteGroup;

public interface Router {

  String getPath();
  RouteGroup getRoutes();
}
