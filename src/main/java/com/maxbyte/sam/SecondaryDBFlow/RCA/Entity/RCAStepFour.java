package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class RCAStepFour {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepFourId;
    private String rcaNumber;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "rcaNumber", referencedColumnName = "rcaNumber")
    List<RCAStepFourCA> tableList;

}
