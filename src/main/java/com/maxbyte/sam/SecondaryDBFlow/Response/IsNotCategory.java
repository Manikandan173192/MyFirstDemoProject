package com.maxbyte.sam.SecondaryDBFlow.Response;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepThreeQuestions;
import lombok.Data;

import java.util.List;

@Data
public class IsNotCategory {
    private List<RCAStepThreeQuestions> IsQuestionList;
    private List<RCAStepThreeQuestions> IsNotQuestionList;
}
