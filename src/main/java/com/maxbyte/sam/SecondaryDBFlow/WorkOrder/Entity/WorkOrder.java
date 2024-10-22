package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "ROW_ID")
    private String rowId;

    @Column(name = "WIP_ENTITY_ID")
    private Integer wipEntityId;

    @Column(name = "WIP_ENTITY_NAME")
    private String wipEntityName;

    @Column(name = "WE_ROW_ID")
    private String weRowId;

    @Column(name = "ENTITY_TYPE")
    private Integer entityType;

    @Column(name = "ORGANIZATION_ID")
    private Integer organizationId;

    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "LAST_UPDATE_LOGIN")
    private Integer lastUpdateLogin;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRIMARY_ITEM_ID")
    private Integer primaryItemId;

    @Column(name = "ASSET_GROUP_ID")
    private Integer assetGroupId;

    @Column(name = "ASSET_NUMBER")
    private String assetNumber;

    @Column(name = "ASSET_DESCRIPTION")
    private String assetDescription;

    @Column(name = "CLASS_CODE")
    private String classCode;

    @Column(name = "STATUS_TYPE")
    private Integer statusType;

    @Column(name = "PM_SCHEDULE_ID")
    private Integer pmScheduleId;

    @Column(name = "MANUAL_REBUILD_FLAG")
    private String manualRebuildFlag;

    @Column(name = "WORK_ORDER_TYPE")
    private Integer workOrderType;

    @Column(name = "WORK_ORDER_TYPE_DISP")
    private String workOrderTypeDisp;

    @Column(name = "MATERIAL_ACCOUNT")
    private Integer materialAccount;

    @Column(name = "MATERIAL_OVERHEAD_ACCOUNT")
    private Integer materialOverheadAccount;

    @Column(name = "RESOURCE_ACCOUNT")
    private Integer resourceAccount;

    @Column(name = "OUTSIDE_PROCESSING_ACCOUNT")
    private Integer outsideProcessingAccount;

    @Column(name = "MATERIAL_VARIANCE_ACCOUNT")
    private Integer materialVarianceAccount;

    @Column(name = "RESOURCE_VARIANCE_ACCOUNT")
    private Integer resourceVarianceAccount;

    @Column(name = "OUTSIDE_PROC_VARIANCE_ACCOUNT")
    private Integer outsideProcVarianceAccount;

    @Column(name = "STD_COST_ADJUSTMENT_ACCOUNT")
    private Integer stdCostAdjustmentAccount;

    @Column(name = "OVERHEAD_ACCOUNT")
    private Integer overheadAccount;

    @Column(name = "OVERHEAD_VARIANCE_ACCOUNT")
    private Integer overheadVarianceAccount;

    @Column(name = "SCHEDULED_START_DATE")
    private Date scheduledStartDate;

    @Column(name = "SCHEDULED_COMPLETION_DATE")
    private Date scheduledCompletionDate;

    @Column(name = "DATE_RELEASED")
    private Date dateReleased;

    @Column(name = "DATE_COMPLETED")
    private Date dateCompleted;

    @Column(name = "DATE_CLOSED")
    private Date dateClosed;

    @Column(name = "OWNING_DEPARTMENT")
    private Integer owningDepartment;

    @Column(name = "OWNING_DEPARTMENT_CODE")
    private String owningDepartmentCode;

    @Column(name = "ACTIVITY_TYPE")
    private String activityType;

    @Column(name = "ACTIVITY_TYPE_DISP")
    private String activityTypeDisp;

    @Column(name = "ACTIVITY_CAUSE")
    private String activityCause;

    @Column(name = "ACTIVITY_CAUSE_DISP")
    private String activityCauseDisp;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRIORITY_DISP")
    private String priorityDisp;

    @Column(name = "REQUESTED_START_DATE")
    private Date requestedStartDate;

    @Column(name = "REQUESTED_DUE_DATE")
    private Date requestedDueDate;

    @Column(name = "ESTIMATION_STATUS")
    private Integer estimationStatus;

    @Column(name = "NOTIFICATION_REQUIRED")
    private String notificationRequired;

    @Column(name = "SHUTDOWN_TYPE")
    private String shutdownType;

    @Column(name = "SHUTDOWN_TYPE_DISP")
    private String shutdownTypeDisp;

    @Column(name = "TAGOUT_REQUIRED")
    private String tagoutRequired;

    @Column(name = "PLAN_MAINTENANCE")
    private String planMaintenance;

    @Column(name = "FIRM_PLANNED_FLAG")
    private Integer firmPlannedFlag;

    @Column(name = "SCHEDULE_GROUP_ID")
    private Integer scheduleGroupId;

    @Column(name = "SCHEDULE_GROUP_NAME")
    private String scheduleGroupName;

    @Column(name = "PROJECT_NUMBER")
    private String projectNumber;

    @Column(name = "PROJECT_NAME")
    private String projectName;

    @Column(name = "PROJECT_ID")
    private Integer projectId;

    @Column(name = "TASK_NUMBER")
    private String taskNumber;

    @Column(name = "TASK_NAME")
    private String taskName;

    @Column(name = "TASK_ID")
    private Integer taskId;

    @Column(name = "PARENT_WIP_ENTITY_ID")
    private Integer parentWipEntityId;

    @Column(name = "PARENT_WIP_ENTITY_NAME")
    private String parentWipEntityName;

    @Column(name = "REBUILD_ITEM_ID")
    private Integer rebuildItemId;

    @Column(name = "REBUILD_SERIAL_NUMBER")
    private String rebuildSerialNumber;

    @Column(name = "BOM_REFERENCE_ID")
    private Integer bomReferenceId;

    @Column(name = "ROUTING_REFERENCE_ID")
    private Integer routingReferenceId;

    @Column(name = "COMMON_BOM_SEQUENCE_ID")
    private Integer commonBomSequenceId;

    @Column(name = "COMMON_ROUTING_SEQUENCE_ID")
    private Integer commonRoutingSequenceId;

    @Column(name = "ALTERNATE_BOM_DESIGNATOR")
    private String alternateBomDesignator;

    @Column(name = "BOM_REVISION")
    private String bomRevision;

    @Column(name = "BOM_REVISION_DATE")
    private Date bomRevisionDate;

    @Column(name = "ALTERNATE_ROUTING_DESIGNATOR")
    private String alternateRoutingDesignator;

    @Column(name = "ROUTING_REVISION")
    private String routingRevision;

    @Column(name = "ROUTING_REVISION_DATE")
    private Date routingRevisionDate;

    @Column(name = "COMPLETION_SUBINVENTORY")
    private String completionSubinventory;

    @Column(name = "COMPLETION_LOCATOR_ID")
    private Integer completionLocatorId;

    @Column(name = "LOT_NUMBER")
    private String lotNumber;

    @Column(name = "DEMAND_CLASS")
    private String demandClass;

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

    @Column(name = "INSTANCE_ID")
    private Integer instanceId;

    @Column(name = "INSTANCE_NUMBER")
    private String instanceNumber;

    @Column(name = "MAINTENANCE_OBJECT_SOURCE")
    private Integer maintenanceObjectSource;

    @Column(name = "MAINTENANCE_OBJECT_TYPE")
    private Integer maintenanceObjectType;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "MAINTENANCE_OBJECT_ID")
    private Integer maintenanceObjectId;

    @Column(name = "SERVICE_REQUEST_ID")
    private Integer serviceRequestId;

    @Column(name = "SERVICE_REQUEST")
    private String serviceRequest;

    @Column(name = "MATERIAL_ISSUE_BY_MO")
    private String materialIssueByMo;

    @Column(name = "ACTIVITY_SOURCE")
    private String activitySource;

    @Column(name = "ACTIVITY_SOURCE_MEANING")
    private String activitySourceMeaning;

    @Column(name = "MAINTAINED_ITEM_ID")
    private Integer maintainedItemId;

    @Column(name = "EAM_LINEAR_LOCATION_ID")
    private Integer eamLinearLocationId;

    @Column(name = "PENDING_FLAG")
    private String pendingFlag;

    @Column(name = "USER_DEFINED_STATUS_ID")
    private Integer userDefinedStatusId;

    @Column(name = "WORK_ORDER_STATUS")
    private String workOrderStatus;

    @Column(name = "WORK_ORDER_STATUS_PENDING")
    private String workOrderStatusPending;

    @Column(name = "MATERIAL_SHORTAGE_CHECK_DATE")
    private Date materialShortageCheckDate;

    @Column(name = "MATERIAL_SHORTAGE_FLAG")
    private Integer materialShortageFlag;

    @Column(name = "MATERIAL_SHORTAGE_DISP")
    private String materialShortageDisp;

    @Column(name = "WORKFLOW_TYPE")
    private String workflowType;

    @Column(name = "WARRANTY_CLAIM_STATUS")
    private String warrantyClaimStatus;

    @Column(name = "CYCLE_ID")
    private Integer cycleId;

    @Column(name = "SEQ_ID")
    private Integer seqId;

    @Column(name = "DS_SCHEDULED_FLAG")
    private String dsScheduledFlag;

    @Column(name = "ESTIMATE_ID")
    private Integer estimateId;

    @Column(name = "ESTIMATE_NUMBER")
    private String estimateNumber;

    @Column(name = "ASSET_SERIAL_NUMBER")
    private String assetSerialNumber;

    @Column(name = "MAINT_ORGANIZATION_ID")
    private Integer maintOrganizationId;

    @Column(name = "TARGET_START_DATE")
    private Date targetStartDate;

    @Column(name = "TARGET_COMPLETION_DATE")
    private Date targetCompletionDate;

    public WorkOrder(String wipEntityName) {
        this.wipEntityName = wipEntityName;
    }
}
