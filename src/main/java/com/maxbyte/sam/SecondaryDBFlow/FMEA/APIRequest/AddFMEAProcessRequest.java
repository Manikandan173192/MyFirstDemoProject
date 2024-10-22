package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEA;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddFMEAProcessRequest {
//    private String fmeaNumber;
//    private String processName;
//    private String description;
//    private Integer fmeaId;

    private String fmeaNumber;
    private Integer processId;
    private Integer parentId;
    private String processName;
    private String description;
//    private int fmeaId;


}
