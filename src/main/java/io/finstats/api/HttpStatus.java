package io.finstats.api;

public enum HttpStatus {
  CREATED(201),
  NO_CONTENT(204),
  BAD_REQUEST(400);

  final int code;

  HttpStatus(int statusCode) {
    this.code = statusCode;
  }

  public int getCode() {
    return code;
  }
}
