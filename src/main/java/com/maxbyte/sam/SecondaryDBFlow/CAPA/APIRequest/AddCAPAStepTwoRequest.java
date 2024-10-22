package com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepTwoPA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest.ActionModel;
import lombok.Data;

import java.util.List;

@Data
public class AddCAPAStepTwoRequest {
    private String capaNumber;
    private String rootCause;
    List<CAPAStepTwoPA> paDetails;

}
