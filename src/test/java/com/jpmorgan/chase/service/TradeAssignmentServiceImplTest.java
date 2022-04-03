package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.model.TradeAssignmentResponse;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TradeAssignmentServiceImpl.class,TradeContractProcessorImpl.class})
class TradeAssignmentServiceImplTest {

    @Autowired
    private TradeAssignmentServiceImpl assignmentService;

    @Autowired
    private TradeContractProcessorImpl tradeContractProcessor;

    @MockBean
    private TradeRepository tradeRepository;

    @MockBean
    private ContractRepository contractRepository;
    @Test
    public void test_assign() {
        Trade firstTrade = new Trade(1L, 100d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(20)));
        Trade secondTrade = new Trade(2L, 200d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
        Trade thirdTrade = new Trade(3L, 300d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)));

        Contract firstContract = new Contract(1L, "First Contract");
        Contract secondContract = new Contract(2L, "Second Contract");
        Contract thirdContract = new Contract(3L, "Third Contract");

        Set<Trade> trades = Set.of(firstTrade, secondTrade, thirdTrade);
        Set<Contract> contracts = Set.of(firstContract, secondContract, thirdContract);

        TradeAssignmentResponse tradeAssignmentResponse = assignmentService.assign(trades,contracts,2L);
        assertEquals(3, tradeAssignmentResponse.getAssignedTradesInfo().size());
    }




}