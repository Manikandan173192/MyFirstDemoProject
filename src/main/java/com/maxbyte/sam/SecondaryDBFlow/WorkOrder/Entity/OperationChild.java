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
public class OperationChild {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "WORK_ORDER_NO")
    private String workOrderNumber;

    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

    @Column(name = "WIP_ENTITY_ID")
    private String wipEntityId;

    @Column(name = "OPERATION_SEQ_NUM")
    private String operationSequence;//check this

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "RESOURCE_SEQ_NUM")
    private String resourceSequence;

    @Column(name = "RESOURCE_ID")
    private String resourceId;

    @Column(name = "RESOURCE_CODE")
    private String resourceCode;

    @Column(name = "USAGE_RATE_OR_AMOUNT")
    private String usage;

    @Column(name = "ASSIGNED_UNITS")
    private String assignedUnits;

    @Column(name = "INSTANCE_ID")
    private String instanceId;

    @Column(name = "INSTANCE_NAME")
    private String instanceName;

    @Column(name = "START_DATE")
    private String startDate;

    @Column(name = "COMPLETION_DATE")
    private String completionDate;

    @Column(name = "DURATION")
    private String duration;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "ORGANIZATION_ID")
    private LocalDateTime organizationId;


    @Column(name = "OPERATIONS_TRANSACTION_TYPE")
    private String operationsTransactionType;

    @Column(name = "RESOURCES_TRANSACTION_TYPE")
    private String resourcesTransactionType;

   @Column(name = "INSTANCES_TRANSACTION_TYPE")
    private String instancesTransactionType;

}
