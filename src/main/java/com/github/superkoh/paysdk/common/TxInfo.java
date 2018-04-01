package com.github.superkoh.paysdk.common;

import com.github.superkoh.paysdk.common.wx.WxTxInfo;
import com.github.superkoh.paysdk.wechat.api.res.WxTxRes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class TxInfo {

  /**
   * 内部订单标识
   */
  private String orderId;
  /**
   * 交易流水ID
   */
  private String txId;
  /**
   * 交易类型
   */
  private String txType;
  /**
   * 交易状态
   */
  private TxState txState;
  /**
   * 第三方交易流水状态标识
   */
  private String txStateStr;
  /**
   * 第三方交易流水状态释义
   */
  private String txStateDesc;
  /**
   * 付款银行
   */
  private String bankType;
  /**
   * 交易金额
   */
  private Long totalFee;
  /**
   * 币种
   */
  private String feeType;
  /**
   * 支付金额
   */
  private Long paidFee;
  /**
   * 支付金额币种
   */
  private String paidFeeType;
  /**
   * 优惠金额
   */
  private Long couponFee;
  /**
   * 附加数据
   */
  private String extra;
  /**
   * 支付时间
   */
  private Instant txEndTime;

  /**
   * 微信独有信息
   */
  private WxTxInfo wxInfo;

  private TxInfo(WxTxRes txRes) {
    this.orderId = txRes.getOutTradeNo();
    this.txId = txRes.getTransactionId();
    this.txType = txRes.getTradeType();
    this.txStateStr = txRes.getTradeState();
    this.txStateDesc = txRes.getTradeStateDesc();
    this.txState = TxState.fromWxState(txRes.getTradeState());
    this.bankType = txRes.getBankType();
    this.totalFee = txRes.getTotalFee();
    this.feeType = txRes.getFeeType();
    this.paidFee = txRes.getCashFee();
    this.paidFeeType = txRes.getCashFeeType();
    this.couponFee = txRes.getCouponFee();
    this.extra = txRes.getAttach();
    if (null != txRes.getTimeEnd()) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
      this.txEndTime = LocalDateTime.parse(txRes.getTimeEnd(), formatter)
          .atZone(ZoneId.of("Asia/Shanghai")).toInstant();
    }
    this.wxInfo = new WxTxInfo(txRes);
  }

  public static TxInfo wxTxInfo(WxTxRes txRes) {
    return new TxInfo(txRes);
  }
}
