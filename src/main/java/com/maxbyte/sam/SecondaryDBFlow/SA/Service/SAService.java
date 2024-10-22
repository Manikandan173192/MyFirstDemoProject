package com.maxbyte.sam.SecondaryDBFlow.SA.Service;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterType;
import com.maxbyte.sam.SecondaryDBFlow.Response.ImageResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest.CheckListExecutionRequest;
import com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest.AddSARequest;
import com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest.UpdateCheckListRequest;
import com.maxbyte.sam.SecondaryDBFlow.SA.APIRequest.SAValidateRequest;
import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.SA.Repository.*;
import com.maxbyte.sam.SecondaryDBFlow.SA.Specification.SASpecificationBuilder;
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
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
@EnableScheduling
public class SAService extends CrudService<SA, Integer> {

    @Autowired
     SARepository saRepository;
    @Autowired
    Environment environment;
    @Autowired
    SACheckListRepository saCheckListRepository;
    @Autowired
    SAUpdateCheckListRepository saUpdateCheckListRepository;
    @Autowired
    SACheckListExecutionRepository saCheckListExecutionRepository;
    @Autowired
    SAImageUploadRepository  imageUploadRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;


    @Override
    public CrudRepository repository() {
        return this.saRepository;
    }

    @Override
    public void validateAdd(SA data) {

        try {
        } catch (Error e) {
            throw new Error(e);
        }
    }

