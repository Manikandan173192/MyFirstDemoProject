package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FMEAUSCause3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer causeIdThree;
    private Integer parentId;
    private String fmeaNumber;
    private String causeName3;
    /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     @JoinColumn(name = "parentId",referencedColumnName = "causeIdThree")
     List<FMEAUSSubCause3> fmeausSubCause3;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeIdThree")
    List<FMEAUSEffect3> fmeausEffect3;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeIdThree")
    List<FMEAUSCause4> fmeausCauses4;
}
