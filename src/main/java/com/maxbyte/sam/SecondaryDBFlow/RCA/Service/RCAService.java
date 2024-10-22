package com.maxbyte.sam.SecondaryDBFlow.RCA.Service;


import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.APIRequest.ValidateRequest;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.RCA.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Repository.*;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Specification.RCASpecificationBuilder;
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

@Service
@EnableScheduling
public class RCAService extends CrudService<RCA,Integer> {

    @Autowired
    private RCARepository rcaRepository;

    @Autowired
    private RCAStepOneRepository rcaStepOneRepository;
    @Autowired
    private RCAStepOneMembersRepository rcaStepOneMembersRepository;
    @Autowired
    private RCAStepTwoRepository rcaStepTwoRepository;
    @Autowired
    private RCAIsNotQuestionRepository rcaIsNotQuestionRepository;

    @Autowired
    private RCAStepThreeRepository rcaStepThreeRepository;
    @Autowired
    private RCAStepThreeQuestionsRepository rcaStepThreeQuestionsRepository;
    @Autowired
    private RCAStepFourRepository rcaStepFourRepository;
    @Autowired
    private RCAStepFourCARepository rcaStepFourCARepository;

    @Autowired
    private RCAStepFiveRepository rcaStepFiveRepository;
    @Autowired
    private RCAStepFiveDCARepository rcaStepFiveDCARepository;

    @Autowired
    private RCAStepSixRepository rcaStepSixRepository;
    @Autowired
    private RCAStepSixCauseRepository rcaStepSixCauseRepository;
    @Autowired
    private RCAStepSevenRepository rcaStepSevenRepository;
    @Autowired
    private RCAStepSevenCARepository rcaStepSevenCARepository;
    @Autowired
    private RCAStepSevenPARepository rcaStepSevenPARepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;

    @Value("${pagination.default-size}")
    private int defaultSize;

    @Autowired
    private Environment environment;

    @Override
    public CrudRepository repository() {
        return this.rcaRepository;
    }

