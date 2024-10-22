package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.CompleteWorkOrderApi;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.CompleteWorkOrderOperationsApi;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service.CompleteWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workOrder/CompleteWorkOrder")
public class CompleteWorkOrderController {
    @Autowired
    private CompleteWorkOrderService completeWorkOrderService;
    @PostMapping("/completeWorkOrder")
    public ResponseEntity<ResponseModel<String>> completeWorkOrder(@RequestBody CompleteWorkOrderApi request) {
        return completeWorkOrderService.completeWorkOrder(request);
    }
}
