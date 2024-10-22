package com.maxbyte.sam.SecondaryDBFlow.Response;

import lombok.Data;

@Data
public class LoginResponse {
    private String Token;
    private String userId;
    private String userName;
    private String userRole;
    private String organizationCode;
    private Integer organizationId;

}
