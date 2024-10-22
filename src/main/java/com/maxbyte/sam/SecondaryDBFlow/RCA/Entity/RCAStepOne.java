package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class RCAStepOne {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepOneId;
    private String rcaNumber;
    private String problemDescription;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "rcaNumber", referencedColumnName = "rcaNumber")
    List<RCAStepOneTeams> teamsList;


}
