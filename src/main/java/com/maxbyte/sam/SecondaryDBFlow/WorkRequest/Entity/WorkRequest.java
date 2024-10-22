package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class WorkRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "ROW_ID")
    private String rowId;

    @Column(name = "WORK_REQUEST_ID")
    private Integer workRequestId;

    @Column(name = "WORK_REQUEST_NUMBER")
    private String workRequestNumber;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private Integer lastUpdatedBy;

    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "CREATED_BY_NAME")
    private String createdByName;

    @Column(name = "LAST_UPDATE_LOGIN")
    private Integer lastUpdateLogin;

    @Column(name = "ASSET_NUMBER")
    private String assetNumber;

    @Column(name = "ASSET_GROUP_ID")
    private Integer assetGroupId;

    @Column(name = "ORGANIZATION_ID")
    private Integer organizationId;

    @Column(name = "WORK_REQUEST_STATUS_ID")
    private Integer workRequestStatusId;

    @Column(name = "WORK_REQUEST_STATUS")
    private String workRequestStatus;

    @Column(name = "WORK_REQUEST_PRIORITY_ID")
    private Integer workRequestPriorityId;

    @Column(name = "WORK_REQUEST_PRIORITY")
    private String workRequestPriority;

    @Column(name = "WORK_REQUEST_OWNING_DEPT_ID")
    private Integer workRequestOwningDeptId;

    @Column(name = "WORK_REQUEST_OWNING_DEPT")
    private String workRequestOwningDept;

    @Column(name = "EXPECTED_RESOLUTION_DATE")
    private LocalDateTime expectedResolutionDate;

    @Column(name = "WIP_ENTITY_ID")
    private Integer wipEntityId;

    @Column(name = "WIP_ENTITY_NAME")
    private String wipEntityName;

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

    @Column(name = "WORK_REQUEST_AUTO_APPROVE")
    private String workRequestAutoApprove;

    @Column(name = "WORK_REQUEST_TYPE_ID")
    private Integer workRequestTypeId;

    @Column(name = "WORK_REQUEST_TYPE")
    private String workRequestType;

    @Column(name = "WORK_REQUEST_CREATED_BY")
    private Integer workRequestCreatedBy;

    @Column(name = "WORK_REQUEST_CREATED_BY_NAME")
    private String workRequestCreatedByName;

    @Column(name = "ASSET_NUMBER_DESCRIPTION")
    private String assetNumberDescription;

    @Column(name = "ASSET_LOCATION_ID")
    private Integer assetLocationId;

    @Column(name = "ASSET_LOCATION")
    private String assetLocation;

    @Column(name = "ASSET_CATEGORY_ID")
    private String assetCategoryId;

    @Column(name = "EXPECTED_START_DATE")
    private LocalDateTime expectedStartDate;

    @Column(name = "ASSET_CRITICALITY")
    private String assetCriticality;

    @Column(name = "AssetGroup")
    private String assetGroup;

    @Column(name = "AssetId")
    private Integer assetId;





}
