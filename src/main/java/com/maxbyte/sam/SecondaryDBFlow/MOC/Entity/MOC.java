package com.maxbyte.sam.SecondaryDBFlow.MOC.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class MOC {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mocId;
    private String mocNumber;
    private String titleOfMoc;
    private String woNumber;
    private Integer woStatus;
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private String assetNumber;
    private Integer assetId;
    private Integer departmentId;
    private String department;
    private String assetDescription;
    private String area;
    private String origin;
    private String costCenter;
    private String initiatorName;
    private String typeOfProposedChange;
    private String docType;
    private String departmentHead;
    private String briefDescriptionOfChange;
    private String attachment;
    private Integer status;
    private boolean isActive;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    private LocalDateTime completedDate;
    private LocalDateTime revertBackDate;
    private LocalDateTime closedDate;

//    private LocalDateTime mocCompletionDate;
//    private LocalDateTime mocClosedDate;

    private Integer revertStages;

}
