package io.finstats.utils;

import io.finstats.api.HttpStatus;
import io.vertx.ext.web.RoutingContext;

public class ResponseHelper {
  private static final String HEADER_CONTENT_TYPE = "content-type";
  private static final String CONTENT_TYPE_JSON = "application/json";

  public static void sendResponse(String json, RoutingContext context) {
    context.response()
        .putHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
        .end(json);
  }

  public static void sendResponse(HttpStatus status, Throwable t, RoutingContext context) {
    sendResponse(status, t.getMessage(), context);
  }

  public static void sendResponse(HttpStatus status, String message, RoutingContext context) {
    context.response()
        .setStatusMessage(message)
        .setStatusCode(status.getCode())
        .end();
  }
}
