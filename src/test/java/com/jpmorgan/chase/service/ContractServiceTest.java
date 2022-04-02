package com.jpmorgan.chase.service;

import com.jpmorgan.chase.ContractServiceImpl;
import com.jpmorgan.chase.TradeServiceImpl;
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
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ContractServiceImpl.class, ContractRepository.class})
public class ContractServiceTest {

    @Autowired
    private ContractServiceImpl service;

    @MockBean
    private ContractRepository contractRepository;

    @Test
    public void testPersistContract() {
        Contract firstContract = new Contract(1L, "First Contract");
        Contract secondContract = new Contract(2L, "Second Contract");
        Contract thirdContract = new Contract(3L, "Third Contract");

        Trade firstTrade = new Trade(1L, 100d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(20)));
        Trade secondTrade = new Trade(2L, 200d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
        Trade thirdTrade = new Trade(3L, 300d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)));

        firstContract.setTrade(firstTrade);
        secondContract.setTrade(secondTrade);
        thirdContract.setTrade(thirdTrade);

        Set<Contract> contracts = Set.of(firstContract, secondContract, thirdContract);

        service.persistContracts(contracts);
        service.persistContract(firstContract);

        verify(contractRepository, times(1)).saveAll(contracts);
        verify(contractRepository, times(1)).save(firstContract);

    }


}