    @Override
    public void validateEdit(SA data) {

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


    public ResponseModel<List<SA>> list(Boolean isActive, String organizationCode, String assetNumber,
                                        String assetDescription, String department, String requestPage) {

        try {
            SASpecificationBuilder builder = new SASpecificationBuilder();
            if (isActive != null) builder.with("isActive", ":", isActive);
            if (assetNumber != null) builder.with("assetNumber", "==", assetNumber);
            if (assetDescription != null) builder.with("assetDescription", "==", assetDescription);
            if (department != null) builder.with("department", "==", department);
            if (organizationCode != null) builder.with("organizationCode", "==", organizationCode);
            var pageRequestCount = 0;

            if(requestPage.matches(".*\\d.*")){
                pageRequestCount = Integer.parseInt(requestPage);
            }else{
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<SA> results = saRepository.findAll(builder.build(), pageRequest);
            List<SA> rcaList = saRepository.findAll();
            var totalCount = String.valueOf(rcaList.size());
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


    public ResponseModel<String> addSa(AddSARequest addSARequest) {
        try {

            SA sa = new SA();
            var SaRep = saRepository.findByAssetNumberAndCheckListType(addSARequest.getAssetNumber(), addSARequest.getCheckListType());
            if (!SaRep.isEmpty()) {
                return new ResponseModel<>(false, "CheckListType Already Exists For This Asset", null);
            }
            sa.setOrganizationCode(addSARequest.getOrganizationCode());
            sa.setAssetGroupId(addSARequest.getAssetGroupId());
            sa.setAssetGroup(addSARequest.getAssetGroup());
            sa.setAssetId(addSARequest.getAssetId());
            sa.setAssetNumber(addSARequest.getAssetNumber());
            sa.setAssetDescription(addSARequest.getAssetDescription());
            sa.setDepartmentId(addSARequest.getDepartmentId());
            sa.setDepartment(addSARequest.getDepartment());
            sa.setArea(addSARequest.getArea());
            sa.setSubject(addSARequest.getSubject());
            sa.setComponent(addSARequest.getComponent());
            sa.setCheckListType(addSARequest.getCheckListType());
            sa.setVersion(addSARequest.getVersion());
            sa.setApproverId(addSARequest.getApproverId());
            sa.setApproverName(addSARequest.getApproverName());
            sa.setCategory(addSARequest.getCategory());
            sa.setWoNumber(addSARequest.getWoNumber());
            sa.setInitiatorName(addSARequest.getInitiatorName());
            sa.setCreatedOn(LocalDateTime.now());
            sa.setIsActive(true);
            sa.setTStatus(0);

            saRepository.save(sa);
            return new ResponseModel<>(true, "SA added successfully", sa.getSaId().toString());


        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }


    public ResponseModel<List<SAUpdateCheckList>> getChecklistsByAssetNumberAndCheckTypes(String assetNumber, String checkListType) {
        try {


            List<SAUpdateCheckList> data = saUpdateCheckListRepository.findByAssetNumberAndCheckListType(assetNumber, checkListType);
            return new ResponseModel<>(true, "Records found", data.reversed());

        } catch (Exception e) {

            return new ResponseModel<>(false, "Records not found", null);
        }
    }


public ResponseModel<String> updateSaCheckList(UpdateCheckListRequest addSARequest) {
    try {
        var saData = saRepository.findByAssetNumber(addSARequest.getAssetNumber());
        var saUpdateCheckListData = saUpdateCheckListRepository.findByAssetNumberAndCheckListType(addSARequest.getAssetNumber(), addSARequest.getChecklistType());
        if (!saData.isEmpty()) {
            SA sa = saData.getLast();
//            SAUpdateCheckList saUpdateCheckList = new SAUpdateCheckList();

            sa.setSubject(addSARequest.getSubject());
            sa.setComponent(addSARequest.getComponent());
            sa.setApproverName(addSARequest.getApproverName());
            sa.setVersion(addSARequest.getVersion());
            saRepository.save(sa);
            if (!saUpdateCheckListData.isEmpty()) {
                SAUpdateCheckList saUpdateCheckList = saUpdateCheckListData.getLast();

                saUpdateCheckList.setAssetGroupId(addSARequest.getAssetGroupId());
                saUpdateCheckList.setAssetGroup(addSARequest.getAssetGroup());
                saUpdateCheckList.setAssetId(addSARequest.getAssetId());
                saUpdateCheckList.setAssetNumber(addSARequest.getAssetNumber());
                saUpdateCheckList.setArea(addSARequest.getArea());
                saUpdateCheckList.setDepartmentId(addSARequest.getDepartmentId());
                saUpdateCheckList.setDepartment(addSARequest.getDepartment());
                saUpdateCheckList.setSubject(addSARequest.getSubject());
                saUpdateCheckList.setComponent(addSARequest.getComponent());
                saUpdateCheckList.setCheckListType(addSARequest.getChecklistType());
                saUpdateCheckList.setVersion(addSARequest.getVersion());
                saUpdateCheckList.setApproverId(addSARequest.getApproverId());
                saUpdateCheckList.setApproverName(addSARequest.getApproverName());
                saUpdateCheckList.setSauCreatedOn(LocalDateTime.now());

                saUpdateCheckListRepository.save(saUpdateCheckList);
            }else {
                SAUpdateCheckList saUpdateCheckList = new SAUpdateCheckList();

                saUpdateCheckList.setAssetGroupId(addSARequest.getAssetGroupId());
                saUpdateCheckList.setAssetGroup(addSARequest.getAssetGroup());
                saUpdateCheckList.setAssetId(addSARequest.getAssetId());
                saUpdateCheckList.setAssetNumber(addSARequest.getAssetNumber());
                saUpdateCheckList.setArea(addSARequest.getArea());
                saUpdateCheckList.setDepartmentId(addSARequest.getDepartmentId());
                saUpdateCheckList.setDepartment(addSARequest.getDepartment());
                saUpdateCheckList.setSubject(addSARequest.getSubject());
                saUpdateCheckList.setComponent(addSARequest.getComponent());
                saUpdateCheckList.setCheckListType(addSARequest.getChecklistType());
                saUpdateCheckList.setVersion(addSARequest.getVersion());
                saUpdateCheckList.setApproverId(addSARequest.getApproverId());
                saUpdateCheckList.setApproverName(addSARequest.getApproverName());
                saUpdateCheckList.setSauCreatedOn(LocalDateTime.now());

                saUpdateCheckListRepository.save(saUpdateCheckList);

            }


            //Delete existing team members for the particular RCA no
            var addedTeamList = saCheckListRepository.findByAssetNumber(saData.getFirst().getAssetNumber());
            if(!addedTeamList.isEmpty()){
                for(SACheckList items: addedTeamList){
                    if(items.getAssetNumber().equals(saData.getFirst().getAssetNumber())){
                        saCheckListRepository.deleteByClId(items.getClId());
                    }
                }
            }
            // Add new checklists
            for (SACheckList saCheckListRequest : addSARequest.getSaCheckLists()) {
                SACheckList saCheckList = new SACheckList();
                saCheckList.setChecklist(saCheckListRequest.getChecklist());
                saCheckList.setType(saCheckListRequest.getType());
                saCheckList.setUcl(saCheckListRequest.getUcl());
                saCheckList.setLcl(saCheckListRequest.getLcl());
                saCheckList.setClStatus(saCheckListRequest.getClStatus());
                saCheckList.setUnits(saCheckListRequest.getUnits());
                saCheckList.setAssetId(saCheckListRequest.getAssetId());
                saCheckList.setAssetNumber(addSARequest.getAssetNumber());
                saCheckListRepository.save(saCheckList);
            }

            return new ResponseModel<>(true, "Schedule Audit updated successfully", sa.getSaId().toString());
        } else {
            return new ResponseModel<>(false, "Schedule Audit not found", null);
        }
    } catch (Exception e) {
        return new ResponseModel<>(false, "Failed to update", null);
    }
}

    public ResponseModel<List<SAUpdateCheckList>> getChecklistsByAssetNumberAndCheckType(String assetNumber, String checkListType) {
        try {


            List<SAUpdateCheckList>data = saUpdateCheckListRepository.findByAssetNumberAndCheckListTypeAndTStatus(assetNumber, checkListType,2);
            return new ResponseModel<>(true, "Records found", data.reversed());

        } catch (Exception e) {

            return new ResponseModel<>(false,"Records not found", null);
        }
    }

    public ResponseModel<String> validate(SAValidateRequest saValidateRequest) {
        try {
            List<SA> saData = saRepository.findByAssetNumberAndCheckListType(saValidateRequest.getAssetNumber(), saValidateRequest.getCheckListType());
            List<SAUpdateCheckList> sauData = saUpdateCheckListRepository.findByAssetNumberAndCheckListType(saValidateRequest.getAssetNumber(), saValidateRequest.getCheckListType());

            if (!saData.isEmpty() && !sauData.isEmpty()) {
                SA sa = saData.getLast();
                SAUpdateCheckList sau = sauData.getLast();
                sa.setApproverComment(saValidateRequest.getApproverComment());

                if (saValidateRequest.getApproverStatus() == 1) {
                    sa.setTStatus(2); // approved
                    sau.setTStatus(2);
                } else if (saValidateRequest.getApproverStatus() == 2) {
                    sa.setTStatus(1); // revert back
                    sau.setTStatus(1);

                }

                saRepository.save(sa);
                saUpdateCheckListRepository.save(sau);
                return new ResponseModel<>(true, "Success", saValidateRequest.getApproverComment());
            }
        } catch (Exception e) {
            // Handle any other exceptions
            e.printStackTrace();
            return new ResponseModel<>(false, "Failed", null);
        }
        return new ResponseModel<>(false, "Failed", null);

    }
    public ResponseModel<String> validateAndUpdateCheckListExecution(List<CheckListExecutionRequest> addMobileRequests) {
        try {
            CheckListExecutionRequest lastRequest = addMobileRequests.get(addMobileRequests.size() - 1);
            switch (lastRequest.getCheckListType().toLowerCase()) {
                case "daily":
                    // Check for duplicate entry for the same date
                    LocalDateTime localDateTime = lastRequest.getCheckListExecutionCreatedOn();
                    List<SACheckListExecution> dailyEntries = saCheckListExecutionRepository.findByCheckListTypeAndCheckListExecutionCreatedOn("Daily", localDateTime);
                    if (!dailyEntries.isEmpty()) {
                        return new ResponseModel<>(false, "Checklist for today already exists", null);
                    }
                    break;
                case "weekly":
                    // Calculate the start of the week and end of the week for the given date
                    LocalDateTime localDateTime1 = lastRequest.getCheckListExecutionCreatedOn();
                    LocalDateTime weekStartDate = localDateTime1.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    LocalDateTime weekEndDate = weekStartDate.plusDays(6);

                    // Check if an entry already exists for this week
                    List<SACheckListExecution> weeklyEntries = saCheckListExecutionRepository.findByCheckListTypeAndCheckListExecutionCreatedOnBetween("Weekly", weekStartDate, weekEndDate);
                    if (!weeklyEntries.isEmpty()) {
                        return new ResponseModel<>(false, "Checklist for this week already exists", null);
                    }
                    break;
                case "monthly":
                    // Check if the month matches the current month
                    LocalDateTime localDateTime2 = lastRequest.getCheckListExecutionCreatedOn();
                    LocalDateTime monthStartDate = localDateTime2.with(TemporalAdjusters.firstDayOfMonth());
                    LocalDateTime monthEndDate = localDateTime2.with(TemporalAdjusters.lastDayOfMonth());

                    // Check if an entry already exists for this month
                    List<SACheckListExecution> monthlyEntries = saCheckListExecutionRepository.findByCheckListTypeAndCheckListExecutionCreatedOnBetween("Monthly", monthStartDate, monthEndDate);
                    if (!monthlyEntries.isEmpty()) {
                        return new ResponseModel<>(false, "Checklist for this month already exists", null);
                    }
                    break;
                case "yearly":
                    // Check if the year matches the current year
                    LocalDateTime localDateTime3 = lastRequest.getCheckListExecutionCreatedOn();
                    LocalDateTime yearStartDate = localDateTime3.with(TemporalAdjusters.firstDayOfYear());
                    LocalDateTime yearEndDate = localDateTime3.with(TemporalAdjusters.lastDayOfYear());

                    // Check if an entry already exists for this year
                    List<SACheckListExecution> yearlyEntries = saCheckListExecutionRepository.findByCheckListTypeAndCheckListExecutionCreatedOnBetween("Yearly", yearStartDate, yearEndDate);
                    if (!yearlyEntries.isEmpty()) {
                        return new ResponseModel<>(false, "Checklist for this year already exists", null);
                    }
                    break;
            }

            for (CheckListExecutionRequest addMobileRequest : addMobileRequests) {
                SACheckListExecution newEntry = new SACheckListExecution();
                newEntry.setChecklist(addMobileRequest.getChecklist());
                newEntry.setType(addMobileRequest.getType());
                newEntry.setUcl(addMobileRequest.getUcl());
                newEntry.setLcl(addMobileRequest.getLcl());
                newEntry.setClStatus(addMobileRequest.getClStatus());
                newEntry.setUnits(addMobileRequest.getUnits());
                newEntry.setCheckListExecutionCreatedOn(addMobileRequest.getCheckListExecutionCreatedOn());
                newEntry.setAssetNumber(addMobileRequest.getAssetNumber());
                newEntry.setCheckListType(addMobileRequest.getCheckListType());
                newEntry.setImage(addMobileRequest.getImage());
                newEntry.setComments(addMobileRequest.getComments());
                saCheckListExecutionRepository.save(newEntry);
            }

            return new ResponseModel<>(true, "Checklists updated successfully", null);
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to update checklists", null);
        }
    }





//    public ResponseModel<ImageResponse> saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
//        try {
//            String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
//            Path uploadPath = Paths.get(uploadDirectory);
//            Path filePath = uploadPath.resolve(uniqueFileName);
//
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//            var imagePath = (uploadDirectory+"/"+uniqueFileName).replace("target/classes/static","");
//
//            return new ResponseModel<>(true, "Image Updated Successfully", imagePath);
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle or log the exception as needed
//            return new ResponseModel<>(false, "Failed to update image", null);
//        }
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
        imagePath.setImagePath((uploadDirectory+"/"+uniqueFileName).replace("target/classes/static",""));
        return new ResponseModel<>(true, "Image Updated Successfully",imagePath);
    }


    @Scheduled(cron = "0 0 0 * * *")  // first 0 Seconds, 0 minute, 0 hours, 0 days, 0 months, 0 week days
    public ResponseModel<ImageResponse> deleteAllImagesInFolder() {
        List<SACheckListExecution> imageUploads = saCheckListExecutionRepository.findAll();
        List<String> images = new ArrayList<>();
        for (SACheckListExecution upload : imageUploads) {
            images.add(upload.getImage());

        }


        String folderPath = environment.getProperty("image.uploadDirectory") + "/SA";
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return new ResponseModel<>(false, "Folder not found or is not a directory");
        }

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


    public ResponseModel<List<SACheckListExecution>> getChecklistsExecutionByAssetNumberAndCheckTypes(String assetNumber, String checkListType) {
        try {


            List<SACheckListExecution> data = saCheckListExecutionRepository.findByAssetNumberAndCheckListType(assetNumber, checkListType);
            return new ResponseModel<>(true, "Records found", data.reversed());

        } catch (Exception e) {

            return new ResponseModel<>(false, "Records not found", null);
        }
    }

    public ResponseModel<List<SA>> findSaByDateTime(String organizationCode, LocalDateTime from, LocalDateTime to, String requestPage) {
        try {
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            var results = saRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, from, to, pageRequest);
            List<SA> saList = saRepository.findAll();
            var totalCount = String.valueOf(saList.size());
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

    public ResponseModel<List<SA>> reportLists(String department, String area, FilterType filterType, String organizationCode, String requestPage, LocalDate startDate, LocalDate endDate) {
        try {

            Page<SA> results;
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
                            saRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    saRepository.findByDepartmentAndCreatedOnBetween(department, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            saRepository.findByAreaAndCreatedOnBetween(area, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                                            saRepository.findByCreatedOnBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest)));
                    break;
                case WEEKLY:
                    LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                    results = organizationCode != null ?
                            saRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    saRepository.findByDepartmentAndCreatedOnBetween(department, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            saRepository.findByAreaAndCreatedOnBetween(area, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                                            saRepository.findByCreatedOnBetween(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest)));
                    break;
                case MONTHLY:
                    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
                    LocalDate endOfMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
                    results = organizationCode != null ?
                            saRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    saRepository.findByDepartmentAndCreatedOnBetween(department, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            saRepository.findByAreaAndCreatedOnBetween(area, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                                            saRepository.findByCreatedOnBetween(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest)));
                    break;
                case QUARTERLY:
                    LocalDate startOfQuarter = LocalDate.now().withMonth(((LocalDate.now().getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
                    LocalDate endOfQuarter = startOfQuarter.plusMonths(3).minusDays(1);


                    results = organizationCode != null ?
                            saRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    saRepository.findByDepartmentAndCreatedOnBetween(department, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59), pageRequest) :
                                    (area != null ?
                                            saRepository.findByAreaAndCreatedOnBetween(area, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
                                            saRepository.findByCreatedOnBetween(startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest)));
                    break;
                case YEARLY:
                    LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
                    LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
                    results = organizationCode != null ?
                            saRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    saRepository.findByDepartmentAndCreatedOnBetween(department, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            saRepository.findByAreaAndCreatedOnBetween(area, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                                            saRepository.findByCreatedOnBetween(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest)));
                    break;
                case CUSTOM:
                    results = organizationCode != null ?
                            saRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    saRepository.findByDepartmentAndCreatedOnBetween(department, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            saRepository.findByAreaAndCreatedOnBetween(area, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                                            saRepository.findByCreatedOnBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest)));
                    break;

                default:

                    return new ResponseModel<>(false, "Invalid filter type", null);
            }

            List<SA> saList = saRepository.findAll();
            var totalCount = String.valueOf(saList.size());
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

    public ResponseModel<List<SA>> findPending(String username, String organizationCode) {
        UserInfo userInfo = userInfoRepository.findByUserNameAndOrganizationCode(username, organizationCode);
        boolean isOperator = "Operator".equals(userInfo.getRole());

        if (isOperator) {
            List<SA> pendingSAs = saRepository.findPending(username,organizationCode);
            return new ResponseModel<>(true,"Operator pending List",pendingSAs);
            //return rcaRepository.findPending(username,organizationCode);
        } else {
            List<SA> result = saRepository.findByUsernameAndOrganizationCodeWithPendingStatus(username, organizationCode);
            return new ResponseModel<>(true,"User Pending List", result);
            //return result;
        }
    }

}