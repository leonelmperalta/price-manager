package com.leonelmperalta.price.manager.prices.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceQuery {

    private Long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long feeId;
    private Long productId;
    private Integer priority;
    private BigDecimal finalPrice;
    private String currency;

    public Integer getPriority() {
        return priority;
    }
}
