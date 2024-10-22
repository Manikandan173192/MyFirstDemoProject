package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.Data;

@Data
public class OperationsResourceInstanceRequest {
    private String organizationId;
    private String organizationCode;
    private String departmentId;
    private String departmentCode;
    private String departmentDescription;
    private String resourceId;
    private String resourceCode;
    private String instanceId;
    private String fullName;
}
