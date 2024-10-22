package com.maxbyte.sam.SecondaryDBFlow.FMEA.Service;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository.*;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Specification.FMEASpecificationBuilder;

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
import java.util.*;

@Service
public class FMEAService extends CrudService <FMEA, Integer> {

    @Autowired
    FMEARepository fmeaRepository;
    @Autowired
    Environment environment;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    FMEADSProcessRepository fmeadsProcessRepository;
    @Autowired
    FMEADSFunctionRepository fmeadsFunctionRepository;
    @Autowired
    FMEADSFailureModeRepository failureModeRepository;
    @Autowired
    FMEAEffectRepository fmeaEffectRepository;

    @Autowired
    FMEADSCauseRepository fmeadsCauseRepository;

    @Autowired
    FMEADSActionRepository fmeadsActionRepository;

    @Autowired
    FMEADSAAddActionTakenRepository fmeadsaAddActionTakenRepository;


    @Autowired
    FMEAUSFunctionRepository fmeausFunctionRepository;

    @Autowired
    FMEAUSFailureModeRepository fmeausFailureModeRepository;

    @Autowired
    FMEAUSCauseRepository fmeausCauseRepository;

    @Autowired
    FMEAUSEffectRepository fmeausEffectRepository;

    @Autowired
    FMEAUSActionRepository fmeausActionRepository;

    @Autowired
    FMEAUSCause1Repository fmeausCause1Repository;

    @Autowired
    FMEAUSEffect1Repository fmeausEffect1Repository;
    @Autowired
    FMEAUSAction1Repository fmeausAction1Repository;

    @Autowired
    FMEAUSCause2Repository fmeausCause2Repository;

    @Autowired
    FMEAUSEffect2Repository fmeausEffect2Repository;
    @Autowired
    FMEAUSAction2Repository fmeausAction2Repository;

    @Autowired
    FMEAUSCause3Repository fmeausCause3Repository;

    @Autowired
    FMEAUSEffect3Repository fmeausEffect3Repository;
    @Autowired
    FMEAUSAction3Repository fmeausAction3Repository;
    @Autowired
    FMEAUSAction4Repository fmeausAction4Repository;
    @Autowired
    FMEAUSCause4Repository fmeausCause4Repository;
    @Autowired
    FMEAUSEffect4Repository fmeausEffect4Repository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;



    @Override
    public CrudRepository repository() {
        return this.fmeaRepository;
    }

    @Override
    public void validateAdd(FMEA data) {

    }

    @Override
    public void validateEdit(FMEA data) {

    }

    @Override
    public void validateDelete(Integer id) {

    }

