package com.maxbyte.sam.SecondaryDBFlow.Response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RevertBackResponse {
    private String approverName;
    private Integer approvalStatus;
    private LocalDateTime approverCompleteDateTime;

}
