package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MOCStepTwoAP {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepTwoAPId;
    private String mocNumber;
    private Integer assetId;
    private String assetNumber;
    private Integer departmentId;
    private String department;
    private String assetDescription;
    private String hindalcoTeamMembers;
    private String NonHindalcoTeamMembers;
    private String actionPlan;
    private Date startDate;
    private Date endDate;
    private String attachmentFile;
    private String attachmentDescription;
    private String url;
}
