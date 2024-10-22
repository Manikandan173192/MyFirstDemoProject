package com.maxbyte.sam.SecondaryDBFlow.AIM.Service;


import com.maxbyte.sam.SecondaryDBFlow.AIM.APIRequest.AddAIMRequest;
import com.maxbyte.sam.SecondaryDBFlow.AIM.APIRequest.ValidateAIMRequest;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.AIMImage;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Repository.AimImageRepository;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Repository.AimRepository;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Specification.AIMSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOC;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterNumberResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.FilterType;
import com.maxbyte.sam.SecondaryDBFlow.Response.ImageResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@EnableScheduling
public class AimService extends CrudService<Aim,Integer> {

    @Autowired
    private AimRepository aimRepository;

    @Autowired
    private AimImageRepository aimImageRepository;

    @Autowired
    private Environment environment;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Value("${pagination.default-page}")
    private int defaultPage;
    @Value("${pagination.default-size}")
    private int defaultSize;

    @Override
    public CrudRepository repository() {
        return this.aimRepository;
    }

    @Override
    public void validateAdd(Aim data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(Aim data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }
    }

    @Override
    public void validateDelete(Integer id) {
        try{

        }
        catch(Error e){
            throw new Error(e);
        }
    }


    public ResponseModel<List<Aim>> findAIMByDateTime(String organizationCode, LocalDateTime from, LocalDateTime to, String requestPage) {
        try {
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            var results = aimRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, from, to, pageRequest);
            List<Aim> aimList = aimRepository.findAll();
            var totalCount = String.valueOf(aimList.size());
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

    public ResponseModel<List<Aim>> list(Boolean isActive, String organizationCode, String assetNumber, String assetDescription,
                          String department, String documentNumber,String requestPage, String woNumber) {

        try {

            AIMSpecificationBuilder builder = new AIMSpecificationBuilder();
            if(isActive!=null)builder.with("isActive",":",isActive);
            if(organizationCode!=null)builder.with("organizationCode","==",organizationCode);
            if(assetNumber!=null)builder.with("assetNumber","==",assetNumber);
            if(assetDescription!=null)builder.with("assetDescription","==",assetDescription);
            if(department!=null)builder.with("department","==",department);
            if(documentNumber!=null)builder.with("aimNumber","==",documentNumber);
            if(woNumber!=null)builder.with("woNumber","==",woNumber);

            var pageRequestCount = 0;

            if(requestPage.matches(".*\\d.*")){
                pageRequestCount = Integer.parseInt(requestPage);
            }else{
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<Aim> results = aimRepository.findAll(builder.build(), pageRequest);
            List<Aim> aimList = aimRepository.findAll();
            var totalCount = String.valueOf(aimList.size());
            var filteredCount = String.valueOf(results.getContent().size());
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount+" Records found & "+filteredCount+ " Filtered", results.getContent());
            }

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }


    public ResponseModel<List<FilterNumberResponse>> getFilterNumber(String organizationCode){
        try {
            List<Aim> aimList = aimRepository.findByOrganizationCode(organizationCode);
            List<FilterNumberResponse> filterList = new ArrayList<>();
            for(Aim item: aimList){
                FilterNumberResponse filterResponse = new FilterNumberResponse();
                filterResponse.setWoNumber(item.getWoNumber());
                filterResponse.setDocumentNumber(item.getAimNumber());
                filterList.add(filterResponse);
            }

            return new ResponseModel<List<FilterNumberResponse>>(true, "Records Found",filterList.reversed());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records Not Found",null);
        }
    }
    public ResponseModel<String> addAim(AddAIMRequest aimRequest){
        try {
            List<Aim> aimList = aimRepository.findAll();
            var aimData = new Aim();
            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            String formattedStartDate = formatter.format(instance);

            var AIMNumber = "";
            if(!aimList.isEmpty()){
                int id = aimList.getLast().getAimId()+1;
                AIMNumber = "AIM_"+
                        aimRequest.getOrganizationCode()+ "_"+
                        aimRequest.getDepartment()+"_" +
                        formattedStartDate+"_"+
                        id;

                aimData.setAimNumber("AIM_"+
                        aimRequest.getOrganizationCode()+ "_"+
                        aimRequest.getDepartment()+"_" +
                        formattedStartDate+"_"+
                        id);
            }else {
                AIMNumber = "AIM_"+
                        aimRequest.getOrganizationCode()+ "_"+
                        aimRequest.getDepartment()+"_" +
                        formattedStartDate+"_"+
                        1;
                aimData.setAimNumber("AIM_"+
                        aimRequest.getOrganizationCode()+ "_"+
                        aimRequest.getDepartment()+"_" +
                        formattedStartDate+"_"+
                        1);
            }

            aimData.setOrganizationCode(aimRequest.getOrganizationCode());
            aimData.setAssetGroupId(aimRequest.getAssetGroupId());
            aimData.setAssetGroup(aimRequest.getAssetGroup());
            aimData.setAssetId(aimRequest.getAssetId());
            aimData.setAssetNumber(aimRequest.getAssetNumber());
            aimData.setAssetDescription(aimRequest.getAssetDescription());
            aimData.setDepartment(aimRequest.getDepartment());
            aimData.setDepartmentId(aimRequest.getDepartmentId());
            aimData.setArea(aimRequest.getArea());
            aimData.setCreatedBy(aimRequest.getCreatedBy());
            aimData.setCreatedById(aimRequest.getCreatedById());
            aimData.setApproverId(aimRequest.getApproverId());
            aimData.setApproverName(aimRequest.getApproverName());
            aimData.setAbnormalityCategory(aimRequest.getAbnormalityCategory());
            aimData.setStartDate(aimRequest.getStartDate());
            aimData.setEndDate(aimRequest.getEndDate());
            aimData.setRecommendation(aimRequest.getRecommendation());
            aimData.setIssueDescription(aimRequest.getIssueDescription());
            aimData.setStatus(0);
            aimData.setActive(true);
            aimData.setWoNumber("");
            aimData.setCreatedOn(LocalDateTime.now());

            aimRepository.save(aimData);

            for(AIMImage item:aimRequest.getIssueImage()){
                var aimImage = new AIMImage();
                aimImage.setUploadImage(item.getUploadImage());
                aimImage.setAimNumber(AIMNumber);
                aimImageRepository.save(aimImage);
            }

            return new ResponseModel<>(true, "AIM Created Successfully", aimData.getAimNumber());

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    public ResponseModel<String> updateAim(String aimNo, Aim aimData){
        try {
            List<Aim> aimList = aimRepository.findByAimNumber(aimNo);
            if(!aimList.isEmpty()){
                aimRepository.save(aimData);
                return new ResponseModel<>(true, "AIM Updated Successfully",null);
            }else{
                return new ResponseModel<>(false, "Records not found",null);
            }

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
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
        imagePath.setImagePath((uploadDirectory+"/"+uniqueFileName).replace("target/classes/static",""));
        return new ResponseModel<>(true, "Image Updated Successfully",imagePath);
    }

    @Scheduled(cron = "0 0 0 * * *")  // first 0 Seconds, 0 minute, 0 hours, 0 days, 0 months, 0 week days
    public ResponseModel<String> deleteAllImagesInFolder() {
        List<Aim> rcaStepThreeList = aimRepository.findAll();

        List<String> images = new ArrayList<>();
        for (Aim aim : rcaStepThreeList) {
            for(AIMImage imageData : aim.getIssueImage()){
                images.add(imageData.getUploadImage());
            }
        }

        String folderPath = environment.getProperty("image.uploadDirectory") + "/AIM";
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return new ResponseModel<>(false, "Folder not found or is not a directory");
        }

        File[] files = folder.listFiles();
        if (files != null && files.length > 0) {
            boolean filesDeleted = false;
            for (File file : files) {
                if (file.isFile()) {

                    String relativeFilePath = folder.toURI().relativize(file.toURI()).getPath();

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

    public ResponseModel<String> validateAIM(ValidateAIMRequest validateRequest){
        try {

            List<Aim> aimList = aimRepository.findByAimNumber(validateRequest.getId());

            aimList.getFirst().setApproverId(validateRequest.getApproverId());
            aimList.getFirst().setApproverName(validateRequest.getApproverName());
            aimList.getFirst().setApproverStatus(validateRequest.getApproverStatus());
            aimList.getFirst().setApproverComments(validateRequest.getApproverComments());
            aimList.getFirst().setApproverDateTime(LocalDateTime.now());

            aimList.getFirst().setStatus(validateRequest.getApproverStatus()==1?2:1);

            aimRepository.save(aimList.getFirst());

            return new ResponseModel<>(true, "Updated Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    public ResponseModel<List<Aim>> reportLists(String organizationCode, String department, String area, FilterType filterType, String requestPage, LocalDate startDate, LocalDate endDate) {
        try {
            Page<Aim> results;
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
                results = aimRepository.findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(organizationCode, department, area, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null && department != null) {
                // Query with organizationCode and department filters
                results = aimRepository.findByOrganizationCodeAndDepartmentAndCreatedOnBetween(organizationCode, department, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null && area != null) {
                // Query with organizationCode and area filters
                results = aimRepository.findByOrganizationCodeAndAreaAndCreatedOnBetween(organizationCode, area, startDateTime, endDateTime, pageRequest);
            } else if (department != null && area != null) {
                // Query with department and area filters
                results = aimRepository.findByDepartmentAndAreaAndCreatedOnBetween(department, area, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null) {
                // Query with organizationCode filter
                results = aimRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDateTime, endDateTime, pageRequest);
            } else if (department != null) {
                // Query with department filter
                results = aimRepository.findByDepartmentAndCreatedOnBetween(department, startDateTime, endDateTime, pageRequest);
            } else if (area != null) {
                // Query with area filter
                results = aimRepository.findByAreaAndCreatedOnBetween(area, startDateTime, endDateTime, pageRequest);
            } else {
                // Default query without any filters
                results = aimRepository.findByCreatedOnBetween(startDateTime, endDateTime, pageRequest);
            }

            //return new ResponseModel<>(true, "Records found", results.getContent());
            List<Aim> AimList = aimRepository.findAll();
            var totalCount = String.valueOf(AimList.size());
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

    public ResponseModel<List<Aim>> findPending(String username, String organizationCode) {
        UserInfo userInfo = userInfoRepository.findByUserNameAndOrganizationCode(username, organizationCode);
        boolean isOperator = "Operator".equals(userInfo.getRole());

        if (isOperator) {
            System.out.println("isOperator=" + isOperator);
            List<Aim> pendingAims = aimRepository.findPending(username,organizationCode);
            return new ResponseModel<>(true,"Operator pending List",pendingAims);
            //return rcaRepository.findPending(username,organizationCode);
        } else {
            List<Aim> result = aimRepository.findByUsernameAndOrganizationCodeWithPendingStatus(username, organizationCode);

            return new ResponseModel<>(true,"User Pending List", result);
            //return result;
        }
    }


}
