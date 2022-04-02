package com.jpmorgan.chase.controller;

import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.model.TradeAssignmentRequest;
import com.jpmorgan.chase.model.TradeAssignmentResponse;
import com.jpmorgan.chase.AssignmentService;
import com.jpmorgan.chase.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TradeAssignmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeAssignmentController.class);

    private final TradeService tradeService;
    private final AssignmentService assignmentService;

    public TradeAssignmentController(TradeService tradeService,
                                     AssignmentService assignmentService) {
        this.tradeService = tradeService;
        this.assignmentService = assignmentService;
    }

    @GetMapping(value = "trades")
    public List<Trade> getAllTrades() {
        LOGGER.info("Fetching all trades");
        return tradeService.fetchAllTrades();
    }

    @GetMapping(value = "trade/{id}")
    public Optional<Trade> getTrade(@PathVariable Long id) {
        LOGGER.info("Fetching trade for tradeId = {}", id);
        return tradeService.fetchTradeById(id);
    }

    @PostMapping(value = "trades/contract-assignment")
    public ResponseEntity<TradeAssignmentResponse> assignContractsToTrades(@RequestBody TradeAssignmentRequest tradeAssignmentRequest) throws Exception {
        LOGGER.info("Initiated contracts to Trades assignment");
        TradeAssignmentResponse tradeAssignmentResponse = assignmentService.assign(tradeAssignmentRequest.getTrades(),
                tradeAssignmentRequest.getContracts(), tradeAssignmentRequest.getTradingTime());
        return ResponseEntity.ok(tradeAssignmentResponse);
    }
}
