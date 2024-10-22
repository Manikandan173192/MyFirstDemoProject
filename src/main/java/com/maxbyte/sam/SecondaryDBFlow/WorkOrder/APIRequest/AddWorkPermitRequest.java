package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkPermitChild;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AddWorkPermitRequest {
    private String workOrderNumber;
    List<WorkPermitChild> workPermitList;
}
