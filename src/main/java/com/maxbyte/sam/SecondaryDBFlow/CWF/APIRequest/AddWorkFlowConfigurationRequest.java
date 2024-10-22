package com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlowConfiguration;
import lombok.Data;

import java.util.List;

@Data
public class AddWorkFlowConfigurationRequest {
    private String workFlowNumber;
    List<WorkFlowConfiguration> workFlowConfigurationList;
}
