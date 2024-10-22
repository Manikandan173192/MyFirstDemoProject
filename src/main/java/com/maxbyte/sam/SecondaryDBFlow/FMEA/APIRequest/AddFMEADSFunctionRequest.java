package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddFMEADSFunctionRequest {
    private String fmeaNumber;
    private Integer parentId;
    private Integer functionId;
    private String functionName;
    private String description;

}
