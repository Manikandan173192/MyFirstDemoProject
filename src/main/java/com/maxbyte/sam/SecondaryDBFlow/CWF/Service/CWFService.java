package com.maxbyte.sam.SecondaryDBFlow.CWF.Service;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepOne;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository.CAPAStepOneRepository;
import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddCWFConfigurationRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddCWFRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddCWFValidateRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWFWorkFlowConfig;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlow;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Repository.CWFWorkflowConfigRepository;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Repository.WorkFlowConfigurationRepository;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Repository.WorkFlowRepository;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Specification.CWFSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWF;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Repository.CWFRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
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
public class CWFService extends CrudService<CWF,Integer> {
    @Autowired
    private CWFRepository cwfRepository;
    @Override
    public CrudRepository repository() {
        return this.cwfRepository;
    }
    @Autowired
    private WorkFlowRepository workFlowRepository;
    @Autowired
    private CWFWorkflowConfigRepository cwfWorkflowConfigRepository;
    @Autowired
    private WorkFlowConfigurationRepository workFlowConfigurationRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
//    @Autowired
//    CAPAStepOneRepository capaStepOneRepository;

    @Autowired
    private Environment environment;
    @Value("${pagination.default-page}")
    private int defaultPage;
    @Value("${pagination.default-size}")
    private int defaultSize;

