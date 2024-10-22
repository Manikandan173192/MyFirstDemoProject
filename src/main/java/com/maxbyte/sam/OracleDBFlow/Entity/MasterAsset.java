package com.maxbyte.sam.OracleDBFlow.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class MasterAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer assetId;
    @Column(name = "ASSET_NUMBER")
    private String assetNumber;
    private String ASSET_DESC;
    private String SERIAL_NUMBER;
    private String ORGANIZATION_CODE;
    private Integer ORGANIZATION_ID;
    private String EAM_ITEM_TYPE;
    private Integer ASSET_GROUP_ID;
    private String ASSET_GROUP;
    private String ASSET_GROUP_DESCRIPTION;
    private String ASSET_TYPE;
    private String ASSET_CRITICALITY;
    private String WIP_ACCOUNTING_CLASS_CODE;
    private String AREA_ID;
    private String AREA;
    private String OWNING_DEPARTMENT_ID;
    private String OWNING_DEPARTMENT;
    private Date CREATION_DATE;
    private Date LAST_UPDATE_DATE;
    private String PARENT_ASSET_NUMBER;
    private String PARENT_SERIAL_NUMBER;
    private String PARENT_ASSET_GROUP;
    private Integer MAINTENANCE_OBJECT_TYPE;
    private Integer MAINTENANCE_OBJECT_ID;
    private String GEN_OBJECT_ID;
    private String INV_ORGANIZATION_CODE;


}
