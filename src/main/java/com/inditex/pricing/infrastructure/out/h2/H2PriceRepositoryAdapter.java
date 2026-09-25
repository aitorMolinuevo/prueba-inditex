package com.inditex.pricing.infrastructure.out.h2;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.PriceQuery;
import com.inditex.pricing.domain.port.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class H2PriceRepositoryAdapter implements PriceRepositoryPort {

    private final SpringDataPriceRepository repository;
    private final PriceMapper mapper;

    @Override
    public Optional<Price> findApplicablePrice(PriceQuery query) {
        return repository.findApplicablePrice(
                query.getBrandId(),
                query.getProductId(),
                query.getApplicationDate()
        ).map(mapper::toDomain);
    }
}
