package com.github.superkoh.paysdk.common.wx;

import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.github.wxpay.sdk.WXPayUtil;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Data;

@Data
public class WxPrepayInfo {

  private String timeStamp;
  private String nonceStr;
  private String package0;
  private String paySign;

  public WxPrepayInfo() {
  }

  public WxPrepayInfo(String prepayId, String appId, String secretKey) {
    this.package0 = "prepayId=" + prepayId;
    this.nonceStr = UUID.randomUUID().toString().replace("-", "")
        .substring(0, 32).toUpperCase();
    Map<String, String> params = new HashMap<>();
    this.timeStamp = String.valueOf(Instant.now().getEpochSecond());
    params.put("timeStamp", this.timeStamp);
    params.put("nonceStr", this.nonceStr);
    params.put("package", this.package0);
    params.put("signType", "MD5");
    params.put("appId", appId);
    try {
      this.paySign = WXPayUtil.generateSignature(params, secretKey, SignType.MD5);
    } catch (Exception e) {
    }
  }
}
