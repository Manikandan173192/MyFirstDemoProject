package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest;

import lombok.*;

@Data
public class FailureResponse {

    private String headers;
    private ErrorData P_ERROR_MESSAGE;
    private WorkRequestNumberData P_WORK_REQUEST_NUMBER;
    private String P_RETURN_STATUS;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorData {
        private String nil;
        // Add more fields if needed
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WorkRequestNumberData {
        private String nil;
        // Add more fields if needed
    }
}
