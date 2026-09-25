package com.inditex.pricing.infrastructure.in.rest.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PriceResponse {
    private Long productId;
    private Integer brandId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double price;
}
