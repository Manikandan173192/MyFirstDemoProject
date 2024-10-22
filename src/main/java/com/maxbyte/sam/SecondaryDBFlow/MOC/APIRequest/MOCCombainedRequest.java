package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MOCCombainedRequest {

    private String organizationCode;
    private String department;
    private String area;
    private String mocNumber;
    private String initiatorName;
    private LocalDateTime createdOn;
    private String assetNumber;
    private Integer status;

    private LocalDateTime completedDate;
    private LocalDateTime revertBackDate;
    private LocalDateTime closedDate;
//    private LocalDateTime mocCompletionDate;
//    private LocalDateTime mocClosedDate;

    private String projectSponsorship;//
    private String projectLeader;//
    private String subject;//
    private String objectiveOfChange;//
    private String expectedBenefits;//


    private Integer processItem;//
    private Integer processRoute;//
    private Integer checkSheet;//
    private Integer failureMode;//
    private Integer sop;//
    private Integer processMapping;//
    private Integer controlPlan;//
    private Integer others;//
}
