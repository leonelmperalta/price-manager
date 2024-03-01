package com.leonelmperalta.price.manager.prices.infrastructure.out.database.repository;

import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.domain.repository.PriceQueryRepository;
import com.leonelmperalta.price.manager.prices.infrastructure.out.database.dbo.SpringDataPriceEntity;
import com.leonelmperalta.price.manager.prices.infrastructure.out.database.mapper.SpringDataPriceQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpringDataPriceQueryRepository implements PriceQueryRepository {

    private final SpringDataPriceRepository priceRepository;
    private final SpringDataPriceQueryMapper springDataPriceQueryMapper;

    @Autowired
    public SpringDataPriceQueryRepository(SpringDataPriceRepository priceRepository,
                                          SpringDataPriceQueryMapper springDataPriceQueryMapper) {
        this.priceRepository = priceRepository;
        this.springDataPriceQueryMapper = springDataPriceQueryMapper;
    }

    @Override
    public List<PriceQuery> findByProductIdAndBrandIdAndApplicationDatesBetween(
            Long productId, Long brandId, LocalDateTime applicationDate
    ) {
        List<SpringDataPriceEntity> priceEntities = this.priceRepository.findByProductIdAndBrandIdAndApplicationDatesBetween(
                productId, brandId, applicationDate
        );
        return this.springDataPriceQueryMapper.toPriceQuery(priceEntities);
    }
}
