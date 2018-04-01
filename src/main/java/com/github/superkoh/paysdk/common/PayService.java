package com.github.superkoh.paysdk.common;

import com.github.superkoh.paysdk.common.param.TxCloseParam;
import com.github.superkoh.paysdk.common.param.TxPrepayParam;
import com.github.superkoh.paysdk.common.param.TxQueryParam;

public interface PayService {

  PrepayInfo prepay(TxPrepayParam prepayParam) throws PayException;

  TxInfo query(TxQueryParam queryParam) throws PayException;

  void close(TxCloseParam closeParam) throws PayException;

  TxInfo fromNotify(String body) throws PayException;
}
