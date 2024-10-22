package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MOCStepFiveDD {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mocStepFiveDDId;
    private String mocNumber;
    private String documentType;
    private String documentUpdate;
    private String responsibility;
    private String supportRequired;
    private String assetNumber;
    private String assetDescription;
    private String attachment;
    private String attachmentDescription;
    private String url;


}
