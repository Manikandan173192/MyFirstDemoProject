package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddBasicDetailsRequest {


    private String assetActivity;
    private Integer assetGroupId;
    private String assetNumber;
    private String assetType;
    private String departmentId;
    private String description;
    private String duration;
    private String firmPlannedType;
    private Integer maintenanceObjectId;
    private Integer maintenanceObjectType;
    private Integer organizationId;
    private String plannerType;
    private String priority;
    private String scheduledEndDate;
    private String scheduledStartDate;
    private String shutdownType;
    private String statusType;
    private String wipAccountingClassCode;
    private String wipEntityId;
    private String workOrderNo;
    private String woType;
    private String wrRequestNo;
    private Integer rebuildItemId;
    private String rebuildSerialNumber;
    private String workRequestType;

    /*private String workOrderNumber;
    private String wipEntityId;
    private Integer organizationId;
    private String assetNumber;
    private Integer assetGroupId;
    private String assetType;
    private Integer rebuildItemId;
    private String rebuildSerialNo;
    private String departmentId;
    private String assetActivity;
    private String wipAccountingClassCode;
    private String scheduledStartDate;
    private String scheduledEndDate;
    private String workRequestNo;
    private String priority;
    private String duration;
    private String plannerType;
    private String woType;
    private String shutdownType;
    private String firmPlannedType;
    private String statusType;
    private String description;
    private Integer maintenanceObjectId;
    private Integer maintenanceObjectType;
    private String workRequestId;
    private String workRequestType;*/



}
