package com.jpmorgan.chase.controller;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ContractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    private final ContractRepository contractRepository;

    public ContractController(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @GetMapping(value = "contracts")
    public List<Contract> getAllContracts() {
        LOGGER.info("Fetching all contracts");
        return (List<Contract>) contractRepository.findAll();
    }

    @GetMapping(value = "contract/{id}")
    public Optional<Contract> getContract(@PathVariable Long id) {
        LOGGER.info("Fetching contract for contractId = {}", id);
        return contractRepository.findById(id);
    }

}
