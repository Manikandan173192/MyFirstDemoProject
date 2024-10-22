package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddAdditionalDetailsRequest {
    private String workOrderNumber;
    private Integer organizationId;
    private String rebuildParent;
    private String activityType;
    private String activityCause;
    private String activitySource;
    private String materialRequestIssue;
    private String planned;
    private String warrantyStatus;
    private String warrantyActive;
    private String warrantyExpirationDate;
    private String tagoutRequired;
    private String notificationRequired;
    private String context;
    private String informDepartments;
    private String safetyPermit;






   /* private String workOrderNumber;
    private String rebuildParent;
    private String activityType;
    private String activityCause;
    private String activitySource;
    private String shutdownPlan;
    private String shutdownPlanName;
    private String samNumber;
    private String samStatus;
    private boolean materialIssue;
    private boolean planned;
    private boolean warrantyActive;
    private String warrantyStatus;
    private LocalDateTime warrantyExpirationDate;
    private boolean tagOutRequired;
    private boolean notificationRequired;
    private boolean safetyPermitRequired;
    private String context;
    private String informDepartment;
    private LocalDateTime createdOn;*/
}
