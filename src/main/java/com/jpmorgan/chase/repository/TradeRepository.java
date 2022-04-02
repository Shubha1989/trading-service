package com.jpmorgan.chase.repository;

import com.jpmorgan.chase.model.Trade;
import org.springframework.data.repository.CrudRepository;

public interface TradeRepository extends CrudRepository<Trade,Long> {
}
