package io.finstats.api;

enum HttpStatus {
  CREATED(201),
  NO_CONTENT(204);

  final int code;

  HttpStatus(int statusCode) {
    this.code = statusCode;
  }

  int getCode() {
    return code;
  }
}
