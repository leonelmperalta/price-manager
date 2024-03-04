package com.leonelmperalta.price.manager.prices.application.service;

import com.leonelmperalta.price.manager.prices.application.exception.PriceConfigurationErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.NotDataFoundException;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;

public interface PriceQueryService {
    PriceQuery priceQuery(Long brandId, Long productId, String applicationDate) throws InternalServerErrorException, NotDataFoundException, PriceConfigurationErrorException;
}
