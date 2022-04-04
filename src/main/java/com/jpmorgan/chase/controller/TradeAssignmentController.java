package com.jpmorgan.chase.controller;

import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.model.TradeAssignmentRequest;
import com.jpmorgan.chase.model.TradeAssignmentResponse;
import com.jpmorgan.chase.repository.TradeRepository;
import com.jpmorgan.chase.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TradeAssignmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeAssignmentController.class);

    private final TradeRepository tradeRepository;
    private final AssignmentService assignmentService;

    public TradeAssignmentController(TradeRepository tradeRepository,
                                     AssignmentService assignmentService) {
        this.tradeRepository = tradeRepository;
        this.assignmentService = assignmentService;
    }

    @GetMapping(value = "trades")
    public List<Trade> getAllTrades() {
        LOGGER.info("Fetching all trades");
        return (List<Trade>) tradeRepository.findAll();
    }

    @GetMapping(value = "trade/{id}")
    public Optional<Trade> getTrade(@PathVariable Long id) {
        LOGGER.info("Fetching trade for tradeId = {}", id);
        return tradeRepository.findById(id);
    }

    @PostMapping(value = "trades/contract-assignment")
    public ResponseEntity<TradeAssignmentResponse> assignContractsToTrades(@RequestBody TradeAssignmentRequest tradeAssignmentRequest)  {
        LOGGER.info("Initiated contracts to Trades assignment");
        TradeAssignmentResponse tradeAssignmentResponse = assignmentService.assign(tradeAssignmentRequest.getTrades(),
                tradeAssignmentRequest.getContracts(), tradeAssignmentRequest.getTradingTime());
        return ResponseEntity.ok(tradeAssignmentResponse);
    }
}
