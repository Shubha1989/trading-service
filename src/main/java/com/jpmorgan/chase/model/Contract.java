package com.jpmorgan.chase.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contracts")
public class Contract {
    public Contract() {
    }

    public Contract(Long contractId, String description) {
        this.contractId = contractId;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long contractId;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trade_id", referencedColumnName = "id")
    @JsonBackReference
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
        return "Contract{" + "contractId=" + contractId + ", description='" + description + '\'' + ", tradeId=" + (Objects.isNull(trade) ? null : trade.getTradeId()) + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (!contractId.equals(contract.contractId)) return false;
        if (!description.equals(contract.description)) return false;
        return trade != null ? trade.equals(contract.trade) : contract.trade == null;
    }

    @Override
    public int hashCode() {
        int result = contractId.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (trade != null ? trade.hashCode() : 0);
        return result;
    }
}
