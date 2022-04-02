package com.jpmorgan.chase;

import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.repository.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TradeServiceImpl implements TradeService {


    private final TradeRepository tradeRepository;


    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public void persistTrades(Set<Trade> trades) {
        tradeRepository.saveAll(trades);
    }

    @Override
    public void persistTrade(Trade trade) {
        tradeRepository.save(trade);
    }

    @Override
    public List<Trade> fetchAllTrades() {
        return (List<Trade>) tradeRepository.findAll();
    }

    @Override
    public Optional<Trade> fetchTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId);
    }


}
