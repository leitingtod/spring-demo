package com.example.demo.exceptions;

public class PasswordWeakException extends Exception {
  public PasswordWeakException() {}

  public PasswordWeakException(String message) {
    super(message);
  }

  public PasswordWeakException(String message, Throwable cause) {
    super(message, cause);
  }

  public PasswordWeakException(Throwable cause) {
    super(cause);
  }
}
