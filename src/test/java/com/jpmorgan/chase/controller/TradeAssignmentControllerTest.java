package com.jpmorgan.chase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmorgan.chase.RequestJson;
import com.jpmorgan.chase.model.TradeAssignmentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeAssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testAssignTradeAndContract() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/trades/contract-assignment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(RequestJson.getRequestJson()))
                .andExpect(status().isOk())
                .andReturn();
        TradeAssignmentResponse tradeAssignmentResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), TradeAssignmentResponse.class);
        assertEquals(tradeAssignmentResponse.getAssignedTradesInfo().size(), 3);
    }

    @Test
    public void testGetAllTrades() throws Exception {
        mockMvc.perform(get("/trades")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetTrade() throws Exception {
        mockMvc.perform(get("/trade/1")).andDo(print()).andExpect(status().isOk());
    }

}
