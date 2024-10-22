package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class AddFMEAUSActionRequest {

    private Integer actionId;
    private Integer parentId;
    private String fmeaNumber;

    private String maintenance;
    private String assetNumber;
    private String suggestedMaintenance;
    private String proposedAction;
    private String responsibility;
    private String sparesRequired;
    private String quality;
    private String interval;
    private String remarks;
    private LocalDateTime scheduledStartDate;
    private LocalDateTime scheduledEndDate;
}
