package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RCAStepThreeQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;
    private String questionCategory;
    private String questionType;
    private String question;
    private String answer;
    private String attachedImage;
    private String url;

    private String rcaNumber;
}
