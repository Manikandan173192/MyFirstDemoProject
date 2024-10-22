package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class RCAStepSix {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepSixId;
    private String rcaNumber;
    private String rootCause;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "rcaNumber", referencedColumnName = "rcaNumber")
    List<RCAStepSixCause> causeList;

}
