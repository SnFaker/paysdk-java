package com.github.superkoh.paysdk.wechat.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "xml")
public class BasePayRes {

  @JacksonXmlCData
  private String returnCode;
  @JacksonXmlCData
  private String returnMsg;
  @JacksonXmlCData
  private String appid;
  @JacksonXmlCData
  private String mchId;
  @JacksonXmlCData
  private String sign;
  @JacksonXmlCData
  private String nonceStr;
  @JacksonXmlCData
  private String resultCode;
  @JacksonXmlCData
  private String resultMsg;
  @JacksonXmlCData
  private String errCode;
  @JacksonXmlCData
  private String errCodeDes;
}
