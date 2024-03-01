package com.leonelmperalta.price.manager.prices.application.service.impl;

import com.leonelmperalta.price.manager.prices.application.service.PriceQueryService;
import com.leonelmperalta.price.manager.prices.infrastructure.in.dto.PriceQueryResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class PriceQueryServiceImpl implements PriceQueryService {

    @Override
    public PriceQueryResponseDTO priceQuery(Long brandId, Long productId, String applicationDate) {
        return null;
    }
}
