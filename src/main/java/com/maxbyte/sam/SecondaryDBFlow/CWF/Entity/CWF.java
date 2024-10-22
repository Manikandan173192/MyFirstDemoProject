package com.maxbyte.sam.SecondaryDBFlow.CWF.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CWF {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cwfId;
    private String configId;
    private String documentNumber;
    private Integer workFlowId;
    private String workFlowName;
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private Integer departmentId;
    private String department;
    private String area;
    private String initiatorName;
    private String firstApprover;
    private Integer firstApproverId;
    private String secondApprover;
    private Integer secondApproverId;
    private String thirdApprover;
    private Integer thirdApproverId;
    private boolean isActive;
    private Integer status; //0 Open, 1 In-progress, 2 active , 3 Verify , 4 Reject  , 5 Completed
    private Integer createdById;
    private String createdBy;
    private String woNumber;
    private LocalDateTime createdOn;
    private Integer validateApprover1;
    private String commentsApprover1;
    private Integer validateApprover2;
    private String commentsApprover2;
    private Integer validateApprover3;
    private String commentsApprover3;
    private LocalDateTime approver1DateTime;
    private LocalDateTime approver2DateTime;
    private LocalDateTime approver3DateTime;

    private Integer referBackApproverId;
    private String referBackApprover;
    private String referBackApproverComments;
    private LocalDateTime referBackDateTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "documentNumber", referencedColumnName = "documentNumber")
    List<CWFWorkFlowConfig> workFlowConfigurations;

}
