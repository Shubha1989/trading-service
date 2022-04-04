package com.jpmorgan.chase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.model.TradeAssignmentRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

public class TestUtils {

    public static String getRequestJson() throws JsonProcessingException {

        Trade firstTrade = new Trade(1L, 100d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(20)));
        Trade secondTrade = new Trade(2L, 200d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
        Trade thirdTrade = new Trade(3L, 300d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)));

        Contract firstContract = new Contract(1L, "First Contract");
        Contract secondContract = new Contract(2L, "Second Contract");
        Contract thirdContract = new Contract(3L, "Third Contract");

        Set<Trade> tradeList = Set.of(firstTrade, secondTrade, thirdTrade);
        Set<Contract> contractList = Set.of(firstContract, secondContract, thirdContract);

        TradeAssignmentRequest request = new TradeAssignmentRequest(tradeList, contractList, 3);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(request);
    }
}
