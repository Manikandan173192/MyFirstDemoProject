package com.maxbyte.sam.SecondaryDBFlow.SA.Entity;


import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFiveDCA;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class SA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer saId;
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private String department;
    private Integer departmentId;
    private String area;
    private String subject;
    private String component;
    private String checkListType;
    private String version;
    private Integer approverId;
    private String approverName;
    private String initiatorName;
    private Integer approverStatus;
    private String category;
    private LocalDateTime createdOn;
    private Boolean isActive;
    private Integer tStatus;
    private String approverComment;
    private String woNumber;





}
