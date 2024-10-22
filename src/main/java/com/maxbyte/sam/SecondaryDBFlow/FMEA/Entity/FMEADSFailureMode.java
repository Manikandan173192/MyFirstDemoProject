package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class FMEADSFailureMode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer failureModeId;
    private Integer parentId;
    private String fmeaNumber;

    private String failureMode;
    private String failureModeDescription;
    private String classification;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "failureModeId")
    private List<FMEAEffect> fmeaEffects;
}
