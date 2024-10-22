package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FMEADSAddActionTaken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer actionTakenId;
    private String fmeaNumber;
    private Integer parentId;

    private String actionTaken;
    private String lifeCycle;
    private String priority;
    private Integer sev;
    private Integer occ;
    private String det;
    private String rpn;
    private String createdOn;

}
