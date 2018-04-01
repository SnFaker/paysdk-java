package com.github.superkoh.paysdk.wechat.api;

import com.github.superkoh.paysdk.wechat.api.req.CloseOrderReq;
import com.github.superkoh.paysdk.wechat.api.req.OrderQueryReq;
import com.github.superkoh.paysdk.wechat.api.req.UnifiedOrderReq;
import com.github.superkoh.paysdk.wechat.api.res.UnifiedOrderRes;
import com.github.superkoh.paysdk.wechat.api.res.WxTxRes;
import com.github.superkoh.paysdk.wechat.common.BasePayRes;
import com.github.superkoh.paysdk.wechat.common.WxPayException;
import java.util.Objects;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class WxPayApi extends WxPayAbstractApi {

  private static final HttpUrl PAY_API = Objects
      .requireNonNull(HttpUrl.parse("https://api.mch.weixin.qq.com/pay"));

  public WxPayApi(String appId, String mchId, String secretKey) {
    super(appId, mchId, secretKey);
  }

  public UnifiedOrderRes unifiedOrder(UnifiedOrderReq req) throws WxPayException {
    return post(
        new Request.Builder().url(PAY_API.newBuilder()
            .addPathSegment("unifiedorder")
            .build()), req, UnifiedOrderRes.class);
  }

  public WxTxRes orderQuery(OrderQueryReq req) throws WxPayException {
    return post(
        new Request.Builder().url(PAY_API.newBuilder()
            .addPathSegment("orderquery")
            .build()), req, WxTxRes.class);
  }

  public BasePayRes closeOrder(CloseOrderReq req) throws WxPayException {
    return post(
        new Request.Builder().url(PAY_API.newBuilder()
            .addPathSegment("closeorder")
            .build()), req, BasePayRes.class);
  }
}
