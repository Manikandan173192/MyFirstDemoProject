package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.MaterialChild;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AddMaterialRequest {
    private String workOrderNumber;
    List<MaterialChild> materialList;
}
