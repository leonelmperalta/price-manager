package com.leonelmperalta.price.manager.prices.infrastructure.in.controller;

import com.leonelmperalta.price.manager.prices.application.service.PriceQueryService;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.infrastructure.in.mapper.PriceQueryMapper;
import com.leonelmperalta.price.manager.prices.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceQueryService priceQueryService;

    @MockBean
    private PriceQueryMapper priceQueryMapper;

    @Test
    public void givenValidRequest_whenQuery_thenReturn200() throws Exception {
        PriceQuery priceQuery = TestUtils.singlePriceQuery().get(0);

        Mockito.when(this.priceQueryService.priceQuery(1L, 35355L, "2020-06-14T16:00:00"))
                .thenReturn(priceQuery);
        Mockito.when(this.priceQueryMapper.toPriceQueryResponse(eq(priceQuery)))
                .thenReturn(TestUtils.getPriceQueryResponse());


        mockMvc.perform(
                get("/price-manager/v1/price")
                        .queryParam("brand_id", "1")
                        .queryParam("product_id", "35455")
                        .queryParam("application_date", "2020-06-14T16:00:00")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

}