package com.inditex.pricing.infrastructure.out.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query(value = "SELECT p FROM PriceEntity p WHERE p.brandId = :brandId AND p.productId = :productId " +
            "AND :applicationDate BETWEEN p.startDate AND p.endDate " +
            "ORDER BY p.priority DESC LIMIT 1")
    Optional<PriceEntity> findApplicablePrice(
            @Param("brandId") Integer brandId,
            @Param("productId") Long productId,
            @Param("applicationDate") LocalDateTime applicationDate);
}
