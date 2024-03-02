package com.leonelmperalta.price.manager.prices.infrastructure.in.controller;

import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.NotDataFoundException;
import com.leonelmperalta.price.manager.prices.application.service.PriceQueryService;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.infrastructure.in.constants.ControllerConstants;
import com.leonelmperalta.price.manager.prices.infrastructure.in.dto.PriceQueryResponseDTO;
import com.leonelmperalta.price.manager.prices.infrastructure.in.mapper.PriceQueryMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerConstants.PRICE_CONTROLLER_BASE_PATH)
public class PriceController {

    private final PriceQueryService priceQueryService;
    private final PriceQueryMapper priceQueryMapper;

    public PriceController(PriceQueryService priceQueryService,
                           PriceQueryMapper priceQueryMapper) {
        this.priceQueryService = priceQueryService;
        this.priceQueryMapper = priceQueryMapper;
    }

    @GetMapping
    public ResponseEntity<PriceQueryResponseDTO> priceQuery(
            @RequestParam(name = ControllerConstants.BRAND_ID_QUERY_PARAM) Long brandId,
            @RequestParam(name = ControllerConstants.PRODUCT_ID_QUERY_PARAM) Long productId,
            @RequestParam(name = ControllerConstants.APPLICATION_DATE_QUERY_PARAM) String applicationDate)
            throws InternalServerErrorException, NotDataFoundException {
        PriceQuery priceQuery = this.priceQueryService.priceQuery(brandId, productId, applicationDate);
        return ResponseEntity.ok(this.priceQueryMapper.toPriceQueryResponse(priceQuery));
    }
}
