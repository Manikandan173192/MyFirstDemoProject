package com.maxbyte.sam.SecondaryDBFlow.AIM.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Aim {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer aimId;
    private String aimNumber;
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private String department;
    private Integer departmentId;
    private String area;
    private String createdBy;
    private Integer createdById;
    private String abnormalityCategory;
    private Date startDate;
    private Date endDate;
    private String recommendation;
    private String issueDescription;
    private Integer approverId;
    private String approverName;
    private String approverComments;
    private LocalDateTime ApproverDateTime;
    private Integer approverStatus;
    private String woNumber;
    private Integer status;
    private boolean isActive;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "aimNumber", referencedColumnName = "aimNumber")
    private List<AIMImage> issueImage;


}
