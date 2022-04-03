package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.repository.ContractRepository;
import com.jpmorgan.chase.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TradeContractProcessorImpl.class)
class TradeContractProcessorImplTest {

    @Autowired
    private TradeContractProcessor tradeContractProcessor;

    @MockBean
    private TradeRepository tradeRepository;

    @MockBean
    private ContractRepository contractRepository;

    @Test
    public void testAssignTradesToContracts() {
        Trade firstTrade = new Trade(1L, 100d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(20)));
        Trade secondTrade = new Trade(2L, 200d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
        Trade thirdTrade = new Trade(3L, 300d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)));

        Contract firstContract = new Contract(1L, "First Contract");
        Contract secondContract = new Contract(2L, "Second Contract");
        Contract thirdContract = new Contract(3L, "Third Contract");

        Set<Trade> tradeList = Set.of(firstTrade, secondTrade, thirdTrade);
        Set<Contract> contractList = Set.of(firstContract, secondContract, thirdContract);

        tradeContractProcessor.assignTradesToContracts( tradeList, contractList,2L);

        assertTrue(tradeList.stream().noneMatch(trade -> Objects.isNull(trade.getContract())));
        assertTrue(contractList.stream().noneMatch(contract -> Objects.isNull(contract.getTrade().getTradeId())));

    }

    @Test
    public void testAssignTradesToContractsWithTradesGreaterThanContracts() {
        Trade firstTrade = new Trade(1L, 100d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(20)));
        Trade secondTrade = new Trade(2L, 200d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
        Trade thirdTrade = new Trade(3L, 300d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)));
        Trade fourthTrade = new Trade(4L, 300d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)));
        Trade fifthTrade = new Trade(5L, 300d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)));

        Contract firstContract = new Contract(1L,"First Contract");
        Contract secondContract = new Contract(2L,"Second Contract");
        Contract thirdContract = new Contract(3L,"Third Contract");

        Set<Trade> tradeList= Set.of(firstTrade,secondTrade,thirdTrade,fourthTrade,fifthTrade);
        Set<Contract> contractList= Set.of(firstContract,secondContract,thirdContract);

        tradeContractProcessor.assignTradesToContracts(tradeList,contractList,2L);

        assertTrue(tradeList.stream().anyMatch(trade -> Objects.isNull(trade.getContract())));
        assertTrue(contractList.stream().noneMatch(contract -> Objects.isNull(contract.getTrade().getTradeId())));


    }

    @Test
    public void testAssignTradesToContractsWithTradesLesserThanContracts() {
        Trade firstTrade = new Trade(1L, 100d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(20)));
        Trade secondTrade = new Trade(2L, 200d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
        Trade thirdTrade = new Trade(3L, 300d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)));

        Contract firstContract = new Contract(1L,"First Contract");
        Contract secondContract = new Contract(2L,"Second Contract");
        Contract thirdContract = new Contract(3L,"Third Contract");
        Contract fourthContract = new Contract(4L,"Third Contract");
        Contract fifthContract = new Contract(5L,"Third Contract");

        Set<Trade> tradeList= Set.of(firstTrade,secondTrade,thirdTrade);
        Set<Contract> contractList= Set.of(firstContract,secondContract,thirdContract,fourthContract,fifthContract);

        tradeContractProcessor.assignTradesToContracts(tradeList,contractList,2L);

        assertTrue(tradeList.stream().noneMatch(trade -> Objects.isNull(trade.getContract().getContractId())));
        assertTrue(contractList.stream().anyMatch(contract -> Objects.isNull(contract.getTrade())));


    }

}