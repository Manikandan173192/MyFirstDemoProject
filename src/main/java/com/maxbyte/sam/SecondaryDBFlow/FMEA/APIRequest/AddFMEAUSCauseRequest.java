package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddFMEAUSCauseRequest {
    private Integer causeId;
    private Integer parentId;
    private String fmeaNumber;
    private String causeName;
}
