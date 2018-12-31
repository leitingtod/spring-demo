package com.example.demo.exceptions;

public class WrongAction extends Exception {
  public WrongAction(Throwable cause) {
    super(cause);
  }

  public WrongAction(String message) {
    super(message);
  }

  public WrongAction(String message, Throwable cause) {
    super(message, cause);
  }
}
