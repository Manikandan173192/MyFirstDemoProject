package com.maxbyte.sam.SecondaryDBFlow.FMEA.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository.FMEARepository;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Service.FMEAService;

import com.maxbyte.sam.SecondaryDBFlow.Response.FilterNumberResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterType;
import com.maxbyte.sam.SecondaryDBFlow.Response.ImageResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
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
@RequestMapping("/api/fmea")
public class FMEAController extends CrudController<FMEA, Integer> {
    @Autowired
    FMEAService fmeaService;
    @Autowired
    FMEARepository fmeaRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;
    @Autowired
    Environment environment;
    @Override
    public ServiceInterface<FMEA, Integer> service() {
        return fmeaService;
    }

    @GetMapping("")
    public ResponseModel<List<FMEA>> list(@RequestParam(required = false) Boolean isActive,
                                          @RequestParam(required = false) String organizationCode,
                                          @RequestParam(required = false) String assetNumber,
                                          @RequestParam(required = false) String assetDescription,
                                          @RequestParam(required = false) String department,
                                          @RequestParam(required = false) String woNumber,
                                          @RequestParam(required = false, defaultValue = "0") String requestPage,
                                          @RequestParam(required = false) String fmeaNumber){
        // If page or size parameters are not provided, use default values
//        int pageNumber = page != null ? page : defaultPage;
//        int pageSize = size != null ? size : defaultSize;
        return this.fmeaService.list(isActive, organizationCode, assetNumber, assetDescription,
                department,woNumber, fmeaNumber, requestPage);

    }

    @GetMapping("/getFilterNumber")
    public ResponseModel<List<FilterNumberResponse>> getFilterNumberResponse(String organizationCode){
        return fmeaService.getFilterNumber(organizationCode);
    }

    @PostMapping(value = "addFMEAImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<ImageResponse> fmeaImageUpload(@RequestParam(value = "file",required = false) MultipartFile multipartFile)throws IOException {
        String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/FMEA";
        return fmeaService.saveImageToStorage(uploadDirectory, multipartFile);
    }

    @DeleteMapping("/deleteNotInDatabase")
    public ResponseModel<String> deleteImagesNotInDatabase() {
        return fmeaService.deleteAllImagesInFolder();
    }

    @PostMapping("/addfmea")
    public ResponseModel<String>addFmea(@RequestBody FMEARequest fmeaRequest){
        return this.fmeaService.addFmea(fmeaRequest);
    }
    @PostMapping("/addOrUpdateDocumentAndDrawing")
    public ResponseModel<String> addDocumentAndDrawing(@RequestBody AddDocumentAndDrawingRequest documentAndDrawingRequest){
        return fmeaService.addDocumentAndDrawing(documentAndDrawingRequest);
    }

    @GetMapping("/getDocumentAndDrawing")
    public ResponseModel<List<FMEA>> getDocumentAndDrawing(String fmeaNumber){
        return fmeaService.getDocumentAndDrawing(fmeaNumber);
    }
    @PostMapping("/addOrUpdateRevisionDate")
    public ResponseModel<String> addRevisionDate(@RequestBody RevisionDateRequest processRequest){
        return fmeaService.addRevisionDate(processRequest);
    }
    @GetMapping("/getRevisionDate")
    public ResponseModel<List<FMEA>> getRevisionDate(String fmeaNumber){
        return fmeaService.getRevisionDate(fmeaNumber);
    }
    @PostMapping("/addOrUpdateFMEADSProcess")
    public ResponseModel<String> addDSProcess(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAProcessRequest fmeaProcessRequest){
        return fmeaService.addOrUpdateFMEADSProcess( /*fmeaNumber,*/fmeaProcessRequest);
    }

