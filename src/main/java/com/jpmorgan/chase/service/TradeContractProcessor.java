package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;

import java.util.Map;
import java.util.Set;

public interface TradeContractProcessor {
    Map<Trade, Contract> assignTradesToContracts(Set<Trade> trades, Set<Contract> contracts, long hours);
    void persistTradeAndContracts(Map<Trade, Contract> assignedTrade);
}
