package com.maxbyte.sam.SecondaryDBFlow.CWF.Controller;

import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddCWFConfigurationRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddCWFRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddCWFValidateRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddWorkFlowRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWF;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Repository.CWFRepository;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Service.CWFService;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterNumberResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterType;
import com.maxbyte.sam.SecondaryDBFlow.Response.ImageResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cwf")
public class CWFController extends CrudController<CWF,Integer> {
    @Autowired
    private CWFService cwfService;
    @Override
    public ServiceInterface service() {
        return cwfService;
    }
    @Autowired
    private CWFRepository cwfRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;

    @Autowired
    private Environment environment;

    @GetMapping("")
    public ResponseModel<List<CWF>> list(@RequestParam(required = false)Boolean isActive,
                                         @RequestParam(required = false)  String organizationCode,
                                         @RequestParam(required = false)  String assetNumber,
                                         @RequestParam(required = false) String assetDescription,
                                         @RequestParam(required = false) String department,
                                         @RequestParam(required = false) String documentNumber,
                                         @RequestParam(required = false, defaultValue = "0") String requestPage,
                                         @RequestParam(required = false) String woNumber){
//        // If page or size parameters are not provided, use default values
//        int pageNumber = page != null ? page : defaultPage;
//        int pageSize = size != null ? size : defaultSize;
        return this.cwfService.list(isActive,organizationCode, assetNumber, assetDescription,
                department, documentNumber, woNumber, requestPage);
    }

    @GetMapping("/findByDateTime")
    public ResponseModel<List<CWF>> findCWFBetweenDateTime(@RequestParam(required = false) LocalDateTime from,
                                                           @RequestParam(required = false) LocalDateTime to,
                                                           @RequestParam(required = false) String organizationCode,
                                                           @RequestParam(required = false, defaultValue = "0") String requestPage) {
        return this.cwfService.findCWFByDateTime(organizationCode, from, to, requestPage);
    }

    @GetMapping("/getFilterNumber")
    public ResponseModel<List<FilterNumberResponse>> getFilterNumberResponse(String organizationCode){
        return this.cwfService.getFilterNumber(organizationCode);
    }

    @PostMapping("/addCwf")
    public ResponseModel<CWF> addCWF(@Valid @RequestBody AddCWFRequest data) {
       return this.cwfService.addCWF(data);
    }

   @PostMapping("/cwfValidate")
   public ResponseModel<String> validateCWF(@RequestBody AddCWFValidateRequest data) {
       return cwfService.validateRequest(data);
   }


    @PostMapping(value = "addCWFImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<ImageResponse> cwfImageUpload(@RequestParam(value = "file",required = false) MultipartFile multipartFile)throws IOException {
        String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/CWF";
        return cwfService.saveImageToStorage(uploadDirectory, multipartFile);
    }

    @PostMapping("/addWorkFlowConfig")
    public ResponseModel<String> addWorkFlows(@RequestBody AddCWFConfigurationRequest addCWFRequest){
        return cwfService.addWorkFlowConfig(addCWFRequest);
    }
    @GetMapping("/cwfReportFilter")
    public ResponseModel<List<CWF>> getCWFs(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String  area,
            @RequestParam FilterType filterType,
            @RequestParam(required = false) String  organizationCode,
            @RequestParam(required = false, defaultValue = "0") String requestPage,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return cwfService.reportLists(department,area,filterType,organizationCode,requestPage, startDate, endDate);
    }

    @GetMapping("/areaList")
    public ResponseModel<List<String>> getDistinctAreas() {
        try {
            List<String> distinctAreas = cwfRepository.findByArea();
            return new ResponseModel<>(true, "Areas List", distinctAreas);
        } catch (Exception e) {
            return new ResponseModel<>(false, "No Area Record", null);
        }
    }

    @GetMapping("/getUserPendingList")
    public List<CWF> getPendingRCAs(@RequestParam(required = false) String initiatorName, @RequestParam(required = false) String organizationCode) {
        return cwfService.findPending(initiatorName,organizationCode);
    }
}
