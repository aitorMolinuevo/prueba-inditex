package com.inditex.pricing.infrastructure.out.h2;

import com.inditex.pricing.domain.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(target = "finalPrice", source = "price")
    Price toDomain(PriceEntity entity);
}
