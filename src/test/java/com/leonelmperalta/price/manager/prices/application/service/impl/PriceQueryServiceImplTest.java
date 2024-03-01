package com.leonelmperalta.price.manager.prices.application.service.impl;

import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.NotDataFoundException;
import com.leonelmperalta.price.manager.prices.application.mapper.DateConverter;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.domain.repository.PriceQueryRepository;
import com.leonelmperalta.price.manager.prices.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = {PriceQueryServiceImpl.class})
class PriceQueryServiceImplTest {

    @MockBean
    private PriceQueryRepository priceQueryRepository;

    @MockBean
    private DateConverter dateConverter;

    @Autowired
    @InjectMocks
    private PriceQueryServiceImpl priceQueryService;

    @Test
    public void givenValidRequest_whenPriceQuery_thenReturnFirstMatchingPrice()
            throws InternalServerErrorException, NotDataFoundException {
        Long brandId = 1L;
        Long productId = 35455L;
        String applicationDate = "2020-06-15T18:00:00Z";
        LocalDateTime applicationDateAsDate = LocalDateTime.of(
                2020, 6, 15, 18, 0, 0
        );
        List<PriceQuery> priceQueries = TestUtils.singlePriceQuery();

        Mockito.when(this.dateConverter.toLocalDateTime(eq(applicationDate))).thenReturn(applicationDateAsDate);

        Mockito.when(this.priceQueryRepository.findByProductIdAndBrandIdAndApplicationDatesBetween(
                eq(brandId), eq(productId), eq(applicationDateAsDate)
        )).thenReturn(priceQueries);

        PriceQuery result = this.priceQueryService.priceQuery(brandId, productId, applicationDate);

        assertNotNull(result);
        assertEquals(priceQueries.get(0), result);
    }

    @Test
    public void givenValidRequest_whenPriceQuery_thenReturnHighestPriorityPrice()
            throws InternalServerErrorException, NotDataFoundException {
        Long brandId = 1L;
        Long productId = 35455L;
        String applicationDate = "2020-06-14T16:00:00Z";
        LocalDateTime applicationDateAsDate = LocalDateTime.of(
                2020, 6, 14, 16, 0, 0
        );
        List<PriceQuery> priceQueries = TestUtils.multiplePriceQuery();

        Mockito.when(this.dateConverter.toLocalDateTime(eq(applicationDate))).thenReturn(applicationDateAsDate);

        Mockito.when(this.priceQueryRepository.findByProductIdAndBrandIdAndApplicationDatesBetween(
                eq(brandId), eq(productId), eq(applicationDateAsDate)
        )).thenReturn(priceQueries);

        PriceQuery result = this.priceQueryService.priceQuery(brandId, productId, applicationDate);

        assertNotNull(result);
        assertEquals(2, result.getFeeId());
        assertEquals(BigDecimal.valueOf(25.45), result.getFinalPrice());
    }

    @Test
    public void givenInvalidArguments_whenPriceQuery_thenReturnNotDataFound()
            throws InternalServerErrorException {
        Long brandId = 1L;
        Long productId = 35455L;
        String applicationDate = "2020-06-14T16:00:00Z";
        LocalDateTime applicationDateAsDate = LocalDateTime.of(
                2020, 6, 14, 16, 0, 0
        );
        List<PriceQuery> priceQueries = new ArrayList<>();

        Mockito.when(this.dateConverter.toLocalDateTime(eq(applicationDate))).thenReturn(applicationDateAsDate);

        Mockito.when(this.priceQueryRepository.findByProductIdAndBrandIdAndApplicationDatesBetween(
                eq(brandId), eq(productId), eq(applicationDateAsDate)
        )).thenReturn(priceQueries);

        assertThrows(NotDataFoundException.class, () -> this.priceQueryService.priceQuery(brandId, productId, applicationDate));
    }

}