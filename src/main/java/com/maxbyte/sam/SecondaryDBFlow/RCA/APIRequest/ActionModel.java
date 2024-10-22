package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ActionModel {
    private String department;
    private String action;
    private Date startDate;
    private Date endDate;
    private String teamMembers;
    private List<AssetModel> assetList;
}
