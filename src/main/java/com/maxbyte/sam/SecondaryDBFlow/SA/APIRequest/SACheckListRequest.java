package com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest;

import lombok.Data;

@Data
public class SACheckListRequest {
    private Integer assetId;
    private String assetNumber;
    private String checklist;
    private String type;
    private String ulc;
    private String lcl;
    private Integer clStatus;
    private String units;
}
