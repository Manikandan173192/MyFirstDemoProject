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
public class MOCStepSeven {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepSevenId;
    private String mocNumber;
    private Integer plantHeadId;
    private String plantHead;
    private Integer plantHeadApproverStatus;
    private String plantHeadApproverComments;
    private LocalDateTime plantHeadUpdatedDateTime;
    private Integer unitHeadId;
    private String unitHead;
    private Integer unitHeadApproverStatus;
    private String unitHeadApproverComments;
    private LocalDateTime unitHeadUpdatedDateTime;
}
