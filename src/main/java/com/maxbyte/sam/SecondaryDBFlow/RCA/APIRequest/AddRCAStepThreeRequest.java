package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepThreeQuestions;
import lombok.Data;

import java.util.List;

@Data
public class AddRCAStepThreeRequest {
    private String rcaNumber;
    List<RCAStepThreeQuestions> questionsList;
}
