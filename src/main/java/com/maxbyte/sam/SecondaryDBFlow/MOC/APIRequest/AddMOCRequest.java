package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import lombok.Data;

@Data
public class AddMOCRequest {
    private String titleOfMoc;
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private String assetNumber;
    private Integer assetId;
    private Integer departmentId;
    private String department;
    private String assetDescription;
    private String area;
    private String origin;
    private String costCenter;
    private String initiatorName;
    private String typeOfProposedChange;
    private String docType;
    private String departmentHead;
    private String briefDescriptionOfChange;
    private String attachment;


}
