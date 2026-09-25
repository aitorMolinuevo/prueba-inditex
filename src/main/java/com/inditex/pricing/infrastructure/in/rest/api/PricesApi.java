package com.inditex.pricing.infrastructure.in.rest.api;

import com.inditex.pricing.infrastructure.in.rest.model.PriceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@RequestMapping("/prices")
@Tag(name = "Price", description = "the Price API")
public interface PricesApi {

    @Operation(summary = "Get applicable price", description = "Returns the applicable price for a given product and brand at a specific date.", tags={ "Price" })
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid parameters")
    @ApiResponse(responseCode = "404", description = "Not Found - No price found for the given criteria")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping
    ResponseEntity<PriceResponse> getApplicablePrice(
        @Parameter(description = "The date and time to check the price for", required = true)
        @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,

        @Parameter(description = "The product identifier", required = true)
        @RequestParam("productId") Long productId,

        @Parameter(description = "The brand identifier", required = true)
        @RequestParam("brandId") Integer brandId
    );
}
