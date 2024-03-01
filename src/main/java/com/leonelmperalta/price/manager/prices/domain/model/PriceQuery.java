package com.leonelmperalta.price.manager.prices.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceQuery {

    private LocalDateTime applicationEndDate;
    private LocalDateTime applicationStartDate;
    private Long brandId;
    private String currency;
    private Long feeId;
    private BigDecimal finalPrice;
    private Integer priority;
    private Long productId;

    public Integer getPriority() {
        return priority;
    }
}
