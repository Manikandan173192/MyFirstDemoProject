package com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RCACombinedDTO {


        private String orgCode;
        private String department;
        private String area;
        private String rcaNo;
        private String rcaInitiator;
        private LocalDateTime rcaInitiationDate;
        private String assetNo;
        private String issueDescription;
        private String rcaProblemDescription;
        private String rcaLastApprovedBy;
        private String rcaStatus;
        private LocalDateTime rcaCompletionDate;
        private LocalDateTime rcaCloseDate;
        private String containmentActionTaken;
        private String correctiveActionTaken;
        private String preventiveActionRCARecommendationProposed;
        private String assetDescription;


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

        private String woNumber;
        private LocalDateTime createdOn;


}
