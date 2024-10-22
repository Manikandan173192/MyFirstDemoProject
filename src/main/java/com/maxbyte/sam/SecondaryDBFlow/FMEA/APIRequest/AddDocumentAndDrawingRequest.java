package com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest;

import lombok.Data;

@Data
public class AddDocumentAndDrawingRequest {
    private String fmeaNumber;
    private String docType;
    private String docToUpload;
    private String responsibility;
    private String supportRequired;
    private String assetNumber;
    private String attachment;
    private String attachmentDescription;
    private String url;
}
