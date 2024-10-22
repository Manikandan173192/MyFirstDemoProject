package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddFMEADSActionRequest {
    private Integer actionId;
    private Integer parentId;
    private String fmeaNumber;

    private String actionRecommended;
    private String spa;
    private String fundingSource;
    private String woNumber;
    private String responsible;
    private String woStatus;
    private String assetNumber;
    private String department;
    private String assetDescription;
    private LocalDateTime scheduleStartDate;
    private LocalDateTime scheduleEndDate;
}
