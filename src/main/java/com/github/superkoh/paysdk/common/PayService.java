package com.github.superkoh.paysdk.common;

import com.github.superkoh.paysdk.common.param.RefundParam;
import com.github.superkoh.paysdk.common.param.RefundQueryParam;
import com.github.superkoh.paysdk.common.param.TxCloseParam;
import com.github.superkoh.paysdk.common.param.TxPrepayParam;
import com.github.superkoh.paysdk.common.param.TxQueryParam;

public interface PayService {

  PrepayInfo prepay(TxPrepayParam prepayParam) throws PayException;

  TxInfo query(TxQueryParam queryParam) throws PayException;

  void close(TxCloseParam closeParam) throws PayException;

  TxInfo fromPayCallback(String body) throws PayException;

  RefundApplyResult refund(RefundParam refundParam) throws PayException;

  RefundInfo queryRefund(RefundQueryParam refundQueryParam) throws PayException;

  RefundResult fromRufundCallback(String body) throws PayException;
}
