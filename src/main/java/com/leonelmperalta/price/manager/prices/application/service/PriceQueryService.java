package com.leonelmperalta.price.manager.prices.application.service;

import com.leonelmperalta.price.manager.prices.infrastructure.in.dto.PriceQueryResponseDTO;

public interface PriceQueryService {
    PriceQueryResponseDTO priceQuery(Long brandId, Long productId, String applicationDate);
}
