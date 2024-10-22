package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class MOCStepEight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mocStepEightId;
    private String mocNumber;
    private String certifiedName1;
    private String designation1;
    private String remarks1;
    private String certifiedName2;
    private String designation2;
    private String remarks2;
    private String operationalHead;
    private String performance;
    private String attachment;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String approverRemark;
    private Integer approverStatus;
    private LocalDateTime approverDateTime;

}
