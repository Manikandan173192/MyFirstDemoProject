package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.Data;

@Data
public class GetWorkOrderListEAM {

    private String wipEntityName;
    private String assetNumber;
    private String assetGroupId;
    private String assetGroup;
    private String creationDate;
    private String scheduledStartDate;
    private String scheduledCompletionDate;
    private String owningDepartment;
    private String workOrderStatus;
}
