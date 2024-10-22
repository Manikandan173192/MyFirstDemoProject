package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.Data;

@Data
public class WorkOrderOperationsResourcesRequest {
    private String wipEntityId;
    private String operationSeqNum;
    private String resourceSeqNum;
    private String organizationId;
    private String lastUpdateDate;
    private String lastUpdatedBy;
    private String creationDate;
    private String createdBy;
    private String lastUpdateLogin;
    private String instanceId;
    private String serialNumber;
    private String resourceId;
    private String resourceType;
    private String instanceName;
    private String description;
    private String effectiveStartDate;
    private String effectiveEndDate;
    private String startDate;
    private String completionDate;
    private String rowId;
    private String batchId;
}
