package com.github.superkoh.paysdk.common;

import java.util.Map;
import lombok.Data;

@Data
public class RefundApplyResult {

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

  private RefundApplyResult() {
  }

  public static RefundApplyResult wxRefundApplyInfo(Map<String, String> wxRefundResp) {
    RefundApplyResult refundApplyResult = new RefundApplyResult();
    refundApplyResult.setOrderId(wxRefundResp.get("out_trade_no"));
    refundApplyResult.setTxId(wxRefundResp.get("transaction_id"));
    refundApplyResult.setRefundOrderId(wxRefundResp.get("out_refund_no"));
    refundApplyResult.setRefundId(wxRefundResp.get("refund_id"));
    refundApplyResult.setRefundFee(Long.valueOf(wxRefundResp.getOrDefault("refund_fee", "0")));
    refundApplyResult.setTotalFee(Long.valueOf(wxRefundResp.getOrDefault("total_fee", "0")));
    refundApplyResult.setFeeType(wxRefundResp.get("fee_type"));
    refundApplyResult.setPaidFee(Long.valueOf(wxRefundResp.getOrDefault("cash_fee", "0")));
    refundApplyResult.setPaidFeeType(wxRefundResp.get("cash_fee_type"));
    refundApplyResult.setPaidRefundFee(Long.valueOf(wxRefundResp.getOrDefault("cash_refund_fee", "0")));
    refundApplyResult
        .setCouponRefundFee(Long.valueOf(wxRefundResp.getOrDefault("coupon_refund_fee", "0")));
    return refundApplyResult;
  }
}
