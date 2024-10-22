package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import lombok.Data;

@Data
public class AddIsNotQuestions {
    private String questionCategory;
    private String questionType;
    private String question;
}
