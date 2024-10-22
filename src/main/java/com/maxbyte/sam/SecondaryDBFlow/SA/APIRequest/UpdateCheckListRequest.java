package com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SACheckList;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class UpdateCheckListRequest {
    private Integer assetGroupId;
    private String assetGroup;
    private Integer approverId;
    private String approverName;
    private Integer assetId;
    private String assetNumber;
    private String department;
    private Integer departmentId;
    private String area;
    private String version;
    private String subject;
    private String component;
    private String checklistType;
    private String category;
    private LocalDateTime sauCreatedOn;

    private List<SACheckList> saCheckLists;

}
