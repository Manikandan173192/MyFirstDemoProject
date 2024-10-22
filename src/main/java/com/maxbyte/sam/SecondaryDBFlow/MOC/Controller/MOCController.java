package com.maxbyte.sam.SecondaryDBFlow.MOC.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Repository.MOCRepository;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Service.MOCService;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.Response.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping("/api/moc")
public class MOCController extends CrudController<MOC,Integer> {
    @Autowired
    MOCService mocService;
    @Autowired
    MOCRepository mocRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;
    @Autowired
    Environment environment;
    @Override
    public ServiceInterface<MOC, Integer> service() {
        return mocService;
    }

    @GetMapping("")
    public ResponseModel<List<MOC>> list(@RequestParam(required = false)Boolean isActive,
                                         @RequestParam(required = false)  String initiatorName,
                                         @RequestParam(required = false)  String assetNumber,
                                         @RequestParam(required = false) String assetDescription,
                                         @RequestParam(required = false) String department,
                                         @RequestParam(required = false) String organizationCode,
                                         @RequestParam(required = false, defaultValue = "0") String requestPage,
                                         @RequestParam(required = false) String mocNumber) {

//        // If page or size parameters are not provided, use default values
//        int pageNumber = page != null ? page : defaultPage;
//        int pageSize = size != null ? size : defaultSize;

        return this.mocService.list(isActive,initiatorName, assetNumber, assetDescription,
                department, mocNumber,organizationCode, requestPage);
    }
    @GetMapping("/findByDateTime")
    public ResponseModel<List<MOC>> findMOCBetweenDateTime(@RequestParam(required = false) String organizationCode,
                                                           @RequestParam(required = false) LocalDateTime from,
                                                           @RequestParam(required = false) LocalDateTime to,
                                                           @RequestParam(required = false, defaultValue = "0") String requestPage) {
        return this.mocService.findMOCByDateTime(organizationCode,from, to, requestPage);
    }

    @DeleteMapping("/deleteNotInDatabase")
    public ResponseModel<String> deleteImagesNotInDatabase() {
        return mocService.deleteAllImagesInFolder();
    }
    @GetMapping("/getFilterNumber")
    public ResponseModel<List<FilterNumberResponse>> getFilterNumberResponse(String organizationCode){
        return mocService.getFilterNumber(organizationCode);
    }
    @PostMapping("/addMOC")
    public ResponseModel<String> addMOCData(@RequestBody AddMOCRequest mocRequest){
        return mocService.addMOC(mocRequest);
    }
    @PutMapping("/updateMoc")
    public ResponseModel<String> updateMOCData(@RequestParam(required = false) String mocNo, @RequestBody MOC mocData){
        return mocService.updateMoc(mocNo,mocData);
    }

    @PostMapping(value = "addMOCImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<ImageResponse> mocImage(@RequestParam(value = "file",required = false) MultipartFile multipartFile)throws IOException {
        String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/MOC";
        return mocService.saveImageToStorage(uploadDirectory, multipartFile);
    }

    @PostMapping("/addOrUpdateStepOne")
    public ResponseModel<String> addStepOne(@RequestBody AddMOCStepOneRequest stepOneRequest){
        return mocService.addMOCStepOne(stepOneRequest);
    }

    @GetMapping("/getStepOne")
    public ResponseModel<MOCStepOne> getStepOne(String mocNumber){
        return mocService.getMOCStepOne(mocNumber);
    }

    @PostMapping("/addOrUpdateStepTwo")
    public ResponseModel<String> addStepTwo(@RequestBody AddMOCStepTwoRequest stepTwoRequest){
        return mocService.addMOCStepTwo(stepTwoRequest);
    }

    @GetMapping("/getStepTwo")
    public ResponseModel<List<MOCStepTwo>> getStepTwo(String mocNumber){
        return mocService.getMOCStepTwo(mocNumber);
    }

    @PostMapping("/addOrUpdateStepThree")
    public ResponseModel<String> addStepThree(@RequestBody AddMOCStepThreeRequest stepThreeRequest){
        return mocService.addStepThree(stepThreeRequest);
    }

    @GetMapping("/getStepThree")
    public ResponseModel<MOCStepThree> getStepThree(String mocNumber){
        return mocService.getMOCStepThree(mocNumber);
    }
    @PostMapping("/addOrUpdateStepFour")
    public ResponseModel<String> addStepFour(@RequestBody AddMOCStepFourRequest stepFourRequest){
        return mocService.addStepFour(stepFourRequest);
    }

    @GetMapping("/getStepFour")
    public ResponseModel<MOCStepFour> getStepFour(String mocNumber){
        return mocService.getMOCStepFour(mocNumber);
    }

    @PostMapping("/addOrUpdateStepFive")
    public ResponseModel<String> addStepFive(@RequestBody AddMOCStepFiveRequest stepFiveRequest){
        return mocService.addMOCStepFive(stepFiveRequest);
    }

