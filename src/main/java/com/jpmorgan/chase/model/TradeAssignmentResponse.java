package com.jpmorgan.chase.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradeAssignmentResponse {
    private Map<Long,Long> assignedTradesInfo;
    private String criticalError;

    public TradeAssignmentResponse(Map<Long, Long> assignedTradesInfo, String criticalError) {
        this.assignedTradesInfo = assignedTradesInfo;
        this.criticalError = criticalError;
    }

    public TradeAssignmentResponse() {
    }

    public Map<Long, Long> getAssignedTradesInfo() {
        return assignedTradesInfo;
    }

    public void setAssignedTradesInfo(Map<Long, Long> assignedTradesInfo) {
        this.assignedTradesInfo = assignedTradesInfo;
    }

    public String getCriticalError() {
        return criticalError;
    }

    public void setCriticalError(String criticalError) {
        this.criticalError = criticalError;
    }
}
