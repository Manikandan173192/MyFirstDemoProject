package com.maxbyte.sam.SecondaryDBFlow.SA.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class SAUpdateCheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer saUpdateId;
    private Integer assetGroupId;
    private String assetGroup;
    private Integer assetId;
    private String assetNumber;
    private String department;
    private Integer departmentId;
    private String area;
    private String subject;
    private String component;
    private String checkListType;
    private String version;
    private Integer approverId;
    private String approverName;
    private Integer tStatus;
    private LocalDateTime sauCreatedOn;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "assetNumber", referencedColumnName = "assetNumber")
    List<SACheckList> checkList;

}
