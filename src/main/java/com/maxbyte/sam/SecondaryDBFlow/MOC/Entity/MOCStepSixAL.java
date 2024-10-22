package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class MOCStepSixAL {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepSixALId;
    private Integer departmentId;
    private String department;
    private Integer approverId;
    private String approverName;
    private Integer approvalStatus;
    private String remarks;
    private String mocNumber;
    private LocalDateTime approverUpdateDateTime;
}
