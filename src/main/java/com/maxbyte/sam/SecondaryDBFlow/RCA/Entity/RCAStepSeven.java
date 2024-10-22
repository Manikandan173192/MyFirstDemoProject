package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class RCAStepSeven {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepSevenId;
    private String rcaNumber;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "rcaNumber", referencedColumnName = "rcaNumber")
    List<RCAStepSevenCA> caList;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "rcaNumber", referencedColumnName = "rcaNumber")
    List<RCAStepSevenPA> paList;


}
