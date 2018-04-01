package com.github.superkoh.paysdk.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PayException extends Exception {

  private int errorCode;

  public PayException(int errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }
}
