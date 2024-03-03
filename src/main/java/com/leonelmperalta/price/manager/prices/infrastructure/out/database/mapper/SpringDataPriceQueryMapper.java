package com.leonelmperalta.price.manager.prices.infrastructure.out.database.mapper;

import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.infrastructure.out.database.dbo.SpringDataPriceEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringDataPriceQueryMapper {

    public List<PriceQuery> toPriceQuery(List<SpringDataPriceEntity> priceEntities) {
        return priceEntities.stream().map(this::toPriceQuery).toList();
    }

    public PriceQuery toPriceQuery(SpringDataPriceEntity springDataPriceEntity) {
        return PriceQuery.builder()
                .applicationEndDate(springDataPriceEntity.getApplicationEndDate())
                .applicationStartDate(springDataPriceEntity.getApplicationStartDate())
                .brandId(springDataPriceEntity.getBrandId())
                .currency(springDataPriceEntity.getCurrency())
                .feeId(springDataPriceEntity.getFeeId())
                .finalPrice(springDataPriceEntity.getFinalPrice())
                .priority(springDataPriceEntity.getPriority())
                .productId(springDataPriceEntity.getProductId())
                .build();
    }
}
