package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class MOCStepOne {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepOneId;
    private String mocNumber;
    private String projectSponsorship;
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
