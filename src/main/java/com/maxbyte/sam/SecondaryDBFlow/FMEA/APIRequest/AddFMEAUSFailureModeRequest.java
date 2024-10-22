package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddFMEAUSFailureModeRequest {
    private Integer failureModeId;
    private Integer parentId;
    private String fmeaNumber;

    private String failureMode;

}
