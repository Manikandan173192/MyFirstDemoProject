package com.maxbyte.sam.SecondaryDBFlow.SA.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IB;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterType;
import com.maxbyte.sam.SecondaryDBFlow.Response.ImageResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest.CheckListExecutionRequest;
import com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest.AddSARequest;
import com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest.UpdateCheckListRequest;
import com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest.SAValidateRequest;
import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SA;
import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SACheckListExecution;
import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SAUpdateCheckList;
import com.maxbyte.sam.SecondaryDBFlow.SA.Repository.SARepository;
import com.maxbyte.sam.SecondaryDBFlow.SA.Service.SAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/SA")
public class SAController extends CrudController<SA, Integer> {
    @Autowired
    private SAService saService;
    @Autowired
    SARepository saRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;
    @Autowired
    private Environment environment;

    @Override
    public ServiceInterface<SA, Integer> service() {
        return saService;
    }

    @GetMapping("/List")
    public ResponseModel<List<SA>> list(@RequestParam(required = false) Boolean isActive,
                                        @RequestParam(required = false) String organizationCode,
                                        @RequestParam(required = false) String assetNumber,
                                        @RequestParam(required = false) String assetDescription,
                                        @RequestParam(required = false, defaultValue = "0") String requestPage,
                                        @RequestParam(required = false) String department) {

//        // If page or size parameters are not provided, use default values
//        int pageNumber = page != null ? page : defaultPage;
//        int pageSize = size != null ? size : defaultSize;
        return this.saService.list(isActive, organizationCode, assetNumber, assetDescription, department, requestPage);

    }

    @PostMapping("/AddSA")
    public ResponseModel<String> addSA(@RequestBody AddSARequest addSARequest) {
        return saService.addSa(addSARequest);
    }


    @PostMapping("updateCheckList")
    public ResponseModel<String> updateSaCheckList(@RequestBody UpdateCheckListRequest request) {
        return saService.updateSaCheckList(request);
    }
    @GetMapping("/checklist")
    public ResponseModel<List<SAUpdateCheckList>> getChecklistsByAssetNumberAndCheckTypes(
            @RequestParam("assetNumber") String assetNumber,
            @RequestParam(value = "checkListType") String checkListType) {
        return saService.getChecklistsByAssetNumberAndCheckTypes(assetNumber,checkListType);
    }

    @GetMapping("/checklistExecution")
    public ResponseModel<List<SACheckListExecution>> getChecklistsExecutionByAssetNumberAndCheckTypes(
            @RequestParam("assetNumber") String assetNumber,
            @RequestParam(value = "checkListType") String checkListType) {
        return saService.getChecklistsExecutionByAssetNumberAndCheckTypes(assetNumber,checkListType);
    }

    @PostMapping("/checksValidate")
    public ResponseModel<String> validate(@RequestBody SAValidateRequest request){
        return saService.validate(request);
    }
@PostMapping("/checkListExecution")
public ResponseModel<String> checkListExecution(@RequestBody List<CheckListExecutionRequest> requests) {
    return saService.validateAndUpdateCheckListExecution(requests);
}

    @PostMapping(value = "addSAImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<ImageResponse> aimImage(@RequestParam(value = "file",required = false) MultipartFile multipartFile)throws IOException {
        String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/SA";
        return saService.saveImageToStorage(uploadDirectory, multipartFile);
    }

    @DeleteMapping("/deleteNotInDatabase")
    public ResponseModel<ImageResponse> deleteImagesNotInDatabase() {
        return saService.deleteAllImagesInFolder();
    }

    @GetMapping("/findByDateTime")
    public ResponseModel<List<SA>> findSABetweenDateTime(@RequestParam(required = false) LocalDateTime from,
                                                         @RequestParam(required = false) LocalDateTime to,
                                                         @RequestParam(required = false) String organizationCode,
                                                         @RequestParam(required = false, defaultValue = "0") String requestPage) {
        return saService.findSaByDateTime(organizationCode, from, to, requestPage);
    }

    @GetMapping("/saReportFilter")
    public ResponseModel<List<SA>> getSAs(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String  area,
            @RequestParam FilterType filterType,
            @RequestParam(required = false) String  organizationCode,
            @RequestParam(required = false, defaultValue = "0") String requestPage,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return saService.reportLists(department,area,filterType,organizationCode,requestPage, startDate, endDate);
    }

    @GetMapping("/areaList")
    public ResponseModel<List<String>> getDistinctAreas() {
        try {
            List<String> distinctAreas = saRepository.findByArea();
            return new ResponseModel<>(true, "Areas List", distinctAreas);
        } catch (Exception e) {
            return new ResponseModel<>(false, "No Area Record", null);
        }
    }

    @GetMapping("/getUserPendingList")
    public ResponseModel<List<SA>> getPendingFMEAs(@RequestParam String username, @RequestParam String organizationCode) {
        return saService.findPending(username,organizationCode);
    }

}


