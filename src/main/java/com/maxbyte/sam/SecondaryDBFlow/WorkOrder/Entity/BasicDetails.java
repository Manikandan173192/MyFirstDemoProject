package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "BasicDetails")
public class BasicDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "WORK_ORDER_NO")
    private String workOrderNumber;

    @Column(name = "WIP_ENTITY_ID")
    private String wipEntityId;

    @Column(name = "WIP_ENTITY_NAME")
    private String wipEntityName;

    @Column(name = "ORGANIZATION_ID")
    private Integer organizationId;

    @Column(name = "ASSET_NUMBER")
    private String assetNumber;

    @Column(name = "ASSET_GROUP_ID")
    private Integer assetGroupId;

    @Column(name = "ASSET_TYPE")
    private String assetType;

    @Column(name = "REBUILD_ITEM_ID")
    private Integer rebuildItemId;

    @Column(name = "REBUILD_SERIAL_NUMBER")
    private String rebuildSerialNo;

    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    @Column(name = "ASSET_ACTIVITY")
    private String assetActivity;

    @Column(name = "WIP_ACCOUNTING_CLASS_CODE")//
    private String wipAccountingClassCode;

    @Column(name = "SCHEDULED_START_DATE")
    private String scheduledStartDate;

    @Column(name = "SCHEDULED_END_DATE")
    private String scheduledEndDate;

    @Column(name = "WORK_REQUEST_NO")
    private String workRequestNo;

    @Column(name = "PRIORITY")
    private String priority;

    @Column(name = "DURATION")
    private String duration;

    @Column(name = "PLANNER_TYPE")
    private String plannerType;

    @Column(name = "WO_TYPE")
    private String woType;

    @Column(name = "SHUTDOWN_TYPE")
    private String shutdownType;

    @Column(name = "FIRM_PLANNED_TYPE")
    private String firmPlannedType;

    @Column(name = "STATUS_TYPE")
    private String statusType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "MAINTENANCE_OBJECT_ID")
    private Integer maintenanceObjectId;

    @Column(name = "MAINTENANCE_OBJECT_TYPE")
    private Integer maintenanceObjectType;

    @Column(name = "WORK_REQUEST_ID")
    private String workRequestId;

    @Column(name = "WORK_REQUEST_TYPE")
    private String workRequestType;






   /* private String workOrderNumber;
    private Integer assetId;
    private String assetNumber;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer departmentId;
    private String department;
    private String departmentDescription;
    private Integer organizationId;
    private String organizationCode;
    private String assetActivity;
    private String wipAccountingClass;
    private LocalDateTime startDate;
    private LocalDateTime completionDate;
    private String duration;
    private Integer requestNumber;
    private String planner;
    private String workOrderType;
    private String shutdownType;
    private String firm;
    private String status;
    private String priority;
    private String basicDescription;
    private LocalDateTime createdOn;*/
}
