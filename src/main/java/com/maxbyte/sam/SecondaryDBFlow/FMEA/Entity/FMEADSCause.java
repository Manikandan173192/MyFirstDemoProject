package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class FMEADSCause {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer causeId;
    private Integer parentId;
    private String fmeaNumber;
    private String causeName;
    private String description;
    private String PreventionControl;
    private String detectionControl;
    private String occurrence;
    private String detection;
    private String imageAttachment;
    private String rpn;
    private String url;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "causeId")
    private List<FMEADSAction> fmeaDSActions;


}
