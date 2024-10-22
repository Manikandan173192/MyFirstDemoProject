package com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest.ActionModel;
import lombok.Data;

@Data
public class AddCAPAStepThreeRequest {
    private String capaNumber;
    private String comments;
}
