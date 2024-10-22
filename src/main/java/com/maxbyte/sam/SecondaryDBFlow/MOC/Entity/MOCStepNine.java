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
public class MOCStepNine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepNineId;
    private String mocNumber;
    private Integer plantHeadId;
    private String plantHead;
    private Integer mocPlantCloserStatus;
    private String plantHeadApproverComments;
    private LocalDateTime plantHeadUpdateDateTime;
    private Integer unitHeadId;
    private String unitHead;
    private Integer mocUnitCloserStatus;
    private String unitHeadApproverComments;
    private LocalDateTime unitHeadUpdateDateTime;
}
