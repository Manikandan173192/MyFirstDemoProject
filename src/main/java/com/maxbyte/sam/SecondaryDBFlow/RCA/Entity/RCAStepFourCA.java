package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Data
public class RCAStepFourCA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rcaStepFourTableId;
    private String difference;
    private String change;
    private LocalDateTime targetDate;
    private boolean applicable;

    private String rcaNumber;
}