    @GetMapping("/getFMEADSProcess")
    public ResponseModel<List<FMEADSProcess>> getStepOne(String fmeaNumber){
        return fmeaService.getFMEADSProcess(fmeaNumber);
    }
    @PostMapping("/addOrUpdateFMEADSFunction")
    public ResponseModel<String> addDSFunction(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEADSFunctionRequest addFMEADSFunctionRequest){
        return fmeaService.addOrUpdateFmeaDsFunction(/*fmeaNumber,*/addFMEADSFunctionRequest);
    }

    @GetMapping("/getFMEADSFunction")
    public ResponseModel<List<FMEADSFunction>> getStepTwo(String fmeaNumber){
        return fmeaService.getFMEADSFunction(fmeaNumber);
    }


    @PostMapping("/addFMEADSFailureMode")
    public ResponseModel<String>addFmeadsFailureMode(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEADSFailureModeRequest fmeadsFailureModeRequest){
        return this.fmeaService.addOrUpdateFmeaDsFailureMode(/*fmeaNumber,*/fmeadsFailureModeRequest);
    }

    @PostMapping("/addFMEADSEffect")
    public ResponseModel<String>addDSEffect(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEADSEffectRequest fmeaEffectRequest){
        return this.fmeaService.addOrUpdateFmeaDsEffect(/*fmeaNumber,*/fmeaEffectRequest);
    }

    @PostMapping("/addFMEADSCause")
    public ResponseModel<String>addOrUpdateCause(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEADSCauseRequest fmeadsCauseRequest){
        return this.fmeaService.addOrUpdateCause(/*fmeaNumber,*/fmeadsCauseRequest);
    }
    @PostMapping("/addFMEADSAction")
    public ResponseModel<String>addOrUpdateAction(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEADSActionRequest fmeadsActionRequest){
        return this.fmeaService.addOrUpdateFMEADSAction(/*fmeaNumber,*/fmeadsActionRequest);
    }

    @PostMapping("/addFMEADSActionTaken")
    public ResponseModel<String>addOrUpdateAction(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEADSActionTakenRequest fmeadsActionTakenRequest){
        return this.fmeaService.addFMEADAddSActionTaken(/*fmeaNumber,*/fmeadsActionTakenRequest);
    }

    @DeleteMapping("/deleteDS")
    public ResponseModel<String> deleteProcess(
            @RequestParam("fmeaNumber") String fmeaNumber,
            @RequestParam("entityId") Integer entityId,
            @RequestParam("entityType") TypeDS processTypeDS) {
        return fmeaService.deleteProcess(fmeaNumber, entityId, processTypeDS);
    }

    @DeleteMapping("/deleteUS")
    public ResponseModel<String> deleteUSProcess(
            @RequestParam("fmeaNumber") String fmeaNumber,
            @RequestParam("entityId") Integer entityId,
            @RequestParam("entityType") TypeUS processTypeUS) {
        return fmeaService.deleteUSProcess(fmeaNumber, entityId, processTypeUS);
    }

    @PostMapping("/addOrUpdateFMEAUSFunction")
    public ResponseModel<String> addUSFunctions(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSFunctionRequest addFMEAUSFunctionRequest){
        return fmeaService.addUSFunction( /*fmeaNumber,*/addFMEAUSFunctionRequest);
    }
    @GetMapping("/getFMEAUSFunction")
    public ResponseModel<List<FMEAUSFunction>> getFmeaUSFunction(String fmeaNumber){
        return fmeaService.getFMEAUSFunction(fmeaNumber);
    }

    @PostMapping("/addFMEAUSFailureMode")
    public ResponseModel<String>addFmeaUSFailureMode(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSFailureModeRequest fmeaUSFailureModeRequest){
        return this.fmeaService.addOrUpdateFmeaUSFailureMode(/*fmeaNumber,*/fmeaUSFailureModeRequest);
    }

    @PostMapping("/addFMEAUSCause")
    public ResponseModel<String>addOrUpdateUSCause(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSCauseRequest fmeausCauseRequest){
        return this.fmeaService.addOrUpdateUSCause(/*fmeaNumber,*/fmeausCauseRequest);
    }

