package com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AddSARequest {
//    private Integer saId;
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private String department;
    private Integer departmentId;
    private String area;
    private String subject;
    private String component;
    private String checkListType;
    private String version;
    private Integer approverId;
    private String approverName;
    private String initiatorName;
    private String category;
    private String woNumber;
    private LocalDateTime saCreatedOn;

}
