package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class RCAStepSevenPA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer paId;
    private String assetGroup;
    private String assetNumber;
    private String department;
    private String action;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String teamMembers;

    private String rcaNumber;
}
