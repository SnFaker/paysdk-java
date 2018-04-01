package com.github.superkoh.paysdk.common.wx;

import com.github.superkoh.paysdk.wechat.api.res.WxTxRes;
import lombok.Data;

@Data
public class WxTxInfo {

  private String deviceInfo;
  private String openid;
  private String isSubscribe;

  public WxTxInfo(WxTxRes txRes) {
    this.deviceInfo = txRes.getDeviceInfo();
    this.openid = txRes.getOpenid();
    this.isSubscribe = txRes.getIsSubscribe();
  }
}
