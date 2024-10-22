package com.maxbyte.sam.SecondaryDBFlow.Configuration;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AssetRequest {

    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private String assetType;
    private String serialNumber;
    private Integer assetGroupId;
    private String assetGroup;
    private String organizationId;
    private String organizationCode;
    private String departmentId;
    private String department;
    private String area;
    private String maintenanceObjectId;
    private String maintenanceObjectType;
    private String wipAccountingClassCode;
}
