package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddFMEADSCauseRequest {

   private Integer causeId;
    private Integer parentId;
    private String fmeaNumber;
    private String causeName;
    private String description;
    private String PreventionControl;
    private String detectionControl;
    private String occurrence;
    private String detection;
    private String imageAttachment;
    private String rpn;
    private String url;
}