package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FMEAUSCause4 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer causeIdFour;
    private Integer parentId;
    private String fmeaNumber;
    private String causeName4;
    /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     @JoinColumn(name = "parentId",referencedColumnName = "causeIdThree")
     List<FMEAUSSubCause3> fmeausSubCause3;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeIdFour")
    List<FMEAUSEffect4> fmeausEffect4;
}
