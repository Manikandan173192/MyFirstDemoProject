package com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWFWorkFlowConfig;
import lombok.Data;

import java.util.List;

@Data
public class AddCWFConfigurationRequest {

    private String documentNumber;
    private List<CWFWorkFlowConfig> workflowConfigurationList;
}
