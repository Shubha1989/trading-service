package com.jpmorgan.chase.model;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "contracts")
public class Contract {
    public Contract(Long contractId, String description) {
        this.contractId = contractId;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;
    private String description;
    @OneToOne(mappedBy = "contract")
    private Trade trade;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractId=" + contractId +
                ", description='" + description + '\'' +
                ", tradeId=" + (Objects.isNull(trade) ? null : trade.getTradeId()) +
                '}';
    }
}
