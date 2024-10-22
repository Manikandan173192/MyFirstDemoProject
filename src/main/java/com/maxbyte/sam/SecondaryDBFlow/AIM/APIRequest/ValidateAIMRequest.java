package com.maxbyte.sam.SecondaryDBFlow.AIM.APIRequest;

import lombok.Data;

@Data
public class ValidateAIMRequest {
    private String id;
    private Integer approverId;
    private String approverName;
    private String approverComments;
    private Integer approverStatus;
}
