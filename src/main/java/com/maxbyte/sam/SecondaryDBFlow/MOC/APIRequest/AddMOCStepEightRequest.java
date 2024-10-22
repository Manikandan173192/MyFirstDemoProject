package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AddMOCStepEightRequest {
    private String mocNumber;
    private String certifiedName1;
    private String designation1;
    private String remarks1;
    private String certifiedName2;
    private String designation2;
    private String remarks2;
    private String operationalHead;
    private String performance;
    private String attachment;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String approverRemark;
    private Integer approverStatus;
}
