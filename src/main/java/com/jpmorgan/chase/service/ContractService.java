package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;

import java.util.List;
import java.util.Optional;

public interface ContractService {
    void persistContracts(List<Contract> contracts);

    List<Contract> fetchAllContracts();

    Optional<Contract> fetchContractById(Long id);

}
