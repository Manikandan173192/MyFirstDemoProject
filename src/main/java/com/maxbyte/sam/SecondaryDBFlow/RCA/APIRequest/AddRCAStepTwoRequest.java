package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import lombok.Data;

@Data
public class AddRCAStepTwoRequest {
    private String rcaNumber;
    private String ContainmentAction;
}
