package com.github.superkoh.paysdk.wechat.common;

import java.util.Comparator;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;

public class WxSignUtils {

  public static String genMd5Sign(Map<String, Object> params, String key) {
    StringBuilder sb = new StringBuilder();
    params.keySet().stream()
        .sorted(Comparator.naturalOrder())
        .forEachOrdered(k -> {
          if (k.equals("sign")) {
            return;
          }
          Object value = params.get(k);
          if (null == value) {
            return;
          }
          String strValue = String.valueOf(value);
          if (strValue.isEmpty()) {
            return;
          }
          sb.append("&").append(k).append("=").append(strValue);
        });
    sb.append("&key=").append(key);
    System.out.println(sb.toString());
    return DigestUtils.md5Hex(sb.substring(1)).toUpperCase();
  }
}
