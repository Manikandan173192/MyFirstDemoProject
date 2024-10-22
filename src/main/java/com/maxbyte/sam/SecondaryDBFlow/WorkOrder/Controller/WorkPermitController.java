package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddWorkPermitRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkPermit;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service.WorkPermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workOrder/workPermit")
public class WorkPermitController {
    @Autowired
    private WorkPermitService workPermitService;

    @GetMapping("/workPermitList")
    public ResponseModel<List<WorkPermit>> list(@RequestParam(required = false) String workOrderNumber){
        return workPermitService.list(workOrderNumber);
    }

    @PostMapping("/addOrUpdateWorkPermit")
    public ResponseModel<String> addWorkPermit(@RequestBody AddWorkPermitRequest addWorkPermitRequest){
        return workPermitService.addWorkPermit(addWorkPermitRequest);
    }

}
