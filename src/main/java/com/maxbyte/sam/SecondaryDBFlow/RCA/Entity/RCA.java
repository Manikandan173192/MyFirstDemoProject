package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RCA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rcaId;
    private String rcaNumber;
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private String department;
    private Integer departmentId;
    private String area;
    private String rcaType;
    private Integer createdById;
    private String createdBy;
    private Integer approver1Id;
    private String approver1Name;
    private String approver1Comments;
    private Integer approver1Status;
    private LocalDateTime approver1DateTime;
    private Integer approver2Id;
    private String approver2Name;
    private String approver2Comments;
    private Integer approver2Status;
    private LocalDateTime approver2DateTime;
    private Integer approver3Id;
    private String approver3Name;
    private String approver3Comments;
    private Integer approver3Status;
    private LocalDateTime approver3DateTime;
    private String issueDescription;
    private String shareComments;

    private LocalDateTime completedDate;
    private LocalDateTime revertBackDate;
    private LocalDateTime closedDate;

    private String woNumber;
    private Integer status;
    private boolean isActive;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
