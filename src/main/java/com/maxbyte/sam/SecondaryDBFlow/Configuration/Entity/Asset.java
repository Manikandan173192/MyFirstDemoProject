package com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.boot.beanvalidation.IntegrationException;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name ="ASSET_NUMBER")
    private String assetNumber;

    @Column(name = "ASSET_DESC")
    private String assetDescription;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "ORGANIZATION_CODE")
    private String organizationCode;

    @Column(name = "ORGANIZATION_ID")
    private Integer organizationId;

    @Column(name = "EAM_ITEM_TYPE")
    private String eamItemType;

    @Column(name = "ASSET_GROUP_ID")
    private Integer assetGroupId;

    @Column(name = "ASSET_GROUP")
    private String assetGroup;

    @Column(name = "ASSET_GROUP_DESCRIPTION")
    private String assetGroupDescription;

    @Column(name = "ASSET_TYPE")
    private String assetType;

    @Column(name = "ASSET_CRITICALITY")
    private String assetCritically;

    @Column(name = "WIP_ACCOUNTING_CLASS_CODE")
    private String wipAccountingClassCode;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "AREA")
    private String area;

    @Column(name = "OWNING_DEPARTMENT_ID")
    private String departmentId;

    @Column(name = "OWNING_DEPARTMENT")
    private String department;

    @Column(name = "CREATION_DATE")
    private LocalDateTime  creationDate;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime  lastUpdateDate;

    @Column(name = "PARENT_ASSET_NUMBER")
    private String parentAssetNumber;

    @Column(name = "PARENT_SERIAL_NUMBER")
    private String parentSerialNumber;

    @Column(name = "PARENT_ASSET_GROUP")
    private String parentAssetGroup;

    @Column(name = "MAINTENANCE_OBJECT_TYPE")
    private Integer maintenanceObjectType;

    @Column(name = "MAINTENANCE_OBJECT_ID")
    private Integer maintenanceObjectId;

    @Column(name = "GEN_OBJECT_ID")
    private String genObjectId;

    @Column(name = "INV_ORGANIZATION_CODE")
    private String invOrganizationCode;

    private Boolean isActive;



}
