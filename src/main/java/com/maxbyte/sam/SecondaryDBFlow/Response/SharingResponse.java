package com.maxbyte.sam.SecondaryDBFlow.Response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class SharingResponse {
    private String id;
    private Integer approver1Id;
    private String approver1Name;
    private String approver1Comments;
    private Integer approver1Status;
    private LocalDateTime approver1DateTime;
    private Integer approver2Id;
    private String approver2Name;
    private String approver2Comments;
    private Integer approver2Status;
    private LocalDateTime approver2DateTime;
    private Integer approver3Id;
    private String approver3Name;
    private String approver3Comments;
    private Integer approver3Status;
    private LocalDateTime approver3DateTime;
    private String shareComments;
}
