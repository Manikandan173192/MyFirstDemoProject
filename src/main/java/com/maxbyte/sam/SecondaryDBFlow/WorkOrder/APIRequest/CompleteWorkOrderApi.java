package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import lombok.Data;

@Data
public class CompleteWorkOrderApi {
    private String organizationId;
    private String workOrderNo ;
    private String  wipEntityId ;
    private String  actualStartDate;
    private String  actualEndDate;
    private String  rcaApplicable;
    private String  capaApplicable;
    private String  message;
    private String  flag;
    private String  curDate;
}
