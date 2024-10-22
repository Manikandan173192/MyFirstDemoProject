package com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CAPAStepTwo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stepTwoId;
    private String capaNumber;
    private String rootCause;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "capaNumber", referencedColumnName = "capaNumber")
    List<CAPAStepTwoPA> paList;


}
