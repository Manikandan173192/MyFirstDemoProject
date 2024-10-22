package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class MOCStepFour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepFourId;
    private String mocNumber;
    private Integer processItem;
    private String processItemAttachment;
    private String processItemUrl;
    private String processItemComments;
    private Integer processRoute;
    private String processRouteAttachment;
    private String processRouteUrl;
    private String processRouteComments;
    private Integer checkSheet;
    private String checkSheetAttachment;
    private String checkSheetUrl;
    private String checkSheetComments;
    private Integer failureMode;
    private String failureModeAttachment;
    private String failureModeUrl;
    private String failureModeComments;
    private Integer sop;
    private String sopAttachment;
    private String sopUrl;
    private String sopComments;
    private Integer processMapping;
    private String processMappingAttachment;
    private String processMappingUrl;
    private String processMappingComments;
    private Integer controlPlan;
    private String controlPlanAttachment;
    private String controlPlanUrl;
    private String controlPlanComments;
    private Integer others;
    private String othersAttachment;
    private String othersUrl;
    private String othersComments;

}
