package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.repository.ContractRepository;
import com.jpmorgan.chase.repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Component
public class TradeContractProcessorImpl implements TradeContractProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeContractProcessorImpl.class);

    private final TradeRepository tradeRepository;
    private final ContractRepository contractRepository;

    public TradeContractProcessorImpl(TradeRepository tradeRepository,
                                      ContractRepository contractRepository) {
        this.tradeRepository = tradeRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public Map<Trade, Contract> assignTradesToContracts(Set<Trade> trades, Set<Contract> contracts, long hours) {
        LOGGER.info("Filtering trades within the trading window");
        Set<Trade> validTrades =  trades.stream()
                .filter(trade -> checkTimestamp.test(trade.getAssignmentSla(),hours))
                .collect(Collectors.toSet());
        LOGGER.info("Valid trades : {}", getTradeIds(validTrades));

        Set<Long> assignedContracts = new HashSet<>();
        Map<Trade,Contract> assignedTrades = validTrades.stream()
                                                        .map(trade -> assignTradeToContract(contracts, assignedContracts, trade))
                                                        .filter(Optional::isPresent)
                                                        .map(Optional::get)
                                                        .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        LOGGER.info("Assigned Trades : {}", getTradeIds(assignedTrades.keySet()));

        return assignedTrades;
    }

    @Override
    @Transactional
    public void persistTradeAndContracts(Map<Trade, Contract> assignedTrade) {
        try {
            LOGGER.info("Started persisting contracts and trades");
            Set<Contract> contracts = new HashSet<>(assignedTrade.values());
            Set<Trade> trades = assignedTrade.keySet();
            contractRepository.saveAll(contracts);
            tradeRepository.saveAll(trades);

        } catch (Exception exception) {
            LOGGER.error("Failed while persisting Trades and Contracts");
            throw exception;
        }
    }
    private String getTradeIds(Set<Trade> validTrades) {
        return validTrades.stream().map(trade -> String.valueOf(trade.getTradeId())).collect(Collectors.joining(","));
    }


    private Optional<Pair<Trade, Contract>> assignTradeToContract(Set<Contract> contracts, Set<Long> assignedContracts, Trade trade) {
        try {
            Contract validContractToAssign = contracts.stream()
                                                    .filter(contract -> !assignedContracts.contains(contract.getContractId()))
                                                    .findAny().orElseThrow(()-> new RuntimeException("No contracts found to attach"));
            validContractToAssign.setTrade(trade);
            trade.setContract(validContractToAssign);
            assignedContracts.add(validContractToAssign.getContractId());
            LOGGER.debug("Assigning Contract with contractId : {} to a Trade with tradeId : {} " ,validContractToAssign.getContractId(), trade.getTradeId());
            return Optional.of(Pair.of(trade, validContractToAssign));
        } catch (RuntimeException e) {
            LOGGER.error("Unable to find a contract to assign a trade with tradeId = {} ", trade.getTradeId());
            return Optional.empty();
        }

    }

    BiPredicate<Timestamp,Long> checkTimestamp = (timestamp,hours) -> timestamp.after(Timestamp.valueOf(LocalDateTime.now()))
            && timestamp.before(Timestamp.valueOf(LocalDateTime.now().plusHours(hours)));
}
