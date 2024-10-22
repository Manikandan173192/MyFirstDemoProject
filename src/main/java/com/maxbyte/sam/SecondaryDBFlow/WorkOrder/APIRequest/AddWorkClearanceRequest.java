package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkClearanceChild;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AddWorkClearanceRequest {
    private String workOrderNumber;
    List<WorkClearanceChild> workClearanceList;
}
