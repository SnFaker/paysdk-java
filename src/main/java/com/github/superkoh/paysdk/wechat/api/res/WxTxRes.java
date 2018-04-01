package com.github.superkoh.paysdk.wechat.api.res;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.github.superkoh.paysdk.wechat.common.BasePayRes;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WxTxRes extends BasePayRes {

  @JacksonXmlCData
  private String deviceInfo;
  @JacksonXmlCData
  private String openid;
  @JacksonXmlCData
  private String isSubscribe;
  @JacksonXmlCData
  private String tradeType;
  @JacksonXmlCData
  private String tradeState;
  @JacksonXmlCData
  private String bankType;
  private Long totalFee;
  private Long settlementTotalFee;
  @JacksonXmlCData
  private String feeType;
  private Long cashFee;
  @JacksonXmlCData
  private String cashFeeType;

  @JacksonXmlCData
  private String transactionId;
  @JacksonXmlCData
  private String outTradeNo;
  @JacksonXmlCData
  private String attach;
  @JacksonXmlCData
  private String timeEnd;
  @JacksonXmlCData
  private String tradeStateDesc;

  private Long couponFee;
//  @JacksonXmlCData
//  private Integer couponCount;

//  代金券类型	coupon_type_$n	否	String	CASH
//  CASH--充值代金券
//  NO_CASH---非充值优惠券
//
//  开通免充值券功能，并且订单使用了优惠券后有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_$0
//
//  代金券ID	coupon_id_$n	否	String(20)	10000 	代金券ID, $n为下标，从0开始编号
//  单个代金券支付金额	coupon_fee_$n	否	Int	100	单个代金券支付金额, $n为下标，从0开始编号
}