    public ResponseModel<List<FMEA>> list(Boolean isActive, String organizationCode, String assetNumber,
                                          String assetDescription, String department,String woNumber,
                                          String fmeaNumber, String requestPage) {

        try {
            FMEASpecificationBuilder builder = new FMEASpecificationBuilder();
            if (isActive != null) builder.with("isActive", ":", isActive);
            if (fmeaNumber != null) builder.with("fmeaNumber","==",fmeaNumber);
            if (assetNumber != null) builder.with("assetNumber", "==", assetNumber);
            if (assetDescription != null) builder.with("assetDescription", "==", assetDescription);
            if (department != null) builder.with("department", "==", department);
            if (organizationCode != null) builder.with("organizationCode", "==", organizationCode);
            if(woNumber!=null)builder.with("woNumber","==",woNumber);


            var pageRequestCount = 0;

            if(requestPage.matches(".*\\d.*")){
                pageRequestCount = Integer.parseInt(requestPage);
            }else{
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<FMEA> results = fmeaRepository.findAll(builder.build(),pageRequest);


            List<FMEA> fmeaList = fmeaRepository.findAll();
            var totalCount = String.valueOf(fmeaList.size());
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

        @Scheduled(cron = "0 0 0 * * *")  // first 0 Seconds, 0 minute, 0 hours, 0 days, 0 months, 0 week days
    public ResponseModel<String> deleteAllImagesInFolder() {
        List<FMEA> fmeaList = fmeaRepository.findAll();
        List<String> images = new ArrayList<>();
        for (FMEA fmea : fmeaList) {
            images.add(fmea.getAttachment());
        }

            List<FMEADSCause> list = fmeadsCauseRepository.findAll();
            for (FMEADSCause fmeadsCause : list){
            images.add(fmeadsCause.getImageAttachment());
            }
        String folderPath = environment.getProperty("image.uploadDirectory") + "/FMEA";
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

    public ResponseModel<List<FilterNumberResponse>> getFilterNumber(String organizationCode){
        try {
            List<FMEA> fmeaList = fmeaRepository.findByOrganizationCode(organizationCode);
            List<FilterNumberResponse> filterList = new ArrayList<>();
            for(FMEA item: fmeaList){
                FilterNumberResponse filterResponse = new FilterNumberResponse();
                filterResponse.setWoNumber(item.getWoNumber());
                filterResponse.setDocumentNumber(item.getFmeaNumber());
                filterList.add(filterResponse);
            }

            return new ResponseModel<List<FilterNumberResponse>>(true, "Records Found",filterList.reversed());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records Not Found",null);
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


    public ResponseModel<String>addFmea(FMEARequest fmeaRequest){
        try {
            List<FMEA> fmeaEntityList = fmeaRepository.findAll();

            var fmeaData = new FMEA();
            LocalDateTime instance = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

            String formattedStartDate = formatter.format(instance);


            if(!fmeaEntityList.isEmpty()){

                int id = fmeaEntityList.getLast().getFmeaId()+1;


                fmeaData.setFmeaNumber("FMEA_"+
                        fmeaRequest.getOrganizationCode()+ "_"+
                        fmeaRequest.getDepartment()+"_" +
                        formattedStartDate+"_"+
                        id);

            }else {

                fmeaData.setFmeaNumber("FMEA_"+
                        fmeaRequest.getOrganizationCode()+ "_"+
                        fmeaRequest.getDepartment()+"_" +
                        formattedStartDate+"_"+
                        1);
            }
//            FMEA fmea = new FMEA();
            fmeaData.setOrganizationCode(fmeaRequest.getOrganizationCode());
            fmeaData.setAssetGroup(fmeaRequest.getAssetGroup());
            fmeaData.setAssetNumber(fmeaRequest.getAssetNumber());
            fmeaData.setDepartment(fmeaRequest.getDepartment());
            fmeaData.setAssetDescription(fmeaRequest.getAssetDescription());
            fmeaData.setArea(fmeaRequest.getArea());
            fmeaData.setFmeaType(fmeaRequest.getFmeaType());
            fmeaData.setTeamMembers(fmeaRequest.getTeamMembers());
            fmeaData.setPreparerName(fmeaRequest.getPreparerName());
            fmeaData.setPreparerId(fmeaRequest.getPreparerId());
            fmeaData.setApproverName(fmeaRequest.getApproverName());
            fmeaData.setApproverId(fmeaRequest.getApproverId());
            fmeaData.setStatus(0);
            fmeaData.setCostCenter(fmeaRequest.getCostCenter());
            fmeaData.setFmeaDescription(fmeaRequest.getFmeaDescription());
            fmeaData.setAssumption(fmeaRequest.getAssumption());
            fmeaData.setSystemBoundaryDefinition(fmeaRequest.getSystemBoundaryDefinition());
            fmeaData.setActive(true);
            fmeaData.setWoNumber("");
            fmeaData.setCreatedOn(LocalDateTime.now());
            fmeaRepository.save(fmeaData);
            return new ResponseModel<String>(true, "FEMA Created Successfully", fmeaData.getFmeaNumber());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel<>(false, "Failed to added", null);
        }
    }
    public ResponseModel<String> addDocumentAndDrawing(AddDocumentAndDrawingRequest documentAndDrawingRequest){
        try{
            List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(documentAndDrawingRequest.getFmeaNumber());
            var actiopnApi = 0;
            if(!fmeaList.isEmpty()){
                fmeaList.getFirst().setFmeaNumber(documentAndDrawingRequest.getFmeaNumber());
                fmeaList.getFirst().setDocType(documentAndDrawingRequest.getDocType());
                fmeaList.getFirst().setDocToUpload(documentAndDrawingRequest.getDocToUpload());
                fmeaList.getFirst().setResponsibility(documentAndDrawingRequest.getResponsibility());
                fmeaList.getFirst().setSupportRequired(documentAndDrawingRequest.getSupportRequired());
                fmeaList.getFirst().setAssetNumber(documentAndDrawingRequest.getAssetNumber());
                fmeaList.getFirst().setAttachment(documentAndDrawingRequest.getAttachment());
                fmeaList.getFirst().setAttachmentDescription(documentAndDrawingRequest.getAttachmentDescription());
                fmeaList.getFirst().setUrl(documentAndDrawingRequest.getUrl());

                fmeaRepository.save(fmeaList.getFirst());
                actiopnApi = 2;
            }else{
                var fmeaData = new FMEA();
                fmeaData.setFmeaNumber(documentAndDrawingRequest.getFmeaNumber());
                fmeaData.setDocType(documentAndDrawingRequest.getDocType());
                fmeaData.setDocToUpload(documentAndDrawingRequest.getDocToUpload());
                fmeaData.setResponsibility(documentAndDrawingRequest.getResponsibility());
                fmeaData.setSupportRequired(documentAndDrawingRequest.getSupportRequired());
                fmeaData.setAssetNumber(documentAndDrawingRequest.getAssetNumber());
                fmeaData.setAttachment(documentAndDrawingRequest.getAttachment());
                fmeaData.setAttachmentDescription(documentAndDrawingRequest.getAttachmentDescription());
                fmeaData.setUrl(documentAndDrawingRequest.getUrl());

                fmeaRepository.save(fmeaData);
                actiopnApi = 1;
            }
            return new ResponseModel<>(true, actiopnApi == 1 ? "Add Document And Drawing Added Successfully" : "Add Document And Drawing Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }
    public ResponseModel<List<FMEA>> getDocumentAndDrawing(String fmeaNumber) {
        try {
            var addProcess = fmeaRepository.findByFmeaNumber(fmeaNumber);

            return  new ResponseModel<>(true,"Record Found",addProcess);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);
        }
    }
    public ResponseModel<String> addRevisionDate(RevisionDateRequest fmeaRequest){
        try{
            List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(fmeaRequest.getFmeaNumber());
            var actiopnApi = 0;
            if(!fmeaList.isEmpty()){
                fmeaList.getFirst().setFmeaNumber(fmeaRequest.getFmeaNumber());
                fmeaList.getFirst().setSystemBoundaryDefinition(fmeaRequest.getSystemBoundaryDefinition());
                fmeaList.getFirst().setAssumption(fmeaRequest.getAssumption());

                fmeaRepository.save(fmeaList.getFirst());
                actiopnApi = 2;
            }else{
                var fmeaData = new FMEA();
                fmeaData.setFmeaNumber(fmeaRequest.getFmeaNumber());
                fmeaData.setSystemBoundaryDefinition(fmeaRequest.getSystemBoundaryDefinition());
                fmeaData.setAssumption(fmeaRequest.getAssumption());

                fmeaRepository.save(fmeaData);
                actiopnApi = 1;
            }
            return new ResponseModel<>(true, actiopnApi == 1 ? "Add Revision Date Added Successfully" : "Add Revision Date Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }
    public ResponseModel<List<FMEA>> getRevisionDate(String fmeaNumber) {
        try {
            var addProcess = fmeaRepository.findByFmeaNumber(fmeaNumber);

            return  new ResponseModel<>(true,"Record Found",addProcess);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);
        }
    }

    public ResponseModel<String> addOrUpdateFMEADSProcess(/*String fmeaNumber,*/ AddFMEAProcessRequest addFMEAProcessRequest) {
        try {
            List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(addFMEAProcessRequest.getFmeaNumber());
            if (!fmeaList.isEmpty()) {
                FMEA fmea = fmeaList.get(0); // Retrieve the first element from the list
                List<FMEADSProcess> existingProcessOptional = fmeadsProcessRepository.findByProcessId(addFMEAProcessRequest.getProcessId());
                if (existingProcessOptional.size()!=0) {
                    // If process already exists, update it
                    FMEADSProcess existingProcess = existingProcessOptional.getFirst();
                    existingProcess.setDescription(addFMEAProcessRequest.getDescription());
                    fmeadsProcessRepository.save(existingProcess);
                } else {
                    // If process does not exist, create a new one and set its parent ID
                    FMEADSProcess newProcess = new FMEADSProcess();
                    newProcess.setFmeaNumber(addFMEAProcessRequest.getFmeaNumber());
                    newProcess.setParentId(fmea.getFmeaId());
                    newProcess.setProcessName(addFMEAProcessRequest.getProcessName());
                    newProcess.setDescription(addFMEAProcessRequest.getDescription());
                    fmeadsProcessRepository.save(newProcess);
                }
                return new ResponseModel<>(true, "FMEADSProcess added or updated and associated with FMEA", null);
            } else {
                return new ResponseModel<>(false, "FMEA not found", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEADSProcess", null);
        }
    }
    public ResponseModel<List<FMEADSProcess>> getFMEADSProcess(String fmeaNumber) {
        try {
            var addProcess = fmeadsProcessRepository.findByFmeaNumber(fmeaNumber);

            return  new ResponseModel<>(true,"Record Found",addProcess.reversed());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);
        }
    }


public ResponseModel<String> addOrUpdateFmeaDsFunction(/*String fmeaNumber,*/ AddFMEADSFunctionRequest addFMEADSFunctionRequest) {
    try {
        Optional<FMEADSProcess> processOptional = fmeadsProcessRepository.findById(addFMEADSFunctionRequest.getParentId());

        if (processOptional.isPresent()) {
            FMEADSProcess fmeadsProcess = processOptional.get();

            Integer functionIdToAddOrUpdate = addFMEADSFunctionRequest.getFunctionId();
            Optional<FMEADSFunction> existingFunctionOptional = fmeadsFunctionRepository.findByFunctionId(functionIdToAddOrUpdate);
            if (existingFunctionOptional.isPresent()) {
                // If function already exists, update it
                FMEADSFunction existingFunction = existingFunctionOptional.get();
                existingFunction.setFunctionName(addFMEADSFunctionRequest.getFunctionName());
                existingFunction.setDescription(addFMEADSFunctionRequest.getDescription());
                fmeadsFunctionRepository.save(existingFunction);
            } else {
                // If function does not exist, create a new one and set its parent ID
                FMEADSFunction newFunction = new FMEADSFunction();
                newFunction.setFmeaNumber(addFMEADSFunctionRequest.getFmeaNumber());
                newFunction.setParentId(fmeadsProcess.getProcessId()); // Set parent ID here
                newFunction.setFunctionName(addFMEADSFunctionRequest.getFunctionName());
                newFunction.setDescription(addFMEADSFunctionRequest.getDescription());
                fmeadsFunctionRepository.save(newFunction);

                // Add the new function to the process's list
                List<FMEADSFunction> functions = fmeadsProcess.getFmeaDSFunctions();
                functions.add(newFunction);
                fmeadsProcess.setFmeaDSFunctions(functions);
                fmeadsProcessRepository.save(fmeadsProcess);
            }

            return new ResponseModel<>(true, "FMEADSFunction added or updated and associated with FMEA(s)", null);
        } else {
            return new ResponseModel<>(false, "FMEADSProcess not found for the provided parentId", null);
        }
    } catch (Exception e) {
        return new ResponseModel<>(false, "Failed to add or update FMEADSFunction", null);
    }
}


    public ResponseModel<List<FMEADSFunction>> getFMEADSFunction(String fmeaNumber) {
        try {
            var addProcess = fmeadsFunctionRepository.findByFmeaNumber(fmeaNumber);

            return  new ResponseModel<>(true,"Record Found",addProcess.reversed());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);
        }
    }


public ResponseModel<String> addOrUpdateFmeaDsFailureMode(/*String fmeaNumber,*/ AddFMEADSFailureModeRequest fmeadsFailureModeRequest) {
    try {
        Optional<FMEADSFunction> functionOptional = fmeadsFunctionRepository.findById(fmeadsFailureModeRequest.getParentId());

        if (functionOptional.isPresent()) {
            FMEADSFunction fmeadsFunction = functionOptional.get();

            // Check if the failure mode already exists
            Integer failureModeIdToAddOrUpdate = fmeadsFailureModeRequest.getFailureModeId();
            Optional<FMEADSFailureMode> existingFailureModeOptional = failureModeRepository.findByFailureModeId(failureModeIdToAddOrUpdate);


            if (existingFailureModeOptional.isPresent()) {
                // If failure mode already exists, update it
                FMEADSFailureMode existingFailureMode = existingFailureModeOptional.get();
                existingFailureMode.setFailureModeDescription(fmeadsFailureModeRequest.getFailureModeDescription());
                existingFailureMode.setClassification(fmeadsFailureModeRequest.getClassification());
                failureModeRepository.save(existingFailureMode);
            } else {
                // If failure mode does not exist, create a new one and set its parent ID
                FMEADSFailureMode failureMode = new FMEADSFailureMode();
                failureMode.setFmeaNumber(fmeadsFailureModeRequest.getFmeaNumber());
                failureMode.setParentId(fmeadsFunction.getFunctionId()); // Set parent ID here
                failureMode.setFailureMode(fmeadsFailureModeRequest.getFailureMode());
                failureMode.setFailureModeDescription(fmeadsFailureModeRequest.getFailureModeDescription());
                failureMode.setClassification(fmeadsFailureModeRequest.getClassification());
                failureModeRepository.save(failureMode);

                // Add the new failure mode to the function's list
                List<FMEADSFailureMode> failureModes = fmeadsFunction.getFmeaDSFailureModeList();
                failureModes.add(failureMode);
                fmeadsFunction.setFmeaDSFailureModeList(failureModes);
                fmeadsFunctionRepository.save(fmeadsFunction);
            }

            return new ResponseModel<>(true, "FMEADSFailureMode added or updated and associated with FMEA(s)", null);
        } else {
            return new ResponseModel<>(false, "FMEADSFunction not found for the provided parentId", null);
        }
    } catch (Exception e) {
        return new ResponseModel<>(false, "Failed to add or update FMEADSFailureMode", null);
    }
}






    public ResponseModel<String> addOrUpdateFmeaDsEffect(/*String fmeaNumber,*/ AddFMEADSEffectRequest fmeaEffectRequest) {
        try {
            Optional<FMEADSFailureMode> failureModeOptional = failureModeRepository.findById(fmeaEffectRequest.getParentId());

            if (failureModeOptional.isPresent()) {
                FMEADSFailureMode failureMode = failureModeOptional.get();

                // Check if the effect already exists
                Integer effectIdToAddOrUpdate = fmeaEffectRequest.getEffectId();
                Optional<FMEAEffect> existingEffectOptional = fmeaEffectRepository.findByEffectId(effectIdToAddOrUpdate);

                if (existingEffectOptional.isPresent()) {
                    // If effect already exists, update it
                    FMEAEffect existingEffect = existingEffectOptional.get();
                    existingEffect.setEffectName(fmeaEffectRequest.getEffectName());
                    existingEffect.setDescription(fmeaEffectRequest.getDescription());
                    existingEffect.setSeverity(fmeaEffectRequest.getSeverity());
                    fmeaEffectRepository.save(existingEffect);
                } else {
                    // If effect does not exist, create a new one and set its parent ID
                    FMEAEffect newEffect = new FMEAEffect();
                    newEffect.setFmeaNumber(fmeaEffectRequest.getFmeaNumber());
                    newEffect.setParentId(failureMode.getFailureModeId()); // Set parent ID here
                    newEffect.setEffectName(fmeaEffectRequest.getEffectName());
                    newEffect.setDescription(fmeaEffectRequest.getDescription());
                    newEffect.setSeverity(fmeaEffectRequest.getSeverity());
                    fmeaEffectRepository.save(newEffect);

                    // Add the new effect to the failure mode's list
                    List<FMEAEffect> effects = failureMode.getFmeaEffects();
                    effects.add(newEffect);
                    failureMode.setFmeaEffects(effects);
                    failureModeRepository.save(failureMode);
                }

                return new ResponseModel<>(true, "FMEADSEffect added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEADSFailureMode not found for the provided parentId", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEADSEffect", null);
        }
    }
    public ResponseModel<String> addOrUpdateCause(/*String fmeaNumber,*/ AddFMEADSCauseRequest fmeaCauseRequest) {
        try {
            Optional<FMEAEffect> effectOptional = fmeaEffectRepository.findById(fmeaCauseRequest.getParentId());

            if (effectOptional.isPresent()) {
                FMEAEffect effect = effectOptional.get();

                // Check if the cause already exists
                Integer causeIdToAddOrUpdate = fmeaCauseRequest.getCauseId();
                Optional<FMEADSCause> existingCauseOptional = fmeadsCauseRepository.findByCauseId(causeIdToAddOrUpdate);


                if (existingCauseOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEADSCause existingCause = existingCauseOptional.get();
                    existingCause.setCauseName(fmeaCauseRequest.getCauseName());
                    existingCause.setDescription(fmeaCauseRequest.getDescription());
                    existingCause.setPreventionControl(fmeaCauseRequest.getPreventionControl());
                    existingCause.setDetectionControl(fmeaCauseRequest.getDetectionControl());
                    existingCause.setOccurrence(fmeaCauseRequest.getOccurrence());
                    existingCause.setDetection(fmeaCauseRequest.getDetection());
                    existingCause.setImageAttachment(fmeaCauseRequest.getImageAttachment());
                    existingCause.setRpn(fmeaCauseRequest.getRpn());
                    existingCause.setUrl(fmeaCauseRequest.getUrl());
                    fmeadsCauseRepository.save(existingCause);
                } else {
                    List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(fmeaCauseRequest.getFmeaNumber());
                    if (!fmeaList.isEmpty()) {
                        FMEA fmea = fmeaList.getFirst();
                        fmea.setStatus(2);
                        fmeaRepository.save(fmea);
                    }

                    // If cause does not exist, create a new one and set its parent ID
                    FMEADSCause newCause = new FMEADSCause();
                    newCause.setFmeaNumber(fmeaCauseRequest.getFmeaNumber());
                    newCause.setParentId(effect.getEffectId()); // Set parent ID here
                    newCause.setCauseName(fmeaCauseRequest.getCauseName());
                    newCause.setDescription(fmeaCauseRequest.getDescription());
                    newCause.setPreventionControl(fmeaCauseRequest.getPreventionControl());
                    newCause.setDetectionControl(fmeaCauseRequest.getDetectionControl());
                    newCause.setOccurrence(fmeaCauseRequest.getOccurrence());
                    newCause.setDetection(fmeaCauseRequest.getDetection());
                    newCause.setImageAttachment(fmeaCauseRequest.getImageAttachment());
                    newCause.setRpn(fmeaCauseRequest.getRpn());
                    newCause.setUrl(fmeaCauseRequest.getUrl());

                    fmeadsCauseRepository.save(newCause);

                    // Add the new cause to the effect's list
                    List<FMEADSCause> causes = effect.getFmeaDSCauses();
                    causes.add(newCause);
                    effect.setFmeaDSCauses(causes);
                    fmeaEffectRepository.save(effect);
                }

                return new ResponseModel<>(true, "FMEADSCause added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAEffect not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEADSCause", null);
        }
    }

    public ResponseModel<String>addOrUpdateFMEADSAction(/*String fmeaNumber,*/ AddFMEADSActionRequest fmeadsActionRequest) {
        try{
            Optional<FMEADSCause> fmeadsCauseOptional = fmeadsCauseRepository.findById(fmeadsActionRequest.getParentId());
            if (fmeadsCauseOptional.isPresent()) {
                FMEADSCause fmeadsCause = fmeadsCauseOptional.get();

                // Check if the action already exists
                Integer actionIdToAddOrUpdate = fmeadsActionRequest.getActionId();
                Optional<FMEADSAction> existingActionOptional = fmeadsActionRepository.findByActionId(actionIdToAddOrUpdate);

                if (existingActionOptional.isPresent()) {
                    // If action already exists, update it
                    FMEADSAction existingAction = existingActionOptional.get();
                    existingAction.setActionRecommended(fmeadsActionRequest.getActionRecommended());
                    existingAction.setDepartment(fmeadsActionRequest.getDepartment());
                    existingAction.setResponsible(fmeadsActionRequest.getResponsible());
                    existingAction.setAssetNumber(fmeadsActionRequest.getAssetNumber());
                    existingAction.setFundingSource(fmeadsActionRequest.getFundingSource());
                    existingAction.setSpa(fmeadsActionRequest.getSpa());
                    existingAction.setWoNumber(fmeadsActionRequest.getWoNumber());
                    existingAction.setWoStatus(fmeadsActionRequest.getWoStatus());
                    existingAction.setScheduleStartDate(fmeadsActionRequest.getScheduleStartDate());
                    existingAction.setScheduleEndDate(fmeadsActionRequest.getScheduleEndDate());
                    existingAction.setAssetDescription(fmeadsActionRequest.getAssetDescription());
                    fmeadsActionRepository.save(existingAction);
                }else {
                    // If action does not exist, create a new one and set its parent ID
                    FMEADSAction newAction = new FMEADSAction();
                    newAction.setFmeaNumber(fmeadsActionRequest.getFmeaNumber());
                    newAction.setParentId(fmeadsCause.getCauseId()); // Set parent ID here
                    newAction.setActionRecommended(fmeadsActionRequest.getActionRecommended());
                    newAction.setDepartment(fmeadsActionRequest.getDepartment());
                    newAction.setResponsible(fmeadsActionRequest.getResponsible());
                    newAction.setAssetNumber(fmeadsActionRequest.getAssetNumber());
                    newAction.setFundingSource(fmeadsActionRequest.getFundingSource());
                    newAction.setSpa(fmeadsActionRequest.getSpa());
                    newAction.setWoNumber(fmeadsActionRequest.getWoNumber());
                    newAction.setWoStatus(fmeadsActionRequest.getWoStatus());
                    newAction.setScheduleStartDate(fmeadsActionRequest.getScheduleStartDate());
                    newAction.setScheduleEndDate(fmeadsActionRequest.getScheduleEndDate());
                    newAction.setAssetDescription(fmeadsActionRequest.getAssetDescription());
                    fmeadsActionRepository.save(newAction);

                    List<FMEADSAction> actions = fmeadsCause.getFmeaDSActions();
                    actions.add(newAction);
                    fmeadsCause.setFmeaDSActions(actions);
                    fmeadsCauseRepository.save(fmeadsCause);
                }
                return new ResponseModel<>(true, "FMEADSAction added or updated and associated with FMEA(s)", null);
            }else {
                return new ResponseModel<>(false, "FMEADSCause not found for the provided parentId", null);
            }

        }catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEADSAction", null);
        }

    }

    public ResponseModel<String> addFMEADAddSActionTaken(/*String fmeaNumber,*/ AddFMEADSActionTakenRequest fmeadsActionTakenRequest) {
        try {
            Optional<FMEADSAction> fmeadsActionOptional = fmeadsActionRepository.findById(fmeadsActionTakenRequest.getParentId());

            if (fmeadsActionOptional.isPresent()) {
                FMEADSAction fmeadsAction = fmeadsActionOptional.get();

                // Check if the action taken already exists
                Integer actionIdToAddOrUpdate = fmeadsActionTakenRequest.getActionTakenId();
                Optional<FMEADSAddActionTaken> existingActionTakenOptional = fmeadsaAddActionTakenRepository.findByActionTakenId(actionIdToAddOrUpdate);

                if (existingActionTakenOptional.isPresent()) {
                    FMEADSAddActionTaken existingActionTaken = existingActionTakenOptional.get();
                    existingActionTaken.setActionTaken(fmeadsActionTakenRequest.getActionTaken());
                    existingActionTaken.setLifeCycle(fmeadsActionTakenRequest.getLifeCycle());
                    existingActionTaken.setPriority(fmeadsActionTakenRequest.getPriority());
                    existingActionTaken.setSev(fmeadsActionTakenRequest.getSev());
                    existingActionTaken.setOcc(fmeadsActionTakenRequest.getOcc());
                    existingActionTaken.setDet(fmeadsActionTakenRequest.getDet());
                    existingActionTaken.setRpn(fmeadsActionTakenRequest.getRpn());
                    fmeadsaAddActionTakenRepository.save(existingActionTaken);
                    return new ResponseModel<>(true, "FMEADSAddActionTaken Updated Successfully", null);
                }

                // Create a new action taken and set its parent ID
                FMEADSAddActionTaken newActionTaken = new FMEADSAddActionTaken();
                newActionTaken.setFmeaNumber(fmeadsActionTakenRequest.getFmeaNumber());
                newActionTaken.setParentId(fmeadsAction.getActionId()); // Set parent ID here
                newActionTaken.setActionTaken(fmeadsActionTakenRequest.getActionTaken());
                newActionTaken.setLifeCycle(fmeadsActionTakenRequest.getLifeCycle());
                newActionTaken.setPriority(fmeadsActionTakenRequest.getPriority());
                newActionTaken.setSev(fmeadsActionTakenRequest.getSev());
                newActionTaken.setOcc(fmeadsActionTakenRequest.getOcc());
                newActionTaken.setDet(fmeadsActionTakenRequest.getDet());
                newActionTaken.setRpn(fmeadsActionTakenRequest.getRpn());
                newActionTaken.setCreatedOn(fmeadsActionTakenRequest.getCreatedOn());

                fmeadsaAddActionTakenRepository.save(newActionTaken);

                // Add the new action taken to the action's list
                List<FMEADSAddActionTaken> actionTakens = fmeadsAction.getFmeadsAddActionTakens();
                actionTakens.add(newActionTaken);
                fmeadsAction.setFmeadsAddActionTakens(actionTakens);
                fmeadsActionRepository.save(fmeadsAction);

                return new ResponseModel<>(true, "FMEADSAddActionTaken added and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEADSAction not found for the provided parentId", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add FMEADSAddActionTaken", null);
        }
    }

//   @Transactional
   public ResponseModel<String> deleteProcess(String fmeaNumber, Integer entityId, TypeDS processTypeDS) {
       try {
           switch (processTypeDS) {
               case FMEA_DS_Process:
                   fmeadsProcessRepository.deleteByFmeaNumberAndProcessId(fmeaNumber, entityId);
                   System.out.println("Called1"+ processTypeDS);
                   break;
               case FMEA_DS_Function:
                   fmeadsFunctionRepository.deleteByFmeaNumberAndFunctionId(fmeaNumber, entityId);
                   break;
               case FMEA_DS_Failure_Mode:
                   failureModeRepository.deleteByFmeaNumberAndFailureModeId(fmeaNumber, entityId);
                   break;
               case FMEA_DS_Effect:
                   fmeaEffectRepository.deleteByFmeaNumberAndEffectId(fmeaNumber, entityId);
                   break;
               case FMEA_DS_Cause:
                   fmeadsCauseRepository.deleteByFmeaNumberAndCauseId(fmeaNumber, entityId);
                   break;
               case FMEA_DS_Action:
                   fmeadsActionRepository.deleteByFmeaNumberAndActionId(fmeaNumber, entityId);
                   break;
               case FMEA_DS_Action_Taken:
                   fmeadsaAddActionTakenRepository.deleteByFmeaNumberAndActionTakenId(fmeaNumber, entityId);
                   break;
               default:
                   return new ResponseModel<>(false, "Invalid entity type", null);
           }
           return new ResponseModel<>(true, "Entity deleted successfully", null);
       } catch (Exception e) {
           return new ResponseModel<>(false, "Failed to delete entity", null);
       }
   }

    public ResponseModel<String> deleteUSProcess(String fmeaNumber, Integer entityId, TypeUS processTypeUS) {
        try {
            switch (processTypeUS) {
                case FMEA_US_Function:
                    fmeausFunctionRepository.deleteByFmeaNumberAndFunctionId(fmeaNumber, entityId);
                    break;
                case FMEA_US_FailureMode:
                    fmeausFailureModeRepository.deleteByFmeaNumberAndFailureModeId(fmeaNumber, entityId);
                    break;
                case FMEA_US_Cause:
                    fmeausCauseRepository.deleteByFmeaNumberAndCauseId(fmeaNumber, entityId);
                    break;
                case FMEA_US_Cause1:
                    fmeausCause1Repository.deleteByFmeaNumberAndCauseIdOne(fmeaNumber, entityId);
                    break;
                case FMEA_US_Cause2:
                    fmeausCause2Repository.deleteByFmeaNumberAndCauseIdTwo(fmeaNumber, entityId);
                    break;
                case FMEA_US_Cause3:
                    fmeausCause3Repository.deleteByFmeaNumberAndCauseIdThree(fmeaNumber, entityId);
                    break;
                case FMEA_US_Effect:
                    fmeausEffectRepository.deleteByFmeaNumberAndEffectId(fmeaNumber, entityId);
                    break;
                case FMEA_US_Effect1:
                    fmeausEffect1Repository.deleteByFmeaNumberAndEffectIdOne(fmeaNumber, entityId);
                    break;
                case FMEA_US_Effect2:
                    fmeausEffect2Repository.deleteByFmeaNumberAndEffectIdTwo(fmeaNumber, entityId);
                    break;
                case FMEA_US_Effect3:
                    fmeausEffect3Repository.deleteByFmeaNumberAndEffectIdThree(fmeaNumber, entityId);
                    break;
                case FMEA_US_Action:
                    fmeausActionRepository.deleteByFmeaNumberAndActionId(fmeaNumber, entityId);
                    break;
                case FMEA_US_Action1:
                    fmeausAction1Repository.deleteByFmeaNumberAndActionIdOne(fmeaNumber, entityId);
                    break;
                case FMEA_US_Action2:
                    fmeausAction2Repository.deleteByFmeaNumberAndActionIdTwo(fmeaNumber, entityId);
                    break;
                case FMEA_US_Action3:
                    fmeausAction3Repository.deleteByFmeaNumberAndActionIdThree(fmeaNumber, entityId);
                    break;

                default:
                    return new ResponseModel<>(false, "Invalid entity type", null);
            }
            return new ResponseModel<>(true, "Entity deleted successfully", null);
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to delete entity", null);
        }
    }

   //===========================================UPSTREAM=====================================================

    public ResponseModel<String> addUSFunction(/*String fmeaNumber,*/ AddFMEAUSFunctionRequest addFMEAUSFunctionRequest) {
        try {
            // Find FMEA by fmeaNumber
//            Optional<FMEA> optionalFMEA = fmeaRepository.findByFmeaNumber(fmeaNumber).stream().findFirst();
//            if (optionalFMEA.isPresent()) {
//                FMEA fmea = optionalFMEA.get();
            List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(addFMEAUSFunctionRequest.getFmeaNumber());
            if (!fmeaList.isEmpty()) {
                FMEA fmea = fmeaList.getFirst();

                // Create FMEAUSFunction entity
                FMEAUSFunction fmeadsFunction = new FMEAUSFunction();
                // Set FMEAUSFunction properties
                fmeadsFunction.setFmeaNumber(addFMEAUSFunctionRequest.getFmeaNumber());
                fmeadsFunction.setParentId(fmea.getFmeaId());
                fmeadsFunction.setFunctionName(addFMEAUSFunctionRequest.getFunctionName());
                fmeadsFunction.setFunctionFailure(addFMEAUSFunctionRequest.getFunctionFailure());
                fmeadsFunction.setFunctionType(addFMEAUSFunctionRequest.getFunctionType());
                fmea.setStatus(1);
                fmeaRepository.save(fmea);
                fmeausFunctionRepository.save(fmeadsFunction);

                return new ResponseModel<>(true, "FMEAUSFunction added and associated with FMEA", null);
            } else {
                return new ResponseModel<>(false, "FMEA not found", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add FMEAUSFunction", null);
        }
    }



    public ResponseModel<List<FMEAUSFunction>> getFMEAUSFunction(String fmeaNumber) {
        try {
            var getFunction = fmeausFunctionRepository.findByFmeaNumber(fmeaNumber);

            return  new ResponseModel<List<FMEAUSFunction>>(true,"Record Found",getFunction.reversed());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Error occurred while retrieving record", null);
        }
    }


    public ResponseModel<String> addOrUpdateFmeaUSFailureMode(/*String fmeaNumber,*/ AddFMEAUSFailureModeRequest fmeausFailureModeRequest) {
        try {
            Optional<FMEAUSFunction> functionOptional = fmeausFunctionRepository.findById(fmeausFailureModeRequest.getParentId());

            if (functionOptional.isPresent()) {
                FMEAUSFunction fmeausFunction = functionOptional.get();

                // Check if the failure mode already exists
                Integer failureModeIdToAddOrUpdate = fmeausFailureModeRequest.getFailureModeId();
                Optional<FMEAUSFailureMode> existingFailureModeOptional = fmeausFailureModeRepository.findByFailureModeId(failureModeIdToAddOrUpdate);


                if (existingFailureModeOptional.isPresent()) {
                    // If failure mode already exists, update it
                    FMEAUSFailureMode existingFailureMode = existingFailureModeOptional.get();
                    existingFailureMode.setFailureMode(fmeausFailureModeRequest.getFailureMode());
                    fmeausFailureModeRepository.save(existingFailureMode);
                } else {
                    // If failure mode does not exist, create a new one and set its parent ID
                    FMEAUSFailureMode failureMode = new FMEAUSFailureMode();
                    failureMode.setFmeaNumber(fmeausFailureModeRequest.getFmeaNumber());
                    failureMode.setParentId(fmeausFunction.getFunctionId()); // Set parent ID here
                    failureMode.setFailureMode(fmeausFailureModeRequest.getFailureMode());
                    fmeausFailureModeRepository.save(failureMode);

                    // Add the new failure mode to the function's list
                    List<FMEAUSFailureMode> failureModes = fmeausFunction.getFmeaUSFailureModes();
                    failureModes.add(failureMode);
                    fmeausFunction.setFmeaUSFailureModes(failureModes);
                    fmeausFunctionRepository.save(fmeausFunction);
                }

                return new ResponseModel<>(true, "FMEAUSFailureMode added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSFunction not found for the provided parentId", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSFailureMode", null);
        }
    }

    public ResponseModel<String> addOrUpdateUSCause(/*String fmeaNumber,*/ AddFMEAUSCauseRequest fmeausCauseRequest) {
        try {
            Optional<FMEAUSFailureMode> failureOptional = fmeausFailureModeRepository.findById(fmeausCauseRequest.getParentId());

            if (failureOptional.isPresent()) {
                FMEAUSFailureMode failureMode = failureOptional.get();

                // Check if the cause already exists
                Integer causeIdToAddOrUpdate = fmeausCauseRequest.getCauseId();
                Optional<FMEAUSCause> existingCauseOptional = fmeausCauseRepository.findByCauseId(causeIdToAddOrUpdate);


                if (existingCauseOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSCause existingCause = existingCauseOptional.get();
                    existingCause.setCauseName(fmeausCauseRequest.getCauseName());

                    fmeausCauseRepository.save(existingCause);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(fmeausCauseRequest.getFmeaNumber());
                    if (!fmeaList.isEmpty()) {
                        FMEA fmea = fmeaList.getFirst();
                        fmea.setStatus(2);
                        fmeaRepository.save(fmea);
                    }
                    FMEAUSCause newCause = new FMEAUSCause();
                    newCause.setFmeaNumber(fmeausCauseRequest.getFmeaNumber());
                    newCause.setParentId(failureMode.getFailureModeId()); // Set parent ID here
                    newCause.setCauseName(fmeausCauseRequest.getCauseName());

                    fmeausCauseRepository.save(newCause);

                    // Add the new cause to the effect's list
                    List<FMEAUSCause> causes = failureMode.getFmeaUSCauses();
                    causes.add(newCause);
                    failureMode.setFmeaUSCauses(causes);
                    fmeausFailureModeRepository.save(failureMode);
                }

                return new ResponseModel<>(true, "FMEAUSCause added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSFailureMode not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSCause", null);
        }
    }


   /* public ResponseModel<String> addOrUpdateUSSubCause(String fmeaNumber, AddFMEAUSSubCauseRequest fmeausSubCauseRequest) {
        try {
            Optional<FMEAUSCause> causeOptional = fmeausCauseRepository.findById(fmeausSubCauseRequest.getParentId());

            if (causeOptional.isPresent()) {
                FMEAUSCause cause = causeOptional.get();

                // Check if the cause already exists
                Integer subCauseIdToAddOrUpdate = fmeausSubCauseRequest.getSubCauseId();
                Optional<FMEAUSSubCause> existingSubCauseOptional = fmeausSubCauseRepository.findBySubCauseId(subCauseIdToAddOrUpdate);


                if (existingSubCauseOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSSubCause existingSubCause = existingSubCauseOptional.get();
                    //existingSubCauseOptional.getFirst().setSubCauseName(fmeausSubCauseRequest.getSubCauseName());
                    existingSubCause.setSubCauseName(fmeausSubCauseRequest.getSubCauseName());

                    fmeausSubCauseRepository.save(existingSubCause);
                    //fmeausSubCauseRepository.save(existingSubCauseOptional.getFirst());
                } else {
                    List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(fmeaNumber);
                    if (!fmeaList.isEmpty()) {
                        FMEA fmea = fmeaList.getFirst();
                        fmea.setStatus(2);
                        fmeaRepository.save(fmea);
                    }
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSSubCause newSubCause = new FMEAUSSubCause();
                    newSubCause.setFmeaNumber(fmeaNumber);
                    newSubCause.setParentId(cause.getCauseId()); // Set parent ID here
                    newSubCause.setSubCauseName(fmeausSubCauseRequest.getSubCauseName());

                    fmeausSubCauseRepository.save(newSubCause);

                    // Add the new cause to the effect's list
                    List<FMEAUSSubCause> subCauses = cause.getFmeaUSSubCauses();
                    subCauses.add(newSubCause);
                    cause.setFmeaUSSubCauses(subCauses);
                    fmeausCauseRepository.save(cause);
                }

                return new ResponseModel<>(true, "FMEADSCause added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAEffect not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEADSCause", null);
        }
    }*/

    public ResponseModel<String> addOrUpdateUSEffect(/*String fmeaNumber,*/ AddFMEAUSEffectRequest fmeausEffectRequest) {
        try {
            Optional<FMEAUSCause> causeOptional = fmeausCauseRepository.findByCauseId(fmeausEffectRequest.getParentId());

            if (causeOptional.isPresent()) {
                FMEAUSCause cause = causeOptional.get();

                // Check if the cause already exists
                Integer effectIdToAddOrUpdate = fmeausEffectRequest.getEffectId();
                Optional<FMEAUSEffect> existingEffectOptional = fmeausEffectRepository.findByEffectId(effectIdToAddOrUpdate);


                if (existingEffectOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSEffect existingEffect= existingEffectOptional.get();
                    existingEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    existingEffect.setE(fmeausEffectRequest.getE());
                    existingEffect.setC(fmeausEffectRequest.getC());
                    existingEffect.setO(fmeausEffectRequest.getO());
                    existingEffect.setRpn(fmeausEffectRequest.getRpn());
                    existingEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    existingEffect.setS(fmeausEffectRequest.getS());
                    existingEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    existingEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    existingEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    existingEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffectRepository.save(existingEffect);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSEffect newEffect = new FMEAUSEffect();
                    newEffect.setFmeaNumber(fmeausEffectRequest.getFmeaNumber());
                    newEffect.setParentId(cause.getCauseId()); // Set parent ID here
                    newEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    newEffect.setE(fmeausEffectRequest.getE());
                    newEffect.setC(fmeausEffectRequest.getC());
                    newEffect.setO(fmeausEffectRequest.getO());
                    newEffect.setRpn(fmeausEffectRequest.getRpn());
                    newEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    newEffect.setS(fmeausEffectRequest.getS());
                    newEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    newEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    newEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    newEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffectRepository.save(newEffect);

                    // Add the new cause to the effect's list
                    List<FMEAUSEffect> effects = cause.getFmeausEffect();
                    effects.add(newEffect);
                    cause.setFmeausEffect(effects);
                    fmeausCauseRepository.save(cause);

                }

                return new ResponseModel<>(true, "FMEAUSEffect added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSCause not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSEffect", null);
        }
    }

    public ResponseModel<String> addOrUpdateUSAction(/*String fmeaNumber,*/ AddFMEAUSActionRequest fmeausActionRequest) {
        try {
            Optional<FMEAUSEffect> effectOptional = fmeausEffectRepository.findById(fmeausActionRequest.getParentId());

            if (effectOptional.isPresent()) {
                FMEAUSEffect effect = effectOptional.get();

                // Check if the cause already exists
                Integer actionIdToAddOrUpdate = fmeausActionRequest.getActionId();
                Optional<FMEAUSAction> existingActionOptional = fmeausActionRepository.findByActionId(actionIdToAddOrUpdate);


                if (existingActionOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSAction existingActtion= existingActionOptional.get();
                    existingActtion.setMaintenance(fmeausActionRequest.getMaintenance());
                    existingActtion.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    existingActtion.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    existingActtion.setProposedAction(fmeausActionRequest.getProposedAction());
                    existingActtion.setResponsibility(fmeausActionRequest.getResponsibility());
                    existingActtion.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    existingActtion.setQuality(fmeausActionRequest.getQuality());
                    existingActtion.setInterval(fmeausActionRequest.getInterval());
                    existingActtion.setRemarks(fmeausActionRequest.getRemarks());
                    existingActtion.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    existingActtion.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());


                    fmeausActionRepository.save(existingActtion);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSAction newAction = new FMEAUSAction();
                    newAction.setFmeaNumber(fmeausActionRequest.getFmeaNumber());
                    newAction.setParentId(effect.getEffectId()); // Set parent ID here

                    newAction.setMaintenance(fmeausActionRequest.getMaintenance());
                    newAction.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    newAction.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    newAction.setProposedAction(fmeausActionRequest.getProposedAction());
                    newAction.setResponsibility(fmeausActionRequest.getResponsibility());
                    newAction.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    newAction.setQuality(fmeausActionRequest.getQuality());
                    newAction.setInterval(fmeausActionRequest.getInterval());
                    newAction.setRemarks(fmeausActionRequest.getRemarks());
                    newAction.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    newAction.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());

                    fmeausActionRepository.save(newAction);

                    // Add the new cause to the effect's list
                    List<FMEAUSAction> actions = effect.getFmeaUSActions();
                    actions.add(newAction);
                    effect.setFmeaUSActions(actions);
                    fmeausEffectRepository.save(effect);

                }

                return new ResponseModel<>(true, "FMEAUSAction added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSEffect not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSAction", null);
        }
    }

    public ResponseModel<String> validate(/*String fmeaNumber,*/ FMEAValidateRequest fmeaValidateRequest) {
        try {
            List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(fmeaValidateRequest.getFmeaNumber());
            if (!fmeaList.isEmpty()) {
                FMEA fmea = fmeaList.getFirst();
                if (fmea.getStatus() == 2) {
                    if (fmeaValidateRequest.getApproverStatus() == 1) {
                        fmea.setApproverStatus(1);
                        fmea.setStatus(4);//closed

                        fmeaRepository.save(fmea);
                        return new ResponseModel<>(true, "FMEA validated and closed successfully", null);
                    } else if (fmeaValidateRequest.getApproverStatus() == 2) {
                        fmea.setStatus(3);//revertbcak
                        fmea.setApproverStatus(2);
                        fmea.setApproverComment(fmeaValidateRequest.getApproverComment());
                        fmea.setResubmissionDate(fmeaValidateRequest.getResubmissionDate());
                        fmeaRepository.save(fmea);
                        return new ResponseModel<>(true, "FMEA status reverted back successfully", null);
                    }
                }
                return new ResponseModel<>(false, "FMEA status is not eligible for validation", null);
            } else {
                return new ResponseModel<>(false, "FMEA not found", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to validate FMEA", null);
        }
    }

    public ResponseModel<List<FMEA>> findFMEAByDateTime(String organizationCode, LocalDateTime from, LocalDateTime to, String requestPage) {
        try {
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            var results = fmeaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, from, to, pageRequest);
            List<FMEA> fmeaList = fmeaRepository.findAll();
            var totalCount = String.valueOf(fmeaList.size());
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


    //NEW code++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    public ResponseModel<String> addOrUpdateUSCause1(/*String fmeaNumber,*/ AddFMEAUSCauseRequest fmeausCauseRequest) {
        try {
            Optional<FMEAUSCause> causeOptional = fmeausCauseRepository.findByCauseId(fmeausCauseRequest.getParentId());

            if (causeOptional.isPresent()) {
                FMEAUSCause cause = causeOptional.get();

                // Check if the cause already exists
                Integer causeId1ToAddOrUpdate = fmeausCauseRequest.getCauseId();
                Optional<FMEAUSCause1> existingCause1Optional = fmeausCause1Repository.findByCauseIdOne(causeId1ToAddOrUpdate);


                if (existingCause1Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSCause1 existingCause1 = existingCause1Optional.get();
                    existingCause1.setCauseName1(fmeausCauseRequest.getCauseName());

                    fmeausCause1Repository.save(existingCause1);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSCause1 newCause1 = new FMEAUSCause1();
                    newCause1.setFmeaNumber(fmeausCauseRequest.getFmeaNumber());
                    newCause1.setParentId(cause.getCauseId()); // Set parent ID here
                    newCause1.setCauseName1(fmeausCauseRequest.getCauseName());

                    fmeausCause1Repository.save(newCause1);

                    // Add the new cause to the effect's list
                    List<FMEAUSCause1> causes1 = cause.getFmeausCause1();
                    causes1.add(newCause1);
                    cause.setFmeausCause1(causes1);
                    fmeausCauseRepository.save(cause);
                }

                return new ResponseModel<>(true, "FMEAUSCause1 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSCause not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSCause1", null);
        }
    }



   /* public ResponseModel<String> addOrUpdateUSSubCause1(String fmeaNumber, AddFMEAUSSubCauseRequest fmeausSubCauseRequest) {
        try {
            Optional<FMEAUSCause1> cause1Optional = fmeausCause1Repository.findByCauseIdOne(fmeausSubCauseRequest.getParentId());

            if (cause1Optional.isPresent()) {
                FMEAUSCause1 cause1 = cause1Optional.get();

                // Check if the cause already exists
                Integer subCauseIdToAddOrUpdate = fmeausSubCauseRequest.getSubCauseId();
                Optional<FMEAUSSubCause1> existingSubCause1Optional = fmeausSubCause1Repository.findBySubCauseIdOne(subCauseIdToAddOrUpdate);


                if (existingSubCause1Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSSubCause1 existingSubCause1 = existingSubCause1Optional.get();
                    //existingSubCauseOptional.getFirst().setSubCauseName(fmeausSubCauseRequest.getSubCauseName());
                    existingSubCause1.setSubCauseName1(fmeausSubCauseRequest.getSubCauseName());

                    fmeausSubCause1Repository.save(existingSubCause1);
                    //fmeausSubCauseRepository.save(existingSubCauseOptional.getFirst());
                } else {
                    List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(fmeaNumber);
                    if (!fmeaList.isEmpty()) {
                        FMEA fmea = fmeaList.getFirst();
                        fmea.setStatus(2);
                        fmeaRepository.save(fmea);
                    }
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSSubCause1 newSubCause = new FMEAUSSubCause1();
                    newSubCause.setFmeaNumber(fmeaNumber);
                    newSubCause.setParentId(cause1.getCauseIdOne()); // Set parent ID here
                    newSubCause.setSubCauseName1(fmeausSubCauseRequest.getSubCauseName());

                    fmeausSubCause1Repository.save(newSubCause);

                    // Add the new cause to the effect's list
                    List<FMEAUSSubCause1> subCauses1 = cause1.getFmeausSubCause1();
                    subCauses1.add(newSubCause);
                    cause1.setFmeausSubCause1(subCauses1);
                    fmeausCause1Repository.save(cause1);
                }

                return new ResponseModel<>(true, "FMEADSSubCauseEffect added or updated and associated with FMEAUSFailure(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAEffect not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEADSCause", null);
        }
    }
*/

    public ResponseModel<String> addOrUpdateUSEffect1(/*String fmeaNumber,*/ AddFMEAUSEffectRequest fmeausEffectRequest) {
        try {
            Optional<FMEAUSCause1> causeOptional1 = fmeausCause1Repository.findByCauseIdOne(fmeausEffectRequest.getParentId());

            if (causeOptional1.isPresent()) {
                FMEAUSCause1 cause1 = causeOptional1.get();

                // Check if the cause already exists
                Integer effectIdToAddOrUpdate = fmeausEffectRequest.getEffectId();
                Optional<FMEAUSEffect1> existingEffectOptional = fmeausEffect1Repository.findByEffectIdOne(effectIdToAddOrUpdate);


                if (existingEffectOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSEffect1 existingEffect= existingEffectOptional.get();
                    existingEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    existingEffect.setE(fmeausEffectRequest.getE());
                    existingEffect.setC(fmeausEffectRequest.getC());
                    existingEffect.setO(fmeausEffectRequest.getO());
                    existingEffect.setRpn(fmeausEffectRequest.getRpn());
                    existingEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    existingEffect.setS(fmeausEffectRequest.getS());
                    existingEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    existingEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    existingEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    existingEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffect1Repository.save(existingEffect);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSEffect1 newEffect = new FMEAUSEffect1();
                    newEffect.setFmeaNumber(fmeausEffectRequest.getFmeaNumber());
                    newEffect.setParentId(cause1.getCauseIdOne()); // Set parent ID here
                    newEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    newEffect.setE(fmeausEffectRequest.getE());
                    newEffect.setC(fmeausEffectRequest.getC());
                    newEffect.setO(fmeausEffectRequest.getO());
                    newEffect.setRpn(fmeausEffectRequest.getRpn());
                    newEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    newEffect.setS(fmeausEffectRequest.getS());
                    newEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    newEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    newEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    newEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffect1Repository.save(newEffect);

                    // Add the new cause to the effect's list
                    List<FMEAUSEffect1> effects = cause1.getFmeausEffect1();
                    effects.add(newEffect);
                    cause1.setFmeausEffect1(effects);
                    fmeausCause1Repository.save(cause1);

                }

                return new ResponseModel<>(true, "FMEAUSEffect1 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSCause1 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSEffect1", null);
        }
    }


    public ResponseModel<String> addOrUpdateUSAction1(/*String fmeaNumber,*/ AddFMEAUSActionRequest fmeausActionRequest) {
        try {
            Optional<FMEAUSEffect1> effectOptional1 = fmeausEffect1Repository.findByEffectIdOne(fmeausActionRequest.getParentId());

            if (effectOptional1.isPresent()) {
                FMEAUSEffect1 effect1 = effectOptional1.get();

                // Check if the cause already exists
                Integer actionIdToAddOrUpdate = fmeausActionRequest.getActionId();
                Optional<FMEAUSAction1> existingActionOptional = fmeausAction1Repository.findByActionIdOne(actionIdToAddOrUpdate);


                if (existingActionOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSAction1 existingActtion= existingActionOptional.get();
                    existingActtion.setMaintenance(fmeausActionRequest.getMaintenance());
                    existingActtion.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    existingActtion.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    existingActtion.setProposedAction(fmeausActionRequest.getProposedAction());
                    existingActtion.setResponsibility(fmeausActionRequest.getResponsibility());
                    existingActtion.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    existingActtion.setQuality(fmeausActionRequest.getQuality());
                    existingActtion.setInterval(fmeausActionRequest.getInterval());
                    existingActtion.setRemarks(fmeausActionRequest.getRemarks());
                    existingActtion.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    existingActtion.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());


                    fmeausAction1Repository.save(existingActtion);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSAction1 newAction = new FMEAUSAction1();
                    newAction.setFmeaNumber(fmeausActionRequest.getFmeaNumber());
                    newAction.setParentId(effect1.getEffectIdOne()); // Set parent ID here

                    newAction.setMaintenance(fmeausActionRequest.getMaintenance());
                    newAction.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    newAction.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    newAction.setProposedAction(fmeausActionRequest.getProposedAction());
                    newAction.setResponsibility(fmeausActionRequest.getResponsibility());
                    newAction.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    newAction.setQuality(fmeausActionRequest.getQuality());
                    newAction.setInterval(fmeausActionRequest.getInterval());
                    newAction.setRemarks(fmeausActionRequest.getRemarks());
                    newAction.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    newAction.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());

                    fmeausAction1Repository.save(newAction);

                    // Add the new cause to the effect's list
                    List<FMEAUSAction1> actions = effect1.getFmeausActions1();
                    actions.add(newAction);
                    effect1.setFmeausActions1(actions);
                    fmeausEffect1Repository.save(effect1);

                }

                return new ResponseModel<>(true, "FMEAUSAction1 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSEffect1 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSAction1", null);
        }
    }

    /* ********************************CAUSE2***************************************/


    public ResponseModel<String> addOrUpdateUSCause2(/*String fmeaNumber,*/ AddFMEAUSCauseRequest fmeausCauseRequest) {
        try {
            Optional<FMEAUSCause1> causeOptional1 = fmeausCause1Repository.findByCauseIdOne(fmeausCauseRequest.getParentId());

            if (causeOptional1.isPresent()) {
                FMEAUSCause1 cause1 = causeOptional1.get();

                // Check if the cause already exists
                Integer causeId2ToAddOrUpdate = fmeausCauseRequest.getCauseId();
                Optional<FMEAUSCause2> existingCause2Optional = fmeausCause2Repository.findByCauseIdTwo(causeId2ToAddOrUpdate);


                if (existingCause2Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSCause2 existingCause2 = existingCause2Optional.get();
                    existingCause2.setCauseName2(fmeausCauseRequest.getCauseName());

                    fmeausCause2Repository.save(existingCause2);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSCause2 newCause2 = new FMEAUSCause2();
                    newCause2.setFmeaNumber(fmeausCauseRequest.getFmeaNumber());
                    newCause2.setParentId(cause1.getCauseIdOne()); // Set parent ID here
                    newCause2.setCauseName2(fmeausCauseRequest.getCauseName());

                    fmeausCause2Repository.save(newCause2);

                    // Add the new cause to the effect's list
                    List<FMEAUSCause2> causes2 = cause1.getFmeausCauses2();
                    causes2.add(newCause2);
                    cause1.setFmeausCauses2(causes2);
                    fmeausCause1Repository.save(cause1);
                }

                return new ResponseModel<>(true, "FMEAUSCause2 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSCause1 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSCause2", null);
        }
    }



   /* public ResponseModel<String> addOrUpdateUSSubCause2(String fmeaNumber, AddFMEAUSSubCauseRequest fmeausSubCauseRequest) {
        try {
            Optional<FMEAUSCause2> cause2Optional = fmeausCause2Repository.findByCauseIdTwo(fmeausSubCauseRequest.getParentId());

            if (cause2Optional.isPresent()) {
                FMEAUSCause2 cause2 = cause2Optional.get();

                // Check if the cause already exists
                Integer subCauseIdTwoToAddOrUpdate = fmeausSubCauseRequest.getSubCauseId();
                Optional<FMEAUSSubCause2> existingSubCause2Optional = fmeausSubCause2Repository.findBySubCauseIdTwo(subCauseIdTwoToAddOrUpdate);


                if (existingSubCause2Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSSubCause2 existingSubCause2 = existingSubCause2Optional.get();
                    //existingSubCauseOptional.getFirst().setSubCauseName(fmeausSubCauseRequest.getSubCauseName());
                    existingSubCause2.setSubCauseName2(fmeausSubCauseRequest.getSubCauseName());

                    fmeausSubCause2Repository.save(existingSubCause2);
                    //fmeausSubCauseRepository.save(existingSubCauseOptional.getFirst());
                } else {
                    List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(fmeaNumber);
                    if (!fmeaList.isEmpty()) {
                        FMEA fmea = fmeaList.getFirst();
                        fmea.setStatus(2);
                        fmeaRepository.save(fmea);
                    }
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSSubCause2 newSubCause = new FMEAUSSubCause2();
                    newSubCause.setFmeaNumber(fmeaNumber);
                    newSubCause.setParentId(cause2.getCauseIdTwo()); // Set parent ID here
                    newSubCause.setSubCauseName2(fmeausSubCauseRequest.getSubCauseName());

                    fmeausSubCause2Repository.save(newSubCause);

                    // Add the new cause to the effect's list
                    List<FMEAUSSubCause2> subCauses2 = cause2.getFmeausSubCause2();
                    subCauses2.add(newSubCause);
                    cause2.setFmeausSubCause2(subCauses2);
                    fmeausCause2Repository.save(cause2);
                }

                return new ResponseModel<>(true, "FMEADSSubCauseEffect added or updated and associated with FMEAUSFailure(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAEffect not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEADSCause", null);
        }
    }*/


    public ResponseModel<String> addOrUpdateUSEffect2(/*String fmeaNumber,*/ AddFMEAUSEffectRequest fmeausEffectRequest) {
        try {
            Optional<FMEAUSCause2> causeOptional2 = fmeausCause2Repository.findByCauseIdTwo(fmeausEffectRequest.getParentId());

            if (causeOptional2.isPresent()) {
                FMEAUSCause2 cause2 = causeOptional2.get();

                // Check if the cause already exists
                Integer effectIdTwoToAddOrUpdate = fmeausEffectRequest.getEffectId();
                Optional<FMEAUSEffect2> existingEffect2Optional = fmeausEffect2Repository.findByEffectIdTwo(effectIdTwoToAddOrUpdate);


                if (existingEffect2Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSEffect2 existingEffect= existingEffect2Optional.get();
                    existingEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    existingEffect.setE(fmeausEffectRequest.getE());
                    existingEffect.setC(fmeausEffectRequest.getC());
                    existingEffect.setO(fmeausEffectRequest.getO());
                    existingEffect.setRpn(fmeausEffectRequest.getRpn());
                    existingEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    existingEffect.setS(fmeausEffectRequest.getS());
                    existingEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    existingEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    existingEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    existingEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffect2Repository.save(existingEffect);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSEffect2 newEffect = new FMEAUSEffect2();
                    newEffect.setFmeaNumber(fmeausEffectRequest.getFmeaNumber());
                    newEffect.setParentId(cause2.getCauseIdTwo()); // Set parent ID here
                    newEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    newEffect.setE(fmeausEffectRequest.getE());
                    newEffect.setC(fmeausEffectRequest.getC());
                    newEffect.setO(fmeausEffectRequest.getO());
                    newEffect.setRpn(fmeausEffectRequest.getRpn());
                    newEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    newEffect.setS(fmeausEffectRequest.getS());
                    newEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    newEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    newEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    newEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffect2Repository.save(newEffect);

                    // Add the new cause to the effect's list
                    List<FMEAUSEffect2> effects = cause2.getFmeausEffect2();
                    effects.add(newEffect);
                    cause2.setFmeausEffect2(effects);
                    fmeausCause2Repository.save(cause2);

                }

                return new ResponseModel<>(true, "FMEAUSEffect2 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSCause2 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSEffect2", null);
        }
    }


    public ResponseModel<String> addOrUpdateUSAction2(/*String fmeaNumber,*/ AddFMEAUSActionRequest fmeausActionRequest) {
        try {
            Optional<FMEAUSEffect2> effectOptional2 = fmeausEffect2Repository.findByEffectIdTwo(fmeausActionRequest.getParentId());

            if (effectOptional2.isPresent()) {
                FMEAUSEffect2 effect2 = effectOptional2.get();

                // Check if the cause already exists
                Integer actionIdTwoToAddOrUpdate = fmeausActionRequest.getActionId();
                Optional<FMEAUSAction2> existingActionOptional = fmeausAction2Repository.findByActionIdTwo(actionIdTwoToAddOrUpdate);


                if (existingActionOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSAction2 existingActtion= existingActionOptional.get();
                    existingActtion.setMaintenance(fmeausActionRequest.getMaintenance());
                    existingActtion.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    existingActtion.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    existingActtion.setProposedAction(fmeausActionRequest.getProposedAction());
                    existingActtion.setResponsibility(fmeausActionRequest.getResponsibility());
                    existingActtion.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    existingActtion.setQuality(fmeausActionRequest.getQuality());
                    existingActtion.setInterval(fmeausActionRequest.getInterval());
                    existingActtion.setRemarks(fmeausActionRequest.getRemarks());
                    existingActtion.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    existingActtion.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());


                    fmeausAction2Repository.save(existingActtion);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSAction2 newAction = new FMEAUSAction2();
                    newAction.setFmeaNumber(fmeausActionRequest.getFmeaNumber());
                    newAction.setParentId(effect2.getEffectIdTwo()); // Set parent ID here

                    newAction.setMaintenance(fmeausActionRequest.getMaintenance());
                    newAction.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    newAction.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    newAction.setProposedAction(fmeausActionRequest.getProposedAction());
                    newAction.setResponsibility(fmeausActionRequest.getResponsibility());
                    newAction.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    newAction.setQuality(fmeausActionRequest.getQuality());
                    newAction.setInterval(fmeausActionRequest.getInterval());
                    newAction.setRemarks(fmeausActionRequest.getRemarks());
                    newAction.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    newAction.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());

                    fmeausAction2Repository.save(newAction);

                    // Add the new cause to the effect's list
                    List<FMEAUSAction2> actions = effect2.getFmeausActions2();
                    actions.add(newAction);
                    effect2.setFmeausActions2(actions);
                    fmeausEffect2Repository.save(effect2);

                }

                return new ResponseModel<>(true, "FMEAUSAction2 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSEffect2 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSAction2", null);
        }
    }



    /* ********************************CAUSE3***************************************/


    public ResponseModel<String> addOrUpdateUSCause3(/*String fmeaNumber,*/ AddFMEAUSCauseRequest fmeausCauseRequest) {
        try {
            Optional<FMEAUSCause2> causeOptional2 = fmeausCause2Repository.findByCauseIdTwo(fmeausCauseRequest.getParentId());

            if (causeOptional2.isPresent()) {
                FMEAUSCause2 cause2 = causeOptional2.get();

                // Check if the cause already exists
                Integer causeId3ToAddOrUpdate = fmeausCauseRequest.getCauseId();
                Optional<FMEAUSCause3> existingCause3Optional = fmeausCause3Repository.findByCauseIdThree(causeId3ToAddOrUpdate);


                if (existingCause3Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSCause3 existingCause3 = existingCause3Optional.get();
                    existingCause3.setCauseName3(fmeausCauseRequest.getCauseName());

                    fmeausCause3Repository.save(existingCause3);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSCause3 newCause3 = new FMEAUSCause3();
                    newCause3.setFmeaNumber(fmeausCauseRequest.getFmeaNumber());
                    newCause3.setParentId(cause2.getCauseIdTwo()); // Set parent ID here
                    newCause3.setCauseName3(fmeausCauseRequest.getCauseName());

                    fmeausCause3Repository.save(newCause3);

                    // Add the new cause to the effect's list
                    List<FMEAUSCause3> causes3 = cause2.getFmeausCauses3();
                    causes3.add(newCause3);
                    cause2.setFmeausCauses3(causes3);
                    fmeausCause2Repository.save(cause2);
                }

                return new ResponseModel<>(true, "FMEAUSCause3 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSCause2 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSCause3", null);
        }
    }



  /*  public ResponseModel<String> addOrUpdateUSSubCause3(String fmeaNumber, AddFMEAUSSubCauseRequest fmeausSubCauseRequest) {
        try {
            Optional<FMEAUSCause3> cause3Optional = fmeausCause3Repository.findByCauseIdThree(fmeausSubCauseRequest.getParentId());

            if (cause3Optional.isPresent()) {
                FMEAUSCause3 cause3 = cause3Optional.get();

                // Check if the cause already exists
                Integer subCauseIdThreeToAddOrUpdate = fmeausSubCauseRequest.getSubCauseId();
                Optional<FMEAUSSubCause3> existingSubCause3Optional = fmeausSubCause3Repository.findBySubCauseIdThree(subCauseIdThreeToAddOrUpdate);


                if (existingSubCause3Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSSubCause3 existingSubCause3 = existingSubCause3Optional.get();
                    //existingSubCauseOptional.getFirst().setSubCauseName(fmeausSubCauseRequest.getSubCauseName());
                    existingSubCause3.setSubCauseName3(fmeausSubCauseRequest.getSubCauseName());

                    fmeausSubCause3Repository.save(existingSubCause3);
                    //fmeausSubCauseRepository.save(existingSubCauseOptional.getFirst());
                } else {
                    List<FMEA> fmeaList = fmeaRepository.findByFmeaNumber(fmeaNumber);
                    if (!fmeaList.isEmpty()) {
                        FMEA fmea = fmeaList.getFirst();
                        fmea.setStatus(2);
                        fmeaRepository.save(fmea);
                    }
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSSubCause3 newSubCause = new FMEAUSSubCause3();
                    newSubCause.setFmeaNumber(fmeaNumber);
                    newSubCause.setParentId(cause3.getCauseIdThree()); // Set parent ID here
                    newSubCause.setSubCauseName3(fmeausSubCauseRequest.getSubCauseName());

                    fmeausSubCause3Repository.save(newSubCause);

                    // Add the new cause to the effect's list
                    List<FMEAUSSubCause3> subCauses3 = cause3.getFmeausSubCause3();
                    subCauses3.add(newSubCause);
                    cause3.setFmeausSubCause3(subCauses3);
                    fmeausCause3Repository.save(cause3);
                }

                return new ResponseModel<>(true, "FMEADSSubCauseEffect3 added or updated and associated with FMEAUSFailure(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAEffect not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEADSCause", null);
        }
    }*/


    public ResponseModel<String> addOrUpdateUSEffect3(/*String fmeaNumber,*/ AddFMEAUSEffectRequest fmeausEffectRequest) {
        try {
            Optional<FMEAUSCause3> causeOptional3 = fmeausCause3Repository.findByCauseIdThree(fmeausEffectRequest.getParentId());

            if (causeOptional3.isPresent()) {
                FMEAUSCause3 cause3 = causeOptional3.get();

                // Check if the cause already exists
                Integer effectIdThreeToAddOrUpdate = fmeausEffectRequest.getEffectId();
                Optional<FMEAUSEffect3> existingEffect3Optional = fmeausEffect3Repository.findByEffectIdThree(effectIdThreeToAddOrUpdate);


                if (existingEffect3Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSEffect3 existingEffect= existingEffect3Optional.get();
                    existingEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    existingEffect.setE(fmeausEffectRequest.getE());
                    existingEffect.setC(fmeausEffectRequest.getC());
                    existingEffect.setO(fmeausEffectRequest.getO());
                    existingEffect.setRpn(fmeausEffectRequest.getRpn());
                    existingEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    existingEffect.setS(fmeausEffectRequest.getS());
                    existingEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    existingEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    existingEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    existingEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffect3Repository.save(existingEffect);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSEffect3 newEffect = new FMEAUSEffect3();
                    newEffect.setFmeaNumber(fmeausEffectRequest.getFmeaNumber());
                    newEffect.setParentId(cause3.getCauseIdThree()); // Set parent ID here
                    newEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    newEffect.setE(fmeausEffectRequest.getE());
                    newEffect.setC(fmeausEffectRequest.getC());
                    newEffect.setO(fmeausEffectRequest.getO());
                    newEffect.setRpn(fmeausEffectRequest.getRpn());
                    newEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    newEffect.setS(fmeausEffectRequest.getS());
                    newEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    newEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    newEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    newEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffect3Repository.save(newEffect);

                    // Add the new cause to the effect's list
                    List<FMEAUSEffect3> effects = cause3.getFmeausEffect3();
                    effects.add(newEffect);
                    cause3.setFmeausEffect3(effects);
                    fmeausCause3Repository.save(cause3);

                }

                return new ResponseModel<>(true, "FMEAUSEffect3 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSCause3 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSEffect3", null);
        }
    }


    public ResponseModel<String> addOrUpdateUSAction3(/*String fmeaNumber,*/ AddFMEAUSActionRequest fmeausActionRequest) {
        try {
            Optional<FMEAUSEffect3> effectOptional3 = fmeausEffect3Repository.findByEffectIdThree(fmeausActionRequest.getParentId());

            if (effectOptional3.isPresent()) {
                FMEAUSEffect3 effect3 = effectOptional3.get();

                // Check if the cause already exists
                Integer actionIdTwoToAddOrUpdate = fmeausActionRequest.getActionId();
                Optional<FMEAUSAction3> existingActionOptional = fmeausAction3Repository.findByActionIdThree(actionIdTwoToAddOrUpdate);


                if (existingActionOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSAction3 existingActtion= existingActionOptional.get();
                    existingActtion.setMaintenance(fmeausActionRequest.getMaintenance());
                    existingActtion.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    existingActtion.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    existingActtion.setProposedAction(fmeausActionRequest.getProposedAction());
                    existingActtion.setResponsibility(fmeausActionRequest.getResponsibility());
                    existingActtion.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    existingActtion.setQuality(fmeausActionRequest.getQuality());
                    existingActtion.setInterval(fmeausActionRequest.getInterval());
                    existingActtion.setRemarks(fmeausActionRequest.getRemarks());
                    existingActtion.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    existingActtion.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());


                    fmeausAction3Repository.save(existingActtion);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSAction3 newAction = new FMEAUSAction3();
                    newAction.setFmeaNumber(fmeausActionRequest.getFmeaNumber());
                    newAction.setParentId(effect3.getEffectIdThree()); // Set parent ID here

                    newAction.setMaintenance(fmeausActionRequest.getMaintenance());
                    newAction.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    newAction.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    newAction.setProposedAction(fmeausActionRequest.getProposedAction());
                    newAction.setResponsibility(fmeausActionRequest.getResponsibility());
                    newAction.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    newAction.setQuality(fmeausActionRequest.getQuality());
                    newAction.setInterval(fmeausActionRequest.getInterval());
                    newAction.setRemarks(fmeausActionRequest.getRemarks());
                    newAction.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    newAction.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());

                    fmeausAction3Repository.save(newAction);

                    // Add the new cause to the effect's list
                    List<FMEAUSAction3> actions = effect3.getFmeausActions3();
                    actions.add(newAction);
                    effect3.setFmeausActions3(actions);
                    fmeausEffect3Repository.save(effect3);

                }

                return new ResponseModel<>(true, "FMEAUSAction3 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSEffect3 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSAction3", null);
        }
    }

    /* ********************************CAUSE4***************************************/


    public ResponseModel<String> addOrUpdateUSCause4(/*String fmeaNumber,*/ AddFMEAUSCauseRequest fmeausCauseRequest) {
        try {
            Optional<FMEAUSCause3> causeOptional3 = fmeausCause3Repository.findByCauseIdThree(fmeausCauseRequest.getParentId());

            if (causeOptional3.isPresent()) {
                FMEAUSCause3 cause3 = causeOptional3.get();

                // Check if the cause already exists
                Integer causeId4ToAddOrUpdate = fmeausCauseRequest.getCauseId();
                Optional<FMEAUSCause4> existingCause4Optional = fmeausCause4Repository.findByCauseIdFour(causeId4ToAddOrUpdate);


                if (existingCause4Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSCause4 existingCause4 = existingCause4Optional.get();
                    existingCause4.setCauseName4(fmeausCauseRequest.getCauseName());

                    fmeausCause4Repository.save(existingCause4);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSCause4 newCause4 = new FMEAUSCause4();
                    newCause4.setFmeaNumber(fmeausCauseRequest.getFmeaNumber());
                    newCause4.setParentId(cause3.getCauseIdThree()); // Set parent ID here
                    newCause4.setCauseName4(fmeausCauseRequest.getCauseName());

                    fmeausCause4Repository.save(newCause4);

                    // Add the new cause to the effect's list
                    List<FMEAUSCause4> causes4 = cause3.getFmeausCauses4();
                    causes4.add(newCause4);
                    cause3.setFmeausCauses4(causes4);
                    fmeausCause3Repository.save(cause3);
                }

                return new ResponseModel<>(true, "FMEAUSCause4 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSCause3 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSCause4", null);
        }
    }

    public ResponseModel<String> addOrUpdateUSEffect4(/*String fmeaNumber,*/ AddFMEAUSEffectRequest fmeausEffectRequest) {
        try {
            Optional<FMEAUSCause4> causeOptional4 = fmeausCause4Repository.findByCauseIdFour(fmeausEffectRequest.getParentId());

            if (causeOptional4.isPresent()) {
                FMEAUSCause4 cause4 = causeOptional4.get();

                // Check if the cause already exists
                Integer effectIdFourToAddOrUpdate = fmeausEffectRequest.getEffectId();
                Optional<FMEAUSEffect4> existingEffect4Optional = fmeausEffect4Repository.findByEffectIdFour(effectIdFourToAddOrUpdate);


                if (existingEffect4Optional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSEffect4 existingEffect= existingEffect4Optional.get();
                    existingEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    existingEffect.setE(fmeausEffectRequest.getE());
                    existingEffect.setC(fmeausEffectRequest.getC());
                    existingEffect.setO(fmeausEffectRequest.getO());
                    existingEffect.setRpn(fmeausEffectRequest.getRpn());
                    existingEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    existingEffect.setS(fmeausEffectRequest.getS());
                    existingEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    existingEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    existingEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    existingEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffect4Repository.save(existingEffect);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSEffect4 newEffect = new FMEAUSEffect4();
                    newEffect.setFmeaNumber(fmeausEffectRequest.getFmeaNumber());
                    newEffect.setParentId(cause4.getCauseIdFour()); // Set parent ID here
                    newEffect.setFunctionEffect(fmeausEffectRequest.getFunctionEffect());
                    newEffect.setE(fmeausEffectRequest.getE());
                    newEffect.setC(fmeausEffectRequest.getC());
                    newEffect.setO(fmeausEffectRequest.getO());
                    newEffect.setRpn(fmeausEffectRequest.getRpn());
                    newEffect.setOccurrence(fmeausEffectRequest.getOccurrence());
                    newEffect.setS(fmeausEffectRequest.getS());
                    newEffect.setSeverity(fmeausEffectRequest.getSeverity());
                    newEffect.setRiskPriority(fmeausEffectRequest.getRiskPriority());
                    newEffect.setDetectionIndex(fmeausEffectRequest.getDetectionIndex());
                    newEffect.setDetectFailure(fmeausEffectRequest.getDetectFailure());


                    fmeausEffect4Repository.save(newEffect);

                    // Add the new cause to the effect's list
                    List<FMEAUSEffect4> effects = cause4.getFmeausEffect4();
                    effects.add(newEffect);
                    cause4.setFmeausEffect4(effects);
                    fmeausCause4Repository.save(cause4);

                }

                return new ResponseModel<>(true, "FMEAUSEffect4 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSCause4 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSEffect4", null);
        }
    }

    public ResponseModel<String> addOrUpdateUSAction4(/*String fmeaNumber,*/ AddFMEAUSActionRequest fmeausActionRequest) {
        try {
            Optional<FMEAUSEffect4> effectOptional4 = fmeausEffect4Repository.findByEffectIdFour(fmeausActionRequest.getParentId());

            if (effectOptional4.isPresent()) {
                FMEAUSEffect4 effect4 = effectOptional4.get();

                // Check if the cause already exists
                Integer actionIdThreeToAddOrUpdate = fmeausActionRequest.getActionId();
                Optional<FMEAUSAction4> existingActionOptional = fmeausAction4Repository.findByActionIdFour(actionIdThreeToAddOrUpdate);


                if (existingActionOptional.isPresent()) {
                    // If cause already exists, update it
                    FMEAUSAction4 existingActtion= existingActionOptional.get();
                    existingActtion.setMaintenance(fmeausActionRequest.getMaintenance());
                    existingActtion.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    existingActtion.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    existingActtion.setProposedAction(fmeausActionRequest.getProposedAction());
                    existingActtion.setResponsibility(fmeausActionRequest.getResponsibility());
                    existingActtion.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    existingActtion.setQuality(fmeausActionRequest.getQuality());
                    existingActtion.setInterval(fmeausActionRequest.getInterval());
                    existingActtion.setRemarks(fmeausActionRequest.getRemarks());
                    existingActtion.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    existingActtion.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());


                    fmeausAction4Repository.save(existingActtion);
                } else {
                    // If cause does not exist, create a new one and set its parent ID
                    FMEAUSAction4 newAction = new FMEAUSAction4();
                    newAction.setFmeaNumber(fmeausActionRequest.getFmeaNumber());
                    newAction.setParentId(effect4.getEffectIdFour()); // Set parent ID here

                    newAction.setMaintenance(fmeausActionRequest.getMaintenance());
                    newAction.setAssetNumber(fmeausActionRequest.getAssetNumber());
                    newAction.setSuggestedMaintenance(fmeausActionRequest.getSuggestedMaintenance());
                    newAction.setProposedAction(fmeausActionRequest.getProposedAction());
                    newAction.setResponsibility(fmeausActionRequest.getResponsibility());
                    newAction.setSparesRequired(fmeausActionRequest.getSparesRequired());
                    newAction.setQuality(fmeausActionRequest.getQuality());
                    newAction.setInterval(fmeausActionRequest.getInterval());
                    newAction.setRemarks(fmeausActionRequest.getRemarks());
                    newAction.setScheduledStartDate(fmeausActionRequest.getScheduledStartDate());
                    newAction.setScheduledEndDate(fmeausActionRequest.getScheduledEndDate());

                    fmeausAction4Repository.save(newAction);

                    // Add the new cause to the effect's list
                    List<FMEAUSAction4> actions = effect4.getFmeausActions4();
                    actions.add(newAction);
                    effect4.setFmeausActions4(actions);
                    fmeausEffect4Repository.save(effect4);

                }

                return new ResponseModel<>(true, "FMEAUSAction4 added or updated and associated with FMEA(s)", null);
            } else {
                return new ResponseModel<>(false, "FMEAUSEffect4 not found for the provided parentId", null);
            }

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add or update FMEAUSAction4", null);
        }
    }

    public ResponseModel<List<FMEA>> reportLists(String department, String area, FilterType filterType, String organizationCode, String requestPage, LocalDate startDate, LocalDate endDate) {
        try {

            Page<FMEA> results;
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
                            fmeaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    fmeaRepository.findByDepartmentAndCreatedOnBetween(department, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            fmeaRepository.findByAreaAndCreatedOnBetween(area, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest) :
                                            fmeaRepository.findByCreatedOnBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59),pageRequest)));
                    break;
                case WEEKLY:
                    LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                    results = organizationCode != null ?
                            fmeaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    fmeaRepository.findByDepartmentAndCreatedOnBetween(department, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            fmeaRepository.findByAreaAndCreatedOnBetween(area, startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest) :
                                            fmeaRepository.findByCreatedOnBetween(startOfWeek.atStartOfDay(), endOfWeek.atTime(23, 59, 59),pageRequest)));
                    break;
                case MONTHLY:
                    LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
                    LocalDate endOfMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
                    results = organizationCode != null ?
                            fmeaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    fmeaRepository.findByDepartmentAndCreatedOnBetween(department, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            fmeaRepository.findByAreaAndCreatedOnBetween(area, startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest) :
                                            fmeaRepository.findByCreatedOnBetween(startOfMonth.atStartOfDay(), endOfMonth.atTime(23, 59, 59),pageRequest)));
                    break;
                case QUARTERLY:
                    LocalDate startOfQuarter = LocalDate.now().withMonth(((LocalDate.now().getMonthValue() - 1) / 3) * 3 + 1).withDayOfMonth(1);
                    LocalDate endOfQuarter = startOfQuarter.plusMonths(3).minusDays(1);


                    results = organizationCode != null ?
                            fmeaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    fmeaRepository.findByDepartmentAndCreatedOnBetween(department, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59), pageRequest) :
                                    (area != null ?
                                            fmeaRepository.findByAreaAndCreatedOnBetween(area, startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest) :
                                            fmeaRepository.findByCreatedOnBetween(startOfQuarter.atStartOfDay(), endOfQuarter.atTime(23, 59, 59),pageRequest)));
                    break;
                case YEARLY:
                    LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
                    LocalDate endOfYear = startOfYear.plusYears(1).minusDays(1);
                    results = organizationCode != null ?
                            fmeaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    fmeaRepository.findByDepartmentAndCreatedOnBetween(department, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            fmeaRepository.findByAreaAndCreatedOnBetween(area, startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest) :
                                            fmeaRepository.findByCreatedOnBetween(startOfYear.atStartOfDay(), endOfYear.atTime(23, 59, 59),pageRequest)));
                    break;
                case CUSTOM:
                    results = organizationCode != null ?
                            fmeaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                            (department != null ?
                                    fmeaRepository.findByDepartmentAndCreatedOnBetween(department, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                                    (area != null ?
                                            fmeaRepository.findByAreaAndCreatedOnBetween(area, startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest) :
                                            fmeaRepository.findByCreatedOnBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59),pageRequest)));
                    break;

                default:

                    return new ResponseModel<>(false, "Invalid filter type", null);
            }

            List<FMEA> fmeaList = fmeaRepository.findAll();
            var totalCount = String.valueOf(fmeaList.size());
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


    public ResponseModel<List<FMEA>> findPending(String username, String organizationCode) {
        UserInfo userInfo = userInfoRepository.findByUserNameAndOrganizationCode(username, organizationCode);
        boolean isOperator = "Operator".equals(userInfo.getRole());

        if (isOperator) {
            List<FMEA> pendingFMEAs = fmeaRepository.findPending(username,organizationCode);
            return new ResponseModel<>(true,"Operator pending List",pendingFMEAs);
            //return rcaRepository.findPending(username,organizationCode);
        } else {
            List<FMEA> result = fmeaRepository.findByUsernameAndOrganizationCodeWithPendingStatus(username, organizationCode);
            return new ResponseModel<>(true,"User Pending List", result);
            //return result;
        }
    }

}

