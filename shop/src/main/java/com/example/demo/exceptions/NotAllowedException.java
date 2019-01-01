package com.example.demo.exceptions;

public class NotAllowedException extends Exception {
  public NotAllowedException(String message) {
    super(message);
  }

  public NotAllowedException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotAllowedException(Throwable cause) {
    super(cause);
  }
}
