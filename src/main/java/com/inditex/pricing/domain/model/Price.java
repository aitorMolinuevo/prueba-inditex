package com.inditex.pricing.domain.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class Price {
    Long productId;
    Integer brandId;
    Integer priceList;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer priority;
    BigDecimal finalPrice;
    String currency;
}
