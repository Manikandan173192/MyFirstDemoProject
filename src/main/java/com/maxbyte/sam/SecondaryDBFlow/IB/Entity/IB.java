package com.maxbyte.sam.SecondaryDBFlow.IB.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IBid;
    private String ibNumber;
    private String organization;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String assetDescription;
    private Integer departmentId;
    private String department;
    private String area;
    private String subComponent;
    private String preparerName;
    private String bypassValue;
    private String bypassDescription;
    private String impactOfBypass;
    private String category;
    private String restoreDescription;

    private LocalDateTime createdOn;
    private String woNumber;
    private String senderType;
    private Integer status;
    private boolean active;
    private String approverType;
    private String approverComment;
    //approverlist1
    private Integer approver1Id;
    private String approverName1;
    private String approverDepartment1;
    private LocalDateTime approverDateAndTime1;
    private Integer approverStatus1;
    //approverList2
    private Integer approver2Id;
    private String approverName2;
    private String approverDepartment2;
    private LocalDateTime approverDateAndTime2;
    private Integer approverStatus2;
    //approverList3
    private Integer approver3Id;
    private String approverName3;
    private String approverDepartment3;
    private Integer approverStatus3;
    private LocalDateTime approverDateAndTime3;
    //senderDetails1
    private String senderName1;
    private String senderDepartment;
    private LocalDateTime senderDateAndTime1;
    private  String senderRemarks1;
// senderDetails2
    private String senderName2;
    private String senderDepartment2;
    private LocalDateTime senderDateAndTime2;
    private  String senderRemarks2;


    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ibNumber", referencedColumnName = "ibNumber")
    private List<IBImage> imageUpload;


}
