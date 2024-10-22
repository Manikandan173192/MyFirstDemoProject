package com.maxbyte.sam.SecondaryDBFlow.CAPA.Service;


import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository.*;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Specification.CAPASpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.Response.*;
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
public class CAPAService extends CrudService<CAPA,Integer> {

    @Autowired
    private CAPARepository capaRepository;

    @Autowired
    private CAPAStepOneRepository capaStepOneRepository;
    @Autowired
    private CAPAStepOneCARepository capaStepOneCARepository;

    @Autowired
    private CAPAStepTwoRepository capaStepTwoRepository;
    @Autowired
    private CAPAStepTwoPARepository capaStepTwoPARepository;
    @Autowired
    private Environment environment;

    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public CrudRepository repository() {
        return this.capaRepository;
    }

    @Override
    public void validateAdd(CAPA data) {
        try {
        } catch (Error e) {
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(CAPA data) {
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

    public ResponseModel<List<CAPA>> findCAPAByDateTime(String organizationCode, LocalDateTime from, LocalDateTime to, String requestPage) {
        try {
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            var results = capaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, from, to, pageRequest);
            List<CAPA> capaList = capaRepository.findAll();
            var totalCount = String.valueOf(capaList.size());
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

    public ResponseModel<List<CAPA>> list(Boolean isActive, String organizationCode, String assetNumber, String assetDescription,
                                          String department, String documentNumber, String woNumber, String requestPage) {
        try {
            CAPASpecificationBuilder builder = new CAPASpecificationBuilder();
            if (isActive != null) builder.with("isActive", ":", isActive);
            if (organizationCode != null) builder.with("organizationCode", "==", organizationCode);
            if (assetNumber != null) builder.with("assetNumber", "==", assetNumber);
            if (assetDescription != null) builder.with("assetDescription", "==", assetDescription);
            if (department != null) builder.with("department", "==", department);
            if (documentNumber != null) builder.with("capaNumber", "==", documentNumber);
            if (woNumber != null) builder.with("woNumber", "==", woNumber);

            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<CAPA> results = capaRepository.findAll(builder.build(), pageRequest);

            List<CAPA> capaList = capaRepository.findAll();
            var totalCount = String.valueOf(capaList.size());
            var filteredCount = String.valueOf(results.getContent().size());

            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", results.getContent());
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }

    public ResponseModel<List<FilterNumberResponse>> getFilterNumber(String organizationCode) {
        try {
            List<CAPA> capaList = capaRepository.findByOrganizationCode(organizationCode);
            List<FilterNumberResponse> filterList = new ArrayList<>();
            for (CAPA item : capaList) {
                FilterNumberResponse filterResponse = new FilterNumberResponse();
                filterResponse.setWoNumber(item.getWoNumber());
                filterResponse.setDocumentNumber(item.getCapaNumber());
                filterList.add(filterResponse);
            }

            return new ResponseModel<List<FilterNumberResponse>>(true, "Records Found", filterList);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records Not Found", null);
        }
    }


    public ResponseModel<String> addCAPA(AddCAPARequest capaRequest) {
        try {
            List<CAPA> capaList = capaRepository.findAll();
            var capaData = new CAPA();
            LocalDateTime instance = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

            String formattedStartDate = formatter.format(instance);

            if (!capaList.isEmpty()) {
                int id = capaList.getLast().getCapaId() + 1;
                capaData.setCapaNumber("CAPA_" +
                        capaRequest.getOrganizationCode() + "_" +
                        capaRequest.getDepartment() + "_" +
                        formattedStartDate + "_" +
                        id);
            } else {
                capaData.setCapaNumber("CAPA_" +
                        capaRequest.getOrganizationCode() + "_" +
                        capaRequest.getDepartment() + "_" +
                        formattedStartDate + "_" +
                        1);
            }

            capaData.setOrganizationCode(capaRequest.getOrganizationCode());
            capaData.setAssetGroupId(capaRequest.getAssetGroupId());
            capaData.setAssetGroup(capaRequest.getAssetGroup());
            capaData.setAssetId(capaRequest.getAssetId());
            capaData.setAssetNumber(capaRequest.getAssetNumber());
            capaData.setAssetDescription(capaRequest.getAssetDescription());
            capaData.setDepartment(capaRequest.getDepartment());
            capaData.setDepartmentId(capaRequest.getDepartmentId());
            capaData.setArea(capaRequest.getArea());
            capaData.setOrigin(capaRequest.getOrigin());
            capaData.setCreatedById(capaRequest.getCreatedById());
            capaData.setCreatedBy(capaRequest.getCreatedBy());
            capaData.setApprover1Id(capaRequest.getApprover1Id());
            capaData.setApprover1Name(capaRequest.getApprover1Name());
            capaData.setApprover2Id(capaRequest.getApprover2Id());
            capaData.setApprover2Name(capaRequest.getApprover2Name());
            capaData.setApprover3Id(capaRequest.getApprover3Id());
            capaData.setApprover3Name(capaRequest.getApprover3Name());
            capaData.setIssueDescription(capaRequest.getIssueDescription());
            capaData.setStatus(0);
            capaData.setActive(true);
            capaData.setWoNumber("");
            capaData.setCreatedOn(LocalDateTime.now());

            capaRepository.save(capaData);
            return new ResponseModel<>(true, "CAPA Created Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<ImageResponse> saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        var imagePath = new ImageResponse();
        imagePath.setImagePath((uploadDirectory + "/" + uniqueFileName).replace("target/classes/static", ""));
        return new ResponseModel<>(true, "Image Updated Successfully", imagePath);
    }

    @Scheduled(cron = "0 0 0 * * *")  // first 0 Seconds, 0 minute, 0 hours, 0 days, 0 months, 0 week days
    public ResponseModel<String> deleteAllImagesInFolder() {
        List<CAPAStepOne> capaStepOnes = capaStepOneRepository.findAll();
        List<String> images = new ArrayList<>();
        for (CAPAStepOne stepOne : capaStepOnes) {
            images.add(stepOne.getAttachment());
        }
        System.out.println("Uploaded images are " +images);

        String folderPath = environment.getProperty("image.uploadDirectory") + "/CAPA";
         //String folderPath = environment.getProperty("image.uploadDirectory1") ;
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return new ResponseModel<>(false, "Folder not found or is not a directory");
        }

//        File[] files = folder.listFiles();
//        if (files != null && files.length > 0) {
//            boolean filesDeleted = false;
//            for (File file : files) {
//                if (file.isFile()) {
//                    String fileName = file.getName();
//                    System.out.println("File Name " +fileName);
//                    boolean shouldDelete = true;
//                    for (String imagePath : images) {
//                        if (imagePath != null && imagePath.equals(fileName)) {
//                            shouldDelete = false;
//                            break;
//                        }
//                    }
//                    if (shouldDelete) {
//                        if (file.delete()) {
//                            filesDeleted = true;
//                        }
//                    }
//                }
//                System.out.println("Files after Deleted " + file);
//            }
//            if (filesDeleted) {
//                return new ResponseModel<>(true, "Images deleted successfully");
//            } else {
//                return new ResponseModel<>(false, "No images found to delete");
//            }
//        } else {
//            return new ResponseModel<>(false, "No files found in the directory");
//        }
//    }

        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            boolean filesDeleted = false;
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
            if (filesDeleted) {
                return new ResponseModel<>(true, "Images deleted successfully");
            } else {
                return new ResponseModel<>(false, "No images found to delete");
            }
        } else {
            return new ResponseModel<>(false, "No files found in the directory");
        }
    }

    //CAPA Step One
    public ResponseModel<String> addCAPAStepOne(AddCAPAStepOneRequest stepOneRequest) {
        try {

            List<CAPAStepOne> stepOneList = capaStepOneRepository.findByCapaNumber(stepOneRequest.getCapaNumber());
            var apiAction = 0;
            if (!stepOneList.isEmpty()) {
                //Update step One CAPA
                stepOneList.getFirst().setCapaNumber(stepOneRequest.getCapaNumber());
                stepOneList.getFirst().setRootCause(stepOneRequest.getRootCause());
                stepOneList.getFirst().setAttachment(stepOneRequest.getAttachment());
                stepOneList.getFirst().setGetUrl(stepOneRequest.getGetUrl());
                capaStepOneRepository.save(stepOneList.getFirst());

                //Delete existing team members for the particular CAPA no
                var addedCAList = capaStepOneCARepository.findByCapaNumber(stepOneList.getFirst().getCapaNumber());
                if (!addedCAList.isEmpty()) {
                    for (CAPAStepOneCA items : addedCAList) {
                        if (items.getCapaNumber().equals(stepOneList.getFirst().getCapaNumber())) {
                            capaStepOneCARepository.deleteByCaId(items.getCaId());
                        }
                    }
                }

                for (CAPAStepOneCA item : stepOneRequest.getCaDetails()) {
                    var caData = new CAPAStepOneCA();
                    caData.setAssetNumber(item.getAssetNumber());
                    caData.setAssetGroup(item.getAssetGroup());
                    caData.setDepartment(item.getDepartment());
                    caData.setAction(item.getAction());
                    caData.setStartDate(item.getStartDate());
                    caData.setEndDate(item.getEndDate());
                    caData.setTeamMembers(item.getTeamMembers());
                    caData.setCapaNumber(stepOneList.getFirst().getCapaNumber());
                    capaStepOneCARepository.save(caData);
                }
                apiAction = 2;
            } else {

                //Add step One CAPA
                var capaData = new CAPAStepOne();
                capaData.setCapaNumber(stepOneRequest.getCapaNumber());
                capaData.setRootCause(stepOneRequest.getRootCause());
                capaData.setAttachment(stepOneRequest.getAttachment());
                capaData.setGetUrl(stepOneRequest.getGetUrl());
                capaStepOneRepository.save(capaData);

                List<CAPAStepOne> CapaStepOneList = capaStepOneRepository.findByCapaNumber(stepOneRequest.getCapaNumber());

                for (CAPAStepOneCA item : stepOneRequest.getCaDetails()) {
                    var caData = new CAPAStepOneCA();
                    caData.setAssetNumber(item.getAssetNumber());
                    caData.setAssetGroup(item.getAssetGroup());
                    caData.setDepartment(item.getDepartment());
                    caData.setAction(item.getAction());
                    caData.setStartDate(item.getStartDate());
                    caData.setEndDate(item.getEndDate());
                    caData.setTeamMembers(item.getTeamMembers());
                    caData.setCapaNumber(CapaStepOneList.getFirst().getCapaNumber());
                    capaStepOneCARepository.save(caData);
                }

                apiAction = 1;
            }

            List<CAPA> capaList = capaRepository.findByCapaNumber(stepOneRequest.getCapaNumber());
            if (!capaList.isEmpty()) {
                //Update CAPA step one status
                if (capaList.getFirst().getStatus() == 0) {
                    capaList.getFirst().setStatus(1);
                }
                capaRepository.save(capaList.getFirst());
            }
            return new ResponseModel<>(true, apiAction == 1 ? "Corrective Action Created Successfully" : "Corrective Action Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<CAPAStepOne> getCAPAStepOne(String capaNo) {
        try {
            List<CAPAStepOne> stepOneList = capaStepOneRepository.findByCapaNumber(capaNo);
            return new ResponseModel<>(true, "Records Found", stepOneList.getFirst());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records Not Found", null);
        }
    }


    //CAPA Step Two
    public ResponseModel<String> addCAPAStepTwo(AddCAPAStepTwoRequest stepTwoRequest) {
        try {

            List<CAPAStepTwo> stepTwoList = capaStepTwoRepository.findByCapaNumber(stepTwoRequest.getCapaNumber());
            var apiAction = 0;
            if (!stepTwoList.isEmpty()) {
                //Update step Two CAPA
                stepTwoList.getFirst().setCapaNumber(stepTwoRequest.getCapaNumber());
                stepTwoList.getFirst().setRootCause(stepTwoRequest.getRootCause());
                capaStepTwoRepository.save(stepTwoList.getFirst());

                //Delete existing team members for the particular CAPA no
                var addedPAList = capaStepTwoPARepository.findByCapaNumber(stepTwoList.getFirst().getCapaNumber());
                if (!addedPAList.isEmpty()) {
                    for (CAPAStepTwoPA items : addedPAList) {
                        if (items.getCapaNumber().equals(stepTwoList.getFirst().getCapaNumber())) {
                            capaStepTwoPARepository.deleteByPaId(items.getPaId());
                        }
                    }
                }

                for (CAPAStepTwoPA item : stepTwoRequest.getPaDetails()) {
                    var paData = new CAPAStepTwoPA();
                    paData.setAssetNumber(item.getAssetNumber());
                    paData.setAssetGroup(item.getAssetGroup());
                    paData.setDepartment(item.getDepartment());
                    paData.setAction(item.getAction());
                    paData.setStartDate(item.getStartDate());
                    paData.setEndDate(item.getEndDate());
                    paData.setTeamMembers(item.getTeamMembers());
                    paData.setCapaNumber(stepTwoList.getFirst().getCapaNumber());
                    capaStepTwoPARepository.save(paData);
                }
                apiAction = 2;
            } else {

                //Add step Two CAPA
                var capaData = new CAPAStepTwo();
                capaData.setCapaNumber(stepTwoRequest.getCapaNumber());
                capaData.setRootCause(stepTwoRequest.getRootCause());
                capaStepTwoRepository.save(capaData);

                List<CAPAStepTwo> capastepTwoList = capaStepTwoRepository.findByCapaNumber(stepTwoRequest.getCapaNumber());

                for (CAPAStepTwoPA item : stepTwoRequest.getPaDetails()) {
                    var paData = new CAPAStepTwoPA();
                    paData.setAssetNumber(item.getAssetNumber());
                    paData.setAssetGroup(item.getAssetGroup());
                    paData.setDepartment(item.getDepartment());
                    paData.setAction(item.getAction());
                    paData.setStartDate(item.getStartDate());
                    paData.setEndDate(item.getEndDate());
                    paData.setTeamMembers(item.getTeamMembers());
                    paData.setCapaNumber(capastepTwoList.getFirst().getCapaNumber());
                    capaStepTwoPARepository.save(paData);
                }

                apiAction = 1;
            }

            List<CAPA> capaList = capaRepository.findByCapaNumber(stepTwoRequest.getCapaNumber());
            if (!capaList.isEmpty()) {
                //Update CAPA step Two status
                if (capaList.getFirst().getStatus() == 1) {
                    capaList.getFirst().setStatus(2);
                }
                capaRepository.save(capaList.getFirst());
            }
            return new ResponseModel<>(true, apiAction == 1 ? "Preventive Action Created Successfully" : "Preventive Action Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<CAPAStepTwo> getCAPAStepTwo(String capaNo) {
        try {
            List<CAPAStepTwo> stepTwoList = capaStepTwoRepository.findByCapaNumber(capaNo);
            return new ResponseModel<>(true, "Records Found", stepTwoList.getFirst());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records Not Found", null);
        }
    }

    //CAPA Step Three
    public ResponseModel<String> addCAPAStepThree(AddCAPAStepThreeRequest stepThreeRequest) {
        try {

            List<CAPA> capaList = capaRepository.findByCapaNumber(stepThreeRequest.getCapaNumber());
            var apiAction = 0;
            if (!capaList.isEmpty()) {
                //Update step Three CAPA
                capaList.getFirst().setCapaNumber(stepThreeRequest.getCapaNumber());
                capaList.getFirst().setShareComments(stepThreeRequest.getComments());

                if (capaList.getFirst().getStatus() == 2) {
                    capaList.getFirst().setStatus(3);
                    capaList.getFirst().setCompletedDate(LocalDateTime.now());
                } else if (capaList.getFirst().getStatus() == 5) {
                    capaList.getFirst().setStatus(3);
                    capaList.getFirst().setApprover1Status(0);
                    capaList.getFirst().setApprover1Comments(null);
                    capaList.getFirst().setApprover1DateTime(null);

                    capaList.getFirst().setApprover2Status(0);
                    capaList.getFirst().setApprover2Comments(null);
                    capaList.getFirst().setApprover2DateTime(null);

                    capaList.getFirst().setApprover3Status(0);
                    capaList.getFirst().setApprover3Comments(null);
                    capaList.getFirst().setApprover3DateTime(null);
                    capaList.getFirst().setCompletedDate(LocalDateTime.now());//completion date
                    capaList.getFirst().setRevertBackDate(null);
                    capaList.getFirst().setClosedDate(null);

                }
                capaRepository.save(capaList.getFirst());

            }

            return new ResponseModel<>(true, "Sharing Added Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<SharingResponse> getCAPAStepThree(String capaNo) {
        try {
            List<CAPA> capaList = capaRepository.findByCapaNumber(capaNo);
            var sharingData = new SharingResponse();
            sharingData.setApprover1Id(capaList.getFirst().getApprover1Id());
            sharingData.setApprover1Name(capaList.getFirst().getApprover1Name());
            sharingData.setApprover1Status(capaList.getFirst().getApprover1Status());
            sharingData.setApprover1Comments(capaList.getFirst().getApprover1Comments());
            sharingData.setApprover1DateTime(capaList.getFirst().getApprover1DateTime());

            sharingData.setApprover2Id(capaList.getFirst().getApprover2Id());
            sharingData.setApprover2Name(capaList.getFirst().getApprover2Name());
            sharingData.setApprover2Status(capaList.getFirst().getApprover2Status());
            sharingData.setApprover2Comments(capaList.getFirst().getApprover2Comments());
            sharingData.setApprover2DateTime(capaList.getFirst().getApprover2DateTime());

            sharingData.setApprover3Id(capaList.getFirst().getApprover3Id());
            sharingData.setApprover3Name(capaList.getFirst().getApprover3Name());
            sharingData.setApprover3Status(capaList.getFirst().getApprover3Status());
            sharingData.setApprover3Comments(capaList.getFirst().getApprover3Comments());
            sharingData.setApprover3DateTime(capaList.getFirst().getApprover3DateTime());
            sharingData.setShareComments(capaList.getFirst().getShareComments());
            sharingData.setId(capaList.getFirst().getCapaNumber());

            return new ResponseModel<>(true, "Records Found", sharingData);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records Not Found", null);
        }
    }

    public ResponseModel<String> validateCAPAStepThree(ValidateRequest validateRequest) {
        try {

            List<CAPA> capaList = capaRepository.findByCapaNumber(validateRequest.getId());
            //Update step Three CAPA

            capaList.getFirst().setApprover1Id(validateRequest.getApprover1Id());
            capaList.getFirst().setApprover1Name(validateRequest.getApprover1Name());
            capaList.getFirst().setApprover1Status(validateRequest.getApprover1Status());
            capaList.getFirst().setApprover1Comments(validateRequest.getApprover1Comments());
            if (validateRequest.getApprover1Status() != 0 && capaList.getFirst().getApprover1DateTime() == null) {
                capaList.getFirst().setApprover1DateTime(LocalDateTime.now());
            }

            capaList.getFirst().setApprover2Id(validateRequest.getApprover2Id());
            capaList.getFirst().setApprover2Name(validateRequest.getApprover2Name());
            capaList.getFirst().setApprover2Status(validateRequest.getApprover2Status());
            capaList.getFirst().setApprover2Comments(validateRequest.getApprover2Comments());
            if (validateRequest.getApprover2Status() != 0 && capaList.getFirst().getApprover2DateTime() == null) {
                capaList.getFirst().setApprover2DateTime(LocalDateTime.now());
            }

            capaList.getFirst().setApprover3Id(validateRequest.getApprover3Id());
            capaList.getFirst().setApprover3Name(validateRequest.getApprover3Name());
            capaList.getFirst().setApprover3Status(validateRequest.getApprover3Status());
            capaList.getFirst().setApprover3Comments(validateRequest.getApprover3Comments());
            if (validateRequest.getApprover3Status() != 0 && capaList.getFirst().getApprover3DateTime() == null) {
                capaList.getFirst().setApprover3DateTime(LocalDateTime.now());
//                System.out.println("lllllllllllllll");
//                capaList.getFirst().setClosedDate(LocalDateTime.now());
//                capaList.getFirst().setStatus(6);
            }

            if (validateRequest.getApprover2Name().isEmpty() && validateRequest.getApprover2Status() == 0) {
                if (capaList.getFirst().getStatus() == 3) {
                    if (validateRequest.getApprover1Status() == 1) {
                        capaList.getFirst().setStatus(6);
                        capaList.getFirst().setClosedDate(LocalDateTime.now());
                    }else if(validateRequest.getApprover1Status() == 2){
                        capaList.getFirst().setStatus(5);
                        System.out.println("Status 5 for approver01 ");
                        capaList.getFirst().setRevertBackDate(LocalDateTime.now());
                    }
                }
                capaRepository.save(capaList.getFirst());

            } else if (validateRequest.getApprover3Name().isEmpty() && validateRequest.getApprover3Status() == 0) {
                if (capaList.getFirst().getStatus() == 4) {
                    if (validateRequest.getApprover2Status() == 1) {
                        capaList.getFirst().setStatus(6);

                        capaList.getFirst().setClosedDate(LocalDateTime.now());
                    }else if(validateRequest.getApprover2Status() == 2){
                        capaList.getFirst().setStatus(5);
                        capaList.getFirst().setRevertBackDate(LocalDateTime.now());
                    }
                } else {
                    capaList.getFirst().setStatus(4);
                }
                capaRepository.save(capaList.getFirst());

            } else if (!validateRequest.getApprover3Name().isEmpty() && validateRequest.getApprover3Status() != 0) {
                if (capaList.getFirst().getStatus() == 4) {
                    System.out.println("HAII");
                    if (validateRequest.getApprover3Status() == 1) {
                        capaList.getFirst().setStatus(6);
                        capaList.getFirst().setClosedDate(LocalDateTime.now());
                    } else if (validateRequest.getApprover3Status() == 2) {
                        capaList.getFirst().setStatus(5);
                        capaList.getFirst().setRevertBackDate(LocalDateTime.now());
                    }
                }
                capaRepository.save(capaList.getFirst());
            }
            else {
                if (validateRequest.getApprover1Status() == 2) {
                    capaList.getFirst().setStatus(5);
                    capaList.getFirst().setRevertBackDate(LocalDateTime.now());
                } else if (validateRequest.getApprover2Status() == 2) {
                    capaList.getFirst().setStatus(5);
                    capaList.getFirst().setRevertBackDate(LocalDateTime.now());
                } else if (validateRequest.getApprover3Status() == 2) {
                    capaList.getFirst().setStatus(5);
                    System.out.println("Status 55");
                    capaList.getFirst().setRevertBackDate(LocalDateTime.now());
                }else {
                    capaList.getFirst().setStatus(4);
                }
                capaRepository.save(capaList.getFirst());

            }

            return new ResponseModel<>(true, "Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

//    public ResponseModel<List<CAPA>> reportLists(String department, String area, FilterType filterType, String organizationCode, String requestPage, LocalDate startDate, LocalDate endDate) {
//        try {
//
//            Page<CAPA> results;
//            var pageRequestCount = 0;
//
//            if (requestPage.matches(".*\\d.*")) {
//                pageRequestCount = Integer.parseInt(requestPage);
//            } else {
//                pageRequestCount = 0;
//            }
//
//            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
//
//            switch (filterType) {
//                case DAILY:
//                    results = organizationCode != null ?
//                            capaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59), pageRequest) :
//                            (department != null ?
//                                    capaRepository.findByDepartmentAndCreatedOnBetween(department, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59), pageRequest) :
//                                    (area != null ?
//                                            capaRepository.findByAreaAndCreatedOnBetween(area, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59), pageRequest) :
//                                            capaRepository.findByCreatedOnBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59), pageRequest)));
//                    break;
//                case WEEKLY:
//                    LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//                    LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
//                    results = organizationCode != null ?
//                            capaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59), pageRequest) :
//                            (department != null ?
//                                    capaRepository.findByDepartmentAndCreatedOnBetween(department, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59), pageRequest) :
//                                    (area != null ?
//                                            capaRepository.findByAreaAndCreatedOnBetween(area, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59), pageRequest) :
//                                            capaRepository.findByCreatedOnBetween(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59), pageRequest)));
//                    break;
//                case MONTHLY:
//                    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
//                    LocalDate endOfMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
//                    results = organizationCode != null ?
//                            capaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59), pageRequest) :
//                            (department != null ?
//                                    capaRepository.findByDepartmentAndCreatedOnBetween(department, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59), pageRequest) :
//                                    (area != null ?
//                                            capaRepository.findByAreaAndCreatedOnBetween(area, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59), pageRequest) :
//                                            capaRepository.findByCreatedOnBetween(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59), pageRequest)));
//                    break;
//                case QUARTERLY:
//                    LocalDate startOfQuarter = LocalDate.now().withMonth(((LocalDate.now().getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
//                    LocalDate endOfQuarter = startOfQuarter.plusMonths(3).minusDays(1);
//
//
//                    results = organizationCode != null ?
//                            capaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59), pageRequest) :
//                            (department != null ?
//                                    capaRepository.findByDepartmentAndCreatedOnBetween(department, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59), pageRequest) :
//                                    (area != null ?
//                                            capaRepository.findByAreaAndCreatedOnBetween(area, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59), pageRequest) :
//                                            capaRepository.findByCreatedOnBetween(startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59), pageRequest)));
//                    break;
//                case YEARLY:
//                    LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
//                    LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
//                    results = organizationCode != null ?
//                            capaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59), pageRequest) :
//                            (department != null ?
//                                    capaRepository.findByDepartmentAndCreatedOnBetween(department, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59), pageRequest) :
//                                    (area != null ?
//                                            capaRepository.findByAreaAndCreatedOnBetween(area, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59), pageRequest) :
//                                            capaRepository.findByCreatedOnBetween(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59), pageRequest)));
//                    break;
//                case CUSTOM:
//                    results = organizationCode != null ?
//                            capaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDate.atStartOfDay(), endDate.atTime(23, 59, 59), pageRequest) :
//                            (department != null ?
//                                    capaRepository.findByDepartmentAndCreatedOnBetween(department, startDate.atStartOfDay(), endDate.atTime(23, 59, 59), pageRequest) :
//                                    (area != null ?
//                                            capaRepository.findByAreaAndCreatedOnBetween(area, startDate.atStartOfDay(), endDate.atTime(23, 59, 59), pageRequest) :
//                                            capaRepository.findByCreatedOnBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59), pageRequest)));
//                    break;
//
//                default:
//
//                    return new ResponseModel<>(false, "Invalid filter type", null);
//            }
//
//            List<CAPA> capaList = capaRepository.findAll();
//            var totalCount = String.valueOf(capaList.size());
//            var filteredCount = String.valueOf(results.getContent().size());
//            if (results.isEmpty()) {
//                return new ResponseModel<>(false, "No records found", null);
//            } else {
//                return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", results.getContent());
//            }
//
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Records not found1", null);
//        }
//    }

    public ResponseModel<List<CAPA>> reportLists(String organizationCode, String department, String area, FilterType filterType, String requestPage, LocalDate startDate, LocalDate endDate) {
        try {
            Page<CAPA> results;
            var pageRequestCount = 0;

            if(requestPage.matches(".*\\d.*")){
                pageRequestCount = Integer.parseInt(requestPage);
            }else{
                pageRequestCount = 0;
            }

            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());


            LocalDateTime startDateTime;
            LocalDateTime endDateTime;

            switch (filterType) {
                case DAILY:
                    startDateTime = LocalDate.now().atStartOfDay();
                    endDateTime = LocalDate.now().atTime(23, 59, 59);
                    break;
                case WEEKLY:
                    LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                    startDateTime = startOfWeek.atStartOfDay();
                    endDateTime = endOfWeek.atTime(23, 59, 59);
                    break;
                case MONTHLY:
                    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
                    LocalDate endOfMonth = startOfMonth.plusMonths(1).withDayOfMonth(1).minusDays(1);
                    startDateTime = startOfMonth.atStartOfDay();
                    endDateTime = endOfMonth.atTime(23, 59, 59);
                    break;
                case QUARTERLY:
                    LocalDate startOfQuarter = LocalDate.now().withMonth(((LocalDate.now().getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
                    LocalDate endOfQuarter = startOfQuarter.plusMonths(3).minusDays(1);
                    startDateTime = startOfQuarter.atStartOfDay();
                    endDateTime = endOfQuarter.atTime(23, 59, 59);
                    break;
                case YEARLY:
                    LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
                    LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
                    startDateTime = startOfYear.atStartOfDay();
                    endDateTime = endOfYear.atTime(23, 59, 59);
                    break;
                case CUSTOM:
                    startDateTime = startDate.atStartOfDay();
                    endDateTime = endDate.atTime(23, 59, 59);
                    break;
                default:
                    return new ResponseModel<>(false, "Invalid filter type", null);
            }

            if (organizationCode != null && department != null && area != null) {
                // Query with all three filters
                results = capaRepository.findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(organizationCode, department, area, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null && department != null) {
                // Query with organizationCode and department filters
                results = capaRepository.findByOrganizationCodeAndDepartmentAndCreatedOnBetween(organizationCode, department, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null && area != null) {
                // Query with organizationCode and area filters
                results = capaRepository.findByOrganizationCodeAndAreaAndCreatedOnBetween(organizationCode, area, startDateTime, endDateTime, pageRequest);
            } else if (department != null && area != null) {
                // Query with department and area filters
                results = capaRepository.findByDepartmentAndAreaAndCreatedOnBetween(department, area, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null) {
                // Query with organizationCode filter
                results = capaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDateTime, endDateTime, pageRequest);
            } else if (department != null) {
                // Query with department filter
                results = capaRepository.findByDepartmentAndCreatedOnBetween(department, startDateTime, endDateTime, pageRequest);
            } else if (area != null) {
                // Query with area filter
                results = capaRepository.findByAreaAndCreatedOnBetween(area, startDateTime, endDateTime, pageRequest);
            } else {
                // Default query without any filters
                results = capaRepository.findByCreatedOnBetween(startDateTime, endDateTime, pageRequest);
            }

            //return new ResponseModel<>(true, "Records found", results.getContent());
            List<CAPA> capaList = capaRepository.findAll();
            var totalCount = String.valueOf(capaList.size());
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

    public ResponseModel<List<CAPA>> findPending(String username, String organizationCode) {
        UserInfo userInfo = userInfoRepository.findByUserNameAndOrganizationCode(username, organizationCode);
        boolean isOperator = "Operator".equals(userInfo.getRole());

        if (isOperator) {
            System.out.println("isOperator=" + isOperator);
            List<CAPA> pendingCAPAs = capaRepository.findPending(username,organizationCode);
            return new ResponseModel<>(true,"Operator Pending List",pendingCAPAs);
            // return capaRepository.findPending(username,organizationCode);
        } else {
            List<CAPA> result = capaRepository.findByUsernameAndOrganizationCodeWithPendingStatus(username, organizationCode);
//            System.out.println(" CAPA called2 or called3");
            return new ResponseModel<>(true, "User Pending List", result);
            //return result;
        }
    }


}