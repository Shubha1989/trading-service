package com.jpmorgan.chase.model;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "trades")
public class Trade {
    public Trade(Long tradeId, Double value, Timestamp assignmentSla) {
        this.tradeId = tradeId;
        this.value = value;
        this.assignmentSla = assignmentSla;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tradeId;
    private Double value;
    private Timestamp assignmentSla;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getAssignmentSla() {
        return assignmentSla;
    }

    public void setAssignmentSla(Timestamp assignmentSla) {
        this.assignmentSla = assignmentSla;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", value=" + value +
                ", assignmentSla=" + assignmentSla +
                ", contractID=" + (Objects.isNull(contract) ? null : contract.getContractId()) +
                '}';
    }
}
