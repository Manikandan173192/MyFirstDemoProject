package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FMEADSFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer functionId;

    private Integer parentId;
    private String fmeaNumber;
    private String functionName;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "functionId")
    private List<FMEADSFailureMode> fmeaDSFailureModeList;
}
