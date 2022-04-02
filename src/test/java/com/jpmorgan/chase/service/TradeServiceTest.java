package com.jpmorgan.chase.service;

import com.jpmorgan.chase.TradeServiceImpl;
import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
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
@ContextConfiguration(classes = {TradeServiceImpl.class,TradeRepository.class})
public class TradeServiceTest {

    @Autowired
    private TradeServiceImpl service;

    @MockBean
    private TradeRepository tradeRepository;

    @Test
    public void testPersisTrades() {
        Contract firstContract = new Contract(1L, "First Contract");
        Contract secondContract = new Contract(2L, "Second Contract");
        Contract thirdContract = new Contract(3L, "Third Contract");

        Trade firstTrade = new Trade(1L, 100d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(20)));
        Trade secondTrade = new Trade(2L, 200d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)));
        Trade thirdTrade = new Trade(3L, 300d, Timestamp.valueOf(LocalDateTime.now().plusMinutes(40)));

        firstTrade.setContract(firstContract);
        secondTrade.setContract(secondContract);
        thirdTrade.setContract(thirdContract);

        Set<Trade> trades = Set.of(firstTrade, secondTrade, thirdTrade);

        service.persistTrades(trades);
        service.persistTrade(firstTrade);

        verify(tradeRepository, times(1)).saveAll(trades);
        verify(tradeRepository, times(1)).save(firstTrade);

    }


}
