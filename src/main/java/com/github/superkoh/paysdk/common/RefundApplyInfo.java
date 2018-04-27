package com.github.superkoh.paysdk.common;

import java.util.Map;
import lombok.Data;

@Data
public class RefundApplyInfo {

  private String txId;
  private String orderId;
  private String refundOrderId;
  private String refundId;
  private Long refundFee;
  private Long totalFee;
  private String feeType;
  private Long paidFee;
  private String paidFeeType;
  private Long paidRefundFee;
  private Long couponRefundFee;

  private RefundApplyInfo() {
  }

  public static RefundApplyInfo wxRefundApplyInfo(Map<String, String> wxRefundResp) {
    RefundApplyInfo refundApplyInfo = new RefundApplyInfo();
    refundApplyInfo.setOrderId(wxRefundResp.get("out_trade_no"));
    refundApplyInfo.setTxId(wxRefundResp.get("transaction_id"));
    refundApplyInfo.setRefundOrderId(wxRefundResp.get("out_refund_no"));
    refundApplyInfo.setRefundId(wxRefundResp.get("refund_id"));
    refundApplyInfo.setRefundFee(Long.valueOf(wxRefundResp.getOrDefault("refund_fee", "0")));
    refundApplyInfo.setTotalFee(Long.valueOf(wxRefundResp.getOrDefault("total_fee", "0")));
    refundApplyInfo.setFeeType(wxRefundResp.get("fee_type"));
    refundApplyInfo.setPaidFee(Long.valueOf(wxRefundResp.getOrDefault("cash_fee", "0")));
    refundApplyInfo.setPaidFeeType(wxRefundResp.get("cash_fee_type"));
    refundApplyInfo.setPaidRefundFee(Long.valueOf(wxRefundResp.getOrDefault("cash_refund_fee", "0")));
    refundApplyInfo
        .setCouponRefundFee(Long.valueOf(wxRefundResp.getOrDefault("coupon_refund_fee", "0")));
    return refundApplyInfo;
  }
}
