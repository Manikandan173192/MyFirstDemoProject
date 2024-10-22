package com.maxbyte.sam.SecondaryDBFlow.SA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class SACheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer clId;

    private String checklist;
    private String type;
    private String ucl;
    private String lcl;
    private Integer clStatus;
    private String units;

    private Integer assetId;
    private String assetNumber;

}
