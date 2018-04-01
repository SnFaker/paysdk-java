package com.github.superkoh.paysdk.wechat;

import com.github.superkoh.paysdk.common.PayException;
import com.github.superkoh.paysdk.common.PayService;
import com.github.superkoh.paysdk.common.PrepayInfo;
import com.github.superkoh.paysdk.common.TxInfo;
import com.github.superkoh.paysdk.common.param.TxCloseParam;
import com.github.superkoh.paysdk.common.param.TxPrepayParam;
import com.github.superkoh.paysdk.common.param.TxQueryParam;
import com.github.superkoh.paysdk.common.wx.WxPrepayInfo;
import com.github.superkoh.paysdk.wechat.api.WxPayApi;
import com.github.superkoh.paysdk.wechat.api.req.CloseOrderReq;
import com.github.superkoh.paysdk.wechat.api.req.OrderQueryReq;
import com.github.superkoh.paysdk.wechat.api.req.UnifiedOrderReq;
import com.github.superkoh.paysdk.wechat.api.res.UnifiedOrderRes;
import com.github.superkoh.paysdk.wechat.api.res.WxTxRes;
import com.github.superkoh.paysdk.wechat.common.WxPayException;
import com.github.superkoh.paysdk.wechat.common.WxXmlUtils;
import java.io.IOException;
import lombok.Data;

@Data
public class WxPayService implements PayService {

  private String appId;
  private String mchId;
  private String secretKey;
  private String notifyUrl;

  private WxPayApi payApi;

  public WxPayService(String appId, String mchId, String secretKey, String notifyUrl) {
    this.appId = appId;
    this.mchId = mchId;
    this.secretKey = secretKey;
    this.notifyUrl = notifyUrl;
    payApi = new WxPayApi(appId, mchId, secretKey);
  }

  @Override
  public PrepayInfo prepay(TxPrepayParam prepayParam) throws PayException {
    UnifiedOrderReq req = UnifiedOrderReq.builder()
        .body(prepayParam.getProductDescription())
        .outTradeNo(prepayParam.getOrderId())
        .totalFee(prepayParam.getTotalFee())
        .spbillCreateIp(prepayParam.getUserIp())
        .notifyUrl(notifyUrl)
        .openid(prepayParam.getWxOpenId())
        .tradeType(prepayParam.getWxTradeType())
        .attach(prepayParam.getExtra())
        .build();
    UnifiedOrderRes res = payApi.unifiedOrder(req);
    PrepayInfo prepayInfo = new PrepayInfo();
    WxPrepayInfo wxPrepayInfo = new WxPrepayInfo(res.getPrepayId(), appId, secretKey);
    prepayInfo.setWxInfo(wxPrepayInfo);
    return prepayInfo;
  }

  @Override
  public TxInfo query(TxQueryParam queryParam) throws PayException {
    OrderQueryReq.OrderQueryReqBuilder builder = OrderQueryReq.builder();
    if (null != queryParam.getTransactionId()) {
      builder.transactionId(queryParam.getTransactionId());
    } else {
      builder.outTradeNo(queryParam.getOrderId());
    }
    WxTxRes res = payApi.orderQuery(builder.build());
    return TxInfo.wxTxInfo(res);
  }

  @Override
  public void close(TxCloseParam closeParam) throws PayException {
    payApi.closeOrder(CloseOrderReq.builder().outTradeNo(closeParam.getOrderId()).build());
  }

  @Override
  public TxInfo fromNotify(String body) throws PayException {
    try {
      WxTxRes txRes = WxXmlUtils.fromXml(body, WxTxRes.class);
      return TxInfo.wxTxInfo(txRes);
    } catch (IOException e) {
      throw new WxPayException(-1, e.getMessage());
    }
  }
}
