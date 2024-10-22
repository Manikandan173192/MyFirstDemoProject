package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Response.ImageResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddWorkClearanceRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkClearance;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service.WorkClearanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/workOrder/workClearance")
public class WorkClearanceController {

    @Autowired
    private WorkClearanceService workClearanceService;
    @Autowired
    private Environment environment;

    @GetMapping("/workClearanceList")
    public ResponseModel<List<WorkClearance>> list(@RequestParam(required = false) String workOrderNumber){
        return workClearanceService.list(workOrderNumber);
    }

    @PostMapping("/addOrUpdateWorkClearance")
    public ResponseModel<String> addWorkClearance(@RequestBody AddWorkClearanceRequest addWorkClearanceRequest){
        return workClearanceService.addWorkClearance(addWorkClearanceRequest);
    }


    @PostMapping(value = "addWorkClearanceImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<ImageResponse> workOrderImage(@RequestParam(value = "file",required = false) MultipartFile multipartFile)throws IOException {
        String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/WorkOrder";
        return workClearanceService.saveImageToStorage(uploadDirectory, multipartFile);
    }


}
