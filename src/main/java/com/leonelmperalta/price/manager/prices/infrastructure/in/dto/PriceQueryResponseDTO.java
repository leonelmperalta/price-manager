package com.leonelmperalta.price.manager.prices.infrastructure.in.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PriceQueryResponseDTO {

    private String applicationEndDate;
    private String applicationStartDate;
    private Long brandId;
    private String currency;
    private Long feeId;
    private BigDecimal finalAmount;
    private Long productId;

    public String getApplicationEndDate() {
        return applicationEndDate;
    }

    public void setApplicationEndDate(String applicationEndDate) {
        this.applicationEndDate = applicationEndDate;
    }

    public String getApplicationStartDate() {
        return applicationStartDate;
    }

    public void setApplicationStartDate(String applicationStartDate) {
        this.applicationStartDate = applicationStartDate;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getFeeId() {
        return feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
