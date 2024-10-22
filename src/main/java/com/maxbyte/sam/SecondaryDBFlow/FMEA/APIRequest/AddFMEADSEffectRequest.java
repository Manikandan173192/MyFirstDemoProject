package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddFMEADSEffectRequest {
    private String fmeaNumber;
    private Integer effectId;
    private Integer parentId;
    private String effectName;
    private String description;
    private String severity;
}
