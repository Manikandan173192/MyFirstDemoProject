package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFiveDCA;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class MOCStepSix {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepSixId;
    private String mocNumber;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "mocNumber", referencedColumnName = "mocNumber")
    List<MOCStepSixAL> approverList;


}
