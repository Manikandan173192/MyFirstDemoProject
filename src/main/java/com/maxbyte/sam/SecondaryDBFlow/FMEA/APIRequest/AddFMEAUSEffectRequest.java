package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddFMEAUSEffectRequest {
    private Integer effectId;
    private Integer parentId;
    private String fmeaNumber;

    private String functionEffect;
    private String s;
    private String c;
    private String e;
    private String o;
    private String severity;
    private String occurrence;
    private String riskPriority;
    private String detectionIndex;
    private String rpn;
    private String detectFailure;



}
