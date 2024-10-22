package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class RevisionDateRequest {
    private String fmeaNumber;
    private String systemBoundaryDefinition;
    private String assumption;
}
