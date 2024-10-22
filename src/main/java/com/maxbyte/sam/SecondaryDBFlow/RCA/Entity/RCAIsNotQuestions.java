package com.maxbyte.sam.SecondaryDBFlow.RCA.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RCAIsNotQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionId;
    private String questionCategory;
    private String questionType;
    private String question;

}
