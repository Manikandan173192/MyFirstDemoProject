package com.maxbyte.sam.SecondaryDBFlow.IB.Controller;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest.IBAddRequest;
import com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest.IBImageRequest;
import com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest.IBSenderRequest;
import com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest.IBValidationRequest;
import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IB;
import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.ImageType;
import com.maxbyte.sam.SecondaryDBFlow.IB.Repository.IBEntityRepository;
import com.maxbyte.sam.SecondaryDBFlow.IB.Service.IBService;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterNumberResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterType;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/api/IB")
public class IBController extends CrudController<IB, Integer> {

    @Autowired
    private IBService ibService;
    @Autowired
    IBEntityRepository ibEntityRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;

    @Autowired
    private Environment environment;
    @Override
    public ServiceInterface service() {
        return this.ibService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<IBEntity> createIBEntity(@RequestBody IBEntity ibEntity) {
//
//        ibService.generateIbAddress(ibEntity);
//
//        IBEntity savedIBEntity = ibService.saveIBEntity(ibEntity);
//
//
//        return new ResponseEntity<>(savedIBEntity, HttpStatus.CREATED);
//    }

    @GetMapping("")
    public ResponseModel<List<IB>> list(@RequestParam(required = false)Boolean isActive,
                                         @RequestParam(required = false)  String organizationCode,
                                         @RequestParam(required = false)  String assetNumber,
                                         @RequestParam(required = false) String assetDescription,
                                         @RequestParam(required = false) String department,
                                         @RequestParam(required = false) String documentNumber,
                                        @RequestParam(required = false, defaultValue = "0") String requestPage,
                                         @RequestParam(required = false) String woNumber) {

//        // If page or size parameters are not provided, use default values
//        int pageNumber = page != null ? page : defaultPage;
//        int pageSize = size != null ? size : defaultSize;

        return ibService.list(isActive,organizationCode, assetNumber, assetDescription,
                department, documentNumber, woNumber, requestPage);
    }

    @GetMapping("/findByDateTime")
    public ResponseModel<List<IB>> findIBBetweenDateTime(@RequestParam(required = false) LocalDateTime from,
                                                         @RequestParam(required = false) LocalDateTime to,
                                                         @RequestParam(required = false) String organization,
                                                         @RequestParam(required = false, defaultValue = "0") String requestPage) {
        return this.ibService.findIBByDateTime(organization, from, to, requestPage);
    }


    @PostMapping("/addIB")
    public ResponseModel<String> addIbEntity(@RequestBody IBAddRequest ibRequest){

        return ibService.addIbEntity(ibRequest);
    }


    @GetMapping("/getFilterNumber")
    public ResponseModel<List<FilterNumberResponse>> getFilterNumberResponse(String organizationCode){
        return ibService.getFilterNumber(organizationCode);
    }


    @PostMapping("/AddIBSender")
    public ResponseModel<String> addSender1(@RequestBody IBSenderRequest ibRequestBody){
        return ibService.IbAddSender(ibRequestBody);
    }



    @PostMapping(value = "addIBImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<IBImageRequest> IBImageUpload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                                       @RequestParam(required = true) ImageType imageType) throws IOException {

        if ("Upload".equals(imageType.toString())) {
            String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/IB/Upload";

            // Call the method to save the image and associate it with the IBEntity
            return ibService.saveImageToStorage(uploadDirectory, multipartFile, imageType);
        } else if ("Restore".equals(imageType.toString())) {
            String uploadDirectory = environment.getProperty("image.uploadDirectory")+"/IB/Restore";

            // Call the method to save the image and associate it with the IBEntity
            return ibService.saveImageToStorage(uploadDirectory, multipartFile, imageType);
        } else {
            // Handle invalid imageType here
            return new ResponseModel<>(false, "Invalid imageType", null);
        }
    }

    @DeleteMapping("/deleteNotInDatabase")
    public ResponseModel<String> deleteImagesNotInDatabase() {
        return ibService.deleteAllImagesInFolder();
    }



   /* @PostMapping(value = "addIBRestoreImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseModel<ImageResponse> IBRestoreImageUpload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                                             @RequestParam(required = true) Integer IBid) throws IOException {
        String uploadDirectory = environment.getProperty("image.IB.restore.uploadDirectory");

        IB ibEntity = ibService.findByIbNumber(IBid);
        if (ibEntity == null) {
            return new ResponseModel<>(false, "IBEntity not found", null);
        }

        // Call the method to save the image and associate it with the IBEntity
        return ibService.saveImageToStorage(uploadDirectory, multipartFile, ibEntity);
    }*/

    @GetMapping("/listAll")
    public List<IB> listAll(){
        return ibService.list();
    }

//    @PostMapping("/validate")
//    public ResponseEntity<String> validateEntity(@RequestBody IBValidationRequest request) {
//        try {
//            ibService.IBvalidate(request);
//            return ResponseEntity.ok("Entity validated successfully.");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
//        }
//    }

    @PostMapping("/validate")
    public ResponseModel<String> validateCWF(@RequestBody IBValidationRequest request) {
        return ibService.IBvalidate(request);
    }
    @GetMapping("/ibReportFilter")
    public ResponseModel<List<IB>> getIBs(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String  area,
            @RequestParam FilterType filterType,
            @RequestParam(required = false) String  organizationCode,
            @RequestParam(required = false, defaultValue = "0") String requestPage,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return ibService.reportLists(department,area,filterType,organizationCode,requestPage, startDate, endDate);
    }

    @GetMapping("/areaList")
    public ResponseModel<List<String>> getDistinctAreas() {
        try {
            List<String> distinctAreas = ibEntityRepository.findByArea();
            return new ResponseModel<>(true, "Areas List", distinctAreas);
        } catch (Exception e) {
            return new ResponseModel<>(false, "No Area Record", null);
        }
    }

    @GetMapping("/getUserPendingList")
    public ResponseModel<List<IB>> getPendingRCAs(@RequestParam String username, @RequestParam String organizationCode) {
        return ibService.findPending(username,organizationCode);
    }
}
