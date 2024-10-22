package com.maxbyte.sam.SecondaryDBFlow.RCA.Controller;

import com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest.ValidateRequest;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Repository.RCARepository;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Service.RCAService;
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
@RequestMapping("/api/rca")
public class RCAController extends CrudController<RCA,Integer> {

    @Autowired
    private Environment environment;

    @Autowired
    private RCAService rcaService;
    @Autowired
    RCARepository rcaRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;
    @Override
    public ServiceInterface service() {
        return rcaService;
    }

    @GetMapping("")
    public ResponseModel<List<RCA>> list(@RequestParam(required = false) Boolean isActive,
                                         @RequestParam(required = false)  String organizationCode,
                                         @RequestParam(required = false)  String assetNumber,
                                         @RequestParam(required = false) String assetDescription,
                                         @RequestParam(required = false) String department,
                                         @RequestParam(required = false) String documentNumber,
                                         @RequestParam(required = false, defaultValue = "0") String requestPage,
                                         @RequestParam(required = false) String woNumber) {

        return this.rcaService.list(isActive,organizationCode, assetNumber, assetDescription,
                department, documentNumber, woNumber, requestPage);
    }

    @GetMapping("/findByDateTime")
    public ResponseModel<List<RCA>> findRCABetweenDateTime(@RequestParam(required = false) LocalDateTime from,
                                                           @RequestParam(required = false) LocalDateTime to,
                                                           @RequestParam(required = false, defaultValue = "0") String requestPage,
                                                           @RequestParam(required = false) String organizationCode) {
        return this.rcaService.findRCAByDateTime(organizationCode, from, to, requestPage);
    }

    @GetMapping("/getFilterNumber")
    public ResponseModel<List<FilterNumberResponse>> getFilterNumberResponse(String organizationCode){
        return rcaService.getFilterNumber(organizationCode);
    }

    @DeleteMapping("/deleteNotInDatabase")
    public ResponseModel<String> deleteImagesNotInDatabase() {
        return rcaService.deleteAllImagesInFolder();
    }

    @PostMapping("/addRCA")
    public ResponseModel<String> addRCAData(@RequestBody AddRCARequest rcaRequest){
        return rcaService.addRCA(rcaRequest);
    }

    @PostMapping(value = "addRCAImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<ImageResponse> rcaImage(@RequestParam(value = "file",required = false) MultipartFile multipartFile)throws IOException{
        String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/RCA";
        return rcaService.saveImageToStorage(uploadDirectory, multipartFile);
    }

    @PostMapping("/addOrUpdateStepOne")
    public ResponseModel<String> addStepOne(@RequestBody AddRCAStepOneRequest stepOneRequest){
        return rcaService.addRCAStepOne(stepOneRequest);
    }

    @GetMapping("/getStepOne")
    public ResponseModel<RCAStepOne> getStepOne( @RequestParam(required = false) String rcaNo){
        return rcaService.getRCAStepOne(rcaNo);
    }

    @PostMapping("/addOrUpdateStepTwo")
    public ResponseModel<String> addStepTwo(@RequestBody AddRCAStepTwoRequest stepTwoRequest){
        return rcaService.addRCAStepTwo(stepTwoRequest);
    }

    @GetMapping("/getStepTwo")
    public ResponseModel<RCAStepTwo> getStepTwo(@RequestParam(required = false) String rcaNo){
        return rcaService.getRCAStepTwo(rcaNo);
    }

    @PostMapping("/addIsNotQuestions")
    public ResponseModel<String> addIsNotQuestions(@RequestBody AddIsNotQuestions isNotQuestions){
        return rcaService.addRCAIsNotQuestions(isNotQuestions);
    }
    @GetMapping("/getIsNotQuestions")
    public ResponseModel<List<RCAIsNotQuestions>> getIsNotQuestions(){
        return rcaService.getRCAIsNotQuestions();
    }

    @PutMapping("/updateIsNotQuestions")
    public ResponseModel<String> updateIsNotQuestions(@RequestParam Integer id, @RequestBody AddIsNotQuestions isNotQuestions){
        return rcaService.updateRCAIsNotQuestions(id, isNotQuestions);
    }

    @DeleteMapping("/deleteIsNotQuestions")
    public ResponseModel<String> deleteIsNotQuestions(@RequestParam Integer id){
        return rcaService.deleteRCAIsNotQuestions(id);
    }

    @PostMapping("/addOrUpdateStepThree")
    public ResponseModel<String> addStepThree(@RequestBody AddRCAStepThreeRequest stepThreeRequest){
        return rcaService.addRCAStepThree(stepThreeRequest);
    }
    @GetMapping("/getStepThree")
    public ResponseModel<List<RCAStepThreeQuestions>> getStepThree(@RequestParam(required = false) String rcaNo){
        return rcaService.getRCAStepThree(rcaNo);
    }