    @GetMapping("/getStepFive")
    public ResponseModel<MOCStepFive> getStepFive(@RequestParam(required = false) String mocNumber){
        return mocService.getMOCStepFive(mocNumber);
    }

    @PostMapping("/addOrUpdateStepSix")
    public ResponseModel<String> addStepSix(@RequestBody AddMOCStepSixRequest stepSixRequest){
        return mocService.addStepSix(stepSixRequest);
    }

    @GetMapping("/getStepSix")
    public ResponseModel<MOCStepSix> getStepSix(String mocNumber){
        return mocService.getMOCStepSix(mocNumber);
    }

    @PostMapping("/validateStepSix")
    public ResponseModel<String> validateMOCStepSix(@RequestBody ValidateMOCStepSixRequest request) {
        return mocService.validateMOCStepSix(request);

    }
    @PostMapping("/addOrUpdateStepSeven")
    public ResponseModel<String> addStepSeven(@RequestBody AddMOCStepSevenRequest stepSevenRequest){
        return mocService.addStepSeven(stepSevenRequest);
    }

    @GetMapping("/getStepSeven")
    public ResponseModel<MOCStepSeven> getStepSeven(String mocNumber){
        return mocService.getMOCStepSeven(mocNumber);
    }

    @PostMapping("/validateStepSeven")
    public ResponseModel<String> validateMOCStepSeven(@RequestBody AddMOCStepSevenRequest request) {
        return mocService.validateMOCStepSeven(request);

    }
    @PostMapping("/addOrUpdateStepEight")
    public ResponseModel<String> addStepEight(@RequestBody AddMOCStepEightRequest stepEightRequest){
        return mocService.addStepEight(stepEightRequest);
    }
    @GetMapping("/getStepEight")
    public ResponseModel<MOCStepEight> getStepEight(String mocNumber){
        return mocService.getMOCStepEight(mocNumber);
    }

    @PostMapping("/validateStepEight")
    public ResponseModel<String> validateMOCStepEight(@RequestBody AddMOCStepEightRequest request) {
        return mocService.validateMOCStepEight(request);

    }

    @PostMapping("/addOrUpdateStepNine")
    public ResponseModel<String> addStepNine(@RequestBody AddMOCStepNineRequest stepNineRequest){
        return mocService.addStepNine(stepNineRequest);
    }

    @GetMapping("/getStepNine")
    public ResponseModel<MOCStepNine> getStepNine(String mocNumber){
        return mocService.getMOCStepNine(mocNumber);
    }

    @PostMapping("/validateStepNine")
    public ResponseModel<String> validateMOCStepEight(@RequestBody AddMOCStepNineRequest request) {
        return mocService.validateMOCStepNine(request);

    }
    @GetMapping("/getAllApproverAndStatus")
    public ResponseModel<List<ApproverResponse>> getAllStatusAndApprovername(String mocNumber){
        return mocService.getAllApproverAndStatus(mocNumber);
    }
//    @PostMapping("/setAllApproverAndStatus")
//    public ResponseModel<String> setAllStatusAndApprovername(Str revertBackRequest){
//        return mocService.setAllApproverAndStatus(revertBackRequest);
//    }
    @PostMapping("/SetAllApprovers")
    public ResponseModel<String> setAllApprovers(@RequestParam String mocNumber){
        return mocService.setAllApprovers(mocNumber);
    }
//    @GetMapping("/mocReportFilter")
//    public ResponseModel<List<MOC>> getMOCs(
//            @RequestParam(required = false) String  organizationCode,
//            @RequestParam(required = false) String department,
//            @RequestParam(required = false) String  area,
//            @RequestParam FilterType filterType,
//            @RequestParam(required = false, defaultValue = "0") String requestPage,
//            @RequestParam(required = false) LocalDate startDate,
//            @RequestParam(required = false) LocalDate endDate) {
//        return mocService.reportLists(organizationCode,department,area,filterType,requestPage, startDate, endDate);
//    }

    @GetMapping("/mocReportFilter")
    public ResponseModel<List<MOCCombainedRequest>> getMOCs(
            @RequestParam(required = false) String organizationCode,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String area,
            @RequestParam FilterType filterType,
            @RequestParam(required = false, defaultValue = "0") String requestPage,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return mocService.reportLists(organizationCode, department, area, filterType, requestPage, startDate, endDate);
    }
    @GetMapping("/areaList")
    public ResponseModel<List<String>> getDistinctAreas() {
        try {
            List<String> distinctAreas = mocRepository.findByArea();
            return new ResponseModel<>(true, "Areas List", distinctAreas);
        } catch (Exception e) {
            return new ResponseModel<>(false, "No Area Record", null);
        }
    }

    @GetMapping("/getUserPendingList")
    public ResponseModel<List<MOC>> getPendingMOCs(@RequestParam String username, @RequestParam String organizationCode) {
        return mocService.findPending(username,organizationCode);
    }
}
