package com.inditex.pricing.application.service;

import com.inditex.pricing.domain.exception.PriceNotFoundException;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.PriceQuery;
import com.inditex.pricing.domain.port.in.GetApplicablePriceUseCase;
import com.inditex.pricing.domain.port.out.PriceRepositoryPort;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceService implements GetApplicablePriceUseCase {

    private final PriceRepositoryPort priceRepository;
    private final MeterRegistry meterRegistry;

    @Override
    public Price getApplicablePrice(PriceQuery query) {
        return priceRepository.findApplicablePrice(query)
                .map(price -> {
                    meterRegistry.counter("pricing.searches.success").increment();
                    return price;
                })
                .orElseThrow(() -> {
                    meterRegistry.counter("pricing.searches.not_found").increment();
                    return new PriceNotFoundException(
                        String.format("No applicable price found for product %d and brand %d at %s",
                                query.getProductId(), query.getBrandId(), query.getApplicationDate())
                    );
                });
    }
}
