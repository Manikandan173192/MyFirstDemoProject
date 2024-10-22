package com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest;

import lombok.Data;

@Data
public class SAValidateRequest {
    private String approverComment;
    private String assetId;
    private String assetNumber;
    private String checkListType;
    private Integer approverStatus;
}
