package com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IBImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IBAddRequest {

//    private Integer id;//remove

    private String organization;

    private Integer assetGroupId;
    private String assetGroup;
    private String assetNumber;

    private String assetDescription;

    private Integer departmentId;
    private String department;

    private String area;
    private String subComponent;
    private String preparerName;

    private Integer approver1Id;
    private String approver1Name;
    private Integer approver2Id;
    private String approver2Name;
    private Integer approver3Id;
    private String approver3Name;

    private String BypassValue;
    private String BypassDescription;

    private String impactOfBypass;

    private String category;

    private LocalDateTime startDate;
//
    private LocalDateTime endDate;

    private List<IBImage> imageUpload;

//    private String ibNumber;//remove
//    private boolean active;//remove
}
