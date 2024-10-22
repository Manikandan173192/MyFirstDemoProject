package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.Operation;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workOrder/operation")
public class OperationController {
    @Autowired
    private OperationService operationService;

    @GetMapping("/operationList")
    public ResponseModel<List<Operation>> list(@RequestParam(required = false) String workOrderNumber){
        return operationService.list(workOrderNumber);
    }
    @PostMapping("/addOrUpdateOperation")
    public ResponseModel<String> addOperation(@RequestBody AddOperationRequest data){
        return operationService.addOperation(data);
    }

    @GetMapping("/OperationsResourceInstance")
    public ResponseModel<List<OperationsResourceInstanceRequest>> OperationsResourceInstanceList(@RequestParam(required = false)String organizationId, @RequestParam(required = false) String departmentId){
        return this.operationService.listOperationsResourceInstances(organizationId, departmentId);
    }

    @GetMapping("/OperationsResource_dd")
    public ResponseModel<List<OperationsResourceDDRequest>> listOperationsResource_DD(@RequestParam(required = false)String organizationId,
                                                                                      @RequestParam(required = false) String departmentId){
        return this.operationService.listOperationsResourceDD(organizationId, departmentId);
    }

    @GetMapping("/WorkOrderOperationsResource")
    public ResponseModel<List<WorkOrderOperationsResourcesRequest>> workOrderOperationsResourceList(@RequestParam(required = false)String wipEntityId){
        return this.operationService.listWorkOrderOperationsResources(wipEntityId);
    }

    @GetMapping("/getOperationByWipEntityId")
    public  ResponseModel<List<GetOperationList>> listOperation(@RequestParam String wipEntityId){
        return operationService.listOperation(wipEntityId);
    }
}
