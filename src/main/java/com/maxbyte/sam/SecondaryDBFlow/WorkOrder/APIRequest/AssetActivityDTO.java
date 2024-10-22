package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetActivityDTO {
    private String activityType;
    private String activitySource;
    private String activityCause;
    private String activity;
    private String activityDescription;
    private String assetActivityId;//
    private String activityTypeCode;//
    private String priority;
    private String shutdownType;
    private String accountingClassCode;
    private String activityCauseCode;//
}
