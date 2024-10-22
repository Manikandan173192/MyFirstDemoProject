package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MOCStepFive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mocStepFiveId;
    private String mocNumber;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "mocNumber", referencedColumnName = "mocNumber")
    List<MOCStepFiveDD> tableList;

}
