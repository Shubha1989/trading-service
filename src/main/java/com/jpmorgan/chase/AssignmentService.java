package com.jpmorgan.chase;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.model.TradeAssignmentResponse;

import java.util.Set;

public interface AssignmentService {

    TradeAssignmentResponse assign(Set<Trade> trades, Set<Contract> contracts, long hours) throws Exception;

}
