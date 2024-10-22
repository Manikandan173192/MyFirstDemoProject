package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.Data;

@Data
public class MaterialInvenoryItemRequest {
    private String itemType;
    private String itemCode;
    private String itemDescription;
    private String inventoryItemId;
    private String organizationId;
    private String primaryUomCode;
    private String subinventoryCode;
    private String onhandQuantity;
    private String availableQuantity;
}
