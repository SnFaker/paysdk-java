package com.github.superkoh.paysdk.common;

import com.github.superkoh.paysdk.common.wx.WxTxInfo;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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

  private TxInfo() {
  }

  public static TxInfo wxTxInfo(Map<String, String> wxTxResp) {
    TxInfo txInfo = new TxInfo();
    txInfo.setOrderId(wxTxResp.get("out_trade_no"));
    txInfo.setTxId(wxTxResp.get("transaction_id"));
    txInfo.setTxStateStr(wxTxResp.get("trade_state"));
    txInfo.setTxStateDesc(wxTxResp.get("trade_state_desc"));
    txInfo.setTxState(TxState.fromWxState(wxTxResp.get("trade_state")));
    txInfo.setBankType(wxTxResp.get("bank_type"));
    txInfo.setTotalFee(Long.valueOf(wxTxResp.getOrDefault("total_fee", "0")));
    txInfo.setFeeType(wxTxResp.get("fee_type"));
    txInfo.setPaidFee(Long.valueOf(wxTxResp.getOrDefault("cash_fee", "0")));
    txInfo.setPaidFeeType(wxTxResp.get("cash_fee_type"));
    txInfo.setCouponFee(Long.valueOf(wxTxResp.getOrDefault("coupon_fee", "0")));
    txInfo.setExtra(wxTxResp.get("attach"));
    if (null != wxTxResp.get("time_end")) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
      txInfo.setTxEndTime(LocalDateTime.parse(wxTxResp.get("time_end"), formatter)
          .atZone(ZoneId.of("Asia/Shanghai")).toInstant());
    }
    txInfo.setWxInfo(new WxTxInfo(wxTxResp));
    return txInfo;
  }
}
