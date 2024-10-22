package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.CompleteWorkOrderOperationsApi;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service.CompleteWorkOrderOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workOrder/CompleteWorkOrderOperations")

public class CompleteWorkOrderOperatonsController {
    @Autowired
    private CompleteWorkOrderOperationsService completeWorkOrderOperationsService;

    @PostMapping("/completeWorkOrderOperations")
    public ResponseEntity<ResponseModel<String>> completeWorkOrder(@RequestBody CompleteWorkOrderOperationsApi request) {
        return completeWorkOrderOperationsService.completeWorkOrderOperations(request);
    }
}
