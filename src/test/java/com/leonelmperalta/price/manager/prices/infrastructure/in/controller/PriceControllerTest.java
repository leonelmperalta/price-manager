package com.leonelmperalta.price.manager.prices.infrastructure.in.controller;

import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.NotDataFoundException;
import com.leonelmperalta.price.manager.prices.application.exception.PriceConfigurationErrorException;
import com.leonelmperalta.price.manager.prices.application.service.PriceQueryService;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.infrastructure.in.dto.PriceQueryResponseDTO;
import com.leonelmperalta.price.manager.prices.infrastructure.in.mapper.PriceQueryMapper;
import com.leonelmperalta.price.manager.prices.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class PriceControllerTest {

    @MockBean
    private PriceQueryService priceQueryService;

    @MockBean
    private PriceQueryMapper priceQueryMapper;

    @Autowired
    private PriceController priceController;

    @Test
    public void givenValidRequest_whenPriceQuery_thenReturnPriceResponse()
            throws InternalServerErrorException, NotDataFoundException, PriceConfigurationErrorException {
        Long brandId = 1L;
        Long productId = 35455L;
        String applicationDate = "2020-06-15T18:00:00";
        PriceQuery priceQuery = TestUtils.singlePriceQuery().get(0);

        Mockito.when(this.priceQueryService.priceQuery(eq(brandId), eq(productId), eq(applicationDate)))
                .thenReturn(priceQuery);

        Mockito.when(this.priceQueryMapper.toPriceQueryResponse(priceQuery))
                .thenReturn(TestUtils.getPriceQueryResponse());

        ResponseEntity<PriceQueryResponseDTO> response = this.priceController.priceQuery(brandId, productId, applicationDate);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(1L, Objects.requireNonNull(response.getBody()).getBrandId());
        assertEquals(4L, response.getBody().getFeeId());
    }

}