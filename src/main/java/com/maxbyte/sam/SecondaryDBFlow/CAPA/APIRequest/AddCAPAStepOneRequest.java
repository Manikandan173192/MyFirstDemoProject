package com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepOneCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest.ActionModel;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOneTeams;
import lombok.Data;

import java.util.List;

@Data
public class AddCAPAStepOneRequest {
    private String capaNumber;
    private String rootCause;
    private String attachment;
    private String getUrl;
    List<CAPAStepOneCA>caDetails;

}
