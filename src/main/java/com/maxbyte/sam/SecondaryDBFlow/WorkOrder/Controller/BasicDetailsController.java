package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Controller;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWF;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddBasicDetailsRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.BasicDetails;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service.BasicDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workOrder/basicDetails")
public class BasicDetailsController {
    @Autowired
    private BasicDetailsService basicDetailsService;

    @GetMapping("/basicList")
    public ResponseModel<List<BasicDetails>> list(@RequestParam(required = false) String workOrderNumber){
        return basicDetailsService.list(workOrderNumber);
    }
    @PostMapping("/addOrUpdateBasicDetails")
    public ResponseModel<String> addBasicDetails(@Valid @RequestBody AddBasicDetailsRequest data) {
        return basicDetailsService.addOrUpdateBasicDetails(data);
    }
   /* @PostMapping("/updateBasicDetails")
    public ResponseModel<BasicDetails> updateBasicDetails(@RequestParam(required = true)Integer tempWONumber, @Valid @RequestBody AddBasicDetailsRequest data) {
        return basicDetailsService.updateBasicDetails(tempWONumber,data);
    }*/
}
