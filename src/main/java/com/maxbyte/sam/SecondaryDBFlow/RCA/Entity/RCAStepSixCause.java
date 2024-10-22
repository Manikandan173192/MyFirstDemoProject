package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RCAStepSixCause {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rcaStepSixCauseId;
    private String causeType;
    private String why1;
    private String why2;
    private String why3;
    private String why4;
    private String why5;
    private String comment;
    private Integer isRca;

    private String rcaNumber;

}
