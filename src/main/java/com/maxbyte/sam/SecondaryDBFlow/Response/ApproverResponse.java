package com.maxbyte.sam.SecondaryDBFlow.Response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ApproverResponse {
    private String approverName;
    private int approvalStatus;
    private LocalDateTime approverUpdateDateTime;

}
