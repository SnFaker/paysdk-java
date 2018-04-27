package com.github.superkoh.paysdk.common;

import com.github.superkoh.paysdk.common.param.RefundParam;
import com.github.superkoh.paysdk.common.param.RefundQueryParam;
import com.github.superkoh.paysdk.common.param.TxCloseParam;
import com.github.superkoh.paysdk.common.param.TxPrepayParam;
import com.github.superkoh.paysdk.common.param.TxQueryParam;

public interface PayService {

  PrepayInfo prepay(TxPrepayParam prepayParam, String tradeType) throws PayException;

  TxInfo query(TxQueryParam queryParam) throws PayException;

  void close(TxCloseParam closeParam) throws PayException;

  TxInfo fromNotify(String body) throws PayException;

  RefundApplyInfo refund(RefundParam refundParam) throws PayException;

  RefundInfo queryRefund(RefundQueryParam refundQueryParam) throws PayException;
}
