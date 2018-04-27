package com.github.superkoh.paysdk.common.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundParam {

  private String transactionId;
  private String orderId;
  @NotBlank
  private String refundOrderId;
  @NotNull
  @Positive
  private Long totalFee;
  @NotNull
  @Positive
  private Long refundFee;
  private String refundReason;
}
