package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest;

import lombok.Data;

@Data
public class GetWorkRequestListEAM {

    private String wrNumber;
    private String assetNumber;
    private String creationDate;
    private String owningDepartment;
    private String requestType;
    private String createdBy;
    private String description;

    private String workRequestStatus;
}
