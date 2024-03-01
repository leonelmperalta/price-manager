package com.leonelmperalta.price.manager.prices.application.service.impl;

import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.NotDataFoundException;
import com.leonelmperalta.price.manager.prices.application.mapper.PriceQueryServiceMapper;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.domain.repository.PriceQueryRepository;
import com.leonelmperalta.price.manager.prices.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = {PriceQueryServiceImpl.class})
class PriceQueryServiceImplTest {

    @MockBean
    private PriceQueryRepository priceQueryRepository;

    @MockBean
    private PriceQueryServiceMapper priceQueryServiceMapper;

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

        Mockito.when(this.priceQueryServiceMapper.toLocalDateTime(eq(applicationDate))).thenReturn(applicationDateAsDate);

        Mockito.when(this.priceQueryRepository.findByProductIdAndBrandIdAndApplicationDatesBetween(
                eq(brandId), eq(productId), eq(applicationDateAsDate)
        )).thenReturn(priceQueries);

        PriceQuery result = this.priceQueryService.priceQuery(brandId, productId, applicationDate);

        assertNotNull(result);
        assertEquals(priceQueries.get(0), result);
    }

}