    /*@PostMapping("/addFMEAUSSubCause")
    public ResponseModel<String>addOrUpdateUSSubCause(@RequestParam String fmeaNumber,@RequestBody AddFMEAUSSubCauseRequest fmeausSubCauseRequest){
        return this.fmeaService.addOrUpdateUSSubCause(fmeaNumber,fmeausSubCauseRequest);
    }
*/
    @PostMapping("/addFMEAUSEffect")
    public ResponseModel<String>addOrUpdateUSEffect(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSEffectRequest fmeausEffectRequest){
        return this.fmeaService.addOrUpdateUSEffect(/*fmeaNumber,*/fmeausEffectRequest);
    }

    @PostMapping("/addFMEAUSAction")
    public ResponseModel<String>addOrUpdateUSAction(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSActionRequest fmeausActionRequest){
        return this.fmeaService.addOrUpdateUSAction(/*fmeaNumber,*/fmeausActionRequest);
    }
    @PostMapping("/FMEAValidate")
    public ResponseModel<String>FMEAvalidate(/*@RequestParam String fmeaNumber,*/@RequestBody FMEAValidateRequest fmeaValidateRequest){
        return this.fmeaService.validate(/*fmeaNumber,*/fmeaValidateRequest);
    }
    @GetMapping("/findByDateTime")
    public ResponseModel<List<FMEA>> findFMEABetweenDateTime(@RequestParam(required = false) LocalDateTime from,
                                                         @RequestParam(required = false) LocalDateTime to,
                                                             @RequestParam(required = false) String organizationCode,
                                                             @RequestParam(required = false, defaultValue = "0") String requestPage
    ) {
        return fmeaService.findFMEAByDateTime(organizationCode, from, to, requestPage);
    }

    //******************************CAUSE1***************************************************


    @PostMapping("/addFMEAUSCause1")
    public ResponseModel<String>addOrUpdateUSCause1(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSCauseRequest fmeausCauseRequest){
        return this.fmeaService.addOrUpdateUSCause1(/*fmeaNumber,*/fmeausCauseRequest);
    }

   /* @PostMapping("/addFMEAUSSubCause1")
    public ResponseModel<String>addOrUpdateUSSubCause1(@RequestParam String fmeaNumber,@RequestBody AddFMEAUSSubCauseRequest fmeausSubCauseRequest){
        return this.fmeaService.addOrUpdateUSSubCause1(fmeaNumber,fmeausSubCauseRequest);
    }*/

    @PostMapping("/addFMEAUSEffect1")
    public ResponseModel<String>addOrUpdateUSEffect1(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSEffectRequest fmeausEffectRequest) {
        return this.fmeaService.addOrUpdateUSEffect1(/*fmeaNumber,*/ fmeausEffectRequest);
    }

    @PostMapping("/addFMEAUSAction1")
    public ResponseModel<String>addOrUpdateUSAction1(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSActionRequest fmeausActionRequest){
        return this.fmeaService.addOrUpdateUSAction1(/*fmeaNumber,*/fmeausActionRequest);
    }

    //******************************************CAUSE2*****************************************************

    @PostMapping("/addFMEAUSCause2")
    public ResponseModel<String>addOrUpdateUSCause2(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSCauseRequest fmeausCauseRequest){
        return this.fmeaService.addOrUpdateUSCause2(/*fmeaNumber,*/fmeausCauseRequest);
    }

    /*@PostMapping("/addFMEAUSSubCause2")
    public ResponseModel<String>addOrUpdateUSSubCause2(@RequestParam String fmeaNumber,@RequestBody AddFMEAUSSubCauseRequest fmeausSubCauseRequest){
        return this.fmeaService.addOrUpdateUSSubCause2(fmeaNumber,fmeausSubCauseRequest);
    }*/

