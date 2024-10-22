package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddFMEAUSFunctionRequest {
    private Integer functionId;

    private Integer parentId;
    private String fmeaNumber;
    private String functionName;
    private String functionType;
    private String functionFailure;
}
