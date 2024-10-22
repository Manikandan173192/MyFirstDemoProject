package com.maxbyte.sam.SecondaryDBFlow.AIM.Controller;

import com.maxbyte.sam.SecondaryDBFlow.AIM.APIRequest.AddAIMRequest;
import com.maxbyte.sam.SecondaryDBFlow.AIM.APIRequest.ValidateAIMRequest;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Repository.AimImageRepository;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Repository.AimRepository;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Service.AimService;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest.ValidateRequest;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
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
@RequestMapping("/api/aim")
public class AIMController extends CrudController<Aim,Integer> {

    @Autowired
    private Environment environment;
    @Autowired
    private AimService aimService;
    @Autowired
    AimRepository aimRepository;

    @Value("${pagination.default-size}")
    private int defaultSize;
    @Override
    public ServiceInterface service() {
        return aimService;
    }

    @GetMapping("")
    public ResponseModel<List<Aim>> list(@RequestParam(required = false)Boolean isActive,
                          @RequestParam(required = false)  String organizationCode,
                          @RequestParam(required = false)  String assetNumber,
                          @RequestParam(required = false) String assetDescription,
                          @RequestParam(required = false) String department,
                          @RequestParam(required = false) String documentNumber,
                          @RequestParam(required = false, defaultValue = "0") String requestPage,
                          @RequestParam(required = false) String woNumber){

        return this.aimService.list(isActive,organizationCode, assetNumber, assetDescription,
                department, documentNumber,requestPage, woNumber);
    }

    @GetMapping("/findByDateTime")
    public ResponseModel<List<Aim>> findAimBetweenDateTime(@RequestParam(required = false) LocalDateTime from,
                                                           @RequestParam(required = false) LocalDateTime to,
                                                           @RequestParam(required = false) String organizationCode,
                                                           @RequestParam(required = false, defaultValue = "0") String requestPage
                                                           )
    {
        return this.aimService.findAIMByDateTime(organizationCode, from, to, requestPage);
    }
    @GetMapping("/getFilterNumber")
    public ResponseModel<List<FilterNumberResponse>> getFilterNumberResponse(String organizationCode){
        return aimService.getFilterNumber(organizationCode);
    }
    @PostMapping("/addAim")
    public ResponseModel<String> addAimData(@RequestBody AddAIMRequest aimRequest){
        return aimService.addAim(aimRequest);
    }

    @PutMapping("/updateAim")
    public ResponseModel<String> updateAimData(@RequestParam(required = false) String aimNo, @RequestBody Aim aimData){
        return aimService.updateAim(aimNo,aimData);
    }

    @PostMapping(value = "addAIMImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<ImageResponse> aimImage(@RequestParam(value = "file",required = false) MultipartFile multipartFile)throws IOException{
        String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/AIM";
        return aimService.saveImageToStorage(uploadDirectory, multipartFile);
    }

    @DeleteMapping("/deleteNotInDatabase")
    public ResponseModel<String> deleteImagesNotInDatabase() {
        return aimService.deleteAllImagesInFolder();
    }


    @PostMapping("/validateAIM")
    public ResponseModel<String> validate(@RequestBody ValidateAIMRequest validateAIMRequest){
        return aimService.validateAIM(validateAIMRequest);
    }
    @GetMapping("/aimReportFilter")
    public ResponseModel<List<Aim>> getAIMs(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String  area,
            @RequestParam FilterType filterType,
            @RequestParam(required = false) String  organizationCode,
            @RequestParam(required = false, defaultValue = "0") String requestPage,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return aimService.reportLists(organizationCode,department,area,filterType,requestPage, startDate, endDate);//Date only
    }

    @GetMapping("/areaList")
    public ResponseModel<List<String>> getDistinctAreas() {
        try {

            List<String> distinctAreas = aimRepository.findByArea();
            return new ResponseModel<>(true, "Areas List", distinctAreas);
        } catch (Exception e) {
            return new ResponseModel<>(false, "No Area Record", null);
        }
    }

    @GetMapping("/getUserPendingList")
    public ResponseModel<List<Aim>> getPendingAims(@RequestParam String username, @RequestParam String organizationCode) {
        return aimService.findPending(username,organizationCode);
    }
}
