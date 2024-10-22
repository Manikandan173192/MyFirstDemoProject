package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.Data;

@Data
public class GetMaterialList {

    private String wipEntityId;
    private String operationSequence;
    private String organizationId;
    private String itemId;
    private String itemType;
    private String itemCode;
    private String itemDescription;
    private String requiredQuantity;
    private String uom;
    private String dateRequired;
}
