package com.jpmorgan.chase.repository;

import com.jpmorgan.chase.model.Contract;
import org.springframework.data.repository.CrudRepository;

public interface ContractRepository extends CrudRepository<Contract,Long> {
}
