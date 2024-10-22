package com.maxbyte.sam.SecondaryDBFlow.Configuration.APIRequest;

import lombok.Data;

@Data
public class GetAssetList {
    private Integer id;
    private String assetNumber;
    private String assetDescription;
    private String serialNumber;
    private String assetType;
    private String area;
    private String department;
    private String departmentId;

    private String maintenanceObjectType;
    private String maintenanceObjectId;
}
