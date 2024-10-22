package com.maxbyte.sam.SecondaryDBFlow.SA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class SACheckListExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mobileId;
    private String checklist;
    private String type;
    private String ucl;
    private String lcl;
    private int clStatus;
    private String units;
    private Long assetId;
    private String assetNumber;
    private String checkListType;
    private LocalDateTime checkListExecutionCreatedOn;

    private String comments;
   private String image;


}
