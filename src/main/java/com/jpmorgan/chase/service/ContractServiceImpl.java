package com.jpmorgan.chase.service;

import com.jpmorgan.chase.model.Contract;
import com.jpmorgan.chase.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }


    @Override
    public void persistContracts(List<Contract> contracts) {
        contractRepository.saveAll(contracts);
    }

    @Override
    public List<Contract> fetchAllContracts() {
        return (List<Contract>) contractRepository.findAll();
    }

    @Override
    public Optional<Contract> fetchContractById(Long id) {
        return contractRepository.findById(id);
    }
}
