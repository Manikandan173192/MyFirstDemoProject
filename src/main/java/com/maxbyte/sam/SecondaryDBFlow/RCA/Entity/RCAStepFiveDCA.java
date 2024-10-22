package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RCAStepFiveDCA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer dcaId;
    private String dcaType;
    private String dcaReason;
    private boolean dcaHighlight;

    private String rcaNumber;
}
