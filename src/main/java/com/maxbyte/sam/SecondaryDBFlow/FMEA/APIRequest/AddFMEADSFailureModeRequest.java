package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddFMEADSFailureModeRequest {
    private  String fmeaNumber;
    private Integer parentId;
    private Integer failureModeId;
    private String failureMode;
    private String failureModeDescription;
    private String classification;
}
