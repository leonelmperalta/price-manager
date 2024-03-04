package com.leonelmperalta.price.manager.prices.application.service.impl;

import com.leonelmperalta.price.manager.prices.application.exception.PriceConfigurationErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.InternalServerErrorException;
import com.leonelmperalta.price.manager.prices.application.exception.NotDataFoundException;
import com.leonelmperalta.price.manager.prices.application.mapper.DateConverter;
import com.leonelmperalta.price.manager.prices.application.service.PriceQueryService;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.domain.repository.PriceQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceQueryServiceImpl implements PriceQueryService {

    private final PriceQueryRepository priceQueryRepository;
    private final DateConverter dateConverter;

    @Autowired
    public PriceQueryServiceImpl(PriceQueryRepository priceQueryRepository,
                                 DateConverter dateConverter) {
        this.priceQueryRepository = priceQueryRepository;
        this.dateConverter = dateConverter;
    }

    @Override
    public PriceQuery priceQuery(Long brandId, Long productId, String applicationDate)
            throws InternalServerErrorException, NotDataFoundException, PriceConfigurationErrorException {
        List<PriceQuery> prices = this.priceQueryRepository.findByProductIdAndBrandIdAndApplicationDatesBetween(
                productId, brandId, this.dateConverter.toLocalDateTime(applicationDate)
        );

        if (prices.isEmpty()) {
            throw new NotDataFoundException();
        }

        if (prices.size() > 1 ) {
            return this.getHighestPriorityPrice(prices);
        }

        return prices.get(0);
    }

    private PriceQuery getHighestPriorityPrice(List<PriceQuery> prices) throws NotDataFoundException, PriceConfigurationErrorException {
        List<Integer> indicesOfMaxPriorities = this.maxIndices(
                prices.stream().map(PriceQuery::getPriority).toList()
        );

        if (indicesOfMaxPriorities.size() > 1) {
            throw new PriceConfigurationErrorException();
        }

        return prices.get(indicesOfMaxPriorities.get(0));
    }

    private List<Integer> maxIndices(List<Integer> list) throws NotDataFoundException {
        int max = list.stream().mapToInt(Integer::intValue).max().orElseThrow(NotDataFoundException::new);

        List<Integer> indices = new ArrayList<>();
        int i = 0;
        for (Integer value : list) {
            if (value == max) {
                indices.add(i);
            }
            i++;
        }

        return indices;
    }
}
