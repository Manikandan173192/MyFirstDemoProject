package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSevenCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSevenPA;
import lombok.Data;

import java.util.List;

@Data
public class AddRCAStepSevenPARequest {
    private String rcaNumber;
    List<RCAStepSevenPA>paDetails;
}
