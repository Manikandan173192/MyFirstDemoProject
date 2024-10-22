package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class FMEADSAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer actionId;
    private Integer parentId;
    private String fmeaNumber;

    private String actionRecommended;//
    private String spa;//
    private String fundingSource;//
    private String woNumber;//
    private String responsible;//
    private String woStatus;//
    private String assetNumber;//
    private String department;//
    private String assetDescription;
    private LocalDateTime scheduleStartDate;
    private LocalDateTime scheduleEndDate;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "actionId")
    private List<FMEADSAddActionTaken> fmeadsAddActionTakens;
}
