package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TradeService {

    void persistTrades(List<Trade> trades);

    List<Trade> fetchAllTrades();

    Optional<Trade> fetchTradeById(Long tradeId);

    Map<Trade, Contract> tradeAssignment(List<Trade> trades, List<Contract> contracts, long hours);

}
