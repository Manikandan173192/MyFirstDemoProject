package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSevenCA;
import lombok.Data;

import java.util.List;

@Data
public class AddRCAStepSevenCARequest {
    private String rcaNumber;
    List<RCAStepSevenCA>caDetails;
}
