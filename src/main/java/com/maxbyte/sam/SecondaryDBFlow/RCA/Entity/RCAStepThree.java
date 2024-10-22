package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class RCAStepThree {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepThreeId;
    private String rcaNumber;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "rcaNumber", referencedColumnName = "rcaNumber")
    private List<RCAStepThreeQuestions> questionsList;
}
