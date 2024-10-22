package com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCAPARequest {
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private String department;
    private Integer departmentId;
    private String area;
    private String origin;
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
