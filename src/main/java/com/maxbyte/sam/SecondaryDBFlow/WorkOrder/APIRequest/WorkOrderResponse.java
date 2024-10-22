package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest.WorkRequestResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderResponse {
    private String message;
    private boolean success;
//    private WorkOrderData data;
    private String data;

    /*@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkOrderData {
//        private Integer id;
        private Integer workOrderNumber;*/
        /*//BasicDetails
        private Integer assetId;
        private String assetNumber;
        private Integer assetGroupId;
        private String assetGroup;
        private Integer departmentId;
        private String department;
        private String departmentDescription;
        private Integer organizationId;
        private String organizationCode;
        private String assetActivity;
        private String wipAccountingClass;
        private Date startDate;
        private Date completionDate;
        private String duration;
        private Integer requestNumber;
        private String planner;
        private String workOrderType;
        private String shutdownType;
        private String firm;
        private String status;
        private String priority;
        private String basicDescription;*/

//    }
}
