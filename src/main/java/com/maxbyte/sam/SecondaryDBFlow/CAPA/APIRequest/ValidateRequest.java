package com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest;

import lombok.Data;

import java.util.Date;

@Data
public class ValidateRequest {
    private String id;
    private Integer approver1Id;
    private String approver1Name;
    private String approver1Comments;
    private Integer approver1Status;
    private Integer approver2Id;
    private String approver2Name;
    private String approver2Comments;
    private Integer approver2Status;
    private Integer approver3Id;
    private String approver3Name;
    private String approver3Comments;
    private Integer approver3Status;
    private String shareComments;
}
