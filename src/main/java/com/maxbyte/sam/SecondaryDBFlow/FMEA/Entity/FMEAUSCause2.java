package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FMEAUSCause2 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer causeIdTwo;
    private Integer parentId;
    private String fmeaNumber;
    private String causeName2;
   /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeIdTwo")
    List<FMEAUSSubCause2> fmeausSubCause2;*/

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeIdTwo")
    List<FMEAUSEffect2> fmeausEffect2;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeIdTwo")
    List<FMEAUSCause3> fmeausCauses3;

}
