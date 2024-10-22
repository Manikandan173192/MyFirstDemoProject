package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest;

import lombok.Data;


@Data
public class GetWorkRequestListByWorkRequestNo {

    private String rowId;
    private String workRequestId;
    private String workRequestNumber;
    private String description;
    private String lastUpdateDate;
    private String lastUpdatedBy;
    private String creationDate;
    private String createdBy;
    private String createdByName;
    private String lastUpdateLogin;
    private String assetNumber;
    private String assetGroupId;
    private String assetGroup;
    private String organizationId;
    private String workRequestStatusId;
    private String workRequestStatus;
    private String workRequestPriorityId;
    private String workRequestPriority;
    private String workRequestOwningDeptId;
    private String workRequestOwningDept;
    private String expectedResolutionDate;
    private String wipEntityId;
    private String wipEntityName;
    private String attributeCategory;
    private String workRequestAutoApprove;
    private String workRequestTypeId;
    private String workRequestType;
    private String workRequestCreatedBy;
    private String workRequestCreatedByName;
    private String assetNumberDescription;
    private String assetLocationId;
    private String assetLocation;
    private String assetCategoryId;
    private String expectedStartDate;
    private String assetCriticality;
}
