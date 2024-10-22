package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSProcess;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class FMEARequest {
    private String organizationCode;
    private String assetGroup;
    private String assetNumber;
    private String assetDescription;
    private String department;
    private String area;
    private String fmeaType;
    private List<String> teamMembers;
    private String preparerName;
    private Integer preparerId;
    private String approverName;
    private Integer approverId;
    private String costCenter;
    private String fmeaDescription;
    private String systemBoundaryDefinition;
    private String assumption;


}
