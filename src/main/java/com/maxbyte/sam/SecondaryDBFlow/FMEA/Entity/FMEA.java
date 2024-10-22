package com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

import java.util.List;


@Data
@Entity
public class FMEA {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer fmeaId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "fmeaId")
    private List<FMEADSProcess> fmeaDSProcess;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "fmeaId")
    private List<FMEAUSFunction> fmeaUSFunctions;

    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private String department;
    private Integer departmentId;
    private String area;
    private String fmeaType;
    private List<String> teamMembers;
    private String preparerName;
    private Integer preparerId;
    private String approverName;
    private Integer approverId;
    private Integer approverStatus;
    private String approverComment;
    private String costCenter;
    private String fmeaNumber;
    private String fmeaDescription;
    private String systemBoundaryDefinition;
    private String assumption;
    private String woNumber;
    private Integer status;
    private boolean isActive;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private LocalDateTime resubmissionDate;

    private String revisionNo;

    private String docType;
    private String docToUpload;
    private String responsibility;
    private String supportRequired;
    private String attachment;
    private String attachmentDescription;
    private String url;

}
