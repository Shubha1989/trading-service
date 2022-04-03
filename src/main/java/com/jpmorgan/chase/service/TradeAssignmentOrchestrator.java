package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Component
public class TradeAssignmentOrchestrator {

    public Map<Trade, Contract> tradeAssignment(Set<Trade> trades, Set<Contract> contracts, long hours) {

        Set<Trade> validTrades =  trades.stream()
                .filter(trade -> checkTimestamp.test(trade.getAssignmentSla(),hours))
                .collect(Collectors.toSet());


        Set<Long> assignedContracts = new HashSet<>();
        Map<Trade,Contract> assignedTrades = validTrades.stream()
                                                        .map(trade -> assignTradeToContract(contracts, assignedContracts, trade))
                                                        .filter(Optional::isPresent)
                                                        .map(Optional::get)
                                                        .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

        return assignedTrades;
    }

    private Optional<Pair<Trade, Contract>> assignTradeToContract(Set<Contract> contracts, Set<Long> assignedContracts, Trade trade) {
        try {
            Contract validContractToAssign = contracts.stream()
                                                    .filter(contract ->! assignedContracts.contains(contract.getContractId()))
                                                    .findAny().orElseThrow(()-> new RuntimeException("No contracts found to attach"));
            trade.setContract(validContractToAssign);
            validContractToAssign.setTrade(trade);
            assignedContracts.add(validContractToAssign.getContractId());
            return Optional.of(Pair.of(trade, validContractToAssign));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    BiPredicate<Timestamp,Long> checkTimestamp = (timestamp,hours) -> timestamp.after(Timestamp.valueOf(LocalDateTime.now()))
            && timestamp.before(Timestamp.valueOf(LocalDateTime.now().plusHours(hours)));
}
