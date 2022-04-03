package com.jpmorgan.chase.model;

import java.util.List;

public class AssignedTradeContract {

    private List<Trade> trades;

    private List<Contract> contracts;

    public long getTradingTime() {
        return tradingTime;
    }

    public void setTradingTime(long tradingTime) {
        this.tradingTime = tradingTime;
    }

    private long tradingTime;

    public List<Trade> getTrades() {
        return trades;
    }

    public List<Contract> getContracts() {
        return contracts;
    }



    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }


}
