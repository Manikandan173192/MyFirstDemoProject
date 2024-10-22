package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetWorkOrderListByWorkOrderNo;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetWorkOrderListEAM;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.BasicDetails;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service.FinalService;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Entity.WorkRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workOrder")
public class FinalController {
    @Autowired
    private FinalService finalService;

    @PostMapping("/addWorkOrder")
    public ResponseEntity<ResponseModel<String>> addWorkOrder(@PathVariable @RequestParam(required = false) String workOrderNumber) {
        return finalService.addWorkOrder(workOrderNumber);
    }

    @PostMapping("/updateWorkOrder")
    public ResponseEntity<ResponseModel<String>> updateWorkOrder(@PathVariable @RequestParam(required = false) String workOrderNumber) {
        return finalService.updateWorkOrder(workOrderNumber);
    }

    @GetMapping("/getWorkOrderListEAM")
    public  ResponseModel<List<GetWorkOrderListEAM>> listWorkOrder(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String organizationId){
        return finalService.listWorkOrder(startDate,endDate,organizationId);
    }

    @GetMapping("/getWorkOrderListByWorkOrderNumber")
    public  ResponseModel<List<GetWorkOrderListByWorkOrderNo>> listWorkOrder(@RequestParam String workOrderNumber){
        return finalService.listWorkOrderWithWorkOrderNumber(workOrderNumber);
    }

    @GetMapping("/searchWorkOrderNumber")
    public  ResponseModel<List<String>> getSearchWorkOrderNumber(@RequestParam(required = false) String workOrderNumber){
        return finalService.getSearchWorkOrderNumber(workOrderNumber);
    }

    @GetMapping("/searchFilters")
    public  ResponseModel<List<GetWorkOrderListEAM>> getSearchFilters(@RequestParam(required = false) String workOrderNumber,@RequestParam(required = false) String assetNumber,@RequestParam(required = false) String department,@RequestParam(required = false) String status){
        return finalService.getSearchFilters(workOrderNumber,assetNumber,department,status);
    }

//    @GetMapping("/getWorkRequestNumber")
//    public  ResponseModel<List<String>> getWorkRequestNumber(@RequestParam(required = false) String workRequestStatus,@RequestParam(required = false) String organizationId){
//        return finalService.getWorkRequestNumber(workRequestStatus,organizationId);
//    }

    @GetMapping("/getWorkRequestNumber")
    public  ResponseModel<List<String>> getWorkRequestNumber(@RequestParam String workRequestStatus,
                                                             @RequestParam String organizationId,
                                                             @RequestParam(required = false) String workRequestNumber) {
        return finalService.getWorkRequestNumber(workRequestStatus,organizationId,workRequestNumber);
    }
}
