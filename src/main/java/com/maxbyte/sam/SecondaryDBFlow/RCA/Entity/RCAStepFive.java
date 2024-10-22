package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class RCAStepFive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepFiveId;
    private String rcaNumber;
    private String problemDescription;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "rcaNumber", referencedColumnName = "rcaNumber")
    List<RCAStepFiveDCA> dcaList;


}
