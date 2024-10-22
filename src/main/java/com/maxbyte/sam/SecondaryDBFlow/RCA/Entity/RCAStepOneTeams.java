package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RCAStepOneTeams {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer teamId;
    private String teamMemberName;
    private String teamMemberDepartment;
    private String teamMemberResponsibility;
    private String teamMemberType;

    private String rcaNumber;
}
