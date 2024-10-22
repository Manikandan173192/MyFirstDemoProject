package com.maxbyte.sam.SecondaryDBFlow.CWF.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkFlowConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String field;
    private boolean attachment;
    private String value;
    private String attachmentFile;
    private String workFlowNumber;

}
