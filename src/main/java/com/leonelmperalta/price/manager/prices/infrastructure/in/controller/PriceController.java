package com.leonelmperalta.price.manager.prices.infrastructure.in.controller;

import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.NotDataFoundException;
import com.leonelmperalta.price.manager.prices.application.service.PriceQueryService;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.infrastructure.in.dto.PriceQueryResponseDTO;
import com.leonelmperalta.price.manager.prices.infrastructure.in.mapper.PriceQueryMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    private final PriceQueryService priceQueryService;
    private final PriceQueryMapper priceQueryMapper;

    public PriceController(PriceQueryService priceQueryService,
                           PriceQueryMapper priceQueryMapper) {
        this.priceQueryService = priceQueryService;
        this.priceQueryMapper = priceQueryMapper;
    }

    public PriceQueryResponseDTO priceQuery(Long brandId, Long productId, String applicationDate)
            throws InternalServerErrorException, NotDataFoundException {
        PriceQuery priceQuery = this.priceQueryService.priceQuery(brandId, productId, applicationDate);
        return this.priceQueryMapper.toPriceQueryResponse(priceQuery);
    }
}
