package com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlow;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlowConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddWorkFlowRequest {

    private String workFlowNumber;
    private String workFlowName;
    private Integer organizationId;
    private String organizationCode;
    private Integer departmentId;
    private String department;
    private String initiatorName;

}
