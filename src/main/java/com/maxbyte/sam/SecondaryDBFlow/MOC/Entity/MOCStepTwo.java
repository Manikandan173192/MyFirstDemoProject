package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MOCStepTwo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mocStepTwoId;
    private String mocNumber;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "mocNumber", referencedColumnName = "mocNumber")
    List<MOCStepTwoAP> actionPlanList;
}
