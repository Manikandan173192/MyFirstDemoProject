package com.maxbyte.sam.SecondaryDBFlow.CWF.Controller;

import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddCWFRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddWorkFlowConfigurationRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddWorkFlowRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWF;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlow;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlowConfig;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Service.WorkFlowService;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workFlow")
public class WorkFlowController extends CrudController<WorkFlow,Integer> {
    @Autowired
    private WorkFlowService workFlowService;
    @Override
    public ServiceInterface service() {
        return workFlowService;
    }

    @GetMapping("")
    public ResponseModel<List<WorkFlow>> list(@RequestParam(required = false) String workFlowName, @RequestParam(required = false) Boolean isActive, @RequestParam(required = false) String createdBy,@RequestParam(required = false) String organizationCode){

        return this.workFlowService.list(workFlowName,isActive,createdBy,organizationCode);
    }

    @PostMapping("/addWorkFlow")
    public ResponseModel addWorkFlow(@Valid @RequestBody AddWorkFlowRequest data) {
        return this.workFlowService.addWorkFlow(data);
    }

    @PostMapping("/addWorkFlowConfig")
    public ResponseModel<String> addWorkFlows(@RequestBody AddWorkFlowConfigurationRequest workFlowRequest){
        return workFlowService.addWorkFlowConfig(workFlowRequest);
    }

    @GetMapping("/getSWorkFlowConfig")
    public ResponseModel<WorkFlowConfig> getWorkFlows(@RequestParam(required = false) String workFlowNumber){
        return workFlowService.getWorkFlowConfig(workFlowNumber);
    }



}
