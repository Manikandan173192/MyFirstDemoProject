package com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IBValidationRequest {

    private String ibNumber;
    private String approverType;
    private String approverComment;
    private Integer approverId1;
    private String approverName1;
    private String approverDepartment1;
    private Integer approverStatus1;
    private Integer validate;
    //approverList2

    private Integer approver2Id;
    private String approverName2;
    private String approverDepartment2;
    private Integer approverStatus2;
    //approverList3
    private Integer approver3Id;
    private String approverName3;
    private String approverDepartment3;
    private Integer approverStatus3;

}
