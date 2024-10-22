package com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest;

import lombok.Data;

@Data
public class AddMOCValidateRequest {

    private String mocNumber;
    private String approverName;
    private Integer approvalStatus;
    private String remarks;
//    private Integer approvedStatus;
}
