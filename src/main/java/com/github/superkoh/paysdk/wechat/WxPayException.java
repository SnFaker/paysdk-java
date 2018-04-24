package com.github.superkoh.paysdk.wechat;

import com.github.superkoh.paysdk.common.PayException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WxPayException extends PayException {

  private String strErrorCode = "ERROR";

  public WxPayException(int errorCode, String strErrorCode, String message) {
    super(errorCode, message);
    this.strErrorCode = strErrorCode;
  }

  public WxPayException(int errorCode, String message) {
    super(errorCode, message);
  }
}
