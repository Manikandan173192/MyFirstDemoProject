package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.Data;

@Data
public class CompleteWorkOrderOperationsApi {
    private String organizationId;
    private String workOrderNo;
    private String wipEntityId;
    private String operationsSeqNum;
    private String resourceSeqNum;
    private String actualStartDate;
    private String actualEndDate;
    private String actualDuration;
    private String shutdownStartDate;
    private String shutdownEndDate;
    private String rcaApplicable;
    private String capaApplicable;
    private String reference;
    private String message;
    private String flag;

}
