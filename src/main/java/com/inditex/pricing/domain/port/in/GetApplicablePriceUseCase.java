package com.inditex.pricing.domain.port.in;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.PriceQuery;

public interface GetApplicablePriceUseCase {
    Price getApplicablePrice(PriceQuery query);
}
