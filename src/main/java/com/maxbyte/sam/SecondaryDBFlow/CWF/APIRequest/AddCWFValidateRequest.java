package com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCWFValidateRequest {

    private String documentNumber;
    private String firstApprover;
    private Integer firstApproverId;
    private String secondApprover;
    private Integer secondApproverId;
    private String thirdApprover;
    private Integer thirdApproverId;
    private Integer validateApprover1;
    private String commentsApprover1;
    private Integer validateApprover2;
    private String commentsApprover2;
    private Integer validateApprover3;
    private String commentsApprover3;

    private Integer referBackApproverId;
    private String referBackApprover;
    private String referBackApproverComments;
}
