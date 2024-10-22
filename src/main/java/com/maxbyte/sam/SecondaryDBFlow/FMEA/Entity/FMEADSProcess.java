package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class FMEADSProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer processId;     //child id
    private Integer parentId;
    private String fmeaNumber;
    private String processName;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "processId")
    private List<FMEADSFunction> fmeaDSFunctions;
}
