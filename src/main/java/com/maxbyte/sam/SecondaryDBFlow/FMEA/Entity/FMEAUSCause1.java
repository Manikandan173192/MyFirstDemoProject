package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FMEAUSCause1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer causeIdOne;
    private Integer parentId;
    private String fmeaNumber;
    private String causeName1;
    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeIdOne")
    List<FMEAUSSubCause1> fmeausSubCause1;*/

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeIdOne")
    List<FMEAUSEffect1> fmeausEffect1;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeIdOne")
    List<FMEAUSCause2> fmeausCauses2;


}
