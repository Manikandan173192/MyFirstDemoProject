package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class FMEAUSAction3 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer actionIdThree;
    private Integer parentId;
    private String fmeaNumber;

    private String maintenance;
    private String assetNumber;
    private String suggestedMaintenance;
    private String proposedAction;
    private String responsibility;
    private String sparesRequired;
    private String quality;
    private String interval;
    private String remarks;
    private LocalDateTime scheduledStartDate;
    private LocalDateTime scheduledEndDate;
}
