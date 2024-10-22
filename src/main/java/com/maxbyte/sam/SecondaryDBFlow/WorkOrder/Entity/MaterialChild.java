package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialChild {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "WORK_ORDER_NO")
    private String workOrderNumber;

    @Column(name = "WIP_ENTITY_ID")
    private String wipEntityId;

    @Column(name = "ORGANIZATION_ID")
    private String organizationId;

    @Column(name = "OPERATION_ID")
    private String operationId;

    @Column(name = "OPERATION_SEQ_NUM")
    private Integer operationSequence;

    @Column(name = "INVENTORY_ITEM_ID")
    private Integer itemId;

    @Column(name = "ITEM_TYPE")
    private String itemType;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "DESCRIPTION")
    private String itemDescription;

    @Column(name = "REQUIRED_QUANTITY")
    private Integer requiredQuantity;

    @Column(name = "UOM")
    private String uom;

    @Column(name = "DATE_REQUIRED")
    private String dateRequired;

    @Column(name = "MATERIAL_TRANSACTION_TYPE")
    private String materialTransactionType;

    @Column(name = "CREATED_ON")
    private String createdOn;
}
