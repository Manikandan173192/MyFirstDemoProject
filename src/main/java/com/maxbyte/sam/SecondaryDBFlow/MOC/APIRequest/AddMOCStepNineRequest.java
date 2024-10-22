package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Data
public class AddMOCStepNineRequest {
    private String mocNumber;
    private Integer plantHeadId;
    private String plantHead;
    private Integer mocPlantCloserStatus;
    private String plantHeadApproverComments;
    private LocalDateTime plantHeadCreatedOn;
    private Integer unitHeadId;
    private String unitHead;
    private Integer mocUnitCloserStatus;
    private String unitHeadApproverComments;
    private LocalDateTime unitHeadCreatedOn;
}