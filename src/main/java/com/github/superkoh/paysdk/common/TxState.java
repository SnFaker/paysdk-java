package com.github.superkoh.paysdk.common;

public enum TxState {
  SUCCESS,
  REFUND,
  NOTPAY,
  CLOSED,
  REVOKED,
  USERPAYING,
  PAYERROR;


  public static TxState fromWxState(String state) {
    switch (state) {
      case "SUCCESS":
        return TxState.SUCCESS;
      case "REFUND":
        return TxState.REFUND;
      case "NOTPAY":
        return TxState.NOTPAY;
      case "CLOSED":
        return TxState.CLOSED;
      case "REVOKED":
        return TxState.REVOKED;
      case "USERPAYING":
        return TxState.USERPAYING;
      default:
        return TxState.PAYERROR;
    }
  }
}
