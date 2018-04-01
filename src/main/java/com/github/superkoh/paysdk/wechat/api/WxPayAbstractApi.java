package com.github.superkoh.paysdk.wechat.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.superkoh.paysdk.wechat.common.BasePayReq;
import com.github.superkoh.paysdk.wechat.common.BasePayRes;
import com.github.superkoh.paysdk.wechat.common.WxPayException;
import com.github.superkoh.paysdk.wechat.common.WxSignUtils;
import com.github.superkoh.paysdk.wechat.common.WxXmlUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class WxPayAbstractApi {

  protected static final Logger logger = LoggerFactory.getLogger("WxPayApi");

  protected static final OkHttpClient httpClient;

  static {
    httpClient = new OkHttpClient();
  }

  private String appId;
  private String mchId;
  private String secretKey;

  public WxPayAbstractApi(String appId, String mchId, String secretKey) {
    this.appId = appId;
    this.mchId = mchId;
    this.secretKey = secretKey;
  }

  protected <T extends BasePayRes> T post(Request.Builder request, BasePayReq reqBody,
      Class<T> clazz) throws WxPayException {
    try {
      reqBody.setAppid(appId);
      reqBody.setMchId(mchId);
      reqBody.genSign(secretKey);
      String xml = WxXmlUtils.toXml(reqBody);
      Request req = request
          .post(RequestBody.create(MediaType.parse("application/xml;charset=UTF-8"), xml))
          .build();
      logger.debug("POST: {}, DATA: {}", req.url(), xml);
      Response response = httpClient.newCall(req).execute();
      String resStr = Objects.requireNonNull(response.body()).string();
      TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
      };
      logger.debug("RES: {}", resStr);
      Map<String, Object> resMap = WxXmlUtils.fromXml(resStr, typeRef);
      if (!"SUCCESS".equals(resMap.getOrDefault("return_code", "FAIL"))) {
        throw new WxPayException(-1, (String) resMap.getOrDefault("return_msg", "未知错误！"));
      }
      if (!"SUCCESS".equals(resMap.getOrDefault("result_code", "FAIL"))) {
        throw new WxPayException(-1, (String) resMap.getOrDefault("err_code", "RESULTERROR"),
            (String) resMap.getOrDefault("err_code_des", "未知错误！"));
      }
      String sign = WxSignUtils.genMd5Sign(resMap, secretKey);
      if (!sign.equals(resMap.get("sign"))) {
        throw new WxPayException(-1, "微信返回签名不匹配");
      }
      return WxXmlUtils.fromXml(resStr, clazz);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
      throw new WxPayException(-1, e.getMessage());
    }
  }
}
