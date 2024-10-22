package com.maxbyte.sam.SecondaryDBFlow.MOC.Service;

import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.DepartmentRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.MOC.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Repository.*;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Specification.MOCSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.Response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class MOCService extends CrudService<MOC,Integer> {
    @Autowired
    MOCRepository mocRepository;
    @Autowired
    MOCStepOneRepository mocStepOneRepository;
    @Autowired
    private MOCStepTwoRepository mocStepTwoRepository;
    @Autowired
    private MOCStepTwoAPRepository mocStepTwoAPRepository;
    @Autowired
    private MOCStepThreeRepository mocStepThreeRepository;
    @Autowired
    private MOCStepFourRepository mocStepFourRepository;
    @Autowired
    private MOCStepFiveRepository mocStepFiveRepository;
    @Autowired
    private MOCStepFiveDDRepository mocStepFiveDDRepository;
    @Autowired
    private MOCStepSixRepository mocStepSixRepository;
    @Autowired
    private MOCStepSixALRepository mocStepSixALRepository;
    @Autowired
    private MOCStepSevenRepository mocStepSevenRepository;
    @Autowired
    private MOCStepEightRepository mocStepEightRepository;
    @Autowired
    private MOCStepNineRepository mocStepNineRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;

    @Override
    public CrudRepository repository() {
        return mocRepository;
    }

    @Autowired
    Environment environment;

    @Override
    public void validateAdd(MOC data) {

    }

    @Override
    public void validateEdit(MOC data) {

    }

    @Override
    public void validateDelete(Integer id) {

    }

    public ResponseModel<List<MOC>> list(Boolean isActive, String initiatorName, String assetNumber, String assetDescription,
                                         String department, String mocNumber, String organizationCode, String requestPage) {

        MOCSpecificationBuilder builder = new MOCSpecificationBuilder();
        if (isActive != null) builder.with("isActive", ":", isActive);
        if (initiatorName != null) builder.with("initiatorName", "==", initiatorName);
        if (assetNumber != null) builder.with("assetNumber", "==", assetNumber);
        if (assetDescription != null) builder.with("assetDescription", "==", assetDescription);
        if (department != null) builder.with("department", "==", department);
        if (mocNumber != null) builder.with("mocNumber", "==", mocNumber);
        if (organizationCode != null) builder.with("organizationCode", "==", organizationCode);

        var pageRequestCount = 0;

        if (requestPage.matches(".*\\d.*")) {
            pageRequestCount = Integer.parseInt(requestPage);
        } else {
            pageRequestCount = 0;
        }

        // Create a PageRequest for pagination
        PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
        Page<MOC> results = mocRepository.findAll(builder.build(), pageRequest);
        List<MOC> mocList = mocRepository.findAll();
        var totalCount = String.valueOf(mocList.size());
        var filteredCount = String.valueOf(results.getContent().size());
        if (results.isEmpty()) {
            return new ResponseModel<>(false, "No records found", null);
        } else {
            return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", results.getContent());
        }
    }

    public ResponseModel<List<MOC>> findMOCByDateTime(String organizationCode, LocalDateTime from, LocalDateTime to, String requestPage) {
        try {
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            var results = mocRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, from, to, pageRequest);
            List<MOC> mocList = mocRepository.findAll();
            var totalCount = String.valueOf(mocList.size());
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

    public ResponseModel<String> addMOC(AddMOCRequest mocRequest) {
        try {
            List<MOC> mocList = mocRepository.findAll();
            var mocData = new MOC();
            LocalDateTime instance = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

            String formattedStartDate = formatter.format(instance);


            if (!mocList.isEmpty()) {
                int id = mocList.getLast().getMocId() + 1;
                mocData.setMocNumber("MOC_" +
                        mocRequest.getOrganizationCode() + "_" +
                        mocRequest.getDepartment() + "_" +
                        formattedStartDate + "_" +
                        id);
            } else {
                mocData.setMocNumber("MOC_" +
                        mocRequest.getOrganizationCode() + "_" +
                        mocRequest.getDepartment() + "_" +
                        formattedStartDate + "_" +
                        1);
            }

            mocData.setOrganizationCode(mocRequest.getOrganizationCode());
            mocData.setTitleOfMoc(mocRequest.getTitleOfMoc());
            mocData.setAssetGroupId(mocRequest.getAssetGroupId());
            mocData.setAssetGroup(mocRequest.getAssetGroup());
            mocData.setAssetNumber(mocRequest.getAssetNumber());
            mocData.setAssetId(mocRequest.getAssetId());
            mocData.setAssetDescription(mocRequest.getAssetDescription());
            mocData.setDepartment(mocRequest.getDepartment());
            mocData.setDepartmentId(mocRequest.getDepartmentId());
            mocData.setDepartmentHead(mocRequest.getDepartmentHead());
            mocData.setArea(mocRequest.getArea());
            mocData.setCostCenter(mocRequest.getCostCenter());
            // mocData.setCreatedById(mocRequest.getCreatedById());
            mocData.setInitiatorName(mocRequest.getInitiatorName());
            mocData.setTypeOfProposedChange(mocRequest.getTypeOfProposedChange());
            mocData.setDocType((mocRequest.getDocType()));
            mocData.setBriefDescriptionOfChange(mocRequest.getBriefDescriptionOfChange());
            mocData.setAttachment(mocRequest.getAttachment());
            mocData.setStatus(0);
            mocData.setActive(true);
            mocData.setWoNumber("");
            mocData.setCreatedOn(LocalDateTime.now());

            mocRepository.save(mocData);
            return new ResponseModel<>(true, "MOC Created Successfully", mocData.getMocNumber());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<String> updateMoc(String mocNo, MOC mocData) {
        try {
            List<MOC> mocList = mocRepository.findByMocNumber(mocNo);
            if (!mocList.isEmpty()) {
                mocRepository.save(mocData);
                return new ResponseModel<>(true, "MOC Updated Successfully", null);
            } else {
                return new ResponseModel<>(false, "Records not found", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<List<FilterNumberResponse>> getFilterNumber(String organizationCode) {
        try {
            List<MOC> mocList = mocRepository.findByOrganizationCode(organizationCode);
            List<FilterNumberResponse> filterList = new ArrayList<>();
            for (MOC item : mocList) {
                FilterNumberResponse filterResponse = new FilterNumberResponse();
                filterResponse.setWoNumber(item.getWoNumber());
                filterResponse.setDocumentNumber(item.getMocNumber());
                filterList.add(filterResponse);
            }

            return new ResponseModel<List<FilterNumberResponse>>(true, "Records Found", filterList.reversed());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records Not Found", null);
        }
    }

//    public ResponseModel<ImageResponse> saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
//        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
//        Path uploadPath = Path.of(uploadDirectory);
//        Path filePath = uploadPath.resolve(uniqueFileName);
//
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        var imagePath = new ImageResponse();
//        imagePath.setImagePath((uploadDirectory + "/" + uniqueFileName).replace("target/classes/static/images", ""));
//        return new ResponseModel<>(true, "Image Updated Successfully", imagePath);
//    }

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
        List<String> images = new ArrayList<>();

        List<MOCStepOne> mocStepOnes = mocStepOneRepository.findAll();

        for (MOCStepOne mocStepOne : mocStepOnes) {
            images.add(mocStepOne.getPsImage());
            images.add(mocStepOne.getEbImage());
            images.add(mocStepOne.getOocImage());
            images.add(mocStepOne.getPiscImage());
            images.add(mocStepOne.getPrsImage());
            images.add(mocStepOne.getMticImage());
        }

        List<MOCStepTwoAP> stepTwoAPS = mocStepTwoAPRepository.findAll();
        for (MOCStepTwoAP mocStepTwoAP : stepTwoAPS) {
            images.add(mocStepTwoAP.getAttachmentFile());
        }

        List<MOCStepThree> stepThreeList = mocStepThreeRepository.findAll();
        for (MOCStepThree stepThree : stepThreeList) {
            images.add(stepThree.getAttachment());
        }

        List<MOCStepFour> mocStepFourList = mocStepFourRepository.findAll();
        for (MOCStepFour stepFour : mocStepFourList) {
            images.add(stepFour.getProcessItemAttachment());
            images.add(stepFour.getProcessRouteAttachment());
            images.add(stepFour.getCheckSheetAttachment());
            images.add(stepFour.getFailureModeAttachment());
            images.add(stepFour.getSopAttachment());
            images.add(stepFour.getProcessMappingAttachment());
            images.add(stepFour.getControlPlanAttachment());
            images.add(stepFour.getOthersAttachment());
        }

        List<MOCStepFiveDD> stepFiveDDList = mocStepFiveDDRepository.findAll();
        for (MOCStepFiveDD fiveDD : stepFiveDDList) {
            images.add(fiveDD.getAttachment());
        }

        List<MOCStepEight> mocStepEightList = mocStepEightRepository.findAll();
        for (MOCStepEight stepEight : mocStepEightList) {
            images.add((stepEight.getAttachment()));
        }

        String folderPath = environment.getProperty("image.uploadDirectory") + "/MOC";
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return new ResponseModel<>(false, "Folder not found or is not a directory");
        }
        System.out.println("images present " + images);
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

    public ResponseModel<String> addMOCStepOne(AddMOCStepOneRequest stepOneRequest) {
        try {
            List<MOCStepOne> stepOneList = mocStepOneRepository.findByMocNumber(stepOneRequest.getMocNumber());
            var actonApi = 0;
            if (!stepOneList.isEmpty()) {
                stepOneList.getFirst().setMocNumber(stepOneRequest.getMocNumber());
                stepOneList.getFirst().setProjectSponsorship(stepOneRequest.getProjectSponsorship());
                stepOneList.getFirst().setProjectLeader(stepOneRequest.getProjectLeader());
                stepOneList.getFirst().setSubject(stepOneRequest.getSubject());
                stepOneList.getFirst().setObjectiveOfChange(stepOneRequest.getObjectiveOfChange());
                stepOneList.getFirst().setOocImage(stepOneRequest.getOocImage());
                stepOneList.getFirst().setPresentScheme(stepOneRequest.getPresentScheme());
                stepOneList.getFirst().setPsImage(stepOneRequest.getPsImage());
                stepOneList.getFirst().setProposedRevisedScheme(stepOneRequest.getProposedRevisedScheme());
                stepOneList.getFirst().setPrsImage(stepOneRequest.getPrsImage());
                stepOneList.getFirst().setExpectedBenefits(stepOneRequest.getExpectedBenefits());
                stepOneList.getFirst().setEbImage(stepOneRequest.getEbImage());
                stepOneList.getFirst().setPreliminaryImpactScreeningChecklist(stepOneRequest.getPreliminaryImpactScreeningChecklist());
                stepOneList.getFirst().setPiscImage(stepOneRequest.getPiscImage());
                stepOneList.getFirst().setMocTriggerIdentificationChecklist(stepOneRequest.getMocTriggerIdentificationChecklist());
                stepOneList.getFirst().setMticImage(stepOneRequest.getMticImage());
                stepOneList.getFirst().setEstimatedCost(stepOneRequest.getEstimatedCost());

                mocStepOneRepository.save(stepOneList.getFirst());
                actonApi = 2;

            } else {
                var mocData = new MOCStepOne();
                mocData.setMocNumber(stepOneRequest.getMocNumber());
                mocData.setProjectLeader(stepOneRequest.getProjectLeader());
                mocData.setProjectSponsorship(stepOneRequest.getProjectSponsorship());
                mocData.setSubject(stepOneRequest.getSubject());
                mocData.setObjectiveOfChange(stepOneRequest.getObjectiveOfChange());
                mocData.setOocImage(stepOneRequest.getOocImage());
                mocData.setPresentScheme(stepOneRequest.getPresentScheme());
                mocData.setPsImage(stepOneRequest.getPsImage());
                mocData.setProposedRevisedScheme(stepOneRequest.getProposedRevisedScheme());
                mocData.setPrsImage(stepOneRequest.getPrsImage());
                mocData.setExpectedBenefits(stepOneRequest.getExpectedBenefits());
                mocData.setEbImage(stepOneRequest.getEbImage());
                mocData.setPreliminaryImpactScreeningChecklist(stepOneRequest.getPreliminaryImpactScreeningChecklist());
                mocData.setPiscImage(stepOneRequest.getPiscImage());
                mocData.setMocTriggerIdentificationChecklist(stepOneRequest.getMocTriggerIdentificationChecklist());
                mocData.setMticImage(stepOneRequest.getMticImage());
                mocData.setEstimatedCost(stepOneRequest.getEstimatedCost());

                mocStepOneRepository.save(mocData);
                actonApi = 1;

            }

            List<MOC> mocList = mocRepository.findByMocNumber(stepOneRequest.getMocNumber());
            if (!mocList.isEmpty()) {
                if (mocList.getFirst().getStatus() == 0) {
                    mocList.getFirst().setStatus(1);
                }
                mocRepository.save(mocList.getFirst());
            }
            return new ResponseModel<>(true, actonApi == 1 ? "Change Summary Added Successfully" : "Change Summary Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<MOCStepOne> getMOCStepOne(String mocNo) {
        try {
            var stepOneList = mocStepOneRepository.findByMocNumber(mocNo);

            return new ResponseModel<>(true, "Record Found", stepOneList.getFirst());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);
        }
    }


    public ResponseModel<String> addMOCStepTwo(AddMOCStepTwoRequest stepTwoRequest) {
        try {
            List<MOCStepTwo> stepTwoList = mocStepTwoRepository.findByMocNumber(stepTwoRequest.getMocNumber());
            var actionApi = 0;
            if (!stepTwoList.isEmpty()) {
                stepTwoList.getFirst().setMocNumber(stepTwoRequest.getMocNumber());
                mocStepTwoRepository.save(stepTwoList.getFirst());

                var addedTableList = mocStepTwoAPRepository.findByMocNumber(stepTwoList.getFirst().getMocNumber());
                if (!addedTableList.isEmpty()) {
                    for (MOCStepTwoAP items : addedTableList) {
                        if (items.getMocNumber().equals(stepTwoList.getFirst().getMocNumber())) {
                            mocStepTwoAPRepository.deleteByStepTwoAPId(items.getStepTwoAPId());
                        }
                    }
                }
                for (MOCStepTwoAP items : stepTwoRequest.getStepTwoAPList()) {
                    var mocStepTwoAP = new MOCStepTwoAP();

                    mocStepTwoAP.setAssetNumber(items.getAssetNumber());
                    mocStepTwoAP.setAssetId(items.getAssetId());
                    mocStepTwoAP.setDepartmentId(items.getDepartmentId());
                    mocStepTwoAP.setDepartment(items.getDepartment());
                    mocStepTwoAP.setAssetDescription(items.getAssetDescription());
                    mocStepTwoAP.setHindalcoTeamMembers(items.getHindalcoTeamMembers());
                    mocStepTwoAP.setNonHindalcoTeamMembers(items.getNonHindalcoTeamMembers());
                    mocStepTwoAP.setActionPlan(items.getActionPlan());
                    mocStepTwoAP.setStartDate(items.getStartDate());
                    mocStepTwoAP.setEndDate(items.getEndDate());
                    mocStepTwoAP.setAttachmentFile(items.getAttachmentFile());
                    mocStepTwoAP.setAttachmentDescription(items.getAttachmentDescription());
                    mocStepTwoAP.setUrl(items.getUrl());
                    mocStepTwoAP.setMocNumber(stepTwoList.getFirst().getMocNumber());

                    mocStepTwoAPRepository.save(mocStepTwoAP);

                }
                actionApi = 2;
            } else {
                var mocStepTwo = new MOCStepTwo();
                mocStepTwo.setMocNumber(stepTwoRequest.getMocNumber());
                mocStepTwo.setActionPlanList(stepTwoRequest.getStepTwoAPList());
                mocStepTwoRepository.save(mocStepTwo);

                List<MOCStepTwo> MOCStepTwoList = mocStepTwoRepository.findAll();

                for (MOCStepTwoAP items : stepTwoRequest.getStepTwoAPList()) {
                    var mocStepTwoAP = new MOCStepTwoAP();

                    mocStepTwoAP.setAssetNumber(items.getAssetNumber());
                    mocStepTwoAP.setAssetId(items.getAssetId());
                    mocStepTwoAP.setDepartment(items.getDepartment());
                    mocStepTwoAP.setDepartmentId(items.getDepartmentId());
                    mocStepTwoAP.setAssetDescription(items.getAssetDescription());
                    mocStepTwoAP.setHindalcoTeamMembers(items.getHindalcoTeamMembers());
                    mocStepTwoAP.setNonHindalcoTeamMembers(items.getNonHindalcoTeamMembers());
                    mocStepTwoAP.setActionPlan(items.getActionPlan());
                    mocStepTwoAP.setStartDate(items.getStartDate());
                    mocStepTwoAP.setEndDate(items.getEndDate());
                    mocStepTwoAP.setAttachmentFile(items.getAttachmentFile());
                    mocStepTwoAP.setAttachmentDescription(items.getAttachmentDescription());
                    mocStepTwoAP.setUrl(items.getUrl());
                    mocStepTwoAP.setMocNumber(MOCStepTwoList.getLast().getMocNumber());

                    mocStepTwoAPRepository.save(mocStepTwoAP);

                }
                actionApi = 1;
            }
            List<MOC> mocList = mocRepository.findByMocNumber(stepTwoRequest.getMocNumber());
            if (!mocList.isEmpty()) {
                if (mocList.getFirst().getStatus() == 1) {
                    mocList.getFirst().setStatus(2);
                }
                mocRepository.save(mocList.getFirst());
            }
            return new ResponseModel<>(true, actionApi == 1 ? "Action Plan Added Successfully" : "Action Plan updated successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<List<MOCStepTwo>> getMOCStepTwo(String mocNumber) {
        try {
            var stepTwoList = mocStepTwoRepository.findByMocNumber(mocNumber);

            return new ResponseModel<>(true, "Records found", stepTwoList.reversed());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }


    public ResponseModel<String> addStepThree(AddMOCStepThreeRequest stepThreeRequest) {
        try {
            List<MOCStepThree> stepThreeList = mocStepThreeRepository.findByMocNumber(stepThreeRequest.getMocNumber());
            var actionApi = 0;
            if (!stepThreeList.isEmpty()) {
                stepThreeList.getFirst().setMocNumber(stepThreeRequest.getMocNumber());
                stepThreeList.getFirst().setAttachment(stepThreeRequest.getAttachment());
                stepThreeList.getFirst().setUrl(stepThreeRequest.getUrl());
                stepThreeList.getFirst().setComments(stepThreeRequest.getComments());

                mocStepThreeRepository.save(stepThreeList.getFirst());

                actionApi = 2;
            } else {
                var mocData = new MOCStepThree();
                mocData.setMocNumber(stepThreeRequest.getMocNumber());
                mocData.setAttachment(stepThreeRequest.getAttachment());
                mocData.setUrl(stepThreeRequest.getUrl());
                mocData.setComments(stepThreeRequest.getComments());

                mocStepThreeRepository.save(mocData);
                actionApi = 1;
            }
            List<MOC> mocList = mocRepository.findByMocNumber(stepThreeRequest.getMocNumber());
            if (!mocList.isEmpty()) {
                if (mocList.getFirst().getStatus() == 2) {
                    mocList.getFirst().setStatus(3);
                }
                mocRepository.save(mocList.getFirst());
            }
            return new ResponseModel<>(true, actionApi == 1 ? "Risk Assessment created Successfully" : "Risk Assessment Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<MOCStepThree> getMOCStepThree(String mocNumber) {
        try {
            var stepThreeList = mocStepThreeRepository.findByMocNumber(mocNumber);

            return new ResponseModel<>(true, "Record Found", stepThreeList.getFirst());
        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);

        }
    }

    public ResponseModel<String> addStepFour(AddMOCStepFourRequest stepFourRequest) {
        try {
            List<MOCStepFour> stepFourList = mocStepFourRepository.findByMocNumber(stepFourRequest.getMocNumber());
            var actionApi = 0;
            if (!stepFourList.isEmpty()) {
                stepFourList.getFirst().setMocNumber(stepFourRequest.getMocNumber());

                stepFourList.getFirst().setProcessItem(stepFourRequest.getProcessItem());
                stepFourList.getFirst().setProcessItemAttachment(stepFourRequest.getProcessItemAttachment());
                stepFourList.getFirst().setProcessItemComments(stepFourRequest.getProcessItemComments());
                stepFourList.getFirst().setProcessItemUrl(stepFourRequest.getProcessItemUrl());

                stepFourList.getFirst().setProcessRoute(stepFourRequest.getProcessRoute());
                stepFourList.getFirst().setProcessRouteAttachment(stepFourRequest.getProcessRouteAttachment());
                stepFourList.getFirst().setProcessRouteComments(stepFourRequest.getProcessRouteComments());
                stepFourList.getFirst().setProcessRouteUrl(stepFourRequest.getProcessRouteUrl());

                stepFourList.getFirst().setCheckSheet(stepFourRequest.getCheckSheet());
                stepFourList.getFirst().setCheckSheetAttachment(stepFourRequest.getCheckSheetAttachment());
                stepFourList.getFirst().setCheckSheetComments(stepFourRequest.getCheckSheetComments());
                stepFourList.getFirst().setCheckSheetUrl(stepFourRequest.getCheckSheetUrl());

                stepFourList.getFirst().setFailureMode(stepFourRequest.getFailureMode());
                stepFourList.getFirst().setFailureModeComments(stepFourRequest.getFailureModeComments());
                stepFourList.getFirst().setFailureModeAttachment(stepFourRequest.getFailureModeAttachment());
                stepFourList.getFirst().setFailureModeUrl(stepFourRequest.getFailureModeUrl());

                stepFourList.getFirst().setSop(stepFourRequest.getSop());
                stepFourList.getFirst().setSopAttachment(stepFourRequest.getSopAttachment());
                stepFourList.getFirst().setSopUrl(stepFourRequest.getSopUrl());
                stepFourList.getFirst().setSopComments(stepFourRequest.getSopComments());

                stepFourList.getFirst().setProcessMapping(stepFourRequest.getProcessMapping());
                stepFourList.getFirst().setProcessMappingComments(stepFourRequest.getProcessMappingComments());
                stepFourList.getFirst().setProcessMappingAttachment(stepFourRequest.getProcessMappingAttachment());
                stepFourList.getFirst().setProcessMappingUrl(stepFourRequest.getProcessMappingUrl());

                stepFourList.getFirst().setControlPlan(stepFourRequest.getControlPlan());
                stepFourList.getFirst().setControlPlanComments(stepFourRequest.getControlPlanComments());
                stepFourList.getFirst().setControlPlanAttachment(stepFourRequest.getControlPlanAttachment());
                stepFourList.getFirst().setControlPlanUrl(stepFourRequest.getControlPlanUrl());

                stepFourList.getFirst().setOthers(stepFourRequest.getOthers());
                stepFourList.getFirst().setOthersComments(stepFourRequest.getOthersComments());
                stepFourList.getFirst().setOthersAttachment(stepFourRequest.getOthersAttachment());
                stepFourList.getFirst().setOthersUrl(stepFourRequest.getOthersUrl());

                mocStepFourRepository.save(stepFourList.getFirst());

                var addedStepOneList = mocStepFourRepository.findByStepFourId(stepFourList.getFirst().getStepFourId());
                if (!addedStepOneList.isEmpty()) {
                    for (MOCStepFour items : addedStepOneList) {
                        if (items.getStepFourId().equals(addedStepOneList.getFirst().getStepFourId())) {
                            mocStepOneRepository.deleteByStepOneId(items.getStepFourId());
                        }
                    }
                }
                actionApi = 2;

            } else {
                var mocData = new MOCStepFour();
                mocData.setMocNumber(stepFourRequest.getMocNumber());
                mocData.setProcessItem(stepFourRequest.getProcessItem());
                mocData.setProcessItemUrl(stepFourRequest.getProcessItemUrl());
                mocData.setProcessItemAttachment(stepFourRequest.getProcessItemAttachment());
                mocData.setProcessItemComments(stepFourRequest.getProcessItemComments());

                mocData.setProcessRoute(stepFourRequest.getProcessRoute());
                mocData.setProcessRouteUrl(stepFourRequest.getProcessRouteUrl());
                mocData.setProcessRouteComments(stepFourRequest.getProcessRouteComments());
                mocData.setProcessRouteAttachment(stepFourRequest.getProcessRouteAttachment());

                mocData.setCheckSheet(stepFourRequest.getCheckSheet());
                mocData.setCheckSheetUrl(stepFourRequest.getCheckSheetUrl());
                mocData.setCheckSheetComments(stepFourRequest.getCheckSheetComments());
                mocData.setCheckSheetAttachment(stepFourRequest.getCheckSheetAttachment());

                mocData.setFailureMode(stepFourRequest.getFailureMode());
                mocData.setFailureModeUrl(stepFourRequest.getFailureModeUrl());
                mocData.setFailureModeComments(stepFourRequest.getFailureModeComments());
                mocData.setFailureModeAttachment(stepFourRequest.getFailureModeAttachment());

                mocData.setSop(stepFourRequest.getSop());
                mocData.setSopUrl(stepFourRequest.getSopUrl());
                mocData.setSopAttachment(stepFourRequest.getSopAttachment());
                mocData.setSopComments(stepFourRequest.getSopComments());

                mocData.setProcessMapping(stepFourRequest.getProcessMapping());
                mocData.setProcessMappingUrl(stepFourRequest.getProcessMappingUrl());
                mocData.setProcessMappingAttachment(stepFourRequest.getProcessMappingAttachment());
                mocData.setProcessMappingComments(stepFourRequest.getProcessMappingComments());

                mocData.setControlPlan(stepFourRequest.getControlPlan());
                mocData.setControlPlanUrl(stepFourRequest.getControlPlanUrl());
                mocData.setControlPlanComments(stepFourRequest.getControlPlanComments());
                mocData.setControlPlanAttachment(stepFourRequest.getControlPlanAttachment());

                mocData.setOthers(stepFourRequest.getOthers());
                mocData.setOthersUrl(stepFourRequest.getOthersUrl());
                mocData.setOthersAttachment(stepFourRequest.getOthersAttachment());
                mocData.setOthersComments(stepFourRequest.getOthersComments());

                mocStepFourRepository.save(mocData);
                actionApi = 1;
            }
            List<MOC> mocList = mocRepository.findByMocNumber(stepFourRequest.getMocNumber());
            if (!mocList.isEmpty()) {
                if (mocList.getFirst().getStatus() == 3) {
                    mocList.getFirst().setStatus(4);
                }
                mocRepository.save(mocList.getFirst());
            }
            return new ResponseModel<>(true, actionApi == 1 ? "Process Document created Successfully" : "Process Document updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<MOCStepFour> getMOCStepFour(String mocNumber) {
        try {
            var stepFourList = mocStepFourRepository.findByMocNumber(mocNumber);

            return new ResponseModel<>(true, "Record Found", stepFourList.getFirst());
        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);

        }
    }

    public ResponseModel<String> addMOCStepFive(AddMOCStepFiveRequest stepFiveRequest) {
        try {
            List<MOCStepFive> stepFiveList = mocStepFiveRepository.findByMocNumber(stepFiveRequest.getMocNumber());
            var actionApi = 0;
            if (!stepFiveList.isEmpty()) {
                stepFiveList.getFirst().setMocNumber(stepFiveRequest.getMocNumber());
                mocStepFiveRepository.save(stepFiveList.getFirst());

                var addedTableList = mocStepFiveDDRepository.findByMocNumber(stepFiveList.getFirst().getMocNumber());
                if (!addedTableList.isEmpty()) {
                    for (MOCStepFiveDD items : addedTableList) {
                        if (items.getMocNumber().equals(stepFiveList.getFirst().getMocNumber())) {
                            mocStepFiveDDRepository.deleteByMocStepFiveDDId(items.getMocStepFiveDDId());
                        }
                    }
                }
                for (MOCStepFiveDD items : stepFiveRequest.getStepFiveDDList()) {
                    var mocStepFiveDD = new MOCStepFiveDD();

                    mocStepFiveDD.setAssetNumber(items.getAssetNumber());
                    mocStepFiveDD.setDocumentType(items.getDocumentType());
                    mocStepFiveDD.setAssetDescription(items.getAssetDescription());
                    mocStepFiveDD.setDocumentUpdate(items.getDocumentUpdate());
                    mocStepFiveDD.setResponsibility(items.getResponsibility());
                    mocStepFiveDD.setSupportRequired(items.getSupportRequired());
                    mocStepFiveDD.setAttachment(items.getAttachment());
                    mocStepFiveDD.setAttachmentDescription(items.getAttachmentDescription());
                    mocStepFiveDD.setUrl(items.getUrl());
                    mocStepFiveDD.setMocNumber(stepFiveList.getFirst().getMocNumber());

                    mocStepFiveDDRepository.save(mocStepFiveDD);

                }
                actionApi = 2;
            } else {
                var mocStepFive = new MOCStepFive();
                mocStepFive.setMocNumber(stepFiveRequest.getMocNumber());
                mocStepFive.setTableList(stepFiveRequest.getStepFiveDDList());
                mocStepFiveRepository.save(mocStepFive);

                List<MOCStepFive> MOCStepFiveList = mocStepFiveRepository.findAll();

                for (MOCStepFiveDD items : stepFiveRequest.getStepFiveDDList()) {
                    var mocStepFiveDD = new MOCStepFiveDD();

                    mocStepFiveDD.setAssetNumber(items.getAssetNumber());
                    mocStepFiveDD.setDocumentType(items.getDocumentType());
                    mocStepFiveDD.setAssetDescription(items.getAssetDescription());
                    mocStepFiveDD.setDocumentUpdate(items.getDocumentUpdate());
                    mocStepFiveDD.setResponsibility(items.getResponsibility());
                    mocStepFiveDD.setSupportRequired(items.getSupportRequired());
                    mocStepFiveDD.setAttachment(items.getAttachment());
                    mocStepFiveDD.setAttachmentDescription(items.getAttachmentDescription());
                    mocStepFiveDD.setUrl(items.getUrl());
                    mocStepFiveDD.setMocNumber(MOCStepFiveList.getLast().getMocNumber());

                    mocStepFiveDDRepository.save(mocStepFiveDD);

                }

                actionApi = 1;
            }
            List<MOC> mocList = mocRepository.findByMocNumber(stepFiveRequest.getMocNumber());
            if (!mocList.isEmpty()) {
                if (mocList.getFirst().getStatus() == 4) {
                    mocList.getFirst().setStatus(5);
                }
                mocRepository.save(mocList.getFirst());
            }

            return new ResponseModel<>(true, actionApi == 1 ? "Documents/Drawing Added Successfully" : "Documents/Drawing Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<MOCStepFive> getMOCStepFive(String mocNumber) {
        try {
            List<MOCStepFive> stepFiveList = mocStepFiveRepository.findByMocNumber(mocNumber);

            return new ResponseModel<>(true, "Records found", stepFiveList.getFirst());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }


//    public ResponseModel<String> addStepSix(AddMOCStepSixRequest stepSixRequest) {
//        try {
////            // Fetch all department names from the department repository
////            List<String> validDepartmentNames = departmentRepository.findAll().stream()
////                    .map(Department::getDepartment)
////                    .toList();
////
////            // Collect department names from the request
////            Set<String> requestedDepartments = stepSixRequest.getApproverList().stream()
////                    .map(MOCStepSixAL::getDepartment)
////                    .collect(Collectors.toSet());
////
////            // Define the required departments
////            List<String> requiredDepartments = List.of("Quality", "Environment", "Safety");
////
////            // Check for missing required departments
////            List<String> missingDepartments = requiredDepartments.stream()
////                    .filter(dept -> !requestedDepartments.contains(dept))
////                    .collect(Collectors.toList());
////
////            // If there are missing required departments, return an error response
////            if (!missingDepartments.isEmpty()) {
////                return new ResponseModel<>(false, "Missing required departments: " + String.join(", ", missingDepartments), null);
////            }
////
////            // Validate if all requested departments are valid
////            List<String> invalidDepartments = requestedDepartments.stream()
////                    .filter(dept -> !validDepartmentNames.contains(dept))
////                    .collect(Collectors.toList());
////
////            // If there are invalid departments, return an error response
////            if (!invalidDepartments.isEmpty()) {
////                return new ResponseModel<>(false, "Invalid departments: " + String.join(", ", invalidDepartments), null);
////            }
//
//            List<MOCStepSix> stepSixList = mocStepSixRepository.findByMocNumber(stepSixRequest.getMocNumber());
//            var actionApi = 0;
//            if (!stepSixList.isEmpty()) {
//                stepSixList.getFirst().setMocNumber(stepSixRequest.getMocNumber());
//                mocStepSixRepository.save(stepSixList.getFirst());
//
//                var addedAlList = mocStepSixALRepository.findByMocNumber(stepSixList.getFirst().getMocNumber());
//                if (!addedAlList.isEmpty()) {
//                    for (MOCStepSixAL items : addedAlList) {
//                        if (items.getMocNumber().equals(stepSixList.getFirst().getMocNumber())) {
//                            mocStepSixALRepository.deleteByStepSixALId(items.getStepSixALId());
//                        }
//                    }
//                }
//
//                for (MOCStepSixAL item : stepSixRequest.getApproverList()) {
//                    var mocApproverData = new MOCStepSixAL();
//                    mocApproverData.setApproverId(item.getApproverId());
//                    mocApproverData.setApproverName(item.getApproverName());
//                    mocApproverData.setDepartmentId(item.getDepartmentId());
//                    mocApproverData.setDepartment(item.getDepartment());
//                    mocApproverData.setRemarks(item.getRemarks());
//                    mocApproverData.setApprovalStatus(item.getApprovalStatus());
//                    mocApproverData.setMocNumber(stepSixList.getFirst().getMocNumber());
//                    mocStepSixALRepository.save(mocApproverData);
//
//                }
//                actionApi = 2;
//            } else {
//                var mocData = new MOCStepSix();
//                mocData.setMocNumber(stepSixRequest.getMocNumber());
//
//                mocStepSixRepository.save(mocData);
//                List<MOCStepSixAL> MOCStepSixList = mocStepSixALRepository.findByMocNumber(stepSixRequest.getMocNumber());
//
//                for (MOCStepSixAL item : stepSixRequest.getApproverList()) {
//                    var mocApproverList = new MOCStepSixAL();
//                    mocApproverList.setApproverId(item.getApproverId());
//                    mocApproverList.setApproverName(item.getApproverName());
//                    mocApproverList.setDepartmentId(item.getDepartmentId());
//                    mocApproverList.setDepartment(item.getDepartment());
//                    mocApproverList.setRemarks(item.getRemarks());
//                    mocApproverList.setApprovalStatus(0);
//                    mocApproverList.setMocNumber(MOCStepSixList.getLast().getMocNumber());
//
//                    mocStepSixALRepository.save(mocApproverList);
//
//                }
//                actionApi = 1;
//            }
//            List<MOC> mocList = mocRepository.findByMocNumber(stepSixRequest.getMocNumber());
//            if (!mocList.isEmpty()) {
//                if (mocList.getFirst().getStatus() == 5) {
//                    mocList.getFirst().setStatus(6);
//                }
//                mocRepository.save(mocList.getFirst());
//            }
//            return new ResponseModel<>(true, actionApi == 1 ? "Review & Endorsement created Successfully" : "Review & Endorsement updated Successfully", null);
//
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Failed to add", null);
//
//        }
//    }

    public ResponseModel<String> addStepSix(AddMOCStepSixRequest stepSixRequest) {
        try {
            List<MOCStepSix> stepSixList = mocStepSixRepository.findByMocNumber(stepSixRequest.getMocNumber());

            var actionApi = 0;
            if (!stepSixList.isEmpty()) {
                stepSixList.getFirst().setMocNumber(stepSixRequest.getMocNumber());
                mocStepSixRepository.save(stepSixList.getFirst());

                var addedAlList = mocStepSixALRepository.findByMocNumber(stepSixList.getFirst().getMocNumber());
                if (!addedAlList.isEmpty()) {
                    for (MOCStepSixAL items : addedAlList) {
                        if (items.getMocNumber().equals(stepSixList.getFirst().getMocNumber())) {
                            mocStepSixALRepository.deleteByStepSixALId(items.getStepSixALId());
                        }
                    }
                }

                for (MOCStepSixAL item : stepSixRequest.getApproverList()) {
                    // Check if the approver is already added for this MOC number
                    MOCStepSixAL existingApprover = mocStepSixALRepository.findByApproverNameAndMocNumber(item.getApproverName(), stepSixRequest.getMocNumber());
                    if (existingApprover != null) {
                        return new ResponseModel<>(false, "Approver " + item.getApproverName() + " already added for MOC number: " + stepSixRequest.getMocNumber(), null);
                    }
                    if (existingApprover == null) {
                        var mocApproverData = new MOCStepSixAL();
                        mocApproverData.setApproverId(item.getApproverId());
                        mocApproverData.setApproverName(item.getApproverName());
                        mocApproverData.setDepartmentId(item.getDepartmentId());
                        mocApproverData.setDepartment(item.getDepartment());
                        mocApproverData.setRemarks(item.getRemarks());
                        mocApproverData.setApprovalStatus(item.getApprovalStatus());
                        mocApproverData.setMocNumber(stepSixRequest.getMocNumber());
                        mocStepSixALRepository.save(mocApproverData);
                    }
                }
                actionApi = 2;
            } else {
                var mocData = new MOCStepSix();
                mocData.setMocNumber(stepSixRequest.getMocNumber());

                mocStepSixRepository.save(mocData);
                List<MOCStepSixAL> MOCStepSixList = mocStepSixALRepository.findByMocNumber(stepSixRequest.getMocNumber());

                for (MOCStepSixAL item : stepSixRequest.getApproverList()) {
                    // Check if the approver is already added for this MOC number
                    MOCStepSixAL existingApprover = mocStepSixALRepository.findByApproverNameAndMocNumber(item.getApproverName(), stepSixRequest.getMocNumber());
                    if (existingApprover != null) {
                        return new ResponseModel<>(false, "Approver " + item.getApproverName() + " already added for MOC number: " + stepSixRequest.getMocNumber(), null);
                    }
                    if (existingApprover == null) {
                        var mocApproverList = new MOCStepSixAL();
                        mocApproverList.setApproverId(item.getApproverId());
                        mocApproverList.setApproverName(item.getApproverName());
                        mocApproverList.setDepartmentId(item.getDepartmentId());
                        mocApproverList.setDepartment(item.getDepartment());
                        mocApproverList.setRemarks(item.getRemarks());
                        mocApproverList.setApprovalStatus(0);
                        mocApproverList.setMocNumber(stepSixRequest.getMocNumber());
                        mocStepSixALRepository.save(mocApproverList);
                    }
                }
                actionApi = 1;
            }
            List<MOC> mocList = mocRepository.findByMocNumber(stepSixRequest.getMocNumber());
            if (!mocList.isEmpty()) {
                MOC moc = mocList.getFirst();
                String proposedChange = moc.getTypeOfProposedChange();
                String docType = moc.getDocType();

                // Check department heads based on proposedChange
                ResponseModel<String> validationResponse = validateApprovers(stepSixRequest.getApproverList(), proposedChange, docType);
                if (!validationResponse.isSuccess()) {
                    return validationResponse; // Return detailed validation error
                }

                if (moc.getStatus() == 5) {
                    moc.setStatus(6);
                }
                mocRepository.save(moc);
            }
            return new ResponseModel<>(true, actionApi == 1 ? "Review & Endorsement created Successfully" : "Review & Endorsement updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    private ResponseModel<String>  validateApprovers(List<MOCStepSixAL> approverList, String proposedChange, String docType) {
        Set<String> requiredDepartments = new HashSet<>();

        if (("People".equalsIgnoreCase(proposedChange) || "Process".equalsIgnoreCase(proposedChange)) &&
                ("Temporary/Emergency".equalsIgnoreCase(docType))){
            requiredDepartments.add("Environment");
            requiredDepartments.add("Safety");
            requiredDepartments.add("Quality");
        } else {
            requiredDepartments.add("Environment");
            requiredDepartments.add("Safety");
            requiredDepartments.add("Quality");
            requiredDepartments.add("Civil");
            requiredDepartments.add("Mechanical");
            requiredDepartments.add("Operation");
            requiredDepartments.add("Electrical");
            requiredDepartments.add("Instrumentation");
        }

        Set<String> presentDepartments = approverList.stream()
                .map(MOCStepSixAL::getDepartment)
                .collect(Collectors.toSet());

        // Find missing departments
        Set<String> missingDepartments = new HashSet<>(requiredDepartments);
        missingDepartments.removeAll(presentDepartments);

        if (missingDepartments.isEmpty()) {
            return new ResponseModel<>(true, "", null);
        } else {
            String missingDepartmentsMessage = String.join(", ", missingDepartments);
            return new ResponseModel<>(false, "Please add department(s): " + missingDepartmentsMessage, null);
        }
    }

    public ResponseModel<MOCStepSix> getMOCStepSix(String mocNumber) {
        try {
            List<MOCStepSix> stepSixes = mocStepSixRepository.findByMocNumber(mocNumber);
            return new ResponseModel<>(true, "Record Found", stepSixes.getFirst());
        } catch (Exception e) {
            return new ResponseModel<>(false, "Record Not Found", null);
        }
    }

//    public ResponseModel<String> validateMOCStepSix(ValidateMOCStepSixRequest request) {
//        try {
//            List<MOCStepSixAL> mocStepSixALList = mocStepSixALRepository.findByMocNumber(request.getMocNumber());
//
//            List<MOC> mocList = mocRepository.findByMocNumber(request.getMocNumber());
//
//
//            for (MOCStepSixAL approver : mocStepSixALList) {
//
//                if (approver.getApproverName().equals(request.getApproverList().getApproverName())) {
//                    approver.setRemarks(request.getApproverList().getRemarks());
//                    if (request.getApproverList().getApprovalStatus() != 0) {
//                        if (request.getApproverList().getApprovalStatus() == 1) {
//                            approver.setApprovalStatus(1);
//                            approver.setApproverUpdateDateTime(LocalDateTime.now());
//                        } else if (request.getApproverList().getApprovalStatus() == 2) {
//                            approver.setApprovalStatus(2);
//                            approver.setApproverUpdateDateTime(LocalDateTime.now());
//                            mocList.getFirst().setStatus(10);
//                        }
//                        mocRepository.save(mocList.getFirst());
//                    }
//                    mocStepSixALRepository.save(approver);
//                }
//            }
//
//            return new ResponseModel<>(true, "Updated Successfully", null);
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Failed to update", null);
//        }
//    }

    public ResponseModel<String> validateMOCStepSix(ValidateMOCStepSixRequest request) {
        try {
            List<MOCStepSixAL> mocStepSixALList = mocStepSixALRepository.findByMocNumber(request.getMocNumber());

            List<MOC> mocList = mocRepository.findByMocNumber(request.getMocNumber());


            for (MOCStepSixAL approver : mocStepSixALList) {

                if (approver.getApproverName().equals(request.getApproverList().getApproverName())) {
                    approver.setRemarks(request.getApproverList().getRemarks());
                    if (request.getApproverList().getApprovalStatus() != 0) {
                        if (request.getApproverList().getApprovalStatus() == 1) {
                            approver.setApprovalStatus(1);
                            approver.setApproverUpdateDateTime(LocalDateTime.now());

                        } else if (request.getApproverList().getApprovalStatus() == 2) {
                            approver.setApprovalStatus(2);
                            approver.setApproverUpdateDateTime(LocalDateTime.now());
                            mocList.getFirst().setStatus(10);
                            mocList.getFirst().setRevertStages(7);
                            mocList.getFirst().setRevertBackDate(LocalDateTime.now());
                        }
                        mocRepository.save(mocList.getFirst());
                    }
                    mocStepSixALRepository.save(approver);
                }
            }

            return new ResponseModel<>(true, "Updated Successfully", null);
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to update", null);
        }
    }


    public ResponseModel<String> addStepSeven(AddMOCStepSevenRequest mocStepSevenRequest) {
        try {
            List<MOCStepSeven> stepSevenList = mocStepSevenRepository.findByMocNumber(mocStepSevenRequest.getMocNumber());

            if (stepSevenList.size() != 0) {
                stepSevenList.getFirst().setPlantHeadId(mocStepSevenRequest.getPlantHeadId());
                stepSevenList.getFirst().setPlantHead(mocStepSevenRequest.getPlantHead());
                stepSevenList.getFirst().setPlantHeadApproverComments(mocStepSevenRequest.getPlantHeadApproverComments());
                stepSevenList.getFirst().setUnitHeadId(mocStepSevenRequest.getUnitHeadId());
                stepSevenList.getFirst().setUnitHead(mocStepSevenRequest.getUnitHead());
                stepSevenList.getFirst().setUnitHeadApproverComments(mocStepSevenRequest.getUnitHeadApproverComments());
                stepSevenList.getFirst().setMocNumber(mocStepSevenRequest.getMocNumber());
                stepSevenList.getFirst().setPlantHeadApproverStatus(mocStepSevenRequest.getPlantHeadApproverStatus());
                stepSevenList.getFirst().setUnitHeadApproverStatus(mocStepSevenRequest.getUnitHeadApproverStatus());

                mocStepSevenRepository.save(stepSevenList.getFirst());

            } else {
                var mocData = new MOCStepSeven();
                mocData.setPlantHeadId(mocStepSevenRequest.getPlantHeadId());
                mocData.setPlantHead(mocStepSevenRequest.getPlantHead());
                mocData.setPlantHeadApproverComments("");
                mocData.setUnitHeadId(mocStepSevenRequest.getUnitHeadId());
                mocData.setUnitHead(mocStepSevenRequest.getUnitHead());
                mocData.setUnitHeadApproverComments("");
                mocData.setMocNumber(mocStepSevenRequest.getMocNumber());
                mocData.setPlantHeadApproverStatus(0);
                mocData.setUnitHeadApproverStatus(0);

                mocStepSevenRepository.save(mocData);

            }
            List<MOC> mocList = mocRepository.findByMocNumber(mocStepSevenRequest.getMocNumber());
            if (!mocList.isEmpty()) {
                if (mocList.getFirst().getStatus() == 6) {
                    mocList.getFirst().setStatus(7);
                    mocList.getFirst().setCompletedDate(LocalDateTime.now());
                } else if (mocList.getFirst().getStatus() == 10) {
                    mocList.getFirst().setStatus(7);
                }
                mocRepository.save(mocList.getFirst());
            }
            return new ResponseModel<>(true, "Final Approval added Successfully", null);
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add approval", null);
        }
    }

    public ResponseModel<MOCStepSeven> getMOCStepSeven(String mocNumber) {

        try {
            var mocData = mocStepSevenRepository.findByMocNumber(mocNumber);
            return new ResponseModel<>(true, "Record Found", mocData.getFirst());
        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);

        }
    }


    public ResponseModel<String> validateMOCStepSeven(AddMOCStepSevenRequest request) {
        try {
            List<MOCStepSeven> mocStepSevenList = mocStepSevenRepository.findByMocNumber(request.getMocNumber());

            List<MOC> mocList = mocRepository.findByMocNumber(request.getMocNumber());
            if (!mocStepSevenList.isEmpty()) {
                MOCStepSeven mocStepSeven = mocStepSevenList.getFirst();

                mocStepSeven.setPlantHead(request.getPlantHead());
                mocStepSeven.setPlantHeadApproverComments(request.getPlantHeadApproverComments());

                mocStepSeven.setUnitHead(request.getUnitHead());
                mocStepSeven.setUnitHeadApproverComments(request.getUnitHeadApproverComments());

                if (request.getPlantHeadApproverStatus() == 1) {
                    mocStepSeven.setPlantHeadApproverStatus(1);
                    mocStepSeven.setPlantHeadUpdatedDateTime(LocalDateTime.now());
                } else if (request.getPlantHeadApproverStatus() == 2) {
                    mocStepSeven.setPlantHeadApproverStatus(2);
                    mocStepSeven.setPlantHeadUpdatedDateTime(LocalDateTime.now());
                    mocList.getFirst().setStatus(10);
                    mocList.getFirst().setRevertStages(7);
                    mocList.getFirst().setRevertBackDate(LocalDateTime.now());
                }

                if (request.getUnitHeadApproverStatus() == 1) {
                    mocStepSeven.setUnitHeadApproverStatus(1);
                    mocStepSeven.setUnitHeadUpdatedDateTime(LocalDateTime.now());
                } else if (request.getUnitHeadApproverStatus() == 2) {
                    mocStepSeven.setUnitHeadApproverStatus(2);
                    mocStepSeven.setUnitHeadUpdatedDateTime(LocalDateTime.now());
                    mocList.getFirst().setStatus(10);
                    mocList.getFirst().setRevertStages(7);
                    mocList.getFirst().setRevertBackDate(LocalDateTime.now());
                }

                mocStepSevenRepository.save(mocStepSeven);

                mocRepository.save(mocList.getFirst());

                return new ResponseModel<>(true, "Updated Successfully", null);
            } else {
                return new ResponseModel<>(false, "MOC Step Seven not found", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to update", null);
        }
    }

    public ResponseModel<String> addStepEight(AddMOCStepEightRequest mocStepEightRequest) {
        try {
            List<MOCStepEight> stepEightList = mocStepEightRepository.findByMocNumber(mocStepEightRequest.getMocNumber());

            if (stepEightList.size() != 0) {
                stepEightList.getFirst().setCertifiedName1(mocStepEightRequest.getCertifiedName1());
                stepEightList.getFirst().setDesignation1(mocStepEightRequest.getDesignation1());
                stepEightList.getFirst().setRemarks1(mocStepEightRequest.getRemarks1());
                stepEightList.getFirst().setMocNumber(mocStepEightRequest.getMocNumber());
                stepEightList.getFirst().setCertifiedName2(mocStepEightRequest.getCertifiedName2());
                stepEightList.getFirst().setDesignation2(mocStepEightRequest.getDesignation2());
                stepEightList.getFirst().setRemarks2(mocStepEightRequest.getRemarks2());
                stepEightList.getFirst().setPerformance(mocStepEightRequest.getPerformance());
                stepEightList.getFirst().setAttachment(mocStepEightRequest.getAttachment());
                stepEightList.getFirst().setStartDate(mocStepEightRequest.getStartDate());
                stepEightList.getFirst().setEndDate(mocStepEightRequest.getEndDate());

                stepEightList.getFirst().setOperationalHead(mocStepEightRequest.getOperationalHead());
                stepEightList.getFirst().setApproverStatus(mocStepEightRequest.getApproverStatus());

                mocStepEightRepository.save(stepEightList.getFirst());
            } else {
                var mocData = new MOCStepEight();
                mocData.setCertifiedName1(mocStepEightRequest.getCertifiedName1());
                mocData.setDesignation1(mocStepEightRequest.getDesignation1());
                mocData.setRemarks1(mocStepEightRequest.getRemarks1());
                mocData.setMocNumber(mocStepEightRequest.getMocNumber());
                mocData.setCertifiedName2(mocStepEightRequest.getCertifiedName2());
                mocData.setDesignation2(mocStepEightRequest.getDesignation2());
                mocData.setRemarks2(mocStepEightRequest.getRemarks2());
                mocData.setPerformance(mocStepEightRequest.getPerformance());
                mocData.setAttachment(mocStepEightRequest.getAttachment());
                mocData.setStartDate(mocStepEightRequest.getStartDate());
                mocData.setEndDate(mocStepEightRequest.getEndDate());

                mocData.setOperationalHead(mocStepEightRequest.getOperationalHead());
                mocData.setApproverStatus(0);

                mocStepEightRepository.save(mocData);
            }
            List<MOC> mocList = mocRepository.findByMocNumber(mocStepEightRequest.getMocNumber());
            if (!mocList.isEmpty()) {
                if (mocList.getFirst().getStatus() == 7) {
                    mocList.getFirst().setStatus(8);
                    mocList.getFirst().setCompletedDate(LocalDateTime.now());
                } else if (mocList.getFirst().getStatus() == 10) {
                    mocList.getFirst().setStatus(8);
                }
                mocRepository.save(mocList.getFirst());
            }
            return new ResponseModel<>(true, "Evaluation added Successfully", null);
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add approval", null);
        }
    }

    public ResponseModel<MOCStepEight> getMOCStepEight(String mocNumber) {
        ;
        try {
            var mocData = mocStepEightRepository.findByMocNumber(mocNumber);
            return new ResponseModel<>(true, "Record Found", mocData.getFirst());
        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);

        }
    }

    public ResponseModel<String> validateMOCStepEight(AddMOCStepEightRequest request) {
        try {
            List<MOCStepEight> mocStepEightList = mocStepEightRepository.findByMocNumber(request.getMocNumber());

            List<MOC> mocList = mocRepository.findByMocNumber(request.getMocNumber());

            if (!mocStepEightList.isEmpty()) {
                MOCStepEight mocStepEight = mocStepEightList.getFirst();

                mocStepEight.setOperationalHead(request.getOperationalHead());
                mocStepEight.setApproverRemark(request.getApproverRemark());

                if (request.getApproverStatus() == 1) {
                    mocStepEight.setApproverStatus(1);
                    mocStepEight.setApproverDateTime(LocalDateTime.now());
                } else if (request.getApproverStatus() == 2) {
                    mocStepEight.setApproverStatus(2);
                    mocStepEight.setApproverDateTime(LocalDateTime.now());
                    mocList.getFirst().setStatus(10);
                    mocList.getFirst().setRevertStages(9);
                    mocList.getFirst().setRevertBackDate(LocalDateTime.now());
                }
                mocRepository.save(mocList.getFirst());

                mocStepEightRepository.save(mocStepEight);
                return new ResponseModel<>(true, "Updated Successfully", null);
            } else {
                return new ResponseModel<>(false, "MOC Step Eight not found", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to update", null);
        }
    }

    public ResponseModel<String> addStepNine(AddMOCStepNineRequest mocStepNineRequest) {
        try {
            List<MOCStepNine> stepNineList = mocStepNineRepository.findByMocNumber(mocStepNineRequest.getMocNumber());

            if (stepNineList.size() != 0) {
                stepNineList.getFirst().setPlantHeadId(mocStepNineRequest.getPlantHeadId());
                stepNineList.getFirst().setPlantHead(mocStepNineRequest.getPlantHead());
                stepNineList.getFirst().setUnitHeadId(mocStepNineRequest.getUnitHeadId());
                stepNineList.getFirst().setUnitHead(mocStepNineRequest.getUnitHead());
                stepNineList.getFirst().setMocNumber(mocStepNineRequest.getMocNumber());
                stepNineList.getFirst().setMocUnitCloserStatus(mocStepNineRequest.getMocUnitCloserStatus());
                stepNineList.getFirst().setMocPlantCloserStatus(mocStepNineRequest.getMocPlantCloserStatus());

                mocStepNineRepository.save(stepNineList.getFirst());

            } else {
                var mocData = new MOCStepNine();
                mocData.setPlantHeadId(mocStepNineRequest.getPlantHeadId());
                mocData.setPlantHead(mocStepNineRequest.getPlantHead());
                mocData.setUnitHeadId(mocStepNineRequest.getUnitHeadId());
                mocData.setUnitHead(mocStepNineRequest.getUnitHead());
                mocData.setMocNumber(mocStepNineRequest.getMocNumber());
                mocData.setMocUnitCloserStatus(0);
                mocData.setMocPlantCloserStatus(0);

                mocStepNineRepository.save(mocData);
            }
            List<MOC> mocList = mocRepository.findByMocNumber(mocStepNineRequest.getMocNumber());
            if (!mocList.isEmpty()) {
                if (mocList.getFirst().getStatus() == 8) {
                    mocList.getFirst().setStatus(9);
                    mocList.getFirst().setClosedDate(LocalDateTime.now());
                } else if (mocList.getFirst().getStatus() == 10) {
                    mocList.getFirst().setStatus(9);
                    mocList.getFirst().setCompletedDate(LocalDateTime.now());
                    mocList.getFirst().setRevertBackDate(null);
                }
                mocRepository.save(mocList.getFirst());
            }

            return new ResponseModel<>(true, "Document Closer added Successfully", null);
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add approval", null);
        }
    }

    public ResponseModel<MOCStepNine> getMOCStepNine(String mocNumber) {
        ;
        try {
            var mocData = mocStepNineRepository.findByMocNumber(mocNumber);
            return new ResponseModel<>(true, "Record Found", mocData.getFirst());
        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);

        }
    }

    public ResponseModel<String> validateMOCStepNine(AddMOCStepNineRequest request) {
        try {
            List<MOCStepNine> mocStepNineList = mocStepNineRepository.findByMocNumber(request.getMocNumber());

            List<MOC> mocList = mocRepository.findByMocNumber(request.getMocNumber());
            if (!mocStepNineList.isEmpty()) {
                MOCStepNine mocStepNine = mocStepNineList.getFirst();

                mocStepNine.setPlantHead(request.getPlantHead());
                mocStepNine.setPlantHeadApproverComments(request.getPlantHeadApproverComments());

                mocStepNine.setUnitHead(request.getUnitHead());
                mocStepNine.setUnitHeadApproverComments(request.getUnitHeadApproverComments());

                if (request.getMocPlantCloserStatus() == 1) {
                    mocStepNine.setMocPlantCloserStatus(1);
                    mocStepNine.setPlantHeadUpdateDateTime(LocalDateTime.now());
                } else if (request.getMocPlantCloserStatus() == 2) {
                    mocStepNine.setMocPlantCloserStatus(2);
                    mocStepNine.setPlantHeadUpdateDateTime(LocalDateTime.now());
                    mocList.getFirst().setStatus(10);
                    mocList.getFirst().setRevertStages(9);
                    mocList.getFirst().setRevertBackDate(LocalDateTime.now());
                }

                if (request.getMocUnitCloserStatus() == 1) {
                    mocStepNine.setMocUnitCloserStatus(1);
                    mocStepNine.setUnitHeadUpdateDateTime(LocalDateTime.now());
                    mocList.getFirst().setStatus(11);
                } else if (request.getMocUnitCloserStatus() == 2) {
                    mocStepNine.setMocUnitCloserStatus(2);
                    mocStepNine.setUnitHeadUpdateDateTime(LocalDateTime.now());
                    mocList.getFirst().setStatus(10);
                    mocList.getFirst().setRevertStages(9);
                    mocList.getFirst().setRevertBackDate(LocalDateTime.now());
                }

                mocRepository.save(mocList.getFirst());

                mocStepNineRepository.save(mocStepNine);
                return new ResponseModel<>(true, "Updated Successfully", null);
            } else {
                return new ResponseModel<>(false, "MOC Step Nine not found", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to update", null);
        }
    }

    public ResponseModel<List<ApproverResponse>> getAllApproverAndStatus(String mocNumber) {
        try {
            List<ApproverResponse> approverResponses = new ArrayList<>();

            // Fetch data for MOCStepSixAL
            List<MOCStepSixAL> mocStepOnes = mocStepSixALRepository.findByMocNumber(mocNumber);
            for (MOCStepSixAL mocStepSixAL : mocStepOnes) {
                ApproverResponse approverResponse = new ApproverResponse();
                approverResponse.setApproverName(mocStepSixAL.getApproverName());
                approverResponse.setApprovalStatus(mocStepSixAL.getApprovalStatus());
                approverResponse.setApproverUpdateDateTime(mocStepSixAL.getApproverUpdateDateTime());
                approverResponses.add(approverResponse);
            }

            // Fetch data for MOCStepSeven
            List<MOCStepSeven> mocStepSevenList = mocStepSevenRepository.findByMocNumber(mocNumber);
            for (MOCStepSeven mocStepSeven : mocStepSevenList) {
                ApproverResponse plantHeadApprover = new ApproverResponse();
                plantHeadApprover.setApproverName(mocStepSeven.getPlantHead());
                plantHeadApprover.setApprovalStatus(mocStepSeven.getPlantHeadApproverStatus());
                plantHeadApprover.setApproverUpdateDateTime(mocStepSeven.getPlantHeadUpdatedDateTime());
                approverResponses.add(plantHeadApprover);

                ApproverResponse unitHeadApprover = new ApproverResponse();
                unitHeadApprover.setApproverName(mocStepSeven.getUnitHead());
                unitHeadApprover.setApprovalStatus(mocStepSeven.getUnitHeadApproverStatus());
                unitHeadApprover.setApproverUpdateDateTime(mocStepSeven.getUnitHeadUpdatedDateTime());
                approverResponses.add(unitHeadApprover);
            }

            List<MOCStepEight> mocStepEightList = mocStepEightRepository.findByMocNumber(mocNumber);
            for (MOCStepEight stepEight : mocStepEightList) {
                ApproverResponse operationalHead = new ApproverResponse();
                operationalHead.setApproverName(stepEight.getOperationalHead());
                operationalHead.setApprovalStatus(stepEight.getApproverStatus());
                operationalHead.setApproverUpdateDateTime(stepEight.getApproverDateTime());
                approverResponses.add(operationalHead);
            }

            List<MOCStepNine> mocStepNineList = mocStepNineRepository.findByMocNumber(mocNumber);
            for (MOCStepNine mocStepNine : mocStepNineList) {

                ApproverResponse plantHeadApprover = new ApproverResponse();
                plantHeadApprover.setApproverName(mocStepNine.getPlantHead());
                plantHeadApprover.setApprovalStatus(mocStepNine.getMocPlantCloserStatus());
                plantHeadApprover.setApproverUpdateDateTime(mocStepNine.getPlantHeadUpdateDateTime());
                approverResponses.add(plantHeadApprover);

                ApproverResponse unitHeadApprover = new ApproverResponse();
                unitHeadApprover.setApproverName(mocStepNine.getUnitHead());
                unitHeadApprover.setApprovalStatus(mocStepNine.getMocUnitCloserStatus());
                unitHeadApprover.setApproverUpdateDateTime(mocStepNine.getUnitHeadUpdateDateTime());
                approverResponses.add(unitHeadApprover);
            }

            return new ResponseModel<>(true, "Listed Approver Name and Status", approverResponses);
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to update", null);
        }
    }

//    public ResponseModel<List<MOC>> reportLists(String department, String area, FilterType filterType, String organizationCode, String requestPage, LocalDate startDate, LocalDate endDate) {
//        try {
//
//            Page<MOC> results;
//            var pageRequestCount = 0;
//
//            if(requestPage.matches(".*\\d.*")){
//                pageRequestCount = Integer.parseInt(requestPage);
//            }else{
//                pageRequestCount = 0;
//            }
//
//            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
//
//            switch (filterType) {
//                case DAILY:
//                    results = organizationCode != null ?
//                            mocRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
//                            (department != null ?
//                                    mocRepository.findByDepartmentAndCreatedOnBetween(department, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
//                                    (area != null ?
//                                            mocRepository.findByAreaAndCreatedOnBetween(area, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
//                                            mocRepository.findByCreatedOnBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest)));
//                    break;
//                case WEEKLY:
//                    LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//                    LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
//                    results = organizationCode != null ?
//                            mocRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
//                            (department != null ?
//                                    mocRepository.findByDepartmentAndCreatedOnBetween(department, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
//                                    (area != null ?
//                                            mocRepository.findByAreaAndCreatedOnBetween(area, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
//                                            mocRepository.findByCreatedOnBetween(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest)));
//                    break;
//                case MONTHLY:
//                    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
//                    LocalDate endOfMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
//                    results = organizationCode != null ?
//                            mocRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
//                            (department != null ?
//                                    mocRepository.findByDepartmentAndCreatedOnBetween(department, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
//                                    (area != null ?
//                                            mocRepository.findByAreaAndCreatedOnBetween(area, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
//                                            mocRepository.findByCreatedOnBetween(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest)));
//                    break;
//                case QUARTERLY:
//                    LocalDate startOfQuarter = LocalDate.now().withMonth(((LocalDate.now().getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
//                    LocalDate endOfQuarter = startOfQuarter.plusMonths(3).minusDays(1);
//
//
//                    results = organizationCode != null ?
//                            mocRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
//                            (department != null ?
//                                    mocRepository.findByDepartmentAndCreatedOnBetween(department, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59), pageRequest) :
//                                    (area != null ?
//                                            mocRepository.findByAreaAndCreatedOnBetween(area, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
//                                            mocRepository.findByCreatedOnBetween(startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest)));
//                    break;
//                case YEARLY:
//                    LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
//                    LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
//                    results = organizationCode != null ?
//                            mocRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
//                            (department != null ?
//                                    mocRepository.findByDepartmentAndCreatedOnBetween(department, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
//                                    (area != null ?
//                                            mocRepository.findByAreaAndCreatedOnBetween(area, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
//                                            mocRepository.findByCreatedOnBetween(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest)));
//                    break;
//                case CUSTOM:
//                    results = organizationCode != null ?
//                            mocRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
//                            (department != null ?
//                                    mocRepository.findByDepartmentAndCreatedOnBetween(department, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
//                                    (area != null ?
//                                            mocRepository.findByAreaAndCreatedOnBetween(area, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
//                                            mocRepository.findByCreatedOnBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest)));
//                    break;
//
//                default:
//
//                    return new ResponseModel<>(false, "Invalid filter type", null);
//            }
//
//            List<MOC> mocList = mocRepository.findAll();
//            var totalCount = String.valueOf(mocList.size());
//            var filteredCount = String.valueOf(results.getContent().size());
//            if (results.isEmpty()) {
//                return new ResponseModel<>(false, "No records found", null);
//            } else {
//                return new ResponseModel<>(true, totalCount+" Records found & "+filteredCount+ " Filtered", results.getContent());
//            }
//
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Records not found1", null);
//        }
//    }

//    public ResponseModel<List<MOC>> reportLists(String organizationCode, String department, String area, FilterType filterType, String requestPage, LocalDate startDate, LocalDate endDate) {
//        try {
//            Page<MOC> results;
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
//
//            LocalDateTime startDateTime;
//            LocalDateTime endDateTime;
//
//            switch (filterType) {
//                case DAILY:
//                    startDateTime = LocalDate.now().atStartOfDay();
//                    endDateTime = LocalDate.now().atTime(23, 59, 59);
//                    break;
//                case WEEKLY:
//                    LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//                    LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
//                    startDateTime = startOfWeek.atStartOfDay();
//                    endDateTime = endOfWeek.atTime(23, 59, 59);
//                    break;
//                case MONTHLY:
//                    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
//                    LocalDate endOfMonth = startOfMonth.plusMonths(1).withDayOfMonth(1).minusDays(1);
//                    startDateTime = startOfMonth.atStartOfDay();
//                    endDateTime = endOfMonth.atTime(23, 59, 59);
//                    break;
//                case QUARTERLY:
//                    LocalDate startOfQuarter = LocalDate.now().withMonth(((LocalDate.now().getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
//                    LocalDate endOfQuarter = startOfQuarter.plusMonths(3).minusDays(1);
//                    startDateTime = startOfQuarter.atStartOfDay();
//                    endDateTime = endOfQuarter.atTime(23, 59, 59);
//                    break;
//                case YEARLY:
//                    LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
//                    LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
//                    startDateTime = startOfYear.atStartOfDay();
//                    endDateTime = endOfYear.atTime(23, 59, 59);
//                    break;
//                case CUSTOM:
//                    startDateTime = startDate.atStartOfDay();
//                    endDateTime = endDate.atTime(23, 59, 59);
//                    break;
//                default:
//                    return new ResponseModel<>(false, "Invalid filter type", null);
//            }
//
//            if (organizationCode != null && department != null && area != null) {
//                // Query with all three filters
//                results = mocRepository.findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(organizationCode, department, area, startDateTime, endDateTime, pageRequest);
//            } else if (organizationCode != null && department != null) {
//                // Query with organizationCode and department filters
//                results = mocRepository.findByOrganizationCodeAndDepartmentAndCreatedOnBetween(organizationCode, department, startDateTime, endDateTime, pageRequest);
//            } else if (organizationCode != null && area != null) {
//                // Query with organizationCode and area filters
//                results = mocRepository.findByOrganizationCodeAndAreaAndCreatedOnBetween(organizationCode, area, startDateTime, endDateTime, pageRequest);
//            } else if (department != null && area != null) {
//                // Query with department and area filters
//                results = mocRepository.findByDepartmentAndAreaAndCreatedOnBetween(department, area, startDateTime, endDateTime, pageRequest);
//            } else if (organizationCode != null) {
//                // Query with organizationCode filter
//                results = mocRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDateTime, endDateTime, pageRequest);
//            } else if (department != null) {
//                // Query with department filter
//                results = mocRepository.findByDepartmentAndCreatedOnBetween(department, startDateTime, endDateTime, pageRequest);
//            } else if (area != null) {
//                // Query with area filter
//                results = mocRepository.findByAreaAndCreatedOnBetween(area, startDateTime, endDateTime, pageRequest);
//            } else {
//                // Default query without any filters
//                results = mocRepository.findByCreatedOnBetween(startDateTime, endDateTime, pageRequest);
//            }
//
//
//            List<MOC> mocList = mocRepository.findAll();
//            var totalCount = String.valueOf(mocList.size());
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

    public ResponseModel<List<MOC>> findPending(String username, String organizationCode) {
        UserInfo userInfo = userInfoRepository.findByUserNameAndOrganizationCode(username, organizationCode);
        boolean isOperator = "Operator".equals(userInfo.getRole());

        if (isOperator) {
            List<MOC> pendingMOCs = mocRepository.findPending(username, organizationCode);
            return new ResponseModel<>(true, "Operator Pending List", pendingMOCs);
        } else {
            List<MOCStepSixAL> stepSixALList = mocStepSixALRepository.findByApproverName(username);
            List<MOCStepSeven> stepSevenList = mocStepSevenRepository.findByApproverName(username);
            List<MOCStepEight> stepEightList = mocStepEightRepository.findByApproverName(username);
            List<MOCStepNine> stepNineList = mocStepNineRepository.findByApproverName(username);

            List<String> mocNumbers = new ArrayList<>();

            for (MOCStepSixAL stepSixAL : stepSixALList) {
                mocNumbers.add(stepSixAL.getMocNumber());
            }
            for (MOCStepSeven stepSeven : stepSevenList) {
                mocNumbers.add(stepSeven.getMocNumber());
            }
            for (MOCStepEight stepEight : stepEightList) {
                mocNumbers.add(stepEight.getMocNumber());
            }
            for (MOCStepNine stepNine : stepNineList) {
                mocNumbers.add(stepNine.getMocNumber());
            }

            List<MOC> result = mocRepository.findByMocNumberInStatusBetweenAndOrganizationCode(mocNumbers, organizationCode);

            System.out.println("mocNumbers: " + mocNumbers);
            System.out.println("organizationCode: " + organizationCode);
            System.out.println("Result size: " + result.size());

            return new ResponseModel<>(true, "User Pending List", result);
        }
    }


    //new report list
    public ResponseModel<List<MOCCombainedRequest>> reportLists(
            String organizationCode, String department, String area, FilterType filterType,
            String requestPage, LocalDate startDate, LocalDate endDate) {
        try {
            Page<MOC> results;
            int pageRequestCount;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
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
                results = mocRepository.findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(organizationCode, department, area, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null && department != null) {
                results = mocRepository.findByOrganizationCodeAndDepartmentAndCreatedOnBetween(organizationCode, department, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null && area != null) {
                results = mocRepository.findByOrganizationCodeAndAreaAndCreatedOnBetween(organizationCode, area, startDateTime, endDateTime, pageRequest);
            } else if (department != null && area != null) {
                results = mocRepository.findByDepartmentAndAreaAndCreatedOnBetween(department, area, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null) {
                results = mocRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDateTime, endDateTime, pageRequest);
            } else if (department != null) {
                results = mocRepository.findByDepartmentAndCreatedOnBetween(department, startDateTime, endDateTime, pageRequest);
            } else if (area != null) {
                results = mocRepository.findByAreaAndCreatedOnBetween(area, startDateTime, endDateTime, pageRequest);
            } else {
                results = mocRepository.findByCreatedOnBetween(startDateTime, endDateTime, pageRequest);
            }

            List<MOCCombainedRequest> combinedResults = new ArrayList<>();

            for (MOC moc : results.getContent()) {
                String mocNumber = moc.getMocNumber();

                MOCStepOne stepOne = mocStepOneRepository.findAllByMocNumber(mocNumber);
                MOCStepFour stepFour = mocStepFourRepository.findAllByMocNumber(mocNumber);
                MOC stepMoc = mocRepository.findAllByMocNumber(mocNumber);

                MOCCombainedRequest mocDto = new MOCCombainedRequest();

                mocDto.setOrganizationCode(moc.getOrganizationCode());
                mocDto.setDepartment(moc.getDepartment());
                mocDto.setArea(moc.getArea());
                mocDto.setMocNumber(moc.getMocNumber());
                mocDto.setInitiatorName(moc.getInitiatorName());
                mocDto.setCreatedOn(moc.getCreatedOn());
                mocDto.setAssetNumber(moc.getAssetNumber());
                mocDto.setStatus(moc.getStatus());
                mocDto.setCompletedDate(moc.getCompletedDate());
                mocDto.setClosedDate(moc.getClosedDate());

//                mocDto.setProjectSponsorship(stepOne != null ? stepOne.getProjectSponsorship() : null);
                mocDto.setProjectLeader(stepOne != null ? stepOne.getProjectLeader() : null);
//                mocDto.setSubject(stepOne != null ? stepOne.getSubject() : null);
//                mocDto.setObjectiveOfChange(stepOne != null ? stepOne.getObjectiveOfChange() : null);
//                mocDto.setExpectedBenefits(stepOne != null ? stepOne.getExpectedBenefits() : null);

//                mocDto.setProcessItem(stepFour != null ? stepFour.getProcessItem() : null);
//                mocDto.setProcessRoute(stepFour != null ? stepFour.getProcessRoute() : null);
//                mocDto.setCheckSheet(stepFour != null ? stepFour.getCheckSheet() : null);
//                mocDto.setFailureMode(stepFour != null ? stepFour.getFailureMode() : null);
//                mocDto.setSop(stepFour != null ? stepFour.getSop() : null);
//                mocDto.setProcessMapping(stepFour != null ? stepFour.getProcessMapping() : null);
//                mocDto.setControlPlan(stepFour != null ? stepFour.getControlPlan() : null);
//                mocDto.setOthers(stepFour != null ? stepFour.getOthers() : null);

                combinedResults.add(mocDto);
            }

            String totalCount = String.valueOf(mocRepository.count());
            String filteredCount = String.valueOf(combinedResults.size());
            if (combinedResults.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", combinedResults);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }

//    public ResponseModel<String> setAllApprovers(String mocNumber) {
//        try {
//
//            List<MOC> mocList = mocRepository.findByMocNumber(mocNumber);
//            List<MOCStepSixAL> mocStepSixs = mocStepSixALRepository.findByMocNumber(mocNumber);
//           // List<MOCStepEight> stepEightList = mocStepEightRepository.findByMocNumber(mocNumber);
//
//             if (mocList.getFirst().getStatus() == 10) {
//                for (MOCStepSixAL mocStepSixAL : mocStepSixs) {
//                    mocStepSixAL.setRemarks(null);
//                    mocStepSixAL.setApprovalStatus(0);
//                }
//                mocStepSixALRepository.saveAll(mocStepSixs);
//
//                List<MOCStepSeven> mocStepSevenList = mocStepSevenRepository.findByMocNumber(mocNumber);
//                for (MOCStepSeven stepSeven : mocStepSevenList){
//                    stepSeven.setPlantHeadApproverComments(null);
//                    stepSeven.setPlantHeadApproverStatus(0);
//
//                    stepSeven.setUnitHeadApproverComments(null);
//                    stepSeven.setUnitHeadApproverStatus(0);
//
//                }
//                mocStepSevenRepository.saveAll(mocStepSevenList);
//
//                List<MOCStepEight> stepEightList = mocStepEightRepository.findByMocNumber(mocNumber);
//                 if (stepEightList.isEmpty()) {
//                     mocList.getFirst().setStatus(7);
//                 }
//                 mocRepository.save(mocList.getFirst());
//
//                for (MOCStepEight stepEight : stepEightList){
//                    stepEight.setApproverStatus(0);
//                    stepEight.setApproverRemark(null);
//                }
//                mocStepEightRepository.saveAll(stepEightList);
//                List<MOCStepNine> stepNineList = mocStepNineRepository.findByMocNumber(mocNumber);
//                for (MOCStepNine stepNine : stepNineList){
//                    stepNine.setMocPlantCloserStatus(0);
//                    stepNine.setPlantHeadApproverComments(null);
//
//                    stepNine.setMocUnitCloserStatus(0);
//                    stepNine.setUnitHeadApproverComments(null);
//                    mocList.getFirst().setStatus(9);
//
//                }
//                mocStepNineRepository.saveAll(stepNineList);
//
//                mocRepository.save(mocList.getFirst());
//                return new ResponseModel<>(true, "Approvers Status Changed", null);
//            }
//            else {
//                return new ResponseModel<>(false, "No approvers with status 2 found", null);
//            }
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "An error occurred while updating approvers", null);
//        }
//    }

    public ResponseModel<String> setAllApprovers(String mocNumber) {
        try {
            List<MOC> mocList = mocRepository.findByMocNumber(mocNumber);
            List<MOCStepNine> stepNineList = mocStepNineRepository.findByMocNumber(mocNumber);
            if (stepNineList.isEmpty()) {
                if (mocList.getFirst().getStatus() == 10) {
                    mocList.getFirst().setStatus(7);
                    mocList.getFirst().setRevertStages(0);

                    List<MOCStepSixAL> mocStepSixs = mocStepSixALRepository.findByMocNumber(mocNumber);
                    for (MOCStepSixAL mocStepSixAL : mocStepSixs) {
                        mocStepSixAL.setRemarks(null);
                        mocStepSixAL.setApprovalStatus(0);
                    }
                    mocStepSixALRepository.saveAll(mocStepSixs);

                    List<MOCStepSeven> mocStepSevenList = mocStepSevenRepository.findByMocNumber(mocNumber);
                    mocStepSevenList.getFirst().setPlantHeadApproverStatus(0);
                    mocStepSevenList.getFirst().setPlantHeadApproverComments(null);

                    mocStepSevenList.getFirst().setUnitHeadApproverStatus(0);
                    mocStepSevenList.getFirst().setUnitHeadApproverComments(null);
                    mocStepSevenRepository.saveAll(mocStepSevenList);
                    mocRepository.save(mocList.getFirst());

                }
                return new ResponseModel<>(true, "Successfully resubmitted", null);
            } else {

                mocList.getFirst().setStatus(9);
                mocList.getFirst().setRevertStages(0);

                List<MOCStepSixAL> mocStepSixs = mocStepSixALRepository.findByMocNumber(mocNumber);
                for (MOCStepSixAL mocStepSixAL : mocStepSixs) {
                    mocStepSixAL.setRemarks(null);
                    mocStepSixAL.setApprovalStatus(0);
                }
                mocStepSixALRepository.saveAll(mocStepSixs);

                List<MOCStepSeven> mocStepSevenList = mocStepSevenRepository.findByMocNumber(mocNumber);
                mocStepSevenList.getFirst().setPlantHeadApproverStatus(0);
                mocStepSevenList.getFirst().setPlantHeadApproverComments(null);

                mocStepSevenList.getFirst().setUnitHeadApproverStatus(0);
                mocStepSevenList.getFirst().setUnitHeadApproverComments(null);
                mocStepSevenRepository.saveAll(mocStepSevenList);

                List<MOCStepEight> mocStepEightList = mocStepEightRepository.findByMocNumber(mocNumber);
                mocStepEightList.getFirst().setApproverStatus(0);
                mocStepEightList.getFirst().setApproverRemark(null);
                mocStepEightRepository.saveAll(mocStepEightList);

                List<MOCStepNine> nineList = mocStepNineRepository.findByMocNumber(mocNumber);
                nineList.getFirst().setMocPlantCloserStatus(0);
                nineList.getFirst().setPlantHeadApproverComments(null);

                nineList.getFirst().setMocUnitCloserStatus(0);
                nineList.getFirst().setUnitHeadApproverComments(null);
                mocStepNineRepository.saveAll(nineList);
                mocRepository.save(mocList.getFirst());
                return new ResponseModel<>(true, "Successfully resubmitted", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found for given MOC Number", null);
        }
    }

}
