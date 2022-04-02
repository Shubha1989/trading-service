package com.jpmorgan.chase.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetContract() throws Exception {
        mockMvc.perform(get("/contract/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testGetAllContracts() throws Exception {
        mockMvc.perform(get("/contracts")).andDo(print()).andExpect(status().isOk());
    }
}
