package com.leonelmperalta.price.manager.prices.domain.repository;

import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceQueryRepository {
    List<PriceQuery> findByProductIdAndBrandIdAndApplicationDatesBetween(
            Long productId, Long brandId, LocalDateTime applicationDate
    );
}
