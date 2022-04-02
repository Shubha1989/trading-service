package com.jpmorgan.chase;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TradeService {

    void persistTrades(Set<Trade> trades);

    void persistTrade(Trade trade);

    List<Trade> fetchAllTrades();

    Optional<Trade> fetchTradeById(Long tradeId);

}
