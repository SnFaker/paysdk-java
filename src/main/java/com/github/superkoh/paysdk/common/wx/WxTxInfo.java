package com.github.superkoh.paysdk.common.wx;

import java.util.Map;
import lombok.Data;

@Data
public class WxTxInfo {

  private String deviceInfo;
  private String openid;
  private String isSubscribe;

  public WxTxInfo(Map<String, String> wxTxResp) {
    this.deviceInfo = wxTxResp.get("device_info");
    this.openid = wxTxResp.get("openid");
    this.isSubscribe = wxTxResp.get("is_subscribe");
  }
}
