package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "WORK_ORDER_NO")
    private String workOrderNumber;

    @Column(name = "ORGANIZATION_ID")
    private Integer organizationId;

    @Column(name = "REBUILD_PARENT")
    private String rebuildParent;

    @Column(name = "ACTIVITY_TYPE")
    private String activityType;

    @Column(name = "ACTIVITY_CAUSE")
    private String activityCause;

    @Column(name = "ACTIVITY_SOURCE")
    private String activitySource;

    @Column(name = "MATERIAL_REQUEST_ISSUE")
    private String materialRequestIssue;

    @Column(name = "PLANNED")
    private String planned;

    @Column(name = "WARRANTY_STATUS")
    private String warrantyStatus;

    @Column(name = "WARRANTY_ACTIVE")
    private String warrantyActive;

    @Column(name = "WARRANTY_EXPIRATION_DATE")
    private String warrantyExpirationDate;

    @Column(name = "TAGOUT_REQUIRED")
    private String tagoutRequired;

    @Column(name = "NOTIFICATION_REQUIRED")
    private String notificationRequired;

    @Column(name = "CONTEXT")
    private String context;

    @Column(name = "INFORM_DEPARTMENTS")
    private String informDepartments;

    @Column(name = "SAFETY_PERMIT")
    private String safetyPermit;





   /* private String rebuildParent;
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
