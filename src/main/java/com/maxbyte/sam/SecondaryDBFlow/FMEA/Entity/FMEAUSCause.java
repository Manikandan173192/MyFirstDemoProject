package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FMEAUSCause {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer causeId;
    private Integer parentId;
    private String fmeaNumber;
    private String causeName;
    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeId")
    List<FMEAUSSubCause> fmeaUSSubCauses;*/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeId")
    List<FMEAUSEffect> fmeausEffect;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "causeId")
    List<FMEAUSCause1> fmeausCause1;


}
