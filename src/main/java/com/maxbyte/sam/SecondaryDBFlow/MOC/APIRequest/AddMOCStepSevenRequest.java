package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import lombok.Data;

@Data
public class AddMOCStepSevenRequest {
    private String mocNumber;
    private Integer plantHeadId;
    private String plantHead;
    private Integer plantHeadApproverStatus;
    private String plantHeadApproverComments;
    private Integer unitHeadId;
    private String unitHead;
    private Integer unitHeadApproverStatus;
    private String unitHeadApproverComments;
}
