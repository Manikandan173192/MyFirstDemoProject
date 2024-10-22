package com.maxbyte.sam.SecondaryDBFlow.IB.Service;

import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEA;
import com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest.IBAddRequest;
import com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest.IBImageRequest;
import com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest.IBSenderRequest;
import com.maxbyte.sam.SecondaryDBFlow.IB.APIRequest.IBValidationRequest;
import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IB;
import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IBImage;
import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.ImageType;
import com.maxbyte.sam.SecondaryDBFlow.IB.Repository.IBEntityRepository;
import com.maxbyte.sam.SecondaryDBFlow.IB.Repository.IBImageRepository;
import com.maxbyte.sam.SecondaryDBFlow.IB.Specification.IBSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterNumberResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterType;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class IBService extends CrudService<IB, Integer> {

    @Autowired
    private IBEntityRepository ibEntityRepository;
    @Autowired
    private IBImageRepository ibImageRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    Environment environment;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;


    @Override
    public CrudRepository repository() {
        return this.ibEntityRepository;
    }

    @Override
    public void validateAdd(IB data) {
        try {
        } catch (Error e) {
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(IB data) {
        try {
        } catch (Error e) {
            throw new Error(e);
        }
    }

    @Override
    public void validateDelete(Integer id) {
        try {

        } catch (Error e) {
            throw new Error(e);
        }
    }


    public ResponseModel<List<IB>> list(Boolean isActive, String organizationCode, String assetNumber, String assetDescription,
                                        String department, String documentNumber, String woNumber, String requestPage) {

        try {

            IBSpecificationBuilder builder = new IBSpecificationBuilder();
            if (isActive != null) builder.with("isActive", ":", isActive);
            if (organizationCode != null) builder.with("organization", "==", organizationCode);
            if (assetNumber != null) builder.with("assetNumber", "==", assetNumber);
            if (assetDescription != null) builder.with("assetDescription", "==", assetDescription);
            if (department != null) builder.with("department", "==", department);
            if (documentNumber != null) builder.with("ibNumber", "==", documentNumber);
            if (woNumber != null) builder.with("woNumber", "==", woNumber);

            var pageRequestCount = 0;

            if(requestPage.matches(".*\\d.*")){
                pageRequestCount = Integer.parseInt(requestPage);
            }else{
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<IB> results = ibEntityRepository.findAll(builder.build(), pageRequest);
            List<IB> ibList = ibEntityRepository.findAll();
            var totalCount = String.valueOf(ibList.size());
            var filteredCount = String.valueOf(results.getContent().size());

            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount+" Records found & "+filteredCount+ " Filtered", results.getContent());
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }

    public ResponseModel<List<IB>> findIBByDateTime(String organization, LocalDateTime from, LocalDateTime to, String requestPage) {
        try {
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            var results = ibEntityRepository.findByOrganizationAndCreatedOnBetween(organization, from, to, pageRequest);
            List<IB> ibList = ibEntityRepository.findAll();
            var totalCount = String.valueOf(ibList.size());
            var filteredCount = String.valueOf(results.getContent().size());
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", results.getContent());
            }
        }catch(Exception e){
            return new ResponseModel<>(false, "Records not found", null);
        }
    }

    public ResponseModel<List<FilterNumberResponse>> getFilterNumber(String organizationCode) {
        try {
            List<IB> ibList = ibEntityRepository.findByOrganization(organizationCode);
            List<FilterNumberResponse> filterList = new ArrayList<>();
            for (IB item : ibList) {
                FilterNumberResponse filterResponse = new FilterNumberResponse();
                filterResponse.setWoNumber(item.getWoNumber());
                filterResponse.setDocumentNumber(item.getIbNumber());
                filterList.add(filterResponse);
            }

            return new ResponseModel<List<FilterNumberResponse>>(true, "Records Found", filterList.reversed());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records Not Found", null);
        }
    }

    public ResponseModel<String> addIbEntity(IBAddRequest ibRequest) {
        try {
            List<IB> ibEntityList = ibEntityRepository.findAll();

            var ibEntityData = new IB();
            LocalDateTime instance = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

            String formattedStartDate = formatter.format(instance);


            if (!ibEntityList.isEmpty()) {

                int id = ibEntityList.getLast().getIBid() + 1;


                ibEntityData.setIbNumber("IB_" +
                        ibRequest.getOrganization() + "_" +
                        ibRequest.getDepartment() + "_" +
                        formattedStartDate + "_" +
                        id);

            } else {

                ibEntityData.setIbNumber("IB_" +
                        ibRequest.getOrganization() + "_" +
                        ibRequest.getDepartment() + "_" +
                        formattedStartDate + "_" +
                        1);
            }

            ibEntityData.setOrganization(ibRequest.getOrganization());
            ibEntityData.setAssetGroup(ibRequest.getAssetGroup());
            ibEntityData.setAssetGroup(ibRequest.getAssetGroup());
            ibEntityData.setAssetNumber(ibRequest.getAssetNumber());
            ibEntityData.setAssetNumber(ibRequest.getAssetNumber());
            ibEntityData.setAssetDescription(ibRequest.getAssetDescription());
            ibEntityData.setDepartmentId(ibRequest.getDepartmentId());
            ibEntityData.setDepartment(ibRequest.getDepartment());
            ibEntityData.setArea(ibRequest.getArea());
            ibEntityData.setSubComponent(ibRequest.getSubComponent());
            ibEntityData.setPreparerName(ibRequest.getPreparerName());
            ibEntityData.setApprover1Id(ibRequest.getApprover1Id());
            ibEntityData.setApproverName1(ibRequest.getApprover1Name());
            ibEntityData.setApprover2Id(ibRequest.getApprover2Id());
            ibEntityData.setApproverName2(ibRequest.getApprover2Name());
            ibEntityData.setApprover3Id(ibRequest.getApprover3Id());
            ibEntityData.setApproverName3(ibRequest.getApprover3Name());
            ibEntityData.setBypassValue(ibRequest.getBypassValue());
            ibEntityData.setBypassDescription(ibRequest.getBypassDescription());
            ibEntityData.setImpactOfBypass(ibRequest.getImpactOfBypass());
            ibEntityData.setCategory(ibRequest.getCategory());
            ibEntityData.setStartDate(ibRequest.getStartDate());
            ibEntityData.setEndDate(ibRequest.getEndDate());
            ibEntityData.setActive(true);
            ibEntityData.setCreatedOn(LocalDateTime.now());
            ibEntityData.setStatus(0);
            ibEntityData.setWoNumber("");

            ibEntityRepository.save(ibEntityData);

            var ibData = ibEntityRepository.findAll();

            if(ibData.size()!=0){
                if (ibRequest.getImageUpload().size()!=0){
                    for(IBImage item : ibRequest.getImageUpload()){
                        IBImage imagePath = new IBImage();
                        imagePath.setImageType(item.getImageType());
                        imagePath.setUploadImage(item.getUploadImage());
                        imagePath.setIbNumber(ibData.getLast().getIbNumber());
                        ibImageRepository.save(imagePath);
                    }}

            }

            return new ResponseModel<>(true, "IB Created Successfully", ibEntityData.getIbNumber());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    /*public ResponseModel<String> IBvalidate(IBValidationRequest validateRequest) {
        try {

            List<IB> ibList = ibEntityRepository.findByIbNumber(validateRequest.getIbNumber());
            ibList.getFirst().setApprover1Id(validateRequest.getApproverId1());
            ibList.getFirst().setApproverName1(validateRequest.getApproverName1());
            ibList.getFirst().setApproverStatus1(validateRequest.getApproverStatus1());
            if (ibList.getFirst().getStatus() == 2 && validateRequest.getApproverStatus1() != 0 && ibList.getFirst().getApproverDateAndTime1() == null) {
                ibList.getFirst().setApproverDateAndTime1(LocalDateTime.now()); // Update the date and time if not already set
            }

            ibList.getFirst().setApprover2Id(validateRequest.getApprover2Id());
            ibList.getFirst().setApproverName2(validateRequest.getApproverName2());
            ibList.getFirst().setApproverStatus2(validateRequest.getApproverStatus2());
            if (ibList.getFirst().getStatus() == 2 && validateRequest.getApproverStatus2() != 0 && ibList.getFirst().getApproverDateAndTime2() == null) {
                ibList.getFirst().setApproverDateAndTime2(LocalDateTime.now()); // Update the date and time if not already set
            }

            ibList.getFirst().setApprover3Id(validateRequest.getApprover3Id());
            ibList.getFirst().setApproverName3(validateRequest.getApproverName3());
            ibList.getFirst().setApproverStatus3(validateRequest.getApproverStatus3());
            if (ibList.getFirst().getStatus() == 2 && validateRequest.getApproverStatus3() != 0 && ibList.getFirst().getApproverDateAndTime3() == null) {
                ibList.getFirst().setApproverDateAndTime3(LocalDateTime.now()); // Update the date and time if not already set
            }

            if (validateRequest.getApproverName2().isEmpty() && validateRequest.getApproverStatus2() == 0) {

                if (ibList.getFirst().getStatus() == 0) {

                    ibList.getFirst().setStatus(validateRequest.getApproverStatus1() == 1 ? 1 : 4);

                } else if (ibList.getFirst().getStatus() == 2) {


                    ibList.getFirst().setStatus(validateRequest.getApproverStatus1() == 1 ? 5 : 4);
                }

                ibEntityRepository.save(ibList.getFirst());

            } else if (validateRequest.getApproverName3().isEmpty() && validateRequest.getApproverStatus3() == 0) {

                if (ibList.getFirst().getStatus() == 0) {
                    ibList.getFirst().setStatus(validateRequest.getApproverStatus2() == 1 ? 1 : 4);

                } else if (ibList.getFirst().getStatus() == 3 && validateRequest.getApproverStatus2() != 0) {
                    ibList.getFirst().setStatus(validateRequest.getApproverStatus2() == 1 ? 5 : 4);
                } else {
                    ibList.getFirst().setStatus(3);
                }
                ibEntityRepository.save(ibList.getFirst());

            } else if (!validateRequest.getApproverName3().isEmpty() && validateRequest.getApproverStatus3() != 0) {

                if (ibList.getFirst().getStatus() == 0) {
                    ibList.getFirst().setStatus(validateRequest.getApproverStatus3() == 1 ? 1 : 4);
                } else if (ibList.getFirst().getStatus() == 3) {

                    ibList.getFirst().setStatus(validateRequest.getApproverStatus3() == 1 ? 5 : 4);
                }

                ibEntityRepository.save(ibList.getFirst());

            } else {
                if (validateRequest.getApproverStatus1() == 2) {
                    ibList.getFirst().setStatus(4);
                } else if (validateRequest.getApproverStatus2() == 2) {
                    ibList.getFirst().setStatus(4);
                } else {
                    if (ibList.getFirst().getStatus() != 0) {
                        ibList.getFirst().setStatus(3);
                        System.out.println("called status");
                    }
                }

                ibEntityRepository.save(ibList.getFirst());

            }

            return new ResponseModel<>(true, "Updated Successfully", null);

        } catch (Exception e) {

            return new ResponseModel<>(false, "Failed to add", null);

        }

    }
*/

    public ResponseModel<String> IBvalidate(IBValidationRequest validateRequest) {
        try {

            List<IB> ibList = ibEntityRepository.findByIbNumber(validateRequest.getIbNumber());
            ibList.getFirst().setApprover1Id(validateRequest.getApproverId1());
            ibList.getFirst().setApproverComment(validateRequest.getApproverComment());
            ibList.getFirst().setApproverName1(validateRequest.getApproverName1());
            ibList.getFirst().setApproverStatus1(validateRequest.getApproverStatus1());
            if (ibList.getFirst().getStatus()==2 &&validateRequest.getApproverStatus1() != 0 && ibList.getFirst().getApproverDateAndTime1() == null) {
                ibList.getFirst().setApproverDateAndTime1(LocalDateTime.now()); // Update the date and time if not already set
            }

            ibList.getFirst().setApprover2Id(validateRequest.getApprover2Id());
            ibList.getFirst().setApproverName2(validateRequest.getApproverName2());
            ibList.getFirst().setApproverStatus2(validateRequest.getApproverStatus2());
            if (ibList.getFirst().getStatus()==3 && validateRequest.getApproverStatus2() != 0 && ibList.getFirst().getApproverDateAndTime2() == null) {
                ibList.getFirst().setApproverDateAndTime2(LocalDateTime.now()); // Update the date and time if not already set
            }

            ibList.getFirst().setApprover3Id(validateRequest.getApprover3Id());
            ibList.getFirst().setApproverName3(validateRequest.getApproverName3());
            ibList.getFirst().setApproverStatus3(validateRequest.getApproverStatus3());
            if (ibList.getFirst().getStatus()==3 &&validateRequest.getApproverStatus3() != 0 && ibList.getFirst().getApproverDateAndTime3() == null) {
                ibList.getFirst().setApproverDateAndTime3(LocalDateTime.now()); // Update the date and time if not already set
            }

            if( validateRequest.getApproverName2().isEmpty() && validateRequest.getApproverStatus2() == 0){

                if(ibList.getFirst().getStatus()==0) {
                    System.out.println("called me "+ibList.getFirst().getStatus());
                    ibList.getFirst().setStatus(validateRequest.getApproverStatus1()==1?1:4);
                    System.out.println("called2"+ibList.getFirst().getStatus());
                } else if (ibList.getFirst().getStatus() == 2) {
                    ibList.getFirst().setStatus(validateRequest.getApproverStatus1()==1?5:4);
                }

                ibEntityRepository.save(ibList.getFirst());

            }else if( validateRequest.getApproverName3().isEmpty() && validateRequest.getApproverStatus3() == 0){

                System.out.println("call 2");
                if(ibList.getFirst().getStatus()==0) {
                    if (validateRequest.getApproverStatus2() == 0) {
                        ibList.getFirst().setStatus(0);
                        System.out.println(" status check");
                    }else {
                        System.out.println("call 3");
                        ibList.getFirst().setStatus(validateRequest.getApproverStatus2() == 1 ? 1 : 4);
                    }
                }else if (ibList.getFirst().getStatus() == 3  && validateRequest.getApproverStatus2() != 0) {
                    ibList.getFirst().setStatus(validateRequest.getApproverStatus2()==1?5:4);
                }else{
                    ibList.getFirst().setStatus(3);
                }
                ibEntityRepository.save(ibList.getFirst());

            }else if(!validateRequest.getApproverName3().isEmpty() && validateRequest.getApproverStatus3() != 0){

                if(ibList.getFirst().getStatus()==0) {
                    ibList.getFirst().setStatus(validateRequest.getApproverStatus3()==1?1:4);
                }else if (ibList.getFirst().getStatus() == 3) {

                    ibList.getFirst().setStatus(validateRequest.getApproverStatus3()==1?5:4);
                }

                ibEntityRepository.save(ibList.getFirst());

            }else {
                if(validateRequest.getApproverStatus1() == 2){
                    ibList.getFirst().setStatus(4);
                }else if(validateRequest.getApproverStatus2() == 2){
                    ibList.getFirst().setStatus(4);
                }else {
                    if (ibList.getFirst().getStatus() != 0) {
                        ibList.getFirst().setStatus(3);
                        System.out.println("called status");
                    }
                    System.out.println("called status2");
//                    ibList.getFirst().setStatus(3);
                }

                ibEntityRepository.save(ibList.getFirst());

            }

            return new ResponseModel<>(true, "Updated Successfully",null);

        }catch (Exception e){

            return new ResponseModel<>(false, "Failed to add",null);

        }

    }

    //sender data
   /* public ResponseModel<String> IbAddSender(IBSenderRequest ibSenderRequest) {
        List<IB> sender1 = ibEntityRepository.findByIbNumber(ibSenderRequest.getIbNumber());
        if (!sender1.isEmpty()) {
            IB sender = sender1.getFirst();
            sender.setRestoreDescription(ibSenderRequest.getRestoreDescription());
            if (sender1.get(0).getStatus() == 0 *//*&& ibSenderRequest.getSenderType() == "sender1"*//*) {
                sender.setIbNumber(ibSenderRequest.getIbNumber());
                sender.setSenderName1(ibSenderRequest.getSenderName());
                sender.setSenderDepartment(ibSenderRequest.getSenderDepartment());
                sender.setSenderDateAndTime1(ibSenderRequest.getDateAndTime());
                sender.setSenderRemarks1(ibSenderRequest.getSenderRemarks());
//            sender.setSenderType(ibSenderRequest.getSenderType());

                for(IBImage item : ibSenderRequest.getImageUpload()){
                    var imagePath = new IBImage();
                    imagePath.setImageType(item.getImageType());
                    imagePath.setUploadImage(item.getUploadImage());
                    imagePath.setIbNumber(sender1.getLast().getIbNumber());
                    ibImageRepository.save(imagePath);
                }

                sender.setStatus(0);
                ibEntityRepository.save(sender);
                return new ResponseModel<String>(true, "Added Successfully", null);

            } else if (sender1.getFirst().getStatus() == 1 *//*&& ibSenderRequest.getSenderType()=="sender2"*//*) {
                sender.setIbNumber(ibSenderRequest.getIbNumber());
                sender.setSenderName2(ibSenderRequest.getSenderName());
                sender.setSenderDepartment(ibSenderRequest.getSenderDepartment());
                sender.setSenderDateAndTime2(ibSenderRequest.getDateAndTime());
                sender.setSenderRemarks2(ibSenderRequest.getSenderRemarks());
//            sender.setSenderType(ibSenderRequest.getSenderType());
                sender.setStatus(2);
                sender.setApproverStatus1(0);
                sender.setApproverStatus2(0);
                sender.setApproverStatus3(0);

                for(IBImage item : ibSenderRequest.getImageUpload()){
                    var imagePath = new IBImage();
                    imagePath.setImageType(item.getImageType());
                    imagePath.setUploadImage(item.getUploadImage());
                    imagePath.setIbNumber(sender1.getLast().getIbNumber());
                    ibImageRepository.save(imagePath);
                }

                ibEntityRepository.save(sender);
                return new ResponseModel<String>(true, "Added Successfully", null);

            }
        } else {
            throw new EntityNotFoundException("Entity not found with id: " + null);
        }


        return null;
    }
*/

    public ResponseModel<String> IbAddSender(IBSenderRequest ibSenderRequest) {
        List<IB> sender1 = ibEntityRepository.findByIbNumber(ibSenderRequest.getIbNumber());
        if (!sender1.isEmpty()) {
            IB sender = sender1.getFirst();
            sender.setRestoreDescription(ibSenderRequest.getRestoreDescription());
            if (sender1.get(0).getStatus() == 0 /*&& ibSenderRequest.getSenderType() == "sender1"*/) {
                sender.setIbNumber(ibSenderRequest.getIbNumber());
                sender.setSenderName1(ibSenderRequest.getSenderName());
                sender.setSenderDepartment(ibSenderRequest.getSenderDepartment());
                sender.setSenderDateAndTime1(ibSenderRequest.getDateAndTime());
                sender.setSenderRemarks1(ibSenderRequest.getSenderRemarks());

                sender.setStatus(0);
                ibEntityRepository.save(sender);
                List<IBImage> existingImages = ibImageRepository.findByIbNumber(sender.getIbNumber());
                if (!existingImages.isEmpty()) {
                    ibImageRepository.deleteAll(existingImages);
                }
                for(IBImage item : ibSenderRequest.getImageUpload()){
                    var imagePath = new IBImage();

                    imagePath.setImageType(item.getImageType());
                    imagePath.setUploadImage(item.getUploadImage());
                    imagePath.setIbNumber(sender1.getLast().getIbNumber());
                    ibImageRepository.save(imagePath);
                }

//                sender.setImageUpload(ibSenderRequest.getImageUpload());

//            sender.setSenderType(ibSenderRequest.getSenderType());

                System.out.println("called 1");

                return new ResponseModel<String>(true, "IB Created Successfully", null);

            } else if (sender1.getFirst().getStatus() == 1 /*&& ibSenderRequest.getSenderType()=="sender2"*/) {
                sender.setIbNumber(ibSenderRequest.getIbNumber());
                sender.setSenderName2(ibSenderRequest.getSenderName());
                sender.setSenderDepartment(ibSenderRequest.getSenderDepartment());
                sender.setSenderDateAndTime2(ibSenderRequest.getDateAndTime());
                sender.setSenderRemarks2(ibSenderRequest.getSenderRemarks());

                sender.setStatus(2);
                sender.setApproverStatus1(0);
                sender.setApproverStatus2(0);
                sender.setApproverStatus3(0);
                ibEntityRepository.save(sender);

                List<IBImage> existingImages = ibImageRepository.findByIbNumber(sender.getIbNumber());
                if (!existingImages.isEmpty()) {
                    ibImageRepository.deleteAll(existingImages);
                }

                for(IBImage item : ibSenderRequest.getImageUpload()){
                    var imagePath = new IBImage();
                    imagePath.setImageType(item.getImageType());
                    imagePath.setUploadImage(item.getUploadImage());
                    imagePath.setIbNumber(sender1.getLast().getIbNumber());
                    ibImageRepository.save(imagePath);
                }
                return new ResponseModel<String>(true, "IB Created Successfully", null);
            }
        } else {
            throw new EntityNotFoundException("Entity not found with id: " + null);
        }
        return null;
    }

    public ResponseModel<IBImageRequest> saveImageToStorage(String uploadDirectory, MultipartFile imageFile, ImageType imageType) throws IOException {
        try {
            String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDirectory);
            Path filePath = uploadPath.resolve(uniqueFileName);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            IBImage ibImageUpload = new IBImage();
            ibImageUpload.setUploadImage((uploadDirectory+"/"+uniqueFileName).replace("target/classes/static",""));
            ibImageUpload.setImageType(imageType); // Set the image type

            ibImageRepository.save(ibImageUpload);

            IBImageRequest imagePath = new IBImageRequest();
            imagePath.setImagePath(ibImageUpload.getUploadImage());
            imagePath.setImageType(ibImageUpload.getImageType()); // Set the image type in the response

            return new ResponseModel<>(true, "Image Updated Successfully", imagePath);
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
            return new ResponseModel<>(false, "Failed to update image", null);
        }
    }
    @Scheduled(cron = "0 0 0 * * *")  // first 0 Seconds, 0 minute, 0 hours, 0 days, 0 months, 0 week days
    public ResponseModel<String> deleteAllImagesInFolder() {
        List<IBImage> ibImageList = ibImageRepository.findAll();
        List<String> images = new ArrayList<>();
        for (IBImage image : ibImageList) {
            images.add(image.getUploadImage());
        }

        String uploadFolderPath = environment.getProperty("image.uploadDirectory") + "/IB/Upload";
        String restoreFolderPath = environment.getProperty("image.uploadDirectory") + "/IB/Restore";

        boolean filesDeleted = false;

        filesDeleted |= deleteImagesInFolder(uploadFolderPath, images);
        filesDeleted |= deleteImagesInFolder(restoreFolderPath, images);

        if (filesDeleted) {
            return new ResponseModel<>(true, "Images deleted successfully");
        } else {
            return new ResponseModel<>(false, "No images found to delete");
        }
    }

    private boolean deleteImagesInFolder(String folderPath, List<String> images) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return false;
        }

        File[] files = folder.listFiles();
        boolean filesDeleted = false;
        if (files != null && files.length > 0) {

            for (File file : files) {
                if (file.isFile()) {
                    // Construct the full path relative to the root folder
                    //String filePath = file.getAbsolutePath();
                    String relativeFilePath = folder.toURI().relativize(file.toURI()).getPath();
                    // System.out.println("Full File Path: " + filePath);
                    //System.out.println("Relative File Path: " + relativeFilePath);

                    boolean shouldDelete = true;
                    for (String imagePath : images) {
                        if (imagePath != null && imagePath.endsWith(relativeFilePath)) {
                            shouldDelete = false;
                            break;
                        }
                    }
                    if (shouldDelete) {
                        if (file.delete()) {
                            filesDeleted = true;
                        }
                    }
                }
                System.out.println("Files after Deletion: " + file);
            }
        }
        return filesDeleted;
    }
    public ResponseModel<List<IB>> reportLists(String department, String area, FilterType filterType, String organizationCode, String requestPage, LocalDate startDate, LocalDate endDate) {
        try {

            Page<IB> results;
            var pageRequestCount = 0;

            if(requestPage.matches(".*\\d.*")){
                pageRequestCount = Integer.parseInt(requestPage);
            }else{
                pageRequestCount = 0;
            }

            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());

            switch (filterType) {
                case DAILY:
                    results = organizationCode != null ?
                            ibEntityRepository.findByOrganizationAndCreatedOnBetween(organizationCode, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    ibEntityRepository.findByDepartmentAndCreatedOnBetween(department, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            ibEntityRepository.findByAreaAndCreatedOnBetween(area, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                                            ibEntityRepository.findByCreatedOnBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest)));
                    break;
                case WEEKLY:
                    LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                    results = organizationCode != null ?
                            ibEntityRepository.findByOrganizationAndCreatedOnBetween(organizationCode, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    ibEntityRepository.findByDepartmentAndCreatedOnBetween(department, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            ibEntityRepository.findByAreaAndCreatedOnBetween(area, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                                            ibEntityRepository.findByCreatedOnBetween(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest)));
                    break;
                case MONTHLY:
                    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
                    LocalDate endOfMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
                    results = organizationCode != null ?
                            ibEntityRepository.findByOrganizationAndCreatedOnBetween(organizationCode, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    ibEntityRepository.findByDepartmentAndCreatedOnBetween(department, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            ibEntityRepository.findByAreaAndCreatedOnBetween(area, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                                            ibEntityRepository.findByCreatedOnBetween(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest)));
                    break;
                case QUARTERLY:
                    LocalDate startOfQuarter = LocalDate.now().withMonth(((LocalDate.now().getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
                    LocalDate endOfQuarter = startOfQuarter.plusMonths(3).minusDays(1);


                    results = organizationCode != null ?
                            ibEntityRepository.findByOrganizationAndCreatedOnBetween(organizationCode, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    ibEntityRepository.findByDepartmentAndCreatedOnBetween(department, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59), pageRequest) :
                                    (area != null ?
                                            ibEntityRepository.findByAreaAndCreatedOnBetween(area, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
                                            ibEntityRepository.findByCreatedOnBetween(startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest)));
                    break;
                case YEARLY:
                    LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
                    LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
                    results = organizationCode != null ?
                            ibEntityRepository.findByOrganizationAndCreatedOnBetween(organizationCode, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    ibEntityRepository.findByDepartmentAndCreatedOnBetween(department, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            ibEntityRepository.findByAreaAndCreatedOnBetween(area, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                                            ibEntityRepository.findByCreatedOnBetween(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest)));
                    break;
                case CUSTOM:
                    results = organizationCode != null ?
                            ibEntityRepository.findByOrganizationAndCreatedOnBetween(organizationCode, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    ibEntityRepository.findByDepartmentAndCreatedOnBetween(department, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            ibEntityRepository.findByAreaAndCreatedOnBetween(area, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                                            ibEntityRepository.findByCreatedOnBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest)));
                    break;

                default:

                    return new ResponseModel<>(false, "Invalid filter type", null);
            }

            List<IB> ibList = ibEntityRepository.findAll();
            var totalCount = String.valueOf(ibList.size());
            var filteredCount = String.valueOf(results.getContent().size());
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount+" Records found & "+filteredCount+ " Filtered", results.getContent());
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found1", null);
        }
    }

    public ResponseModel<List<IB>> findPending(String username, String organizationCode) {
        UserInfo userInfo = userInfoRepository.findByUserNameAndOrganizationCode(username, organizationCode);
        boolean isOperator = "Operator".equals(userInfo.getRole());

        if (isOperator) {
           // System.out.println("isOperator=" + isOperator);
            List<IB> pendingIBs = ibEntityRepository.findPending(username, organizationCode);
            return new ResponseModel<>(true, "Operator pending List", pendingIBs);
            //return rcaRepository.findPending(username,organizationCode);
        } else {
            List<IB> result = ibEntityRepository.findByUsernameAndOrganizationCodeWithPendingStatus(username, organizationCode);

            return new ResponseModel<>(true, "User Pending List", result);
            //return result;
        }
    }

}