    @PostMapping("/addOrUpdateStepFour")
    public ResponseModel<String> addStepFour(@RequestBody AddRCAStepFourRequest stepFourRequest){
        return rcaService.addRCAStepFour(stepFourRequest);
    }

    @GetMapping("/getStepFour")
    public ResponseModel<List<RCAStepFourCA>> getStepFour(@RequestParam(required = false) String rcaNo){
        return rcaService.getRCAStepFour(rcaNo);
    }

    @PostMapping("/addOrUpdateStepFive")
    public ResponseModel<String> addStepFive(@RequestBody AddRCAStepFiveRequest stepFiveRequest){
        return rcaService.addRCAStepFive(stepFiveRequest);
    }

    @GetMapping("/getStepFive")
    public ResponseModel<RCAStepFive> getStepFive(@RequestParam(required = false) String rcaNo){
        return rcaService.getRCAStepFive(rcaNo);
    }

    @PostMapping("/addOrUpdateStepSix")
    public ResponseModel<String> addStepSix(@RequestBody AddRCAStepSixRequest stepSixRequest){
        return rcaService.addRCAStepSix(stepSixRequest);
    }

    @GetMapping("/getStepSix")
    public ResponseModel<RCAStepSix> getStepSix(@RequestParam(required = false) String rcaNo){
        return rcaService.getRCAStepSix(rcaNo);
    }

    @PostMapping("/addOrUpdateStepSevenCA")
    public ResponseModel<String> addStepSevenCA(@RequestBody AddRCAStepSevenCARequest stepSevenRequest){
        return rcaService.addRCAStepSevenCA(stepSevenRequest);
    }

    @PostMapping("/addOrUpdateStepSevenPA")
    public ResponseModel<String> addStepSevenPA(@RequestBody AddRCAStepSevenPARequest stepSevenRequest){
        return rcaService.addRCAStepSevenPA(stepSevenRequest);
    }

    @GetMapping("/getStepSeven")
    public ResponseModel<RCAStepSeven> getStepSeven(@RequestParam(required = false) String rcaNo){
        return rcaService.getRCAStepSeven(rcaNo);
    }


    @PostMapping("/addOrUpdateStepEight")
    public ResponseModel<String> addStepEight(@RequestBody AddRCAStepEightRequest stepEightRequest){
        return rcaService.addRCAStepEight(stepEightRequest);
    }

    @GetMapping("/getStepEight")
    public ResponseModel<SharingResponse> getStepEight(@RequestParam(required = false) String rcaNo){
        return rcaService.getRCAStepEight(rcaNo);
    }

    @PostMapping("/validateRca")
    public ResponseModel<String> validateStepEight(@RequestBody ValidateRequest stepEightRequest){
        return rcaService.validateRCAStepEight(stepEightRequest);
    }

//    @GetMapping("/rcaReportFilter")
//    public ResponseModel<List<RCA>> getRCAs(@RequestParam(required = false) String  organizationCode,
//            @RequestParam(required = false) String department,
//            @RequestParam(required = false) String  area,
//            @RequestParam FilterType filterType,
//            @RequestParam(required = false, defaultValue = "0") String requestPage,
//            @RequestParam(required = false) LocalDate startDate,
//            @RequestParam(required = false) LocalDate endDate) {
//        return rcaService.reportLists(organizationCode,department,area,filterType,requestPage, startDate, endDate);
//    }
    @GetMapping("/rcaReportFilter")
    public ResponseModel<List<RCACombinedDTO>> getRCAs(@RequestParam(required = false) String  organizationCode,
                                                       @RequestParam(required = false) String department,
                                                       @RequestParam(required = false) String  area,
                                                       @RequestParam FilterType filterType,
                                                       @RequestParam(required = false, defaultValue = "0") String requestPage,
                                                       @RequestParam(required = false) LocalDate startDate,
                                                       @RequestParam(required = false) LocalDate endDate) {
        return rcaService.reportLists(organizationCode,department,area,filterType,requestPage, startDate, endDate);
    }

    @GetMapping("/areaList")
    public ResponseModel<List<String>> getDistinctAreas() {
        try {
            List<String> distinctAreas = rcaRepository.findByArea();
            return new ResponseModel<>(true, "Areas List", distinctAreas);
        } catch (Exception e) {
            return new ResponseModel<>(false, "No Area Record", null);
        }
    }

    @GetMapping("/getUserPendingList")
    public ResponseModel<List<RCA>> getPendingRCAs(@RequestParam(required = false) String username, @RequestParam(required = false) String organizationCode) {
        return rcaService.findPending(username,organizationCode);
    }
}
