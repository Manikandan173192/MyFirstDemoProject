package com.maxbyte.sam.OracleDBFlow.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MasterAssetActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "ROW_ID")
    private String rowId;

    @Column(name = "ACTIVITY_ASSOCIATION_ID")
    private Integer activityAssociationId;

    @Column(name = "ORGANIZATION_ID")
    private Integer organizationId;

    @Column(name = "ASSET_ACTIVITY_ID")
    private Integer assetActivityId;

    @Column(name = "ACTIVITY")
    private String activity;

    @Column(name = "ACTIVITY_DESCRIPTION")
    private String activityDescription;

    @Column(name = "INSTANCE_NUMBER")
    private String instanceNumber;

    @Column(name = "INVENTORY_ITEM_ID")
    private Integer inventoryItemId;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "START_DATE_ACTIVE")
    private LocalDateTime startDateActive;

    @Column(name = "END_DATE_ACTIVE")
    private LocalDateTime endDateActive;

    @Column(name = "PRIORITY")
    private String priority;

    @Column(name = "PRIORITY_CODE")
    private String priorityCode;

    @Column(name = "ACTIVITY_CAUSE")
    private String activityCause;

    @Column(name = "ACTIVITY_CAUSE_CODE")
    private Integer activityCauseCode;

    @Column(name = "ACTIVITY_TYPE")
    private String activityType;

    @Column(name = "ACTIVITY_TYPE_CODE")
    private Integer activityTypeCode;

    @Column(name = "OWNING_DEPARTMENT")
    private String owningDepartment;

    @Column(name = "OWNING_DEPARTMENT_ID")
    private Integer owningDepartmentId;

    @Column(name = "TAGGING_REQUIRED_FLAG")
    private String taggingRequiredFlag;

    @Column(name = "SHUTDOWN_TYPE")
    private String shutdownType;

    @Column(name = "SHUTDOWN_TYPE_CODE")
    private Integer shutdownTypeCode;

    @Column(name = "ACCOUNTING_CLASS_CODE")
    private String accountingClassCode;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "LAST_UPDATE_LOGIN")
    private Integer lastUpdateLogin;

    @Column(name = "ATTRIBUTE_CATEGORY")
    private String attributeCategory;

    @Column(name = "ATTRIBUTE1")
    private String attribute1;

    @Column(name = "ATTRIBUTE2")
    private String attribute2;

    @Column(name = "ATTRIBUTE3")
    private String attribute3;

    @Column(name = "ATTRIBUTE4")
    private String attribute4;

    @Column(name = "ATTRIBUTE5")
    private String attribute5;

    @Column(name = "ATTRIBUTE6")
    private String attribute6;

    @Column(name = "ATTRIBUTE7")
    private String attribute7;

    @Column(name = "ATTRIBUTE8")
    private String attribute8;

    @Column(name = "ATTRIBUTE9")
    private String attribute9;

    @Column(name = "ATTRIBUTE10")
    private String attribute10;

    @Column(name = "ATTRIBUTE11")
    private String attribute11;

    @Column(name = "ATTRIBUTE12")
    private String attribute12;

    @Column(name = "ATTRIBUTE13")
    private String attribute13;

    @Column(name = "ATTRIBUTE14")
    private String attribute14;

    @Column(name = "ATTRIBUTE15")
    private String attribute15;

    @Column(name = "REQUEST_ID")
    private String requestId;

    @Column(name = "PROGRAM_APPLICATION_ID")
    private String programApplicationId;

    @Column(name = "PROGRAM_ID")
    private String programId;

    @Column(name = "PROGRAM_UPDATE_DATE")
    private LocalDateTime programUpdateDate;

    @Column(name = "MAINTENANCE_OBJECT_ID")
    private Integer maintenanceObjectId;

    @Column(name = "MAINTENANCE_OBJECT_TYPE")
    private Integer maintenanceObjectType;

    @Column(name = "CREATION_ORGANIZATION_ID")
    private Integer creationOrganizationId;

    @Column(name = "TEMPLATE_FLAG")
    private String templateFlag;

    @Column(name = "ACTIVITY_SOURCE")
    private String activitySource;

    @Column(name = "ACTIVITY_SOURCE_CODE")
    private Integer activitySourceCode;

    @Column(name = "LAST_SERVICE_START_DATE")
    private LocalDateTime lastServiceStartDate;

    @Column(name = "LAST_SERVICE_END_DATE")
    private LocalDateTime lastServiceEndDate;

    @Column(name = "ASSET_REBUILD_GROUP")
    private String assetRebuildGroup;

    @Column(name = "EAM_ITEM_TYPE")
    private Integer eamItemType;

    @Column(name = "WORK_ORDER_TYPE_DISP")
    private String workOrderTypeDisp;

    @Column(name = "WORK_ORDER_TYPE")
    private Integer workOrderType;

    @Column(name = "PLANNER_MAINTENANCE_DISP")
    private String plannerMaintenanceDisp;

    @Column(name = "PLANNER_MAINTENANCE")
    private String plannerMaintenance;

    @Column(name = "FIRM_PLANNED_FLAG")
    private String firmPlannedFlag;

    @Column(name = "PLAN_MAINTENANCE")
    private String planMaintenance;

    @Column(name = "NOTIFICATION_REQUIRED")
    private String notificationRequired;
}
