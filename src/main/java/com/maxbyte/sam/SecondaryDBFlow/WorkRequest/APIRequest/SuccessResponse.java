package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class SuccessResponse {

    private String headers;
    private ErrorData P_ERROR_MESSAGE;
    private String P_WORK_REQUEST_NUMBER;
    private String P_RETURN_STATUS;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorData {
        private String nil;
        // Add more fields if needed
    }

}
