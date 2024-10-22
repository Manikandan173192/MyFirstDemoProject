package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import lombok.Data;

@Data
public class AddMOCStepOneRequest {
    private String projectSponsorship;
    private String mocNumber;
    private String projectLeader;
    private String subject;
    private String objectiveOfChange;
    private String oocImage;
    private String presentScheme;
    private String psImage;
    private String proposedRevisedScheme;
    private String prsImage;
    private String expectedBenefits;
    private String ebImage;
    private String preliminaryImpactScreeningChecklist;
    private String piscImage;
    private String mocTriggerIdentificationChecklist;
    private String mticImage;
    private Integer estimatedCost;
}
