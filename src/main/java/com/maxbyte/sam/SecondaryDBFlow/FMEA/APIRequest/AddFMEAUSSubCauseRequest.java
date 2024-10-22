package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddFMEAUSSubCauseRequest {
    private Integer subCauseId;
    private Integer parentId;
    private String fmeaNumber;
    private String subCauseName;
}
