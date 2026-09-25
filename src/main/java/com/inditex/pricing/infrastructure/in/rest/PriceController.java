package com.inditex.pricing.infrastructure.in.rest;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.PriceQuery;
import com.inditex.pricing.domain.port.in.GetApplicablePriceUseCase;
import com.inditex.pricing.infrastructure.in.rest.api.PricesApi;
import com.inditex.pricing.infrastructure.in.rest.model.PriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PriceController implements PricesApi {

    private final GetApplicablePriceUseCase useCase;

    @Override
    public ResponseEntity<PriceResponse> getApplicablePrice(LocalDateTime applicationDate, Long productId, Integer brandId) {
        PriceQuery query = PriceQuery.builder()
                .applicationDate(applicationDate)
                .productId(productId)
                .brandId(brandId)
                .build();

        Price price = useCase.getApplicablePrice(query);
        PriceResponse response = mapToResponse(price);

        return ResponseEntity.ok(response);
    }

    private PriceResponse mapToResponse(Price price) {
        PriceResponse response = new PriceResponse();
        response.setProductId(price.getProductId());
        response.setBrandId(price.getBrandId());
        response.setPriceList(price.getPriceList());
        response.setStartDate(price.getStartDate());
        response.setEndDate(price.getEndDate());
        response.setPrice(price.getFinalPrice().doubleValue());
        return response;
    }
}
