package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FMEAUSFailureMode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer failureModeId;
    private Integer parentId;
    private String fmeaNumber;

    private String failureMode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "failureModeId")
    List<FMEAUSCause>fmeaUSCauses;


}
