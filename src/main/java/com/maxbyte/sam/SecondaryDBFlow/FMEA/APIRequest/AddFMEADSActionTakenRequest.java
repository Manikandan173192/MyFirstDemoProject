package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddFMEADSActionTakenRequest {
    private Integer actionTakenId;
    private String fmeaNumber;
    private Integer parentId;

    private String actionTaken;
    private String lifeCycle;
    private String priority;
    private Integer sev;
    private  Integer occ;
    private String det;
    private String rpn;
    private String createdOn;
}
