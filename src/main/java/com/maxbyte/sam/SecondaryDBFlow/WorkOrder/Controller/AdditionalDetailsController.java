package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddAdditionalDetailsRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.AdditionalDetails;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service.AdditionalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workOrder/additionalDetails")
public class AdditionalDetailsController {
    @Autowired
    private AdditionalDetailsService additionalDetailsService;

    @GetMapping("/additionalDetails")
    public ResponseModel<List<AdditionalDetails>> list(@RequestParam(required = false) String workOrderNumber){
        return additionalDetailsService.list(workOrderNumber);
    }
    @PostMapping("/addOrUpdateAdditionalDetails")
    public ResponseModel<String> addAdditionalDetails(@RequestBody AddAdditionalDetailsRequest data){
        return additionalDetailsService.addOrUpdateAdditionalDetails(data);
    }
}
