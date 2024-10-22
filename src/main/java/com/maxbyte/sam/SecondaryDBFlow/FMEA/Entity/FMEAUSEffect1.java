package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
public class FMEAUSEffect1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer effectIdOne;
    private Integer parentId;
    private String fmeaNumber;

    private String functionEffect;
    private String s;
    private String c;
    private String e;
    private String o;
    private String severity;
    private String occurrence;
    private String riskPriority;
    private String detectionIndex;
    private String rpn;
    private String detectFailure;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId",referencedColumnName = "effectIdOne")
    List<FMEAUSAction1> fmeausActions1;
}
