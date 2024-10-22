package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFourCA;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AddRCAStepFourRequest {
    private String rcaNumber;
    List<RCAStepFourCA> stepFourTables;
}
