package com.github.superkoh.paysdk.wechat;

import com.github.superkoh.paysdk.common.PayException;
import com.github.superkoh.paysdk.common.PayService;
import com.github.superkoh.paysdk.common.PrepayInfo;
import com.github.superkoh.paysdk.common.TxInfo;
import com.github.superkoh.paysdk.common.param.TxCloseParam;
import com.github.superkoh.paysdk.common.param.TxPrepayParam;
import com.github.superkoh.paysdk.common.param.TxQueryParam;
import com.github.superkoh.paysdk.common.wx.WxPrepayInfo;
import com.github.superkoh.paysdk.utils.ConstraintViolationUtils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class WxPayService implements PayService {

  private String appId;
  private String mchId;
  private String secretKey;
  private String notifyUrl;

  private WXPay wxPay;

  private class MyWxPayConfig implements WXPayConfig {

    private String appId;
    private String mchId;
    private String secretKey;
    private byte[] certData;

    public MyWxPayConfig(String appId, String mchId, String secretKey, String certPath) {
      this.appId = appId;
      this.mchId = mchId;
      this.secretKey = secretKey;
      try {
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
      } catch (Exception e) {

      }
    }

    @Override
    public String getAppID() {
      return appId;
    }

    @Override
    public String getMchID() {
      return mchId;
    }

    @Override
    public String getKey() {
      return secretKey;
    }

    @Override
    public InputStream getCertStream() {
      return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
      return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
      return 10000;
    }
  }

  public WxPayService(String appId, String mchId, String secretKey, String notifyUrl) {
    this.appId = appId;
    this.mchId = mchId;
    this.secretKey = secretKey;
    this.notifyUrl = notifyUrl;

    wxPay = new WXPay(new MyWxPayConfig(appId, mchId, secretKey, ""));
  }

  @Override
  public PrepayInfo prepay(TxPrepayParam prepayParam, String tradeType) throws PayException {
    if (null == prepayParam) {
      throw new IllegalArgumentException("prepayParam is null");
    }
    ConstraintViolationUtils.validate(prepayParam);
    if (null == tradeType) {
      throw new IllegalArgumentException("illegal trade type");
    }
    if (null == prepayParam.getWxOpenId()) {
      throw new IllegalArgumentException("illegal wechat openid");
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    Map<String, String> data = new HashMap<>();
    data.put("body", prepayParam.getProductDescription());
    data.put("out_trade_no", prepayParam.getOrderId());
    data.put("total_fee", prepayParam.getTotalFee().toString());
    data.put("spbill_create_ip", prepayParam.getUserIp());
    data.put("notify_url", notifyUrl);
    data.put("trade_type", tradeType);
    data.put("openid", prepayParam.getWxOpenId());
    data.put("time_start",
        prepayParam.getOrderCreateTime().atZone(ZoneId.of("Asia/Shanghai")).format(formatter));
    data.put("time_expire",
        prepayParam.getOrderExpireTime().atZone(ZoneId.of("Asia/Shanghai")).format(formatter));
    if (null != prepayParam.getExtra()) {
      data.put("attach", prepayParam.getExtra());
    }
    if (null != prepayParam.getProductId()) {
      data.put("product_id", prepayParam.getProductId());
    }

    Map<String, String> resp;
    try {
      resp = wxPay.unifiedOrder(data);
    } catch (Exception e) {
      throw new PayException(-1, e.getMessage());
    }
    processResponse(resp);

    PrepayInfo prepayInfo = new PrepayInfo();
    WxPrepayInfo wxPrepayInfo = new WxPrepayInfo(resp.get("prepay_id"), appId, secretKey);
    prepayInfo.setWxInfo(wxPrepayInfo);
    return prepayInfo;
  }

  @Override
  public TxInfo query(TxQueryParam queryParam) throws PayException {
    Map<String, String> data = new HashMap<>();
    if (null != queryParam.getTransactionId()) {
      data.put("transaction_id", queryParam.getTransactionId());
    } else if (null != queryParam.getOrderId()) {
      data.put("out_trade_no", queryParam.getOrderId());
    } else {
      throw new IllegalArgumentException("illegal query param");
    }

    Map<String, String> resp;
    try {
      resp = wxPay.orderQuery(data);
    } catch (Exception e) {
      throw new PayException(-1, e.getMessage());
    }
    processResponse(resp);

    return TxInfo.wxTxInfo(resp);
  }

  @Override
  public void close(TxCloseParam closeParam) throws PayException {
    Map<String, String> data = new HashMap<>();
    data.put("out_trade_no", closeParam.getOrderId());

    Map<String, String> resp;
    try {
      resp = wxPay.orderQuery(data);
    } catch (Exception e) {
      throw new PayException(-1, e.getMessage());
    }
    processResponse(resp);
  }

  @Override
  public TxInfo fromNotify(String body) throws PayException {
    try {
      Map<String, String> txMap = WXPayUtil.xmlToMap(body);
      return TxInfo.wxTxInfo(txMap);
    } catch (Exception e) {
      throw new WxPayException(-1, e.getMessage());
    }
  }

  private void processResponse(Map<String, String> resp) throws WxPayException {
    if (!"SUCCESS".equals(resp.getOrDefault("return_code", "FAIL"))) {
      throw new WxPayException(-1, resp.getOrDefault("return_msg", "未知错误！"));
    }
    if (!"SUCCESS".equals(resp.getOrDefault("result_code", "FAIL"))) {
      throw new WxPayException(-1, resp.getOrDefault("err_code", "RESULTERROR"),
          resp.getOrDefault("err_code_des", "未知错误！"));
    }
  }
}
