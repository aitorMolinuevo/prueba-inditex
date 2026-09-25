package com.inditex.pricing.application.service;

import com.inditex.pricing.domain.exception.PriceNotFoundException;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.PriceQuery;
import com.inditex.pricing.domain.port.out.PriceRepositoryPort;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    @Mock
    private PriceRepositoryPort priceRepository;

    @Mock
    private MeterRegistry meterRegistry;

    @Mock
    private Counter counter;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(meterRegistry.counter(anyString())).thenReturn(counter);
    }

    @Test
    void test1_singleApplicablePrice_shouldReturnIt() {
        PriceQuery query = PriceQuery.builder().productId(35455L).brandId(1).applicationDate(LocalDateTime.now()).build();
        Price expectedPrice = Price.builder().priceList(1).finalPrice(BigDecimal.valueOf(35.50)).build();

        when(priceRepository.findApplicablePrice(query)).thenReturn(Optional.of(expectedPrice));

        Price result = priceService.getApplicablePrice(query);

        assertEquals(expectedPrice, result);
        verify(meterRegistry).counter("pricing.searches.success");
        verify(counter).increment();
    }

    @Test
    void test2_multipleApplicablePrices_repositoryReturnsHighestPriority() {
        PriceQuery query = PriceQuery.builder().productId(35455L).brandId(1).applicationDate(LocalDateTime.now()).build();
        Price expectedPrice = Price.builder().priceList(2).priority(1).finalPrice(BigDecimal.valueOf(25.45)).build();

        // The logic for filtering and sorting by priority is delegated to the Persistence Adapter / DB query
        when(priceRepository.findApplicablePrice(query)).thenReturn(Optional.of(expectedPrice));

        Price result = priceService.getApplicablePrice(query);

        assertEquals(expectedPrice, result);
        verify(meterRegistry).counter("pricing.searches.success");
        verify(counter).increment();
    }

    @Test
    void test3_noApplicablePrice_shouldThrowException() {
        PriceQuery query = PriceQuery.builder().productId(35455L).brandId(1).applicationDate(LocalDateTime.now()).build();

        when(priceRepository.findApplicablePrice(query)).thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class, () -> priceService.getApplicablePrice(query));
        verify(meterRegistry).counter("pricing.searches.not_found");
        verify(counter).increment();
    }
}
