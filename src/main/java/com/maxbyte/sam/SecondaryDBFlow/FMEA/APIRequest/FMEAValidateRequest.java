package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FMEAValidateRequest {
    private String fmeaNumber;
    private Integer approverStatus;
    private String approverComment;
    private LocalDateTime resubmissionDate;
}
