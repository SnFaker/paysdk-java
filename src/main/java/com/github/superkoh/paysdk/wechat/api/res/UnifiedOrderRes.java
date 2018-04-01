package com.github.superkoh.paysdk.wechat.api.res;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.github.superkoh.paysdk.wechat.common.BasePayRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UnifiedOrderRes extends BasePayRes {

  @JacksonXmlCData
  private String deviceInfo;
  @JacksonXmlCData
  private String tradeType;
  @JacksonXmlCData
  private String prepayId;
  @JacksonXmlCData
  private String codeUrl;
}
