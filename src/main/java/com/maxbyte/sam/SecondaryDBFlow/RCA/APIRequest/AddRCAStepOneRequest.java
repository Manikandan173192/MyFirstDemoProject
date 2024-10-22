package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOneTeams;
import lombok.Data;

import java.util.List;

@Data
public class AddRCAStepOneRequest {
    private String rcaNumber;
    private String problemDescription;
    List<RCAStepOneTeams> teamsList;
}
