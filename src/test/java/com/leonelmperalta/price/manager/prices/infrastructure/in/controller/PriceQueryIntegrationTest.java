package com.leonelmperalta.price.manager.prices.infrastructure.in.controller;

import com.leonelmperalta.price.manager.prices.infrastructure.in.controller.advice.CustomResponseHandler;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
public class PriceQueryIntegrationTest {


    @Autowired
    private PriceController priceController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(priceController)
                .setControllerAdvice(new CustomResponseHandler())
                .build();
    }


    /**
     * Test case 1: This case has only one matching application dates in db prices. So the one returned will be the result.
     * <p>
     *  Request:
     *  application_date: 2020-06-14 10:00:00,
     *  brand_id = 1 (ZARA),
     *  product_id = 35455
     * <p>
     * Should return:
     *  brand_id = 1 (ZARA),
     *  product_id = 35455,
     *  fee_id = 1,
     *  final_price = 35.50
     *
     */
    @Test
    public void whenPriceQuery_ThenReturnFee1Price() throws Exception {

        mockMvc.perform(
                        get("/price-manager/v1/price")
                                .queryParam("brand_id", "1")
                                .queryParam("product_id", "35455")
                                .queryParam("application_date", "2020-06-14T10:00:00")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].fee_id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].final_price", CoreMatchers.is(35.5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].brand_id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].product_id", CoreMatchers.is(35455)));
    }

    /**
     * Test case 2: This case has two matching application dates in db prices. So the one whit highest priority
     * will be the result.
     * <p>
     *  Request:
     *  application_date: 2020-06-14 16:00:00,
     *  brand_id = 1 (ZARA),
     *  product_id = 35455
     * <p>
     * Should return:
     *  brand_id = 1 (ZARA),
     *  product_id = 35455,
     *  fee_id = 2,
     *  final_price = 25.45
     *
     */
    @Test
    public void whenPriceQuery_ThenReturnFee1PriceHighestPriority() throws Exception {

        mockMvc.perform(
                        get("/price-manager/v1/price")
                                .queryParam("brand_id", "1")
                                .queryParam("product_id", "35455")
                                .queryParam("application_date", "2020-06-14T16:00:00")
                                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].fee_id", CoreMatchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].final_price", CoreMatchers.is(25.45)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].brand_id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].product_id", CoreMatchers.is(35455)));
    }


}
