package com.inditex.pricing.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class PriceQuery {
    LocalDateTime applicationDate;
    Long productId;
    Integer brandId;
}
