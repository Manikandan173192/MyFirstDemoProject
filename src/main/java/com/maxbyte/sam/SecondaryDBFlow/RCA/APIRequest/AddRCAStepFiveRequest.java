package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFiveDCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOneTeams;
import lombok.Data;

import java.util.List;

@Data
public class AddRCAStepFiveRequest {
    private String rcaNumber;
    private String problemDescription;
    List<RCAStepFiveDCA> dcaList;
}
