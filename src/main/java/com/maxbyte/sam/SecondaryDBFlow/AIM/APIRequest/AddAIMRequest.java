package com.maxbyte.sam.SecondaryDBFlow.AIM.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.AIMImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAIMRequest {
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private String department;
    private Integer departmentId;
    private String area;
    private String createdBy;
    private Integer createdById;
    private Integer approverId;
    private String approverName;
    private String abnormalityCategory;
    private Date startDate;
    private Date endDate;
    private String recommendation;
    private String issueDescription;
    private List<AIMImage> issueImage;
}