    @Override
    public void validateAdd(CWF data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(CWF data) {
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

    public ResponseModel<List<CWF>> list(Boolean isActive, String organizationCode, String assetNumber, String assetDescription,
                                         String department, String documentNumber, String woNumber, String requestPage) {
        try {
            CWFSpecificationBuilder builder = new CWFSpecificationBuilder();
            if(isActive!=null)builder.with("isActive",":",isActive);
            if(organizationCode!=null)builder.with("organizationCode","==",organizationCode);
            if(assetNumber!=null)builder.with("assetNumber","==",assetNumber);
            if(assetDescription!=null)builder.with("assetDescription","==",assetDescription);
            if(department!=null)builder.with("department","==",department);
            if(documentNumber!=null)builder.with("documentNumber","==",documentNumber);
            if(woNumber!=null)builder.with("woNumber","==",woNumber);

            var pageRequestCount = 0;

            if(requestPage.matches(".*\\d.*")){
                pageRequestCount = Integer.parseInt(requestPage);
            }else{
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<CWF> results = cwfRepository.findAll(builder.build(),pageRequest);
            List<CWF> cwfList = cwfRepository.findAll();
            System.out.println(cwfList.size());
            var totalCount = String.valueOf(cwfList.size());
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

    public ResponseModel<List<CWF>> findCWFByDateTime(String organizationCode, LocalDateTime from, LocalDateTime to, String requestPage) {
        try {
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            var results = cwfRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, from, to, pageRequest);
            List<CWF> cwfList = cwfRepository.findAll();
            var totalCount = String.valueOf(cwfList.size());
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

    public ResponseModel<CWF> addCWF(AddCWFRequest data) {
        try {
            List<CWF> listData = cwfRepository.findAll();

            List<WorkFlow> workFlowList= workFlowRepository.findByWorkFlowName(data.getWorkFlowName());

            String fieldName = "";

            if (!workFlowList.isEmpty()) {
                fieldName = workFlowList.getFirst().getWorkFlowNumber();
            }

            CWF cwf= new CWF();

            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            String formattedStartDate = formatter.format(instance);

            var documentNo = "";

            if(listData.isEmpty()){
                documentNo = data.getWorkFlowName()+"_" + data.getOrganizationCode() + "_" + data.getDepartment() + "_" + formattedStartDate + "_" + 1;
                cwf.setDocumentNumber(documentNo.replaceAll(" ","_"));

            }else{
                documentNo =data.getWorkFlowName()+"_" + data.getOrganizationCode() + "_" + data.getDepartment() + "_" + formattedStartDate + "_" + (listData.get(listData.size()-1).getCwfId()+1);
                cwf.setDocumentNumber(documentNo.replaceAll(" ","_"));
            }

            cwf.setConfigId(fieldName);
            cwf.setWorkFlowName(data.getWorkFlowName());
            cwf.setWorkFlowId(data.getWorkFlowId());
            cwf.setOrganizationCode(data.getOrganizationCode());
            cwf.setAssetGroup(data.getAssetGroup());
            cwf.setAssetGroupId(data.getAssetGroupId());
            cwf.setAssetNumber(data.getAssetNumber());
            cwf.setAssetId(data.getAssetId());
            cwf.setAssetDescription(data.getAssetDescription());
            cwf.setDepartment(data.getDepartment());
            cwf.setDepartmentId(data.getDepartmentId());
            cwf.setArea(data.getArea());
            cwf.setInitiatorName(data.getInitiatorName());
            cwf.setFirstApprover(data.getFirstApprover());
            cwf.setFirstApproverId(data.getFirstApproverId());
            cwf.setSecondApprover(data.getSecondApprover());
            cwf.setSecondApproverId(data.getSecondApproverId());
            cwf.setThirdApprover(data.getThirdApprover());
            cwf.setThirdApproverId(data.getThirdApproverId());
            cwf.setCreatedOn(LocalDateTime.now());
            cwf.setWoNumber("");
            cwf.setActive(true);
            cwf.setStatus(0);

            cwfRepository.save(cwf);

            for(CWFWorkFlowConfig item:data.getWorkflowConfigurationList()){
                var cwfWorkFlowConfig = new CWFWorkFlowConfig();
                cwfWorkFlowConfig.setField(item.getField());
                cwfWorkFlowConfig.setAttachment(item.isAttachment());
                cwfWorkFlowConfig.setValue(item.getValue());
                cwfWorkFlowConfig.setAttachmentFile(item.getAttachmentFile());
                cwfWorkFlowConfig.setDocumentNumber(documentNo);
                cwfWorkflowConfigRepository.save(cwfWorkFlowConfig);
            }
            return new ResponseModel<CWF>(true,"Added Successfully ",null);
        } catch (Exception e) {
            return new ResponseModel(false,"Failed to Add",null);
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

       /* List<CWF> cwfList = cwfRepository.findByDocumentNumber(documentNumber);
        if (cwfList.isEmpty()) {
            return new ResponseModel<>(false, "CWF not found that documentNumber: " + documentNumber, null);
        }

        CWF cwf = cwfList.get(0);

        CWFWorkFlowConfig imageUpload = new CWFWorkFlowConfig();
        imageUpload.setDocumentNumber(cwf.getDocumentNumber());
        imageUpload.setId(cwf.getCwfId());
        imageUpload.setAttachmentFile(uploadDirectory + "/" + uniqueFileName);
        cwfWorkflowConfigRepository.save(imageUpload);*/

        var imagePath = new ImageResponse();
        imagePath.setImagePath((uploadDirectory+"/"+uniqueFileName).replace("target/classes/static",""));
        return new ResponseModel<>(true, "Image Updated Successfully",imagePath);
    }

//    @Scheduled(cron = "0 0 0 * * *")  // first 0 Seconds, 0 minute, 0 hours, 0 days, 0 months, 0 week days
//    public ResponseModel<String> deleteAllImagesInFolder() {
//        List<CAPAStepOne> capaStepOnes = capaStepOneRepository.findAll();
//        System.out.println("Five minutes call");
//        List<String> images = new ArrayList<>();
//        for (CAPAStepOne stepThree : capaStepOnes) {
//            images.add(stepThree.getAttachment());
//        }
//
//        String folderPath = environment.getProperty("image.uploadDirectory") + "/CAPA";
//        File folder = new File(folderPath);
//        if (!folder.exists() || !folder.isDirectory()) {
//            return new ResponseModel<>(false, "Folder not found or is not a directory");
//        }
//
//        File[] files = folder.listFiles();
//        if (files != null && files.length > 0) {
//            boolean filesDeleted = false;
//            for (File file : files) {
//                if (file.isFile()) {
//                    String fileName = file.getName();               //  periyasamy
//                    System.out.println(fileName);
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


    public ResponseModel<List<FilterNumberResponse>> getFilterNumber(String organizationCode){
        try {
            List<CWF> cwfList = cwfRepository.findByOrganizationCode(organizationCode);
            List<FilterNumberResponse> filterList = new ArrayList<>();
            for(CWF item: cwfList){
                FilterNumberResponse filterResponse = new FilterNumberResponse();
                filterResponse.setWoNumber(item.getWoNumber());
                filterResponse.setDocumentNumber(item.getDocumentNumber());
                filterList.add(filterResponse);
            }

            return new ResponseModel<List<FilterNumberResponse>>(true, "Records Found",filterList.reversed());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records Not Found",null);
        }
    }

    public ResponseModel<String> validateRequest(AddCWFValidateRequest data){
        try {

            List<CWF> cwfList = cwfRepository.findByDocumentNumber(data.getDocumentNumber());

            cwfList.getFirst().setFirstApprover(data.getFirstApprover());
            cwfList.getFirst().setFirstApproverId(data.getFirstApproverId());
            cwfList.getFirst().setValidateApprover1(data.getValidateApprover1());
            cwfList.getFirst().setCommentsApprover1(data.getCommentsApprover1());

            if(data.getValidateApprover1()!=0 && data.getValidateApprover1()!=3 && cwfList.getFirst().getApprover1DateTime()== null){
                cwfList.getFirst().setApprover1DateTime(LocalDateTime.now());
            }

            cwfList.getFirst().setSecondApprover(data.getSecondApprover());
            cwfList.getFirst().setSecondApproverId(data.getSecondApproverId());
            cwfList.getFirst().setValidateApprover2(data.getValidateApprover2());
            cwfList.getFirst().setCommentsApprover2(data.getCommentsApprover2());

            if(data.getValidateApprover2()!=0 &&data.getValidateApprover2()!=3 && cwfList.getFirst().getApprover2DateTime()== null){
                cwfList.getFirst().setApprover2DateTime(LocalDateTime.now());
            }

            cwfList.getFirst().setThirdApprover(data.getThirdApprover());
            cwfList.getFirst().setThirdApproverId(data.getThirdApproverId());
            cwfList.getFirst().setValidateApprover3(data.getValidateApprover3());
            cwfList.getFirst().setCommentsApprover3(data.getCommentsApprover3());

            if(data.getValidateApprover3()!=0 && data.getValidateApprover3()!=3 && cwfList.getFirst().getApprover3DateTime()== null){
                cwfList.getFirst().setApprover3DateTime(LocalDateTime.now());
            }

            cwfList.getFirst().setReferBackApproverComments(data.getReferBackApproverComments());
            cwfList.getFirst().setReferBackApproverId(data.getReferBackApproverId());
            if (data.getValidateApprover1() == 3 || data.getValidateApprover2() ==3 || data.getValidateApprover3()==3) {
                cwfList.getFirst().setReferBackApprover(data.getReferBackApprover());
                cwfList.getFirst().setStatus(2);
            }

            if(!data.getReferBackApproverComments().isEmpty()){
                if (cwfList.getFirst().getReferBackDateTime()==null){
                    cwfList.getFirst().setReferBackDateTime(LocalDateTime.now());
                }
            }

            if(data.getSecondApprover().isEmpty() && data.getValidateApprover2() == 0){
                if(cwfList.getFirst().getStatus()==1|| cwfList.getFirst().getStatus() == 2) {
                    System.out.println("Test"+cwfList.getFirst().getStatus());
                    if(data.getValidateApprover1()!=0 && data.getValidateApprover1()!=3) {
                        cwfList.getFirst().setStatus(data.getValidateApprover1() == 1 ? 4 : 3);
                    }
                }
                cwfRepository.save(cwfList.getFirst());

            }else if(data.getThirdApprover().isEmpty() && data.getValidateApprover3() == 0){
                System.out.println("test2");
                if(cwfList.getFirst().getStatus()==2) {
                    if(data.getValidateApprover2()!=0 && data.getValidateApprover2()!=3) {
                        cwfList.getFirst().setStatus(data.getValidateApprover2() == 1 ? 4 : 3);
                    }
                }
                else if(data.getValidateApprover1()==2){
                    cwfList.getFirst().setStatus(3);
                }
                else {
                    cwfList.getFirst().setStatus(2);
                }
                cwfRepository.save(cwfList.getFirst());
            }else if(!data.getThirdApprover().isEmpty() && data.getValidateApprover3() != 0){
                if(cwfList.getFirst().getStatus()==2) {
                    if(data.getValidateApprover3()!=0 && data.getValidateApprover3()!=3) {
                        cwfList.getFirst().setStatus(data.getValidateApprover3() == 1 ? 4 : 3);
                    }
                }
                cwfRepository.save(cwfList.getFirst());
            }else{
                if(data.getValidateApprover1() == 2){
                    cwfList.getFirst().setStatus(3);
                }else if(data.getValidateApprover2() == 2){
                    cwfList.getFirst().setStatus(3);
                }else {
                    cwfList.getFirst().setStatus(2);
                }

                cwfRepository.save(cwfList.getFirst());
            }

            return new ResponseModel<>(true, "Updated Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }


    public ResponseModel<String> addWorkFlowConfig(AddCWFConfigurationRequest cwfRequest){
        try {

            List<CWF> cwfList = cwfRepository.findByDocumentNumber(cwfRequest.getDocumentNumber());

            if(!cwfList.isEmpty()){

                var cwfWorkFlowConfigList = cwfWorkflowConfigRepository.findByDocumentNumber(cwfRequest.getDocumentNumber());
                if(!cwfWorkFlowConfigList.isEmpty()){
                    for(CWFWorkFlowConfig items: cwfWorkFlowConfigList){
                        if(items.getDocumentNumber().equals(cwfList.getFirst().getDocumentNumber())){
                            cwfWorkflowConfigRepository.deleteById(items.getId());
                        }
                    }
                }

                for(CWFWorkFlowConfig item:cwfRequest.getWorkflowConfigurationList()){
                    var cwfWorkFlowConfig = new CWFWorkFlowConfig();
                    cwfWorkFlowConfig.setField(item.getField());
                    cwfWorkFlowConfig.setAttachment(item.isAttachment());
                    cwfWorkFlowConfig.setValue(item.getValue());
                    cwfWorkFlowConfig.setAttachmentFile(item.getAttachmentFile());
                    cwfWorkFlowConfig.setDocumentNumber(cwfList.getFirst().getDocumentNumber());
                    cwfWorkflowConfigRepository.save(cwfWorkFlowConfig);
                }

            }else{

                System.out.println(cwfRequest.getDocumentNumber());
                List<CWF> cwfList1 = cwfRepository.findByDocumentNumber(cwfRequest.getDocumentNumber());

                for(CWFWorkFlowConfig item:cwfRequest.getWorkflowConfigurationList()){
                    var cwfWorkFlowConfigData = new CWFWorkFlowConfig();
                    cwfWorkFlowConfigData.setField(item.getField());
                    cwfWorkFlowConfigData.setAttachment(item.isAttachment());
                    cwfWorkFlowConfigData.setValue(item.getValue());
                    cwfWorkFlowConfigData.setAttachmentFile(item.getAttachmentFile());
                    cwfWorkFlowConfigData.setDocumentNumber(cwfList1.getFirst().getDocumentNumber());

                    cwfWorkflowConfigRepository.save(cwfWorkFlowConfigData);
                }
            }

            List<CWF> cwfList1 = cwfRepository.findByDocumentNumber(cwfRequest.getDocumentNumber());
            if(!cwfList1.isEmpty()){
                if(cwfList1.getFirst().getStatus()==0) {
                    cwfList1.getFirst().setStatus(1);
                }
                cwfRepository.save(cwfList1.getFirst());
            }
            return new ResponseModel<>(true, "WorkFlow Created Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }
    public ResponseModel<List<CWF>> reportLists(String department, String area, FilterType filterType, String organizationCode,String requestPage, LocalDate startDate, LocalDate endDate) {
        try {

            Page<CWF> results;
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
                            cwfRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    cwfRepository.findByDepartmentAndCreatedOnBetween(department, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            cwfRepository.findByAreaAndCreatedOnBetween(area, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                                            cwfRepository.findByCreatedOnBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest)));
                    break;
                case WEEKLY:
                    LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                    results = organizationCode != null ?
                            cwfRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    cwfRepository.findByDepartmentAndCreatedOnBetween(department, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            cwfRepository.findByAreaAndCreatedOnBetween(area, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                                            cwfRepository.findByCreatedOnBetween(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest)));
                    break;
                case MONTHLY:
                    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
                    LocalDate endOfMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
                    results = organizationCode != null ?
                            cwfRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    cwfRepository.findByDepartmentAndCreatedOnBetween(department, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            cwfRepository.findByAreaAndCreatedOnBetween(area, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                                            cwfRepository.findByCreatedOnBetween(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest)));
                    break;
                case QUARTERLY:
                    LocalDate startOfQuarter = LocalDate.now().withMonth(((LocalDate.now().getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
                    LocalDate endOfQuarter = startOfQuarter.plusMonths(3).minusDays(1);


                    results = organizationCode != null ?
                            cwfRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    cwfRepository.findByDepartmentAndCreatedOnBetween(department, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59), pageRequest) :
                                    (area != null ?
                                            cwfRepository.findByAreaAndCreatedOnBetween(area, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
                                            cwfRepository.findByCreatedOnBetween(startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest)));
                    break;
                case YEARLY:
                    LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
                    LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
                    results = organizationCode != null ?
                            cwfRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    cwfRepository.findByDepartmentAndCreatedOnBetween(department, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            cwfRepository.findByAreaAndCreatedOnBetween(area, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                                            cwfRepository.findByCreatedOnBetween(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest)));
                    break;
                case CUSTOM:
                    results = organizationCode != null ?
                            cwfRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    cwfRepository.findByDepartmentAndCreatedOnBetween(department, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            cwfRepository.findByAreaAndCreatedOnBetween(area, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                                            cwfRepository.findByCreatedOnBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest)));
                    break;

                default:

                    return new ResponseModel<>(false, "Invalid filter type", null);
            }

            List<CWF> cwfList = cwfRepository.findAll();
            var totalCount = String.valueOf(cwfList.size());
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

    public List<CWF> findPending(String initiatorName, String organizationCode) {
        UserInfo userInfo = userInfoRepository.findByUserNameAndOrganizationCode(initiatorName, organizationCode);
        boolean isOperator = "Operator".equals(userInfo.getRole());
        if (isOperator) {
            //System.out.println("isOperator=" + isOperator);
            return cwfRepository.findPending(initiatorName,organizationCode);
        } else {
            List<CWF> result = cwfRepository.findByUsernameAndOrganizationCodeWithPendingStatus(initiatorName, organizationCode);

            return result;
        }
    }


}
