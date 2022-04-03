package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.model.TradeAssignmentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradeAssignmentServiceImpl implements  AssignmentService{
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeAssignmentServiceImpl.class);
    private final TradeContractProcessor tradeContractProcessorImpl;


    public TradeAssignmentServiceImpl(TradeContractProcessor tradeContractProcessorImpl) {
        this.tradeContractProcessorImpl = tradeContractProcessorImpl;
    }


    @Override
    public TradeAssignmentResponse assign(Set<Trade> trades, Set<Contract> contracts, long hours){
        try {
            Map<Trade,Contract> assignedTradesWithContracts = tradeContractProcessorImpl.assignTradesToContracts(trades,contracts,hours);
            tradeContractProcessorImpl.persistTradeAndContracts(assignedTradesWithContracts);
            return generateResponse(assignedTradesWithContracts);
        } catch (Exception exception) {
            LOGGER.error("Exception encountered during Trade Assignment",exception);
            TradeAssignmentResponse tradeAssignmentResponse = new TradeAssignmentResponse();
            tradeAssignmentResponse.setCriticalError(exception.getMessage());
            return tradeAssignmentResponse;
        }
    }

    private TradeAssignmentResponse generateResponse(Map<Trade, Contract> assignedTrade) {
        Map<Long,Long> validTradesAndContracts = assignedTrade.entrySet().stream()
                                                .collect(Collectors.toMap(e-> e.getKey().getTradeId()
                                                                                 ,e-> e.getValue().getContractId()));
        TradeAssignmentResponse tradeAssignmentResponse = new TradeAssignmentResponse();
        tradeAssignmentResponse.setAssignedTradesInfo(validTradesAndContracts);
        return tradeAssignmentResponse;
    }

}
