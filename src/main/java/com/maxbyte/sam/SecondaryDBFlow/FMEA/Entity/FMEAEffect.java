package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class FMEAEffect {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer effectId;
    private Integer parentId;
    private String fmeaNumber;

    private String effectName;
    private String description;
    private String severity;        //   Integer

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "effectId")
    private List<FMEADSCause> fmeaDSCauses;

}
