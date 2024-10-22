package com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CheckListExecutionRequest {

    private String checklist;
    private String type;
    private String ucl;
    private String lcl;
    private Integer clStatus;
    private String units;

    private Integer assetId;
    private String assetNumber;
    private String checkListType;
    private String comments;
    private LocalDateTime checkListExecutionCreatedOn;
    private String image;

}
