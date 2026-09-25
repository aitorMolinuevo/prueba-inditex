package com.inditex.pricing.domain.port.out;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.PriceQuery;

import java.util.Optional;

public interface PriceRepositoryPort {
    Optional<Price> findApplicablePrice(PriceQuery query);
}
