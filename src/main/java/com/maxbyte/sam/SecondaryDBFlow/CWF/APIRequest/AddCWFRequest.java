package com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWFWorkFlowConfig;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlowConfiguration;
import io.swagger.models.auth.In;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCWFRequest {

    private String configId;
    private String documentNumber;
    private Integer workFlowId;
    private String workFlowName;
    private String organizationCode;
    private Integer assetGroupId;
    private String assetGroup;
    private String assetNumber;
    private Integer assetId;
    private String assetDescription;
    private Integer departmentId;
    private String department;
    private String area;
    private String initiatorName;
    private String firstApprover;
    private Integer firstApproverId;
    private String secondApprover;
    private Integer secondApproverId;
    private String thirdApprover;
    private Integer thirdApproverId;

    private List<CWFWorkFlowConfig> workflowConfigurationList;


}
