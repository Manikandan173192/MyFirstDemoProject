package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor@NoArgsConstructor
public class MaterialInventoryListRequest {

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
