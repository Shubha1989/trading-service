package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.repository.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private TradeAssignmentOrchestrator tradeAssignmentOrchestrator;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public void persistTrades(List<Trade> trades) {
       tradeRepository.saveAll(trades);
    }

    @Override
    public List<Trade> fetchAllTrades() {
       return (List<Trade>) tradeRepository.findAll();
    }

    @Override
    public Optional<Trade> fetchTradeById(Long tradeId) {
        return tradeRepository.findById(tradeId);
    }

    @Override
    public Map<Trade, Contract> tradeAssignment(List<Trade> trades, List<Contract> contracts, long hours) {
        Set<Trade> tradeSet = trades.stream().collect(Collectors.toSet());
        Set<Contract> contractSet = contracts.stream().collect(Collectors.toSet());
        Map<Trade,Contract> assignedTrade = tradeAssignmentOrchestrator.tradeAssignment(tradeSet,contractSet,hours);
        return assignedTrade;
    }

}
