package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import lombok.Data;

@Data
public class AddRCARequest {
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private String department;
    private Integer departmentId;
    private String area;
    private String rcaType;
    private String createdBy;
    private Integer createdById;
    private Integer approver1Id;
    private String approver1Name;
    private Integer approver2Id;
    private String approver2Name;
    private Integer approver3Id;
    private String approver3Name;
    private String issueDescription;
}
