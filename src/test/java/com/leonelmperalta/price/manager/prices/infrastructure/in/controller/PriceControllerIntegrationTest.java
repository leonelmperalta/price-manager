package com.leonelmperalta.price.manager.prices.infrastructure.in.controller;

import com.leonelmperalta.price.manager.prices.application.service.PriceQueryService;
import com.leonelmperalta.price.manager.prices.domain.model.PriceQuery;
import com.leonelmperalta.price.manager.prices.infrastructure.in.controller.advice.CustomResponseHandler;
import com.leonelmperalta.price.manager.prices.infrastructure.in.mapper.PriceQueryMapper;
import com.leonelmperalta.price.manager.prices.util.TestUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



@SpringBootTest(classes = {PriceController.class})
@AutoConfigureMockMvc
@EnableWebMvc
class PriceControllerIntegrationTest {

    private MockMvc mockMvc;

    @MockBean
    private PriceQueryService priceQueryService;

    @MockBean
    private PriceQueryMapper priceQueryMapper;

    @Autowired
    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(priceController)
                .setControllerAdvice(new CustomResponseHandler())
                .build();
    }

    @Test
    public void givenValidRequest_whenQuery_thenReturn200() throws Exception {
        PriceQuery priceQuery = TestUtils.singlePriceQuery().get(0);

        Mockito.when(this.priceQueryService.priceQuery(eq(1L), eq(35455L), eq("2020-06-14T16:00:00")))
                .thenReturn(priceQuery);
        Mockito.when(this.priceQueryMapper.toPriceQueryResponse(eq(priceQuery)))
                .thenReturn(TestUtils.getPriceQueryResponse());


        mockMvc.perform(
                get("/price-manager/v1/price")
                        .queryParam("brand_id", "1")
                        .queryParam("product_id", "35455")
                        .queryParam("application_date", "2020-06-14T16:00:00")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta_data.method", CoreMatchers.is("GET")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.meta_data.operation", CoreMatchers.is("/price-manager/v1/price")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].fee_id", CoreMatchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].final_price", CoreMatchers.is(38.95)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", CoreMatchers.is(new ArrayList<>())));
    }

}