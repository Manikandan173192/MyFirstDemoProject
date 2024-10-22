package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class FMEAUSFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer functionId;

    private Integer parentId;
    private String fmeaNumber;
    private String functionName;
    private String functionType;
    private String functionFailure;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "functionId")
    private List<FMEAUSFailureMode> fmeaUSFailureModes;

}