    @PostMapping("/addFMEAUSEffect2")
    public ResponseModel<String>addOrUpdateUSEffect2(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSEffectRequest fmeausEffectRequest) {
        return this.fmeaService.addOrUpdateUSEffect2(/*fmeaNumber,*/ fmeausEffectRequest);
    }

    @PostMapping("/addFMEAUSAction2")
    public ResponseModel<String>addOrUpdateUSAction2(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSActionRequest fmeausActionRequest){
        return this.fmeaService.addOrUpdateUSAction2(/*fmeaNumber,*/fmeausActionRequest);
    }



    //******************************************CAUSE3*****************************************************

    @PostMapping("/addFMEAUSCause3")
    public ResponseModel<String>addOrUpdateUSCause3(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSCauseRequest fmeausCauseRequest){
        return this.fmeaService.addOrUpdateUSCause3(/*fmeaNumber,*/fmeausCauseRequest);
    }

    /*@PostMapping("/addFMEAUSSubCause3")
    public ResponseModel<String>addOrUpdateUSSubCause3(@RequestParam String fmeaNumber,@RequestBody AddFMEAUSSubCauseRequest fmeausSubCauseRequest){
        return this.fmeaService.addOrUpdateUSSubCause3(fmeaNumber,fmeausSubCauseRequest);
    }*/

    @PostMapping("/addFMEAUSEffect3")
    public ResponseModel<String>addOrUpdateUSEffect3(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSEffectRequest fmeausEffectRequest) {
        return this.fmeaService.addOrUpdateUSEffect3(/*fmeaNumber,*/ fmeausEffectRequest);
    }

    @PostMapping("/addFMEAUSAction3")
    public ResponseModel<String>addOrUpdateUSAction3(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSActionRequest fmeausActionRequest){
        return this.fmeaService.addOrUpdateUSAction3(/*fmeaNumber,*/fmeausActionRequest);
    }

    /////////////////////////////caus4

    @PostMapping("/addFMEAUSCause4")
    public ResponseModel<String>addOrUpdateUSCause4(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSCauseRequest fmeausCauseRequest){
        return this.fmeaService.addOrUpdateUSCause4(/*fmeaNumber,*/fmeausCauseRequest);
    }

    @PostMapping("/addFMEAUSEffect4")
    public ResponseModel<String>addOrUpdateUSEffect4(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSEffectRequest fmeausEffectRequest) {
        return this.fmeaService.addOrUpdateUSEffect4(/*fmeaNumber,*/ fmeausEffectRequest);
    }

    @PostMapping("/addFMEAUSAction4")
    public ResponseModel<String>addOrUpdateUSAction4(/*@RequestParam String fmeaNumber,*/@RequestBody AddFMEAUSActionRequest fmeausActionRequest){
        return this.fmeaService.addOrUpdateUSAction4(/*fmeaNumber,*/fmeausActionRequest);
    }
    @GetMapping("/fmeaReportFilter")
    public ResponseModel<List<FMEA>> getFMEAs(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String  area,
            @RequestParam FilterType filterType,
            @RequestParam(required = false) String  organizationCode,
            @RequestParam(required = false, defaultValue = "0") String requestPage,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return fmeaService.reportLists(department,area,filterType,organizationCode,requestPage, startDate, endDate);
    }

    @GetMapping("/areaList")
    public ResponseModel<List<String>> getDistinctAreas() {
        try {
            List<String> distinctAreas = fmeaRepository.findByArea();
            return new ResponseModel<>(true, "Areas List", distinctAreas);
        } catch (Exception e) {
            return new ResponseModel<>(false, "No Area Record", null);
        }
    }

    @GetMapping("/getUserPendingList")
    public ResponseModel<List<FMEA>> getPendingFMEAs(@RequestParam String username, @RequestParam String organizationCode) {
        return fmeaService.findPending(username,organizationCode);
    }
}