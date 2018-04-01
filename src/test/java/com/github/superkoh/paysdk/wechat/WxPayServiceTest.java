package com.github.superkoh.paysdk.wechat;

import com.github.superkoh.paysdk.common.PayException;
import com.github.superkoh.paysdk.common.PrepayInfo;
import com.github.superkoh.paysdk.common.param.TxCloseParam;
import com.github.superkoh.paysdk.common.param.TxPrepayParam;
import com.github.superkoh.paysdk.common.TxInfo;
import com.github.superkoh.paysdk.common.param.TxQueryParam;
import org.junit.jupiter.api.Test;

class WxPayServiceTest {

  private WxPayService payService = new WxPayService(
      "wxdb05eaa66df521af",
      "1500383572",
      "e9631beb83eba37fa9033ba7dedba913",
      "http://localhost"
  );

  @Test
  void prepay() throws PayException {
    TxPrepayParam orderInfo = TxPrepayParam.builder()
        .orderId("test123457")
        .productId("abc")
        .totalFee(100L)
        .productDescription("商品信息")
        .userIp("127.0.0.1")
        .wxOpenId("oY_mk5HxCpU9fTJNn7reL68CciF4")
        .wxTradeType("JSAPI")
        .build();
    PrepayInfo prepayInfo = payService.prepay(orderInfo);
    System.out.println(prepayInfo);
  }

  @Test
  void query() throws PayException {
    TxQueryParam queryInfo = new TxQueryParam();
    queryInfo.setOrderId("test123457");
    TxInfo txInfo = payService.query(queryInfo);
    System.out.println(txInfo);
  }

  @Test
  void close() throws PayException {
    TxCloseParam closeParam = new TxCloseParam();
    closeParam.setOrderId("test123457");
    payService.close(closeParam);
  }
}