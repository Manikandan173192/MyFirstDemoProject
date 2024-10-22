package com.maxbyte.sam.SecondaryDBFlow.FNDUser.APIRequest;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FNDUserRequest {

    private Integer userId;
    private String userName;
//    private String DESCRIPTION;
    private String EMAIL_ADDRESS;
//    private LocalDateTime END_DATE;
}
