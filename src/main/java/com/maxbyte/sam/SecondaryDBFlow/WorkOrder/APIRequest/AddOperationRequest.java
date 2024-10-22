package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.OperationChild;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddOperationRequest {
    private String workOrderNumber;

    List<OperationChild> operationList;
}
