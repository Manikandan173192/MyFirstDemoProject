package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FNDUserRequest {

    private Integer USER_ID;
    private String USER_NAME;
    private String DESCRIPTION;
    private String EMAIL_ADDRESS;
    private String END_DATE;
}
