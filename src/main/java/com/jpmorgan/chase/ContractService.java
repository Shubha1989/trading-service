package com.jpmorgan.chase;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.model.Trade;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ContractService {
    void persistContracts(Set<Contract> contracts);

    List<Contract> fetchAllContracts();

    void persistContract(Contract contract);

    Optional<Contract> fetchContractById(Long id);

}
