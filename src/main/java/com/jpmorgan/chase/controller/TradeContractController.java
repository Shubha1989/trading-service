package com.jpmorgan.chase.controller;

import com.jpmorgan.chase.model.AssignedTradeContract;
import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;
import com.jpmorgan.chase.service.ContractService;
import com.jpmorgan.chase.service.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TradeContractController {

    private final TradeService tradeService;
    private final ContractService contractService;

    public TradeContractController(TradeService tradeService, ContractService contractService) {
        this.tradeService = tradeService;
        this.contractService = contractService;
    }

    @GetMapping(value="trades")
    public List<Trade> getAllTrades(){
        return tradeService.fetchAllTrades();
    }
    @GetMapping(value="trades/{id}")
    public Optional<Trade> getTrade(@PathVariable Long id){
        return tradeService.fetchTradeById(id);
    }

    @GetMapping(value="contracts")
    public List<Contract> getAllContracts(){
        return contractService.fetchAllContracts();
    }
    @GetMapping(value="trades/{id}")
    public Optional<Contract> getContract(@PathVariable Long id){
        return contractService.fetchContractById(id);
    }

    @PostMapping
    public ResponseEntity assignTradeAndContract(@RequestBody AssignedTradeContract assignedTradeContract){
       Map<Trade,Contract> tradeContractMap =  tradeService.tradeAssignment(assignedTradeContract.getTrades(),
               assignedTradeContract.getContracts(),assignedTradeContract.getTradingTime());
       List<Trade> validTrades = new ArrayList<>(tradeContractMap.keySet());
       List<Contract> contractList = tradeContractMap.values().stream().toList();
       tradeService.persistTrades(validTrades);
       contractService.persistContracts(contractList);

       return ResponseEntity.ok(tradeContractMap);
    }
}
