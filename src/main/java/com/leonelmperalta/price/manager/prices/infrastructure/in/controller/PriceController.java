package com.leonelmperalta.price.manager.prices.infrastructure.in.controller;

import com.leonelmperalta.price.manager.prices.application.exception.PriceConfigurationErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.NotDataFoundException;
import com.leonelmperalta.price.manager.prices.application.service.PriceQueryService;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.infrastructure.in.constants.ControllerConstants;
import com.leonelmperalta.price.manager.prices.infrastructure.in.constants.ValidationConstants;
import com.leonelmperalta.price.manager.prices.infrastructure.in.dto.PriceQueryResponseDTO;
import com.leonelmperalta.price.manager.prices.infrastructure.in.mapper.PriceQueryMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerConstants.PRICE_CONTROLLER_BASE_PATH)
@Validated
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
            @Valid
            @RequestParam(name = ControllerConstants.BRAND_ID_QUERY_PARAM)
            @NotNull(message = ValidationConstants.BRAND_ID_NOT_NULL)
            @Positive(message = ValidationConstants.BRAND_ID_POSITIVE) Long brandId,
            @Valid
            @RequestParam(name = ControllerConstants.PRODUCT_ID_QUERY_PARAM)
            @NotNull(message = ValidationConstants.PRODUCT_ID_NOT_NULL)
            @Positive(message = ValidationConstants.PRODUCT_ID_POSITIVE) Long productId,
            @Valid
            @RequestParam(name = ControllerConstants.APPLICATION_DATE_QUERY_PARAM)
            @NotBlank(message = ValidationConstants.APPLICATION_DATE_NOT_BLANK)
            @Pattern(regexp = ValidationConstants.ISO_8601_FORMAT_REGEXP,
                    message = ValidationConstants.APPLICATION_DATE_ISO_8601)
            String applicationDate)
            throws InternalServerErrorException, NotDataFoundException, PriceConfigurationErrorException {
        PriceQuery priceQuery = this.priceQueryService.priceQuery(brandId, productId, applicationDate);
        return ResponseEntity.ok(this.priceQueryMapper.toPriceQueryResponse(priceQuery));
    }
}
