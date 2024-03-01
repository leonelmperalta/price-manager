package com.leonelmperalta.price.manager.prices.util;

import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TestUtils {
    public static List<PriceQuery> singlePriceQuery() {
        LocalDateTime startDate = LocalDateTime.of(
                2022, 6, 15, 16, 0 ,0
        );

        LocalDateTime endDate = LocalDateTime.of(
                2022, 12, 31, 23, 59 ,59
        );

        PriceQuery price = PriceQuery.builder()
                .applicationEndDate(endDate)
                .applicationStartDate(startDate)
                .brandId(1L)
                .currency("EUR")
                .feeId(4L)
                .finalPrice(BigDecimal.valueOf(38.95))
                .productId(35455L)
                .build();

        return Collections.singletonList(price);
    }
}
