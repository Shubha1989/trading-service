package com.jpmorgan.chase.model;

import java.util.List;
import java.util.Set;

public class TradeAssignmentRequest {

    private Set<Trade> trades;
    private Set<Contract> contracts;
    private long tradingTime;

    public TradeAssignmentRequest() {
    }

    public TradeAssignmentRequest(Set<Trade> trades, Set<Contract> contracts, long tradingTime) {
        this.trades = trades;
        this.contracts = contracts;
        this.tradingTime = tradingTime;
    }

    public long getTradingTime() {
        return tradingTime;
    }

    public void setTradingTime(long tradingTime) {
        this.tradingTime = tradingTime;
    }

    public Set<Trade> getTrades() {
        return trades;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setTrades(Set<Trade> trades) {
        this.trades = trades;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }


}
