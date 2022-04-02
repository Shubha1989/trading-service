package com.jpmorgan.chase.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "trades")
public class Trade {
    public Trade() {
    }

    public Trade(Long tradeId, Double value, Timestamp assignmentSla) {
        this.tradeId = tradeId;
        this.value = value;
        this.assignmentSla = assignmentSla;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long tradeId;

    @Column(name = "value")
    private Double value;

    @Column(name="assignment_sla")
    private Timestamp assignmentSla;

    @OneToOne(mappedBy = "trade", cascade = CascadeType.ALL)
    @JsonManagedReference
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (!tradeId.equals(trade.tradeId)) return false;
        if (!value.equals(trade.value)) return false;
        return assignmentSla.equals(trade.assignmentSla);
    }

    @Override
    public int hashCode() {
        int result = tradeId.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + assignmentSla.hashCode();
        return result;
    }
}
