package io.finstats.utils;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.Json;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonConverter {

  public static <T> T fromJson(String json, Class<T> clazz) {
    if (Objects.isNull(json) || json.isEmpty()) {
      throw new IllegalArgumentException(String.format("Input json is null or empty: '%s'", json));
    }
    try {
      return Json.decodeValue(json, clazz);
    } catch (DecodeException e) {
      log.error("Failed to parse json '{}'", json, e);
      throw e;
    }
  }

  public static String toJson(Object obj) {
    return Json.encode(obj);
  }
}
