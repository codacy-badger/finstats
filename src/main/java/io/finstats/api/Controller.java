package io.finstats.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface Controller extends Router {
  String APPLICATION_JSON = "application/json";
  Gson GSON = new GsonBuilder().create();
}
