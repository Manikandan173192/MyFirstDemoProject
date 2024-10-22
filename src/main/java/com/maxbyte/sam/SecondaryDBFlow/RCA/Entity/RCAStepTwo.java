package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RCAStepTwo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer containmentActionId;
    private String rcaNumber;
    private String ContainmentAction;
}