    @Override
    public void validateAdd(RCA data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(RCA data) {
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

    public ResponseModel<List<RCA>> findRCAByDateTime(String orgCode, LocalDateTime from, LocalDateTime to, String requestPage) {
        try {
            //var dateTimeFilterList = rcaRepository.findByOrganizationCodeAndCreatedOnBetween(orgCode, from, to);
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<RCA> results = rcaRepository.findByOrganizationCodeAndCreatedOnBetween(orgCode, from, to, pageRequest);
            List<RCA> rcaList = rcaRepository.findAll();
            var totalCount = String.valueOf(rcaList.size());
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


    public ResponseModel<List<RCA>> list(Boolean isActive, String organizationCode, String assetNumber, String assetDescription,
                                         String department, String documentNumber, String woNumber, String requestPage) {
        try {
            RCASpecificationBuilder builder = new RCASpecificationBuilder();
            if(isActive!=null)builder.with("isActive",":",isActive);
            if(organizationCode!=null)builder.with("organizationCode","==",organizationCode);
            if(assetNumber!=null)builder.with("assetNumber","==",assetNumber);
            if(assetDescription!=null)builder.with("assetDescription","==",assetDescription);
            if(department!=null)builder.with("department","==",department);
            if(documentNumber!=null)builder.with("rcaNumber","==",documentNumber);
            if(woNumber!=null)builder.with("woNumber","==",woNumber);

            var pageRequestCount = 0;

            if(requestPage.matches(".*\\d.*")){
                pageRequestCount = Integer.parseInt(requestPage);
            }else{
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<RCA> results = rcaRepository.findAll(builder.build(), pageRequest);
            List<RCA> rcaList = rcaRepository.findAll();
            var totalCount = String.valueOf(rcaList.size());
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

    public ResponseModel<String> addRCA(AddRCARequest rcaRequest){
        try {
            List<RCA> rcaList = rcaRepository.findAll();
            var rcaData = new RCA();
            LocalDateTime instance = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

            String formattedStartDate = formatter.format(instance);


            if(!rcaList.isEmpty()){
                int id = rcaList.getLast().getRcaId()+1;
                rcaData.setRcaNumber("RCA_"+
                        rcaRequest.getOrganizationCode()+ "_"+
                        rcaRequest.getDepartment()+"_" +
                        formattedStartDate+"_"+
                        id);
            }else {
                rcaData.setRcaNumber("RCA_"+
                        rcaRequest.getOrganizationCode()+ "_"+
                        rcaRequest.getDepartment()+"_" +
                        formattedStartDate+"_"+
                        1);
            }

            rcaData.setOrganizationCode(rcaRequest.getOrganizationCode());
            rcaData.setAssetGroupId(rcaRequest.getAssetGroupId());
            rcaData.setAssetGroup(rcaRequest.getAssetGroup());
            rcaData.setAssetId(rcaRequest.getAssetId());
            rcaData.setAssetNumber(rcaRequest.getAssetNumber());
            rcaData.setAssetDescription(rcaRequest.getAssetDescription());
            rcaData.setDepartment(rcaRequest.getDepartment());
            rcaData.setDepartmentId(rcaRequest.getDepartmentId());
            rcaData.setArea(rcaRequest.getArea());
            rcaData.setRcaType(rcaRequest.getRcaType());
            rcaData.setCreatedById(rcaRequest.getCreatedById());
            rcaData.setCreatedBy(rcaRequest.getCreatedBy());
            rcaData.setApprover1Id(rcaRequest.getApprover1Id());
            rcaData.setApprover1Name(rcaRequest.getApprover1Name());
            rcaData.setApprover2Id(rcaRequest.getApprover2Id());
            rcaData.setApprover2Name(rcaRequest.getApprover2Name());
            rcaData.setApprover3Id(rcaRequest.getApprover3Id());
            rcaData.setApprover3Name(rcaRequest.getApprover3Name());
            rcaData.setIssueDescription(rcaRequest.getIssueDescription());
            rcaData.setStatus(0);
            rcaData.setActive(true);
            rcaData.setWoNumber("");
            rcaData.setCreatedOn(LocalDateTime.now());

            rcaRepository.save(rcaData);
            return new ResponseModel<>(true, "RCA Created Successfully",rcaData.getRcaNumber());

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    public ResponseModel<List<FilterNumberResponse>> getFilterNumber(String organizationCode){
        try {
            List<RCA> rcaList = rcaRepository.findByOrganizationCode(organizationCode);
            List<FilterNumberResponse> filterList = new ArrayList<>();
            for(RCA item: rcaList){
                FilterNumberResponse filterResponse = new FilterNumberResponse();
                filterResponse.setWoNumber(item.getWoNumber());
                filterResponse.setDocumentNumber(item.getRcaNumber());
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

    @Scheduled(cron = "0 0 0 * * *")  // first 0 Seconds, 0 minute, 0 hours, 0 days, 0 months, 0 week days
    public ResponseModel<String> deleteAllImagesInFolder() {
        List<RCAStepThreeQuestions> rcaStepThreeList = rcaStepThreeQuestionsRepository.findAll();
        System.out.println("Five minutes call");
        List<String> images = new ArrayList<>();
        for (RCAStepThreeQuestions stepThree : rcaStepThreeList) {
            images.add(stepThree.getAttachedImage());
        }

        String folderPath = environment.getProperty("image.uploadDirectory") + "/RCA";
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


    //RCA Step One
    public ResponseModel<String> addRCAStepOne(AddRCAStepOneRequest stepOneRequest){
        try {

            List<RCAStepOne> stepOneList = rcaStepOneRepository.findByRcaNumber(stepOneRequest.getRcaNumber());
            var apiAction = 0;
            if(!stepOneList.isEmpty()){
                //Update step one RCA
                stepOneList.getFirst().setRcaNumber(stepOneRequest.getRcaNumber());
                stepOneList.getFirst().setProblemDescription(stepOneRequest.getProblemDescription());
                rcaStepOneRepository.save(stepOneList.getFirst());

                //Delete existing team members for the particular RCA no
                var addedTeamList = rcaStepOneMembersRepository.findByRcaNumber(stepOneList.getFirst().getRcaNumber());
                if(!addedTeamList.isEmpty()){
                    for(RCAStepOneTeams items: addedTeamList){
                        if(items.getRcaNumber().equals(stepOneList.getFirst().getRcaNumber())){
                            rcaStepOneMembersRepository.deleteByTeamId(items.getTeamId());
                        }
                    }
                }

                for(RCAStepOneTeams item:stepOneRequest.getTeamsList()){
                    var rcaTeamsData = new RCAStepOneTeams();
                    rcaTeamsData.setTeamMemberName(item.getTeamMemberName());
                    rcaTeamsData.setTeamMemberDepartment(item.getTeamMemberDepartment());
                    rcaTeamsData.setTeamMemberResponsibility(item.getTeamMemberResponsibility());
                    rcaTeamsData.setTeamMemberType(item.getTeamMemberType());
                    rcaTeamsData.setRcaNumber(stepOneList.getFirst().getRcaNumber());
                    rcaStepOneMembersRepository.save(rcaTeamsData);
                }
                apiAction = 2;
            }else{

                //Add step one RCA
                var rcaData = new RCAStepOne();
                rcaData.setRcaNumber(stepOneRequest.getRcaNumber());
                rcaData.setProblemDescription(stepOneRequest.getProblemDescription());

                rcaStepOneRepository.save(rcaData);

                List<RCAStepOne> RCAStepOneList = rcaStepOneRepository.findByRcaNumber(stepOneRequest.getRcaNumber());

                for(RCAStepOneTeams item:stepOneRequest.getTeamsList()){
                    var rcaTeamsData = new RCAStepOneTeams();
                    rcaTeamsData.setTeamMemberName(item.getTeamMemberName());
                    rcaTeamsData.setTeamMemberDepartment(item.getTeamMemberDepartment());
                    rcaTeamsData.setTeamMemberResponsibility(item.getTeamMemberResponsibility());
                    rcaTeamsData.setTeamMemberType(item.getTeamMemberType());
                    rcaTeamsData.setRcaNumber(RCAStepOneList.getLast().getRcaNumber());

                    rcaStepOneMembersRepository.save(rcaTeamsData);
                }
                apiAction = 1;
            }

            List<RCA> rcaList = rcaRepository.findByRcaNumber(stepOneRequest.getRcaNumber());
            if(!rcaList.isEmpty()){
                //Update RCA step one status
                if(rcaList.getFirst().getStatus()==0) {
                    rcaList.getFirst().setStatus(1);
                }
                rcaRepository.save(rcaList.getFirst());
            }
            return new ResponseModel<>(true, apiAction ==1?"Team Members & Problem Description Created Successfully":"Team Members & Problem Description Updated Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    public ResponseModel<RCAStepOne> getRCAStepOne(String rcaNo){
        try {
            List<RCAStepOne> stepOneList = rcaStepOneRepository.findByRcaNumber(rcaNo);

            return new ResponseModel<>(true, "Records found",stepOneList.getFirst());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }

    //RCA Step Two
    public ResponseModel<String> addRCAStepTwo(AddRCAStepTwoRequest stepTwoRequest){
        try {

            List<RCAStepTwo> stepTwoList = rcaStepTwoRepository.findByRcaNumber(stepTwoRequest.getRcaNumber());
            var apiAction = 0;
            if(!stepTwoList.isEmpty()){
                //Update RCA step two
                stepTwoList.getFirst().setRcaNumber(stepTwoRequest.getRcaNumber());
                stepTwoList.getFirst().setContainmentAction(stepTwoRequest.getContainmentAction());
                rcaStepTwoRepository.save(stepTwoList.getFirst());
                apiAction = 2;
            }else{
                //Add RCA step two
                var rcaData = new RCAStepTwo();
                rcaData.setRcaNumber(stepTwoRequest.getRcaNumber());
                rcaData.setContainmentAction(stepTwoRequest.getContainmentAction());
                rcaStepTwoRepository.save(rcaData);
                apiAction = 1;
            }


            List<RCA> rcaList = rcaRepository.findByRcaNumber(stepTwoRequest.getRcaNumber());
            if(!rcaList.isEmpty()){
                //Update RCA step two status
                if(rcaList.getFirst().getStatus()==1) {
                    rcaList.getFirst().setStatus(2);
                }
                rcaRepository.save(rcaList.getFirst());
            }

            return new ResponseModel<>(true, apiAction == 1?"Containment Action Created Successfully":"Containment Action Updated Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    public ResponseModel<RCAStepTwo> getRCAStepTwo(String rcaNo){
        try {
            List<RCAStepTwo> stepTwoList = rcaStepTwoRepository.findByRcaNumber(rcaNo);
            return new ResponseModel<>(true, "Records Found",stepTwoList.getFirst());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records Not Found",null);
        }
    }

    public ResponseModel<String> addRCAIsNotQuestions(AddIsNotQuestions isNotQuestions){
        try {
            //Add RCA step three questions
            var rcaData = new RCAIsNotQuestions();
            rcaData.setQuestionCategory(isNotQuestions.getQuestionCategory());
            rcaData.setQuestionType(isNotQuestions.getQuestionType());
            rcaData.setQuestion(isNotQuestions.getQuestion());

            rcaIsNotQuestionRepository.save(rcaData);

            return new ResponseModel<>(true, "Questions Added Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }
    public ResponseModel<List<RCAIsNotQuestions>> getRCAIsNotQuestions(){
        try {

             var questionData = rcaIsNotQuestionRepository.findAll();

            return new ResponseModel<>(true, "Records Found",questionData);

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not Found",null);
        }
    }

    public ResponseModel<String> updateRCAIsNotQuestions(Integer id, AddIsNotQuestions isNotQuestions){
        try {

             var questionData = rcaIsNotQuestionRepository.findByQuestionId(id);
            //Update RCA step three questions
            if(!questionData.isEmpty()){
                questionData.getFirst().setQuestionCategory(isNotQuestions.getQuestionCategory());
                questionData.getFirst().setQuestionType(isNotQuestions.getQuestionType());
                questionData.getFirst().setQuestion(isNotQuestions.getQuestion());
                rcaIsNotQuestionRepository.save(questionData.getFirst());
            }

            return new ResponseModel<>(true, "Questions Updated Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }
    public ResponseModel<String> deleteRCAIsNotQuestions(Integer id){
        try {

             var questionData = rcaIsNotQuestionRepository.findByQuestionId(id);
            //Update RCA step three questions
            if(!questionData.isEmpty()){
                rcaIsNotQuestionRepository.delete(questionData.getFirst());
            }

            return new ResponseModel<>(true, "Questions Deleted Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    //RCA Step Three
//    public ResponseModel<String> addRCAStepThree(AddRCAStepThreeRequest stepThreeRequest) {
//        try {
//
//            List<RCAStepThree> stepThreeList = rcaStepThreeRepository.findByRcaNumber(stepThreeRequest.getRcaNumber());
//            var apiAction = 0;
//
//            if(!stepThreeList.isEmpty()){
//                //Update step RCA
//                stepThreeList.getFirst().setRcaNumber(stepThreeRequest.getRcaNumber());
//                stepThreeList.getFirst().setQuestionsList(stepThreeRequest.getQuestionsList());
//                rcaStepThreeRepository.save(stepThreeList.getFirst());
//
//                //Delete existing Questions for the particular RCA no
//                var addedQuestionsList = rcaStepThreeQuestionsRepository.findByRcaNumber(stepThreeList.getFirst().getRcaNumber());
//                if(!addedQuestionsList.isEmpty()){
//                    for(RCAStepThreeQuestions items: addedQuestionsList){
//                        if(items.getRcaNumber().equals(stepThreeList.getFirst().getRcaNumber())){
//                            rcaStepThreeQuestionsRepository.deleteByQuestionId(items.getQuestionId());
//                        }
//                    }
//                }
//
//                for(RCAStepThreeQuestions item:stepThreeRequest.getQuestionsList()){
//                    var rcaTeamsData = new RCAStepThreeQuestions();
//                    rcaTeamsData.setQuestionCategory(item.getQuestionCategory());
//                    rcaTeamsData.setQuestionType(item.getQuestionType());
//                    rcaTeamsData.setQuestion(item.getQuestion());
//                    rcaTeamsData.setAnswer(item.getAnswer());
//                    rcaTeamsData.setAttachedImage(item.getAttachedImage());
//                    rcaTeamsData.setUrl(item.getUrl());
//                    rcaTeamsData.setRcaNumber(stepThreeList.getFirst().getRcaNumber());
//                    rcaStepThreeQuestionsRepository.save(rcaTeamsData);
//                }
//                apiAction = 2;
//
//            }else{
//
//                //Add step  RCA
//                var rcaData = new RCAStepThree();
//                rcaData.setRcaNumber(stepThreeRequest.getRcaNumber());
//                rcaData.setQuestionsList(stepThreeRequest.getQuestionsList());
//                rcaStepThreeRepository.save(rcaData);
//
//                List<RCAStepThree> RCAStepThreeList = rcaStepThreeRepository.findByRcaNumber(stepThreeRequest.getRcaNumber());
//
//                for(RCAStepThreeQuestions item:stepThreeRequest.getQuestionsList()){
//                    var rcaQuestionsData = new RCAStepThreeQuestions();
//                    rcaQuestionsData.setQuestionCategory(item.getQuestionCategory());
//                    rcaQuestionsData.setQuestionType(item.getQuestionType());
//                    rcaQuestionsData.setQuestion(item.getQuestion());
//                    rcaQuestionsData.setAnswer(item.getAnswer());
//                    rcaQuestionsData.setAttachedImage(item.getAttachedImage());
//                    rcaQuestionsData.setUrl(item.getUrl());
//                    rcaQuestionsData.setRcaNumber(RCAStepThreeList.getFirst().getRcaNumber());
//                    rcaStepThreeQuestionsRepository.save(rcaQuestionsData);
//                }
//                apiAction = 1;
//            }
//
//            List<RCA> rcaList = rcaRepository.findByRcaNumber(stepThreeRequest.getRcaNumber());
//            if(!rcaList.isEmpty()){
//                //Update RCA step status
//                if(rcaList.getFirst().getStatus()==2) {
//                    rcaList.getFirst().setStatus(3);
//                }
//                rcaRepository.save(rcaList.getFirst());
//            }
//            return new ResponseModel<>(true, apiAction==1?"IS & IS-NOT Created Successfully":"IS & IS-NOT Updated Successfully",null);
//
//        }catch (Exception e){
//            return new ResponseModel<>(false, "Failed to add",null);
//        }
//    }



    public ResponseModel<String> addRCAStepThree(AddRCAStepThreeRequest stepThreeRequest) {
        try {
            // Check if "what" and "when" categories have at least one not null answer
            boolean hasNotNullAnswerForWhat = false;
            boolean hasNotNullAnswerForWhen = false;

            for (RCAStepThreeQuestions item : stepThreeRequest.getQuestionsList()) {
                if (item.getQuestionCategory().equalsIgnoreCase("what") && item.getAnswer() != null && item.getAnswer() != "") {
                    hasNotNullAnswerForWhat = true;
                } else if (item.getQuestionCategory().equalsIgnoreCase("when") && item.getAnswer() != null && item.getAnswer() != "") {
                    hasNotNullAnswerForWhen = true;
                }
            }

            if (!hasNotNullAnswerForWhat) {
                return new ResponseModel<>(false, "At least one answer is required for 'what' category", null);
            }

            if (!hasNotNullAnswerForWhen) {
                return new ResponseModel<>(false, "At least one answer is required for 'when' category", null);
            }

            List<RCAStepThree> stepThreeList = rcaStepThreeRepository.findByRcaNumber(stepThreeRequest.getRcaNumber());
            var apiAction = 0;

            if(!stepThreeList.isEmpty()){
                //Update step one RCA
                stepThreeList.getFirst().setRcaNumber(stepThreeRequest.getRcaNumber());
                stepThreeList.getFirst().setQuestionsList(stepThreeRequest.getQuestionsList());
                rcaStepThreeRepository.save(stepThreeList.getFirst());

                //Delete existing Questions for the particular RCA no
                var addedQuestionsList = rcaStepThreeQuestionsRepository.findByRcaNumber(stepThreeList.getFirst().getRcaNumber());
                if(!addedQuestionsList.isEmpty()){
                    for(RCAStepThreeQuestions items: addedQuestionsList){
                        if(items.getRcaNumber().equals(stepThreeList.getFirst().getRcaNumber())){
                            rcaStepThreeQuestionsRepository.deleteByQuestionId(items.getQuestionId());
                        }
                    }
                }

                for(RCAStepThreeQuestions item:stepThreeRequest.getQuestionsList()){
                    var rcaTeamsData = new RCAStepThreeQuestions();
                    rcaTeamsData.setQuestionCategory(item.getQuestionCategory());
                    rcaTeamsData.setQuestionType(item.getQuestionType());
                    rcaTeamsData.setQuestion(item.getQuestion());
                    rcaTeamsData.setAnswer(item.getAnswer());
                    rcaTeamsData.setAttachedImage(item.getAttachedImage());
                    rcaTeamsData.setUrl(item.getUrl());
                    rcaTeamsData.setRcaNumber(stepThreeList.getFirst().getRcaNumber());
                    rcaStepThreeQuestionsRepository.save(rcaTeamsData);
                }
                apiAction = 2;

            } else {
                // Add step RCA
                var rcaData = new RCAStepThree();
//                // Check if "what" and "when" categories have at least one not null answer
//                for (RCAStepThreeQuestions item : stepThreeRequest.getQuestionsList()) {
//                    if (item.getQuestionCategory().equalsIgnoreCase("what") && item.getAnswer() != null && !item.getAnswer().equals("")) {
//                        hasNotNullAnswerForWhat = true;
//                    } else if (item.getQuestionCategory().equalsIgnoreCase("when") && item.getAnswer() != null && !item.getAnswer().equals("")) {
//                        hasNotNullAnswerForWhen = true;
//                    }
//                }
//
//                if (!hasNotNullAnswerForWhat) {
//                    return new ResponseModel<>(false, "At least one answer is required for 'what' category", null);
//                }
//
//                if (!hasNotNullAnswerForWhen) {
//                    return new ResponseModel<>(false, "At least one answer is required for 'when' category", null);
//                }
                rcaData.setRcaNumber(stepThreeRequest.getRcaNumber());
                rcaData.setQuestionsList(stepThreeRequest.getQuestionsList());
                rcaStepThreeRepository.save(rcaData);

                List<RCAStepThree> RCAStepThreeList = rcaStepThreeRepository.findByRcaNumber(stepThreeRequest.getRcaNumber());

                for(RCAStepThreeQuestions item:stepThreeRequest.getQuestionsList()){
                    var rcaQuestionsData = new RCAStepThreeQuestions();
                    rcaQuestionsData.setQuestionCategory(item.getQuestionCategory());
                    rcaQuestionsData.setQuestionType(item.getQuestionType());
                    rcaQuestionsData.setQuestion(item.getQuestion());
                    rcaQuestionsData.setAnswer(item.getAnswer());
                    rcaQuestionsData.setAttachedImage(item.getAttachedImage());
                    rcaQuestionsData.setUrl(item.getUrl());
                    rcaQuestionsData.setRcaNumber(RCAStepThreeList.getFirst().getRcaNumber());
                    rcaStepThreeQuestionsRepository.save(rcaQuestionsData);
                }
                apiAction = 1;
            }

            List<RCA> rcaList = rcaRepository.findByRcaNumber(stepThreeRequest.getRcaNumber());
            if (!rcaList.isEmpty()) {
                // Update RCA step status
                RCA rca = rcaList.getFirst();
                if (rca.getStatus() == 2) {
                    rca.setStatus(3);
                }
                rcaRepository.save(rca);
            }

            return new ResponseModel<>(true, apiAction == 1 ? "IS & IS-NOT Created Successfully" : "IS & IS-NOT Updated Successfully", null);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel<>(false, "Failed to add ", null);
        }
    }

//    public ResponseModel<String> addRCAStepThree(AddRCAStepThreeRequest stepThreeRequest) {
//
//        try {
//
//            // Check if "what" and "when" categories have at least one not null answer
//
//            boolean hasNotNullAnswerForWhat = false;
//
//            boolean hasNotNullAnswerForWhen = false;
//
//            if (stepThreeRequest.getQuestionsList() == null || stepThreeRequest.getQuestionsList().isEmpty()) {
//
//                return new ResponseModel<>(false, "Questions list cannot be empty", null);
//
//            }
//
//            for (RCAStepThreeQuestions item : stepThreeRequest.getQuestionsList()) {
//
//                if (item.getQuestionCategory() == null) {
//
//                    return new ResponseModel<>(false, "Question category cannot be null", null);
//
//                }
//
//                if (item.getQuestionCategory().equalsIgnoreCase("what") && item.getAnswer() != null && !item.getAnswer().isEmpty()) {
//
//                    hasNotNullAnswerForWhat = true;
//
//                } else if (item.getQuestionCategory().equalsIgnoreCase("when") && item.getAnswer() != null && !item.getAnswer().isEmpty()) {
//
//                    hasNotNullAnswerForWhen = true;
//
//                }
//
//            }
//
//            if (!hasNotNullAnswerForWhat) {
//
//                return new ResponseModel<>(false, "At least one answer is required for 'what' category", null);
//
//            }
//
//            if (!hasNotNullAnswerForWhen) {
//
//                return new ResponseModel<>(false, "At least one answer is required for 'when' category", null);
//
//            }
//
//            List<RCAStepThree> stepThreeList = rcaStepThreeRepository.findByRcaNumber(stepThreeRequest.getRcaNumber());
//
//            boolean isUpdate = !stepThreeList.isEmpty();
//
//            RCAStepThree rcaStepThree;
//
//            if (isUpdate) {
//
//                rcaStepThree = stepThreeList.getFirst();
//
//                rcaStepThree.setQuestionsList(stepThreeRequest.getQuestionsList());
//
//                rcaStepThreeRepository.deleteByRcaNumber(rcaStepThree.getRcaNumber());
//
//            } else {
//
//                rcaStepThree = new RCAStepThree();
//
//                rcaStepThree.setRcaNumber(stepThreeRequest.getRcaNumber());
//
//                rcaStepThree.setQuestionsList(stepThreeRequest.getQuestionsList());
//
//            }
//
//            rcaStepThreeRepository.save(rcaStepThree);
//
//            for (RCAStepThreeQuestions item : stepThreeRequest.getQuestionsList()) {
//
//                RCAStepThreeQuestions rcaQuestionsData = new RCAStepThreeQuestions();
//
//                rcaQuestionsData.setQuestionCategory(item.getQuestionCategory());
//
//                rcaQuestionsData.setQuestionType(item.getQuestionType());
//
//                rcaQuestionsData.setQuestion(item.getQuestion());
//
//                rcaQuestionsData.setAnswer(item.getAnswer());
//
//                rcaQuestionsData.setAttachedImage(item.getAttachedImage());
//
//                rcaQuestionsData.setUrl(item.getUrl());
//
//                rcaQuestionsData.setRcaNumber(rcaStepThree.getRcaNumber());
//
//                rcaStepThreeQuestionsRepository.save(rcaQuestionsData);
//
//            }
//
//            List<RCA> rcaList = rcaRepository.findByRcaNumber(stepThreeRequest.getRcaNumber());
//
//            if (!rcaList.isEmpty()) {
//
//                RCA rca = rcaList.getFirst();
//
//                if (rca.getStatus() == 2) {
//
//                    rca.setStatus(3);
//
//                }
//
//                rcaRepository.save(rca);
//
//            }
//
//            String message = isUpdate ? "IS & IS-NOT Updated Successfully" : "IS & IS-NOT Created Successfully";
//
//            return new ResponseModel<>(true, message, null);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            return new ResponseModel<>(false, "Failed to add: " + e.getMessage(), null);
//
//        }
//
//    }


    public ResponseModel<List<RCAStepThreeQuestions>> getRCAStepThree(String rcaNo){
        try {
            List<RCAStepThree> stepThreeList = rcaStepThreeRepository.findByRcaNumber(rcaNo);
            return new ResponseModel<>(true, "Records Found",stepThreeList.getFirst().getQuestionsList().reversed());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records Not Found",null);
        }
    }

    public ResponseModel<String> addRCAStepFour(AddRCAStepFourRequest stepFourRequest) {
        try {
            List<RCAStepFour> stepFourList = rcaStepFourRepository.findByRcaNumber(stepFourRequest.getRcaNumber());
            var apiAction = 0;
            if (!stepFourList.isEmpty()) {
                stepFourList.getFirst().setRcaNumber(stepFourRequest.getRcaNumber());
                rcaStepFourRepository.save(stepFourList.getFirst());

                var addedTableList = rcaStepFourCARepository.findByRcaNumber(stepFourList.getFirst().getRcaNumber());
                if (!addedTableList.isEmpty()) {
                    for (RCAStepFourCA items : addedTableList) {
                        if (items.getRcaNumber().equals(stepFourList.getFirst().getRcaNumber())) {
                            rcaStepFourCARepository.deleteByRcaStepFourTableId(items.getRcaStepFourTableId());
                        }
                    }
                }
                for (RCAStepFourCA items : stepFourRequest.getStepFourTables()) {
                    var rcaTableData = new RCAStepFourCA();
                    rcaTableData.setChange(items.getChange());
                    rcaTableData.setDifference(items.getDifference());
                    rcaTableData.setTargetDate(items.getTargetDate());
                    rcaTableData.setApplicable(items.isApplicable());
                    rcaTableData.setRcaNumber(stepFourList.getFirst().getRcaNumber());

                    rcaStepFourCARepository.save(rcaTableData);
                }
                apiAction = 2;
            } else {
                //Add step RCA
                var rcaData = new RCAStepFour();
                rcaData.setRcaNumber(stepFourRequest.getRcaNumber());
                rcaData.setTableList(stepFourRequest.getStepFourTables());
                rcaStepFourRepository.save(rcaData);

                List<RCAStepFour> RCAStepFourList = rcaStepFourRepository.findAll();

                for (RCAStepFourCA item : stepFourRequest.getStepFourTables()) {
                    var rcaTeamsData = new RCAStepFourCA();
                    rcaTeamsData.setChange(item.getChange());
                    rcaTeamsData.setDifference(item.getDifference());
                    rcaTeamsData.setTargetDate(item.getTargetDate());
                    rcaTeamsData.setApplicable(item.isApplicable());
                    rcaTeamsData.setRcaNumber(RCAStepFourList.getLast().getRcaNumber());

                    rcaStepFourCARepository.save(rcaTeamsData);
                }
                apiAction = 1;
                List<RCA> rcaList = rcaRepository.findByRcaNumber(stepFourRequest.getRcaNumber());
                if (!rcaList.isEmpty()) {
                    //Update RCA step four status
                    if (rcaList.getFirst().getStatus() == 3) {
                        rcaList.getFirst().setStatus(4);
                    }
                    rcaRepository.save(rcaList.getFirst());
                }
            }
            return new ResponseModel<>(true, apiAction == 1 ? "Comparative Analysis is Created Successfully" : "Comparative Analysis is Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<List<RCAStepFourCA>> getRCAStepFour(String rcaNo) {
        try {
            List<RCAStepFour> stepFourList = rcaStepFourRepository.findByRcaNumber(rcaNo);

            return new ResponseModel<>(true, "Records found", stepFourList.getFirst().getTableList().reversed());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }

    //RCA Step Five
    public ResponseModel<String> addRCAStepFive(AddRCAStepFiveRequest stepFiveRequest){
        try {

            List<RCAStepFive> stepFiveList = rcaStepFiveRepository.findByRcaNumber(stepFiveRequest.getRcaNumber());
            var apiAction = 0;
            if(!stepFiveList.isEmpty()){
                //Update step one RCA
                stepFiveList.getFirst().setRcaNumber(stepFiveRequest.getRcaNumber());
                stepFiveList.getFirst().setProblemDescription(stepFiveRequest.getProblemDescription());
                rcaStepFiveRepository.save(stepFiveList.getFirst());

                //Delete existing team members for the particular RCA no
                var addedTeamList = rcaStepFiveDCARepository.findByRcaNumber(stepFiveList.getFirst().getRcaNumber());
                System.out.println(addedTeamList.size());
                if(!addedTeamList.isEmpty()){
                    for(RCAStepFiveDCA items: addedTeamList){

                        if(items.getRcaNumber().equals(stepFiveList.getFirst().getRcaNumber())){
                            rcaStepFiveDCARepository.deleteByDcaId(items.getDcaId());
                            System.out.println(items.getDcaId());
                        }
                    }
                }

                for(RCAStepFiveDCA item:stepFiveRequest.getDcaList()){
                    var rcaTeamsData = new RCAStepFiveDCA();
                    rcaTeamsData.setDcaType(item.getDcaType());
                    rcaTeamsData.setDcaHighlight(item.isDcaHighlight());
                    rcaTeamsData.setDcaReason(item.getDcaReason());
                    rcaTeamsData.setRcaNumber(stepFiveList.getFirst().getRcaNumber());
                    rcaStepFiveDCARepository.save(rcaTeamsData);
                }
                apiAction = 2;
            }else{

                //Add step one RCA
                var rcaData = new RCAStepFive();
                rcaData.setRcaNumber(stepFiveRequest.getRcaNumber());
                rcaData.setProblemDescription(stepFiveRequest.getProblemDescription());

                rcaStepFiveRepository.save(rcaData);

                List<RCAStepFive> RCAStepFiveList = rcaStepFiveRepository.findByRcaNumber(stepFiveRequest.getRcaNumber());

                for(RCAStepFiveDCA item:stepFiveRequest.getDcaList()){
                    var rcaTeamsData = new RCAStepFiveDCA();
                    rcaTeamsData.setDcaType(item.getDcaType());
                    rcaTeamsData.setDcaHighlight(item.isDcaHighlight());
                    rcaTeamsData.setDcaReason(item.getDcaReason());
                    rcaTeamsData.setRcaNumber(RCAStepFiveList.getLast().getRcaNumber());

                    rcaStepFiveDCARepository.save(rcaTeamsData);
                }
                apiAction = 1;
            }

            List<RCA> rcaList = rcaRepository.findByRcaNumber(stepFiveRequest.getRcaNumber());
            if(!rcaList.isEmpty()){
                //Update RCA step one status
                if(rcaList.getFirst().getStatus()==4) {
                    rcaList.getFirst().setStatus(5);
                }
                rcaRepository.save(rcaList.getFirst());
            }
            return new ResponseModel<>(true, apiAction ==1?"Direct Cause Analysis Created Successfully":"Direct Cause Analysis Updated Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    public ResponseModel<RCAStepFive> getRCAStepFive(String rcaNo){
        try {
            List<RCAStepFive> stepFiveList = rcaStepFiveRepository.findByRcaNumber(rcaNo);
            return new ResponseModel<>(true, "Records Found",stepFiveList.getFirst());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records Not Found",null);
        }
    }

    public ResponseModel<String> addRCAStepSix(AddRCAStepSixRequest stepSixRequest) {
        try {
            List<RCAStepSix> stepSixList = rcaStepSixRepository.findByRcaNumber(stepSixRequest.getRcaNumber());

            var apiAction = 0;
            if (!stepSixList.isEmpty()) {
                stepSixList.getFirst().setRcaNumber(stepSixRequest.getRcaNumber());
                stepSixList.getFirst().setRootCause(stepSixRequest.getRootCause());
                rcaStepSixRepository.save(stepSixList.getFirst());

                var addedTableList = rcaStepSixCauseRepository.findByRcaNumber(stepSixList.getFirst().getRcaNumber());
                if (!addedTableList.isEmpty()) {
                    for (RCAStepSixCause items : addedTableList) {
                        if (items.getRcaNumber().equals(stepSixList.getFirst().getRcaNumber())) {
                            rcaStepSixCauseRepository.deleteByRcaStepSixCauseId(items.getRcaStepSixCauseId());
                        }
                    }
                }
                for (RCAStepSixCause items : stepSixRequest.getCauseList()) {
                    var rcaCauseData = new RCAStepSixCause();
                    rcaCauseData.setCauseType(items.getCauseType());
                    rcaCauseData.setWhy1(items.getWhy1());
                    rcaCauseData.setWhy2(items.getWhy2());
                    rcaCauseData.setWhy3(items.getWhy3());
                    rcaCauseData.setWhy4(items.getWhy4());
                    rcaCauseData.setWhy5(items.getWhy5());
                    rcaCauseData.setComment(items.getComment());
                    rcaCauseData.setIsRca(items.getIsRca());
                    rcaCauseData.setRcaNumber(stepSixList.getFirst().getRcaNumber());

                    rcaStepSixCauseRepository.save(rcaCauseData);
                }
                apiAction = 2;
            } else {
                //Add step six RCA
                var rcaData = new RCAStepSix();
                rcaData.setRcaNumber(stepSixRequest.getRcaNumber());
                rcaData.setRootCause(stepSixRequest.getRootCause());
                rcaStepSixRepository.save(rcaData);

                List<RCAStepSix> RCAStepSixList = rcaStepSixRepository.findAll();

                for (RCAStepSixCause item : stepSixRequest.getCauseList()) {
                    var rcaCauseData = new RCAStepSixCause();
                    rcaCauseData.setCauseType(item.getCauseType());
                    rcaCauseData.setWhy1(item.getWhy1());
                    rcaCauseData.setWhy2(item.getWhy2());
                    rcaCauseData.setWhy3(item.getWhy3());
                    rcaCauseData.setWhy4(item.getWhy4());
                    rcaCauseData.setWhy5(item.getWhy5());
                    rcaCauseData.setIsRca(item.getIsRca());
                    rcaCauseData.setRcaNumber(RCAStepSixList.getLast().getRcaNumber());

                    rcaStepSixCauseRepository.save(rcaCauseData);
                }
                apiAction = 1;
            }

            List<RCA> rcaList = rcaRepository.findByRcaNumber(stepSixRequest.getRcaNumber());
            if (!rcaList.isEmpty()) {
                //Update RCA step six status
                if (rcaList.getFirst().getStatus() == 5) {
                    rcaList.getFirst().setStatus(6);
                }
                rcaRepository.save(rcaList.getFirst());
            }
            return new ResponseModel<>(true, apiAction == 1 ? "Root Cause Analysis is Created Successfully" : "Root Cause Analysis is Updated Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<RCAStepSix> getRCAStepSix(String rcaNo) {
        try {
            List<RCAStepSix> stepSixList = rcaStepSixRepository.findByRcaNumber(rcaNo);

            return new ResponseModel<>(true, "Records found", stepSixList.getFirst());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }

    //RCA Step Seven
    public ResponseModel<String> addRCAStepSevenCA(AddRCAStepSevenCARequest stepSevenRequest){
        try {

            List<RCAStepSeven> stepSevenList = rcaStepSevenRepository.findByRcaNumber(stepSevenRequest.getRcaNumber());
            var apiAction = 0;
            if(!stepSevenList.isEmpty()){
                //Update step Seven RCA
                //Delete existing team members for the particular RCA no
                var addedCAList = rcaStepSevenCARepository.findByRcaNumber(stepSevenList.getFirst().getRcaNumber());
                if(!addedCAList.isEmpty()){
                    for(RCAStepSevenCA items: addedCAList){
                        if(items.getRcaNumber().equals(stepSevenList.getFirst().getRcaNumber())){
                            rcaStepSevenCARepository.deleteByCaId(items.getCaId());
                        }
                    }
                }

                for(RCAStepSevenCA item:stepSevenRequest.getCaDetails()){
                    var caData = new RCAStepSevenCA();
                    caData.setAssetNumber(item.getAssetNumber());
                    caData.setAssetGroup(item.getAssetGroup());
                    caData.setDepartment(item.getDepartment());
                    caData.setAction(item.getAction());
                    caData.setStartDate(item.getStartDate());
                    caData.setEndDate(item.getEndDate());
                    caData.setTeamMembers(item.getTeamMembers());
                    caData.setRcaNumber(stepSevenList.getFirst().getRcaNumber());
                    rcaStepSevenCARepository.save(caData);
                }

                apiAction = 2;
            }else{

                //Add step Seven RCA
                var rcaData = new RCAStepSeven();
                rcaData.setRcaNumber(stepSevenRequest.getRcaNumber());
                rcaStepSevenRepository.save(rcaData);

                for(RCAStepSevenCA item:stepSevenRequest.getCaDetails()){
                    var caData = new RCAStepSevenCA();
                    caData.setAssetNumber(item.getAssetNumber());
                    caData.setAssetGroup(item.getAssetGroup());
                    caData.setDepartment(item.getDepartment());
                    caData.setAction(item.getAction());
                    caData.setStartDate(item.getStartDate());
                    caData.setEndDate(item.getEndDate());
                    caData.setTeamMembers(item.getTeamMembers());
                    caData.setRcaNumber(stepSevenRequest.getRcaNumber());
                    rcaStepSevenCARepository.save(caData);
                }

                apiAction = 1;
            }

            return new ResponseModel<>(true, apiAction ==1?"Corrective Action Created Successfully":"Corrective Action Updated Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    public ResponseModel<String> addRCAStepSevenPA(AddRCAStepSevenPARequest stepSevenRequest){
        try {

            List<RCAStepSeven> stepSevenList = rcaStepSevenRepository.findByRcaNumber(stepSevenRequest.getRcaNumber());

            var apiAction = 0;
            if(!stepSevenList.isEmpty()){
                //Update step Seven RCA
                //Delete existing team members for the particular RCA no
                var addedPAList = rcaStepSevenPARepository.findByRcaNumber(stepSevenList.getFirst().getRcaNumber());
                if(!addedPAList.isEmpty()){
                    for(RCAStepSevenPA items: addedPAList){
                        if(items.getRcaNumber().equals(stepSevenList.getFirst().getRcaNumber())){
                            rcaStepSevenPARepository.deleteByPaId(items.getPaId());
                        }
                    }
                }

                for(RCAStepSevenPA item:stepSevenRequest.getPaDetails()){
                    var paData = new RCAStepSevenPA();
                    paData.setAssetNumber(item.getAssetNumber());
                    paData.setAssetGroup(item.getAssetGroup());
                    paData.setDepartment(item.getDepartment());
                    paData.setAction(item.getAction());
                    paData.setStartDate(item.getStartDate());
                    paData.setEndDate(item.getEndDate());
                    paData.setTeamMembers(item.getTeamMembers());
                    paData.setRcaNumber(stepSevenList.getFirst().getRcaNumber());
                    rcaStepSevenPARepository.save(paData);
                }

                apiAction = 2;
            }else {

                //Add step Seven RCA
                var rcaData = new RCAStepSeven();
                rcaData.setRcaNumber(stepSevenRequest.getRcaNumber());

                for (RCAStepSevenPA item : stepSevenRequest.getPaDetails()) {
                    var paData = new RCAStepSevenPA();

                    paData.setAssetNumber(item.getAssetNumber());
                    paData.setAssetGroup(item.getAssetGroup());
                    paData.setDepartment(item.getDepartment());
                    paData.setAction(item.getAction());
                    paData.setStartDate(item.getStartDate());
                    paData.setEndDate(item.getEndDate());
                    paData.setTeamMembers(item.getTeamMembers());
                    paData.setRcaNumber(stepSevenRequest.getRcaNumber());

                    rcaStepSevenPARepository.save(paData);

                }
                apiAction = 1;
                rcaStepSevenRepository.save(rcaData);
            }

                List<RCA> rcaList = rcaRepository.findByRcaNumber(stepSevenRequest.getRcaNumber());
                if (!rcaList.isEmpty()) {
                    //Update RCA step one status
                    if (rcaList.getFirst().getStatus() == 6) {
                        rcaList.getFirst().setStatus(7);
                    }
                    rcaRepository.save(rcaList.getFirst());
                }

            return new ResponseModel<>(true, apiAction ==1 ? "Preventive Action Created Successfully" : "Preventive Action Updated Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }


    public ResponseModel<RCAStepSeven> getRCAStepSeven(String rcaNo){
        try {
            List<RCAStepSeven> stepSevenList = rcaStepSevenRepository.findByRcaNumber(rcaNo);
            return new ResponseModel<>(true, "Records Found",stepSevenList.getFirst());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records Not Found",null);
        }
    }

    //RCA Step Eight
    public ResponseModel<String> addRCAStepEight(AddRCAStepEightRequest stepEightRequest){
        try {

            List<RCA> rcaList = rcaRepository.findByRcaNumber(stepEightRequest.getRcaNumber());
            var apiAction = 0;
            if(!rcaList.isEmpty()){
                //Update step Eight RCA
                rcaList.getFirst().setRcaNumber(stepEightRequest.getRcaNumber());
                rcaList.getFirst().setShareComments(stepEightRequest.getComments());

                if(rcaList.getFirst().getStatus()==7) {
                    rcaList.getFirst().setStatus(8);
                    rcaList.getFirst().setCompletedDate(LocalDateTime.now());
                }
                else if(rcaList.getFirst().getStatus()==10){
                    rcaList.getFirst().setStatus(8);
                    rcaList.getFirst().setApprover1Status(0);
                    rcaList.getFirst().setApprover1Comments(null);
                    rcaList.getFirst().setApprover1DateTime(null);

                    rcaList.getFirst().setApprover2Status(0);
                    rcaList.getFirst().setApprover2Comments(null);
                    rcaList.getFirst().setApprover2DateTime(null);

                    rcaList.getFirst().setApprover3Status(0);
                    rcaList.getFirst().setApprover3Comments(null);
                    rcaList.getFirst().setApprover3DateTime(null);
                    rcaList.getFirst().setCompletedDate(LocalDateTime.now());//completion date ///
                    rcaList.getFirst().setRevertBackDate(null);
                    rcaList.getFirst().setClosedDate(null);

                }
                rcaRepository.save(rcaList.getFirst());

            }

            return new ResponseModel<>(true, "Sharing Added Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    public ResponseModel<SharingResponse> getRCAStepEight(String rcaNo){
        try {
            List<RCA> rcaList = rcaRepository.findByRcaNumber(rcaNo);
            var sharingData = new SharingResponse();
            sharingData.setApprover1Id(rcaList.getFirst().getApprover1Id());
            sharingData.setApprover1Name(rcaList.getFirst().getApprover1Name());
            sharingData.setApprover1Status(rcaList.getFirst().getApprover1Status());
            sharingData.setApprover1Comments(rcaList.getFirst().getApprover1Comments());
            sharingData.setApprover1DateTime(rcaList.getFirst().getApprover1DateTime());

            sharingData.setApprover2Id(rcaList.getFirst().getApprover2Id());
            sharingData.setApprover2Name(rcaList.getFirst().getApprover2Name());
            sharingData.setApprover2Status(rcaList.getFirst().getApprover2Status());
            sharingData.setApprover2Comments(rcaList.getFirst().getApprover2Comments());
            sharingData.setApprover2DateTime(rcaList.getFirst().getApprover2DateTime());

            sharingData.setApprover3Id(rcaList.getFirst().getApprover3Id());
            sharingData.setApprover3Name(rcaList.getFirst().getApprover3Name());
            sharingData.setApprover3Status(rcaList.getFirst().getApprover3Status());
            sharingData.setApprover3Comments(rcaList.getFirst().getApprover3Comments());
            sharingData.setApprover3DateTime(rcaList.getFirst().getApprover3DateTime());
            sharingData.setShareComments(rcaList.getFirst().getShareComments());
            sharingData.setId(rcaList.getFirst().getRcaNumber());

            return new ResponseModel<>(true, "Records Found",sharingData);

        }catch (Exception e){
            return new ResponseModel<>(false, "Records Not Found",null);
        }
    }

    public ResponseModel<String> validateRCAStepEight(ValidateRequest validateRequest){
        try {

            List<RCA> rcaList = rcaRepository.findByRcaNumber(validateRequest.getId());
            //Update step Eight RCA

            rcaList.getFirst().setApprover1Id(validateRequest.getApprover1Id());
            rcaList.getFirst().setApprover1Name(validateRequest.getApprover1Name());
            rcaList.getFirst().setApprover1Status(validateRequest.getApprover1Status());
            rcaList.getFirst().setApprover1Comments(validateRequest.getApprover1Comments());
            if(validateRequest.getApprover1Status()!=0 && rcaList.getFirst().getApprover1DateTime()==null){
                rcaList.getFirst().setApprover1DateTime(LocalDateTime.now());
            }

            rcaList.getFirst().setApprover2Id(validateRequest.getApprover2Id());
            rcaList.getFirst().setApprover2Name(validateRequest.getApprover2Name());
            rcaList.getFirst().setApprover2Status(validateRequest.getApprover2Status());
            rcaList.getFirst().setApprover2Comments(validateRequest.getApprover2Comments());
            if(validateRequest.getApprover2Status()!=0 && rcaList.getFirst().getApprover2DateTime()==null){
                rcaList.getFirst().setApprover2DateTime(LocalDateTime.now());
            }

            rcaList.getFirst().setApprover3Id(validateRequest.getApprover3Id());
            rcaList.getFirst().setApprover3Name(validateRequest.getApprover3Name());
            rcaList.getFirst().setApprover3Status(validateRequest.getApprover3Status());
            rcaList.getFirst().setApprover3Comments(validateRequest.getApprover3Comments());
            if(validateRequest.getApprover3Status()!=0 && rcaList.getFirst().getApprover3DateTime()==null){
                rcaList.getFirst().setApprover3DateTime(LocalDateTime.now());
                rcaList.getFirst().setClosedDate(LocalDateTime.now());
                rcaList.getFirst().setStatus(11);
            }

            if(validateRequest.getApprover2Name().isEmpty() && validateRequest.getApprover2Status() == 0){
                if(rcaList.getFirst().getStatus()==8) {
                    if(validateRequest.getApprover1Status()==1){
                        rcaList.getFirst().setStatus(11);
                        rcaList.getFirst().setClosedDate(LocalDateTime.now());
                    } else if (validateRequest.getApprover1Status() == 2) {
                        rcaList.getFirst().setStatus(10);
                        rcaList.getFirst().setRevertBackDate(LocalDateTime.now());
                    }
                }
                rcaRepository.save(rcaList.getFirst());

            }else if(validateRequest.getApprover3Name().isEmpty() && validateRequest.getApprover3Status() == 0){
                if(rcaList.getFirst().getStatus()==9) {
                    if(validateRequest.getApprover2Status()==1){
                        rcaList.getFirst().setStatus(11);
                        rcaList.getFirst().setClosedDate(LocalDateTime.now());
                    } else if (validateRequest.getApprover2Status()==2) {
                        rcaList.getFirst().setStatus(10);
                        rcaList.getFirst().setRevertBackDate(LocalDateTime.now());
                    }
                }else {
                    rcaList.getFirst().setStatus(9);
                }
                rcaRepository.save(rcaList.getFirst());
            }else if(!validateRequest.getApprover3Name().isEmpty() && validateRequest.getApprover3Status() == 1){
                if(rcaList.getFirst().getStatus()==9) {
                            rcaList.getFirst().setStatus(11);
                            rcaList.getFirst().setClosedDate(LocalDateTime.now());
                }
                rcaRepository.save(rcaList.getFirst());
            }else{

                if(validateRequest.getApprover1Status() == 2){
                    rcaList.getFirst().setStatus(10);
                    rcaList.getFirst().setRevertBackDate(LocalDateTime.now());
                }else if(validateRequest.getApprover2Status() == 2){
                    rcaList.getFirst().setStatus(10);
                    rcaList.getFirst().setRevertBackDate(LocalDateTime.now());
                } else if (validateRequest.getApprover3Status() == 2) {
                    rcaList.getFirst().setStatus(10);
                    rcaList.getFirst().setRevertBackDate(LocalDateTime.now());
                } else {
                    rcaList.getFirst().setStatus(9);
                }

                rcaRepository.save(rcaList.getFirst());

            }

            return new ResponseModel<>(true, "Updated Successfully",null);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }



public ResponseModel<List<RCA>> findPending(String username, String organizationCode) {
    UserInfo userInfo = userInfoRepository.findByUserNameAndOrganizationCode(username, organizationCode);
    boolean isOperator = "Operator".equals(userInfo.getRole());

    if (isOperator) {
        System.out.println("isOperator=" + isOperator);
        List<RCA> pendingRcas = rcaRepository.findPending(username,organizationCode);
        return new ResponseModel<>(true,"Operator pending List",pendingRcas);
    } else {
        List<RCA> result = rcaRepository.findByUsernameAndOrganizationCodeWithPendingStatus(username, organizationCode);


        return new ResponseModel<>(true,"User Pending List", result);

    }
}

//    public ResponseModel<List<RCACombinedDTO>> reportLists(
//            String organizationCode, String department, String area, FilterType filterType,
//            String requestPage, LocalDate startDate, LocalDate endDate) {
//        try {
//            Page<RCA> results;
//            int pageRequestCount;
//
//
//            if (requestPage.matches(".*\\d.*")) {
//                pageRequestCount = Integer.parseInt(requestPage);
//            } else {
//                pageRequestCount = 0;
//            }
//
//            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
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
//                results = rcaRepository.findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(organizationCode, department, area, startDateTime, endDateTime, pageRequest);
//            } else if (organizationCode != null && department != null) {
//                results = rcaRepository.findByOrganizationCodeAndDepartmentAndCreatedOnBetween(organizationCode, department, startDateTime, endDateTime, pageRequest);
//            } else if (organizationCode != null && area != null) {
//                results = rcaRepository.findByOrganizationCodeAndAreaAndCreatedOnBetween(organizationCode, area, startDateTime, endDateTime, pageRequest);
//            } else if (department != null && area != null) {
//                results = rcaRepository.findByDepartmentAndAreaAndCreatedOnBetween(department, area, startDateTime, endDateTime, pageRequest);
//            } else if (organizationCode != null) {
//                results = rcaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDateTime, endDateTime, pageRequest);
//            } else if (department != null) {
//                results = rcaRepository.findByDepartmentAndCreatedOnBetween(department, startDateTime, endDateTime, pageRequest);
//            } else if (area != null) {
//                results = rcaRepository.findByAreaAndCreatedOnBetween(area, startDateTime, endDateTime, pageRequest);
//            } else {
//                results = rcaRepository.findByCreatedOnBetween(startDateTime, endDateTime, pageRequest);
//            }
//
//            List<RCACombinedDTO> combinedResults = new ArrayList<>();
//
//            for (RCA rca : results.getContent()) {
//                String rcaNumber = rca.getRcaNumber();
//
//                System.out.println("Called RCA = "+ rcaNumber);
//                RCAStepTwo stepTwo = rcaStepTwoRepository.findAllByRcaNumber(rcaNumber);
//                RCAStepSevenCA stepSevenCA = rcaStepSevenCARepository.findAllByRcaNumber(rcaNumber);
//                RCAStepSevenPA stepSevenPA = rcaStepSevenPARepository.findAllByRcaNumber(rcaNumber);
//                System.out.println("Called RCAStepTwo = " +stepTwo);
//                RCACombinedDTO dto = new RCACombinedDTO();
//
//                System.out.println("Called RCACominedDTO = "+dto);
//                dto.setOrgCode(rca.getOrganizationCode());
//                dto.setDepartment(rca.getDepartment());
//                dto.setArea(rca.getArea());
//                dto.setRcaNo(rca.getRcaNumber());
//                dto.setRcaInitiator(rca.getCreatedBy());
//                dto.setRcaInitiationDate(rca.getCreatedOn());
//                dto.setAssetNo(rca.getAssetNumber());
//                dto.setApprover1Id(rca.getApprover1Id());
//                dto.setApprover1Name(rca.getApprover1Name());
//                dto.setApprover1Comments(rca.getApprover1Comments());
//                dto.setApprover1DateTime(rca.getApprover1DateTime());
//                dto.setApprover2Id(rca.getApprover2Id());
//                dto.setApprover2Name(rca.getApprover2Name());
//                dto.setApprover2Status(rca.getApprover2Status());
//                dto.setApprover2Comments(rca.getApprover2Comments());
//                dto.setApprover2DateTime(rca.getApprover2DateTime());
//                dto.setApprover3Id(rca.getApprover3Id());
//                dto.setApprover3Name(rca.getApprover3Name());
//                dto.setApprover3Comments(rca.getApprover3Comments());
//                dto.setApprover3DateTime(rca.getApprover3DateTime());
//                dto.setIssueDescription(rca.getIssueDescription());
//                dto.setRcaProblemDescription(rca.getShareComments());
//                dto.setRcaLastApprovedBy(rca.getApprover3Name());
//                dto.setWoNumber(rca.getWoNumber());
//                dto.setCreatedOn(rca.getCreatedOn());
//                dto.setRcaStatus(rca.getStatus() != null ? rca.getStatus() : null);
//                dto.setRcaCompletionDate(rca.getCompletedDate());
//                dto.setRcaCloseDate(rca.getClosedDate());
//                dto.setContainmentActionTaken(stepTwo != null ? stepTwo.getContainmentAction() : null);
//                dto.setCorrectiveActionTaken(stepSevenCA != null ? stepSevenCA.getAction() : null);
//                dto.setPreventiveActionRCARecommendationProposed(stepSevenPA != null ? stepSevenPA.getAction() : null);
//                dto.setAssetDescription(rca.getAssetDescription());
//
//                combinedResults.add(dto);
//            }
//
//
//            String totalCount = String.valueOf(rcaRepository.count());
//            String filteredCount = String.valueOf(combinedResults.size());
//            System.out.println("cobined Results = "+combinedResults);
//            if (combinedResults.isEmpty()) {
//                return new ResponseModel<>(false, "No records found", null);
//            } else {
//                return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", combinedResults);
//            }
//
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Records not found", null);
//        }
//    }


    //      shiva code for RCA Report

    public ResponseModel<List<RCACombinedDTO>> reportLists(
            String organizationCode, String department, String area, FilterType filterType,
            String requestPage, LocalDate startDate, LocalDate endDate) {
        try {
            Page<RCA> results;
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
                results = rcaRepository.findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(organizationCode, department, area, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null && department != null) {
                results = rcaRepository.findByOrganizationCodeAndDepartmentAndCreatedOnBetween(organizationCode, department, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null && area != null) {
                results = rcaRepository.findByOrganizationCodeAndAreaAndCreatedOnBetween(organizationCode, area, startDateTime, endDateTime, pageRequest);
            } else if (department != null && area != null) {
                results = rcaRepository.findByDepartmentAndAreaAndCreatedOnBetween(department, area, startDateTime, endDateTime, pageRequest);
            } else if (organizationCode != null) {
                results = rcaRepository.findByOrganizationCodeAndCreatedOnBetween(organizationCode, startDateTime, endDateTime, pageRequest);
            } else if (department != null) {
                results = rcaRepository.findByDepartmentAndCreatedOnBetween(department, startDateTime, endDateTime, pageRequest);
            } else if (area != null) {
                results = rcaRepository.findByAreaAndCreatedOnBetween(area, startDateTime, endDateTime, pageRequest);
            } else {
                results = rcaRepository.findByCreatedOnBetween(startDateTime, endDateTime, pageRequest);
            }

            List<RCACombinedDTO> combinedResults = new ArrayList<>();

            for (RCA rca : results.getContent()) {
                String rcaNumber = rca.getRcaNumber();

                RCAStepTwo stepTwo = rcaStepTwoRepository.findAllByRcaNumber(rcaNumber);
                RCAStepSevenCA stepSevenCA = rcaStepSevenCARepository.findAllByRcaNumber(rcaNumber);
                RCAStepSevenPA stepSevenPA = rcaStepSevenPARepository.findAllByRcaNumber(rcaNumber);

                RCACombinedDTO dto = new RCACombinedDTO();

                dto.setOrgCode(rca.getOrganizationCode());
                dto.setDepartment(rca.getDepartment());
                dto.setArea(rca.getArea());
                dto.setRcaNo(rca.getRcaNumber());
                dto.setRcaInitiator(rca.getCreatedBy());
                dto.setRcaInitiationDate(rca.getCreatedOn());
                dto.setAssetNo(rca.getAssetNumber());
                dto.setApprover1Id(rca.getApprover1Id());
                dto.setApprover1Name(rca.getApprover1Name());
                dto.setApprover1Comments(rca.getApprover1Comments());
                dto.setApprover1DateTime(rca.getApprover1DateTime());
                dto.setApprover2Id(rca.getApprover2Id());
                dto.setApprover2Name(rca.getApprover2Name());
                dto.setApprover2Status(rca.getApprover2Status());
                dto.setApprover2Comments(rca.getApprover2Comments());
                dto.setApprover2DateTime(rca.getApprover2DateTime());
                dto.setApprover3Id(rca.getApprover3Id());
                dto.setApprover3Name(rca.getApprover3Name());
                dto.setApprover3Comments(rca.getApprover3Comments());
                dto.setApprover3DateTime(rca.getApprover3DateTime());
                dto.setIssueDescription(rca.getIssueDescription());
                dto.setRcaProblemDescription(rca.getShareComments());
                dto.setRcaLastApprovedBy(rca.getApprover3Name());
                dto.setWoNumber(rca.getWoNumber());
                dto.setCreatedOn(rca.getCreatedOn());
                dto.setRcaStatus(rca.getStatus() != null ? rca.getStatus().toString() : null);
                dto.setRcaCompletionDate(rca.getCompletedDate());
                dto.setRcaCloseDate(rca.getClosedDate());
                dto.setContainmentActionTaken(stepTwo != null ? stepTwo.getContainmentAction() : null);
                dto.setCorrectiveActionTaken(stepSevenCA != null ? stepSevenCA.getAction() : null);
                dto.setPreventiveActionRCARecommendationProposed(stepSevenPA != null ? stepSevenPA.getAction() : null);
                dto.setAssetDescription(rca.getAssetDescription());

                combinedResults.add(dto);
            }

            String totalCount = String.valueOf(rcaRepository.count());
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



}

