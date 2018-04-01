package com.github.superkoh.paysdk.common.param;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxPrepayParam {

  /**
   * 通用字段
   */

  private String productDescription;
  private String extra;
  private String orderId;
  private Long totalFee;
  private String userIp;
  private Instant orderCreateTime;
  private Instant orderExpireTime;
  private String productId;


  /**
   * 微信特有字段
   */

  private String wxTradeType;
  private String wxOpenId;

}
