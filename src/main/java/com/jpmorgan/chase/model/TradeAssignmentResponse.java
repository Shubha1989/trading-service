package com.jpmorgan.chase.model;

import java.util.List;
import java.util.Map;

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
