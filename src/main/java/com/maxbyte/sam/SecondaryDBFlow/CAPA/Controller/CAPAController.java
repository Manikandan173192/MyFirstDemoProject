package com.maxbyte.sam.SecondaryDBFlow.CAPA.Controller;

import com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPA;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepOne;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepTwo;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository.CAPARepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Service.CAPAService;
import com.maxbyte.sam.SecondaryDBFlow.Response.*;
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
@RequestMapping("/api/capa")
public class CAPAController extends CrudController<CAPA,Integer> {

    @Autowired
    private Environment environment;

    @Autowired
    private CAPAService capaService;
    @Autowired
    CAPARepository capaRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;
    @Override
    public ServiceInterface service() {
        return capaService;
    }

    @GetMapping("")
    public ResponseModel<List<CAPA>> list(@RequestParam(required = false)Boolean isActive,
                          @RequestParam(required = false)  String organizationCode,
                          @RequestParam(required = false)  String assetNumber,
                          @RequestParam(required = false) String assetDescription,
                          @RequestParam(required = false) String department,
                          @RequestParam(required = false) String documentNumber,
                                          @RequestParam(required = false, defaultValue = "0") String requestPage,
                          @RequestParam(required = false) String woNumber){


        return this.capaService.list(isActive,organizationCode, assetNumber, assetDescription,
                department, documentNumber, woNumber, requestPage);
    }

    @GetMapping("/findByDateTime")
    public ResponseModel<List<CAPA>> findCAPABetweenDateTime(@RequestParam(required = false) LocalDateTime from,
                                                             @RequestParam(required = false) LocalDateTime to,
                                                             @RequestParam(required = false) String organizationCode,
                                                             @RequestParam(required = false, defaultValue = "0") String requestPage) {
        return this.capaService.findCAPAByDateTime(organizationCode, from, to, requestPage);
    }

    @PostMapping("/addCAPA")
    public ResponseModel<String> addCAPAData(@RequestBody AddCAPARequest capaRequest){
        return capaService.addCAPA(capaRequest);
    }

    @PostMapping(value = "addCAPAImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<ImageResponse> capaImage(@RequestParam(value = "file",required = false) MultipartFile multipartFile)throws IOException{
        String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/CAPA";
        return capaService.saveImageToStorage(uploadDirectory, multipartFile);
    }

    @DeleteMapping("/deleteNotInDatabase")
    public ResponseModel<String> deleteImagesNotInDatabase() {
        return capaService.deleteAllImagesInFolder();
    }


    @GetMapping("/getFilterNumber")
    public ResponseModel<List<FilterNumberResponse>> getFilterNumberResponse(String organizationCode){
        return capaService.getFilterNumber(organizationCode);
    }

    @PostMapping("/addOrUpdateStepOne")
    public ResponseModel<String> addStepOne(@RequestBody AddCAPAStepOneRequest stepOneRequest){
        return capaService.addCAPAStepOne(stepOneRequest);
    }

    @GetMapping("/getStepOne")
    public ResponseModel<CAPAStepOne> getStepOne(@RequestParam(required = false) String capaNo){
        return capaService.getCAPAStepOne(capaNo);
    }

    @PostMapping("/addOrUpdateStepTwo")
    public ResponseModel<String> addStepTwo(@RequestBody AddCAPAStepTwoRequest stepTwoRequest){
        return capaService.addCAPAStepTwo(stepTwoRequest);
    }

    @GetMapping("/getStepTwo")
    public ResponseModel<CAPAStepTwo> getStepTwo(@RequestParam(required = false) String capaNo){
        return capaService.getCAPAStepTwo(capaNo);
    }

    @PostMapping("/addOrUpdateStepThree")
    public ResponseModel<String> addStepThree(@RequestBody AddCAPAStepThreeRequest stepThreeRequest){
        return capaService.addCAPAStepThree(stepThreeRequest);
    }

    @GetMapping("/getStepThree")
    public ResponseModel<SharingResponse> getStepThree(@RequestParam(required = false) String capaNo){
        return capaService.getCAPAStepThree(capaNo);
    }
    @PostMapping("/validateCapa")
    public ResponseModel<String> validateStepThree(@RequestBody ValidateRequest stepThreeRequest){
        return capaService.validateCAPAStepThree(stepThreeRequest);
    }
    @GetMapping("/capaReportFilter")
    public ResponseModel<List<CAPA>> getCAPAs(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String  area,
            @RequestParam FilterType filterType,
            @RequestParam(required = false) String  organizationCode,
            @RequestParam(required = false, defaultValue = "0") String requestPage,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return capaService.reportLists(organizationCode,department,area,filterType,requestPage, startDate, endDate);
    }

    @GetMapping("/areaList")
    public ResponseModel<List<String>> getDistinctAreas() {
        try {
            List<String> distinctAreas = capaRepository.findByArea();
            return new ResponseModel<>(true, "Areas List", distinctAreas);
        } catch (Exception e) {
            return new ResponseModel<>(false, "No Area Record", null);
        }
    }

    @GetMapping("/getUserPendingList")
    public ResponseModel<List<CAPA>> getPendingCAPAs(@RequestParam String username, @RequestParam String organizationCode) {
        return capaService.findPending(username,organizationCode);
    }
}
