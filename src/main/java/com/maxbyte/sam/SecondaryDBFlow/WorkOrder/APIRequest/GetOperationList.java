package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetOperationList {

    private String wipEntityName;
    private String operationSeqNum;
    private String operationSequenceId;
    private String department;
    private String departmentId;
    private String description;
    private String resourceSequenceNum;
    private String resourceId;
    private String resourceCode;
    private String usage;
    private String assignedUnits;
    private String startDate;
    private String completionDate;
    private String instanceId;
    private String instanceName;


}
