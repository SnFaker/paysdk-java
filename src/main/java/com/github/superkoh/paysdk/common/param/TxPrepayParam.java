package com.github.superkoh.paysdk.common.param;

import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxPrepayParam {

  /**
   * 通用字段
   */

  @NotBlank
  private String productDescription;
  private String extra;
  @NotBlank
  private String orderId;
  @NotNull
  @Positive
  private Long totalFee;
  @NotBlank
  private String userIp;
  @NotNull
  private Instant orderCreateTime;
  @NotNull
  private Instant orderExpireTime;
  private String productId;


  /**
   * 微信特有字段
   */
  private String wxOpenId;

}
