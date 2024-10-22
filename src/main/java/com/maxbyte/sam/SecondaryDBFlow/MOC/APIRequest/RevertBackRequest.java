package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepSixAL;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RevertBackRequest {
    private String mocNumber;
    MOCStepSixAL approverList;
    private String approverName;
    private int approvalStatus;
    private LocalDateTime approverCompleteDateTime;
}
