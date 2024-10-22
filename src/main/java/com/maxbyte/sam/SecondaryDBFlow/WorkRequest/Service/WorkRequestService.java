package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maxbyte.sam.OracleDBFlow.Entity.MasterWorkRequest;
import com.maxbyte.sam.OracleDBFlow.Repository.MasterWorkRequestRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.EmployeeDetails;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetGroupRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.EmployeeDetailsRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.EmployeeDetailsService;
import com.maxbyte.sam.SecondaryDBFlow.FNDUser.Entity.FNDUser;
import com.maxbyte.sam.SecondaryDBFlow.FNDUser.Repository.FNDUserRepository;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Entity.WrWoType;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Repository.WrWoTypeRepository;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Service.WrWoTypeService;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetWorkOrderListByWorkOrderNo;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetWorkOrderListEAM;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Entity.WorkRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Repository.WorkRequestRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Specification.WorkRequestSpecificationBuilder;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class WorkRequestService extends CrudService<WorkRequest,Integer> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private WorkRequestRepository workRequestRepository;
    @Autowired
    private AssetGroupRepository assetGroupRepository;
    @Autowired
    private MasterWorkRequestRepository masterWorkRequestRepository;
    @Value("${pagination.default-page}")
    private int defaultPage;
    @Value("${pagination.default-size}")
    private int defaultSize;
    @Autowired
    EmployeeDetailsRepository employeeDetailsRepository;
    @Autowired
    AssetRepository assetRepository;
    @Autowired
    FNDUserRepository fndUserRepository;
    @Autowired
    WrWoTypeRepository wrWoTypeRepository;
    @Override
    public CrudRepository repository() {
        return this.workRequestRepository;
    }
    /*@Autowired
    private MasterWorkRequestRepository masterWorkRequestRepository;

    @Autowired
    private MasterWorkRequestService masterWorkRequestService;*/

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public void validateAdd(WorkRequest data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(WorkRequest data) {
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


    public ResponseModel<List<WorkRequest>> list(String WRNumber,  String assetNumber, String department, String organizationId, String workRequestStatus, String requestPage) {
try {
    WorkRequestSpecificationBuilder builder = new WorkRequestSpecificationBuilder();
    if (WRNumber != null) builder.with("WORK_REQUEST_NUMBER", ":", WRNumber);
    if (assetNumber != null) builder.with("assetNumber", ":", assetNumber);
    if (department != null) builder.with("department", ":", department);
    if (organizationId != null) builder.with("ORGANIZATION_ID", ":", organizationId);

    var pageRequestCount = 0;

    if(requestPage.matches(".*\\d.*")){
        pageRequestCount = Integer.parseInt(requestPage);
    }else{
        pageRequestCount = 0;
    }

    var list = workRequestRepository.findAll();
    System.out.println(list.getFirst());

    // Create a PageRequest for pagination
    PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("creationDate").descending());
    Page<WorkRequest> results =workRequestRepository.findByWrNumberAndAssetNumberAndDepartmentAndOrganizationIdAndWorkRequestStatus(WRNumber,assetNumber,department,organizationId,workRequestStatus,pageRequest);
    List<WorkRequest> wrlist = workRequestRepository.findAll();
    var totalCount = String.valueOf(wrlist.size());
    var filteredCount = String.valueOf(results.getContent().size());

    //var filteredCount = String.valueOf(results.getContent().size());
    return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", results.getContent());

}catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }



    public ResponseModel<List<WorkRequest>> findWorkRequestByDateTime(String orgCode, LocalDateTime from, LocalDateTime to, String requestPage) {
        try {
            //var dateTimeFilterList = rcaRepository.findByOrganizationCodeAndCreatedOnBetween(orgCode, from, to);
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("creationDate").descending());
            Page<WorkRequest> results = workRequestRepository.findByOrganizationIdAndCreationDateBetween(orgCode, from, to, pageRequest);
            List<WorkRequest> wrList = workRequestRepository.findAll();
            var totalCount = String.valueOf(wrList.size());
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



//    private void fetchAndSaveData() throws Exception {
//        // Get the latest record's last update date from our OracleDB repository
//        Optional<MasterWorkRequest> latestRecordOpt = masterWorkRequestRepository.findTopByOrderByLastUpdateDateDesc();
//        LocalDateTime lastUpdateDateTime = latestRecordOpt.map(MasterWorkRequest::getLastUpdateDate).orElse(null);
//
//        // Convert LocalDateTime to Timestamp to avoid precision issues
//        Timestamp lastUpdateTimestamp = (lastUpdateDateTime != null) ? Timestamp.valueOf(lastUpdateDateTime) : null;
//
//        // Query the external APPS.WIP_EAM_WORK_REQUESTS_V table for records updated after or at our latest update date
//        String sql = "SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V WHERE LAST_UPDATE_DATE >= ?";
//
//        List<MasterWorkRequest> newRecords;
//
//        if (lastUpdateTimestamp != null) {
//            newRecords = jdbcTemplate.query(
//                    sql,
//                    new Object[]{lastUpdateTimestamp},
//                    BeanPropertyRowMapper.newInstance(MasterWorkRequest.class)
//            );
//        } else {
//            // If there's no last update timestamp, fetch all records
//            newRecords = jdbcTemplate.query(
//                    "SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V",
//                    BeanPropertyRowMapper.newInstance(MasterWorkRequest.class)
//            );
//        }
//
//        // Save the fetched records to both local OracleDB and SAM DB
//        if (newRecords.isEmpty()) {
//            System.out.println("********************No new Work Requests found.");
//        } else {
//            System.out.println("********************New Work Requests found: " + newRecords.size());
//
//            for (MasterWorkRequest masterWorkRequest : newRecords) {
//                // Save to local OracleDB
//                boolean existsInMaster = masterWorkRequestRepository.existsByWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
//                if (!existsInMaster) {
//                    masterWorkRequestRepository.save(masterWorkRequest);
//                    System.out.println("Saved Work Request in Master DB: " + masterWorkRequest.getWorkRequestNumber());
//                } else {
//                    System.out.println("Skipped duplicate Work Request in Master DB: " + masterWorkRequest.getWorkRequestNumber());
//                }
//
//                EmployeeDetails employeeDetails = employeeDetailsRepository.findById(masterWorkRequest.getCreatedBy()).orElse(null);
//                EmployeeDetails createdByName = employeeDetailsRepository.findById(masterWorkRequest.getWorkRequestCreatedBy()).orElse(null);
//
//                Asset assetNumber = assetRepository.findByAssetNumber(masterWorkRequest.getAssetNumber());
//                // Save to SAM DB
//                boolean existsInSam = workRequestRepository.existsByWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
//                if (!existsInSam) {
//                    WorkRequest workRequest = new WorkRequest();
//
//                    workRequest.setRowId(masterWorkRequest.getRowId());
//                    workRequest.setWorkRequestId(masterWorkRequest.getWorkRequestId());
//                    workRequest.setWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
//                    workRequest.setDescription(masterWorkRequest.getDescription());
//                    workRequest.setLastUpdateDate(masterWorkRequest.getLastUpdateDate());
//                    workRequest.setLastUpdatedBy(masterWorkRequest.getLastUpdatedBy());
//                    workRequest.setCreationDate(masterWorkRequest.getCreationDate()); // You may still want to store CreationDate if needed
//                    workRequest.setCreatedBy(masterWorkRequest.getCreatedBy());
//                    if (employeeDetails != null) {
//                        workRequest.setCreatedByName(employeeDetails.getFullName()); // Assuming you want to use the employee's full name
//                    } else {
//                        workRequest.setCreatedByName(""); // Fallback value if employee details are not found
//                    }
//
//                    if (createdByName != null) {
//                        workRequest.setWorkRequestCreatedByName(createdByName.getFullName()); // Assuming you want to use the employee's full name
//                    } else {
//                        workRequest.setWorkRequestCreatedByName(""); // Fallback value if employee details are not found
//                    }
//                    workRequest.setLastUpdateLogin(masterWorkRequest.getLastUpdateLogin());
//                    workRequest.setAssetNumber(masterWorkRequest.getAssetNumber());
//                    if (assetNumber != null && assetNumber.getAssetNumber().equals(masterWorkRequest.getAssetNumber())) {
//                        workRequest.setAssetId(assetNumber.getId()); // Set assetGroup from Asset to WorkRequest
//                        workRequest.setAssetGroupId(assetNumber.getAssetGroupId()); // Set assetGroup from Asset to WorkRequest
//                        workRequest.setAssetGroup(assetNumber.getAssetGroup()); // Set assetGroup from Asset to WorkRequest
//                    }
//
//                    workRequest.setOrganizationId(masterWorkRequest.getOrganizationId());
//                    workRequest.setWorkRequestStatusId(masterWorkRequest.getWorkRequestStatusId());
//                    workRequest.setWorkRequestStatus(masterWorkRequest.getWorkRequestStatus());
//                    workRequest.setWorkRequestPriorityId(masterWorkRequest.getWorkRequestPriorityId());
//                    workRequest.setWorkRequestPriority(masterWorkRequest.getWorkRequestPriority());
//                    workRequest.setWorkRequestOwningDeptId(masterWorkRequest.getWorkRequestOwningDeptId());
//                    workRequest.setWorkRequestOwningDept(masterWorkRequest.getWorkRequestOwningDept());
//                    workRequest.setExpectedResolutionDate(masterWorkRequest.getExpectedResolutionDate());
//                    workRequest.setWipEntityId(masterWorkRequest.getWipEntityId());
//                    workRequest.setWipEntityName(masterWorkRequest.getWipEntityName());
//                    workRequest.setAttributeCategory(masterWorkRequest.getAttributeCategory());
//                    workRequest.setAttribute1(masterWorkRequest.getAttribute1());
//                    workRequest.setAttribute2(masterWorkRequest.getAttribute2());
//                    workRequest.setAttribute3(masterWorkRequest.getAttribute3());
//                    workRequest.setAttribute4(masterWorkRequest.getAttribute4());
//                    workRequest.setAttribute5(masterWorkRequest.getAttribute5());
//                    workRequest.setAttribute6(masterWorkRequest.getAttribute6());
//                    workRequest.setAttribute7(masterWorkRequest.getAttribute7());
//                    workRequest.setAttribute8(masterWorkRequest.getAttribute8());
//                    workRequest.setAttribute9(masterWorkRequest.getAttribute9());
//                    workRequest.setAttribute10(masterWorkRequest.getAttribute10());
//                    workRequest.setAttribute11(masterWorkRequest.getAttribute11());
//                    workRequest.setAttribute12(masterWorkRequest.getAttribute12());
//                    workRequest.setAttribute13(masterWorkRequest.getAttribute13());
//                    workRequest.setAttribute14(masterWorkRequest.getAttribute14());
//                    workRequest.setAttribute15(masterWorkRequest.getAttribute15());
//                    workRequest.setWorkRequestAutoApprove(masterWorkRequest.getWorkRequestAutoApprove());
//                    workRequest.setWorkRequestTypeId(masterWorkRequest.getWorkRequestTypeId());
//                    workRequest.setWorkRequestType(masterWorkRequest.getWorkRequestType());
//                    workRequest.setWorkRequestCreatedBy(masterWorkRequest.getWorkRequestCreatedBy());
//                    workRequest.setAssetNumberDescription(masterWorkRequest.getAssetNumberDescription());
//                    workRequest.setAssetLocationId(masterWorkRequest.getAssetLocationId());
//                    workRequest.setAssetLocation(masterWorkRequest.getAssetLocation());
//                    workRequest.setAssetCategoryId(masterWorkRequest.getAssetCategoryId());
//                    workRequest.setExpectedStartDate(masterWorkRequest.getExpectedStartDate());
//                    workRequest.setAssetCriticality(masterWorkRequest.getAssetCriticality());
//
//                    // Save the new record in SAM database
//                    workRequestRepository.save(workRequest);
//                    System.out.println("Saved Work Request in SAM DB: " + workRequest.getWorkRequestNumber());
//                } else {
//                    System.out.println("Skipped duplicate Work Request in SAM DB: " + masterWorkRequest.getWorkRequestNumber());
//                }
//            }
//        }
//    }
//

    public ResponseEntity<ResponseModel<String>> addWorkRequest(@RequestBody AddWorkRequest1 data) {
        try {
            String url = "http://10.36.113.75:9054/soa-infra/resources/PM/XXHIL_EAM_MAXBYTE_WR_CREATE_UPD/WORK_REQUEST_CREATE_SERVICE/WORK_REQUEST_CREATE_RP";
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("P_ASSET_GROUP_ID", data.getP_ASSET_GROUP_ID());
            body.add("P_ASSET_NUMBER", data.getP_ASSET_NUMBER());
            body.add("P_CREATED_FOR", data.getP_CREATED_FOR());
            body.add("P_DESCRIPTION", data.getP_DESCRIPTION());
            body.add("P_EXPECTED_RESOLUTION_DATE", data.getP_EXPECTED_RESOLUTION_DATE());
            body.add("P_EXPECTED_START_DATE", data.getP_EXPECTED_START_DATE());
            body.add("P_E_MAIL", data.getP_E_MAIL());
            body.add("P_ORGANIZATION_ID", data.getP_ORGANIZATION_ID());
            body.add("P_OWNING_DEPARTMENT_ID", data.getP_OWNING_DEPARTMENT_ID());
            body.add("P_PHONE_NUMBER", data.getP_PHONE_NUMBER());
            body.add("P_USER_ID", data.getP_USER_ID());
            body.add("P_WORK_ATTACHMENT", data.getP_WORK_ATTACHMENT());
            body.add("P_WORK_CREATEDBY", data.getP_WORK_CREATEDBY());
            body.add("P_WORK_REQUEST_PRIORITY_ID", data.getP_WORK_REQUEST_PRIORITY_ID());
            body.add("P_WORK_REQUEST_TYPE_ID", data.getP_WORK_REQUEST_TYPE_ID());

            HttpEntity<MultiValueMap> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            System.out.println("Response Entity"+ responseEntity);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String response = responseEntity.getBody();

                if (!response.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                    JsonNode jsonNode = objectMapper.readTree(response);
                    String responseStatus = jsonNode.get("P_RETURN_STATUS").asText();

                    if (responseStatus != null && responseStatus.equals("S")) {
                        String requestNumber = jsonNode.get("P_WORK_REQUEST_NUMBER").asText();
                        System.out.println(requestNumber);

                        // Execute the method to fetch and save data
//                        fetchAndSaveData();

                        return ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseModel<>(true, "Successfully Created!.", requestNumber));
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ResponseModel<>(false, "Failed to process the response.", null));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseModel<>(false, "Empty response from the server.", null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseModel<>(false, "Failed to connect to the server.", null));
            }
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getStatusCode().toString(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel<>(false, "Unexpected error occurred.", null));
        }
    }


        public ResponseEntity<ResponseModel<String>> updateWorkRequest(@RequestBody UpdateWorkRequest data) {
            try {
                String url = "http://10.36.113.75:9054/soa-infra/resources/PM/XXHIL_EAM_MAXBYTE_WR_CREATE_UPD/WORK_REQUEST_UPDATE_SERVICE/WORK_REQUEST_UPDATE_RP";
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
                body.add("P_ASSET_GROUP_ID", data.getP_ASSET_GROUP_ID());
                body.add("P_ASSET_NUMBER", data.getP_ASSET_NUMBER());
                body.add("P_CREATED_FOR", data.getP_CREATED_FOR());
                body.add("P_DESCRIPTION", data.getP_DESCRIPTION());
                body.add("P_EXPECTED_RESOLUTION_DATE", data.getP_EXPECTED_RESOLUTION_DATE());
                body.add("P_EXPECTED_START_DATE", data.getP_EXPECTED_START_DATE());
                body.add("P_E_MAIL", data.getP_E_MAIL());
                body.add("P_ORGANIZATION_ID", data.getP_ORGANIZATION_ID());
                body.add("P_OWNING_DEPARTMENT_ID", data.getP_OWNING_DEPARTMENT_ID());
                body.add("P_PHONE_NUMBER", data.getP_PHONE_NUMBER());
                body.add("P_USER_ID", data.getP_USER_ID());
                body.add("P_WORK_ATTACHMENT", data.getP_WORK_ATTACHMENT());
                //body.add("P_WORK_CREATEDBY", data.getP_WORK_CREATEDBY());
                body.add("P_WORK_REQUEST_PRIORITY_ID", data.getP_WORK_REQUEST_PRIORITY_ID());
                body.add("P_WORK_REQUEST_TYPE_ID", data.getP_WORK_REQUEST_TYPE_ID());
                body.add("P_WORK_REQUEST_ID",data.getP_WORK_REQUEST_ID());


                HttpEntity<MultiValueMap> entity = new HttpEntity<>(body, headers);
                System.out.println("Entity :" + entity);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
                System.out.println("Response Entity"+ responseEntity);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    String response = responseEntity.getBody();

                    System.out.println("Response :"+response);

                    if (!response.isEmpty()) {
                        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                        JsonNode jsonNode = objectMapper.readTree(response);
                        String responseStatus = jsonNode.get("P_RETURN_STATUS").asText();

                        if (responseStatus != null && responseStatus.equals("S")) {
                            String requestNumber = jsonNode.get("P_WORK_REQUEST_NUMBER").asText();
                            System.out.println(requestNumber);

                            // Execute the method to fetch and save data
    //                        fetchAndSaveData();
//                            updateAndSaveData();

                            return ResponseEntity.status(HttpStatus.OK)
                                    .body(new ResponseModel<>(true, "Successfully Updated!.", requestNumber));
                        } else {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(new ResponseModel<>(false, "Failed to process the response.", null));
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new ResponseModel<>(false, "Empty response from the server.", null));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ResponseModel<>(false, "Failed to connect to the server.", null));
                }
            } catch (HttpStatusCodeException ex) {
                return ResponseEntity.status(ex.getStatusCode())
                        .body(new ResponseModel<>(false, "HTTP Error: " + ex.getStatusCode().toString(), null));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseModel<>(false, "Unexpected error occurred.", null));
            }
        }

//    private void updateAndSaveData() throws Exception {
//        // Get the latest record's last update date from our OracleDB repository
//        Optional<MasterWorkRequest> latestRecordOpt = masterWorkRequestRepository.findTopByOrderByLastUpdateDateDesc();
//        LocalDateTime lastUpdateDateTime = latestRecordOpt.map(MasterWorkRequest::getLastUpdateDate).orElse(null);
//
//        // Convert LocalDateTime to Timestamp to avoid precision issues
//        Timestamp lastUpdateTimestamp = (lastUpdateDateTime != null) ? Timestamp.valueOf(lastUpdateDateTime) : null;
//
//        // Query the external APPS.WIP_EAM_WORK_REQUESTS_V table for records updated after or at our latest update date
//        String sql = "SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V WHERE LAST_UPDATE_DATE >= ?";
//
//        List<MasterWorkRequest> newRecords;
//
//        if (lastUpdateTimestamp != null) {
//            newRecords = jdbcTemplate.query(
//                    sql,
//                    new Object[]{lastUpdateTimestamp},
//                    BeanPropertyRowMapper.newInstance(MasterWorkRequest.class)
//            );
//        } else {
//            // If there's no last update timestamp, fetch all records
//            newRecords = jdbcTemplate.query(
//                    "SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V",
//                    BeanPropertyRowMapper.newInstance(MasterWorkRequest.class)
//            );
//        }
//
//        // Save the fetched records to both local OracleDB and SAM DB
//        if (newRecords.isEmpty()) {
//            System.out.println("********************No new Work Requests found.");
//        } else {
//            System.out.println("********************New Work Requests found: " + newRecords.size());
//
//            for (MasterWorkRequest masterWorkRequest : newRecords) {
//                // Save to local OracleDB
//                boolean existsInMaster = masterWorkRequestRepository.existsByWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
//                if (!existsInMaster) {
//                    masterWorkRequestRepository.save(masterWorkRequest);
//                    System.out.println("Saved Work Request in Master DB: " + masterWorkRequest.getWorkRequestNumber());
//                } else {
//                    System.out.println("Skipped duplicate Work Request in Master DB: " + masterWorkRequest.getWorkRequestNumber());
//                }
//
//                EmployeeDetails employeeDetails = employeeDetailsRepository.findById(masterWorkRequest.getCreatedBy()).orElse(null);
//                EmployeeDetails createdByName = employeeDetailsRepository.findById(masterWorkRequest.getWorkRequestCreatedBy()).orElse(null);
//
//                WorkRequest workRequest;
//                Asset assetNumber = assetRepository.findByAssetNumber(masterWorkRequest.getAssetNumber());
//
//                String workRequestNumber = masterWorkRequest.getWorkRequestNumber();
//
//                Optional<WorkRequest> optionalWorkRequest = workRequestRepository.findByWorkRequestNumber(workRequestNumber);
//
//
//                if (optionalWorkRequest.isPresent()) {
//                    workRequest = optionalWorkRequest.get();
//                    workRequest.setRowId(masterWorkRequest.getRowId());
//                    workRequest.setWorkRequestId(masterWorkRequest.getWorkRequestId());
//                    workRequest.setWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
//                    workRequest.setDescription(masterWorkRequest.getDescription());
//                    workRequest.setLastUpdateDate(masterWorkRequest.getLastUpdateDate());
//                    workRequest.setLastUpdatedBy(masterWorkRequest.getLastUpdatedBy());
//                    workRequest.setCreationDate(masterWorkRequest.getCreationDate()); // You may still want to store CreationDate if needed
//                    workRequest.setCreatedBy(masterWorkRequest.getCreatedBy());
//                    if (employeeDetails != null) {
//                        workRequest.setCreatedByName(employeeDetails.getFullName()); // Assuming you want to use the employee's full name
//                    } else {
//                        workRequest.setCreatedByName(""); // Fallback value if employee details are not found
//                    }
//
//                    if (createdByName != null) {
//                        workRequest.setWorkRequestCreatedByName(createdByName.getFullName()); // Assuming you want to use the employee's full name
//                    } else {
//                        workRequest.setWorkRequestCreatedByName(""); // Fallback value if employee details are not found
//                    }
//
//                    if (assetNumber != null && assetNumber.getAssetNumber().equals(masterWorkRequest.getAssetNumber())) {
//                        workRequest.setAssetId(assetNumber.getId()); // Set assetGroup from Asset to WorkRequest
//                        workRequest.setAssetGroupId(assetNumber.getAssetGroupId()); // Set assetGroup from Asset to WorkRequest
//                        workRequest.setAssetGroup(assetNumber.getAssetGroup()); // Set assetGroup from Asset to WorkRequest
//                    }
//
//                    workRequest.setLastUpdateLogin(masterWorkRequest.getLastUpdateLogin());
//                    workRequest.setAssetNumber(masterWorkRequest.getAssetNumber());
//                    workRequest.setOrganizationId(masterWorkRequest.getOrganizationId());
//                    workRequest.setWorkRequestStatusId(masterWorkRequest.getWorkRequestStatusId());
//                    workRequest.setWorkRequestStatus(masterWorkRequest.getWorkRequestStatus());
//                    workRequest.setWorkRequestPriorityId(masterWorkRequest.getWorkRequestPriorityId());
//                    workRequest.setWorkRequestPriority(masterWorkRequest.getWorkRequestPriority());
//                    workRequest.setWorkRequestOwningDeptId(masterWorkRequest.getWorkRequestOwningDeptId());
//                    workRequest.setWorkRequestOwningDept(masterWorkRequest.getWorkRequestOwningDept());
//                    workRequest.setExpectedResolutionDate(masterWorkRequest.getExpectedResolutionDate());
//                    workRequest.setWipEntityId(masterWorkRequest.getWipEntityId());
//                    workRequest.setWipEntityName(masterWorkRequest.getWipEntityName());
//                    workRequest.setAttributeCategory(masterWorkRequest.getAttributeCategory());
//                    workRequest.setAttribute1(masterWorkRequest.getAttribute1());
//                    workRequest.setAttribute2(masterWorkRequest.getAttribute2());
//                    workRequest.setAttribute3(masterWorkRequest.getAttribute3());
//                    workRequest.setAttribute4(masterWorkRequest.getAttribute4());
//                    workRequest.setAttribute5(masterWorkRequest.getAttribute5());
//                    workRequest.setAttribute6(masterWorkRequest.getAttribute6());
//                    workRequest.setAttribute7(masterWorkRequest.getAttribute7());
//                    workRequest.setAttribute8(masterWorkRequest.getAttribute8());
//                    workRequest.setAttribute9(masterWorkRequest.getAttribute9());
//                    workRequest.setAttribute10(masterWorkRequest.getAttribute10());
//                    workRequest.setAttribute11(masterWorkRequest.getAttribute11());
//                    workRequest.setAttribute12(masterWorkRequest.getAttribute12());
//                    workRequest.setAttribute13(masterWorkRequest.getAttribute13());
//                    workRequest.setAttribute14(masterWorkRequest.getAttribute14());
//                    workRequest.setAttribute15(masterWorkRequest.getAttribute15());
//                    workRequest.setWorkRequestAutoApprove(masterWorkRequest.getWorkRequestAutoApprove());
//                    workRequest.setWorkRequestTypeId(masterWorkRequest.getWorkRequestTypeId());
//                    workRequest.setWorkRequestType(masterWorkRequest.getWorkRequestType());
//                    workRequest.setWorkRequestCreatedBy(masterWorkRequest.getWorkRequestCreatedBy());
//                    workRequest.setAssetNumberDescription(masterWorkRequest.getAssetNumberDescription());
//                    workRequest.setAssetLocationId(masterWorkRequest.getAssetLocationId());
//                    workRequest.setAssetLocation(masterWorkRequest.getAssetLocation());
//                    workRequest.setAssetCategoryId(masterWorkRequest.getAssetCategoryId());
//                    workRequest.setExpectedStartDate(masterWorkRequest.getExpectedStartDate());
//                    workRequest.setAssetCriticality(masterWorkRequest.getAssetCriticality());
//
//                    // Save the new record in SAM database
//                    workRequestRepository.save(workRequest);
//                    System.out.println("Skipped duplicate Work Request in SAM DB: " + masterWorkRequest.getWorkRequestNumber());
//                }
//            }
//        }
//    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public ResponseModel<List<GetWorkRequestListEAM>> listWorkRequest(String startDateStr, String endDateStr, String organizationId) {

        LocalDateTime startDate = LocalDateTime.parse(startDateStr, FORMATTER);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr, FORMATTER);

        // Convert LocalDateTime to Timestamp
        Timestamp startTimestamp = Timestamp.valueOf(startDate);
        Timestamp endTimestamp = Timestamp.valueOf(endDate);


        String sql = "SELECT * FROM (" +
                "    SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V" +
                "    WHERE CREATION_DATE BETWEEN ? AND ?" +
                "    AND ORGANIZATION_ID = ?" +
                "    ORDER BY CREATION_DATE DESC" +
                ") WHERE ROWNUM <= 100 " +
                "ORDER BY CREATION_DATE DESC";


//        String sql = "SELECT * FROM APPS.EAM_WORK_ORDERS_V " +
//                "WHERE (CREATION_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')) " +
//                "AND (ORGANIZATION_ID = ?) " +
//                "ORDER BY CREATION_DATE DESC";
        System.out.println("Length "+sql.length());

        List<GetWorkRequestListEAM> results = jdbcTemplate.query(sql, new Object[]{startTimestamp, endTimestamp, organizationId},
                (rs, rowNum) -> {

                    FNDUser createdByName = fndUserRepository.findByUserId(Integer.parseInt(rs.getString("CREATED_BY")!=null?rs.getString("CREATED_BY"):"0")).orElse(null);

                    GetWorkRequestListEAM workRequest = new GetWorkRequestListEAM();
                    workRequest.setWrNumber(rs.getString("WORK_REQUEST_NUMBER"));
                    workRequest.setAssetNumber(rs.getString("ASSET_NUMBER"));
                    workRequest.setCreationDate(rs.getString("CREATION_DATE"));
                    workRequest.setOwningDepartment(rs.getString("WORK_REQUEST_OWNING_DEPT"));
                    workRequest.setRequestType(rs.getString("WORK_REQUEST_TYPE"));

                    if (createdByName != null) {
                        workRequest.setCreatedBy(createdByName.getUserName());
                    } else {
                        workRequest.setCreatedBy("");
                    }
                    workRequest.setWorkRequestStatus(rs.getString("WORK_REQUEST_STATUS"));


                    return workRequest;
                }

        );
//        System.out.println(results);

        System.out.println("WorkRequest List = "+results.size());

        return new ResponseModel<>(true, "Success", results);
    }



    public ResponseModel<List<GetWorkRequestListByWorkRequestNo>> listWorkRequestListByWorkRequestNo(String workRequestNumber) {
        String sql = "SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V WHERE WORK_REQUEST_NUMBER = ?";

        List<GetWorkRequestListByWorkRequestNo> results = jdbcTemplate.query(sql, new Object[]{workRequestNumber},
                (rs, rowNum) -> {

                    FNDUser employeeDetails = fndUserRepository.findByUserId(Integer.parseInt(rs.getString("CREATED_BY")!=null?rs.getString("CREATED_BY"):"0")).orElse(null);
                    FNDUser createdByName = fndUserRepository.findByUserId(Integer.parseInt(rs.getString("WORK_REQUEST_CREATED_BY")!=null?rs.getString("WORK_REQUEST_CREATED_BY"):"0")).orElse(null);

                    Asset assetNumber = assetRepository.findByAssetNumber(rs.getString("ASSET_NUMBER")!=null?rs.getString("ASSET_NUMBER"):"0");


                    GetWorkRequestListByWorkRequestNo workRequest = new GetWorkRequestListByWorkRequestNo();
                    workRequest.setRowId(rs.getString("ROW_ID"));
                    workRequest.setWorkRequestId(rs.getString("WORK_REQUEST_ID"));
                    workRequest.setWorkRequestNumber(rs.getString("WORK_REQUEST_NUMBER"));
                    workRequest.setDescription(rs.getString("DESCRIPTION"));
                    workRequest.setLastUpdateDate(rs.getString("LAST_UPDATE_DATE"));
                    workRequest.setLastUpdatedBy(rs.getString("LAST_UPDATED_BY"));
                    workRequest.setCreationDate(rs.getString("CREATION_DATE"));
                    workRequest.setCreatedBy(rs.getString("CREATED_BY"));
                    workRequest.setLastUpdatedBy(rs.getString("LAST_UPDATE_LOGIN"));
                    workRequest.setAssetNumber(rs.getString("ASSET_NUMBER"));
                    workRequest.setAssetGroupId(rs.getString("ASSET_GROUP_ID"));
                    workRequest.setOrganizationId(rs.getString("ORGANIZATION_ID"));
                    workRequest.setWorkRequestStatusId(rs.getString("WORK_REQUEST_STATUS_ID"));
                    workRequest.setWorkRequestStatus(rs.getString("WORK_REQUEST_STATUS"));
                    workRequest.setWorkRequestPriorityId(rs.getString("WORK_REQUEST_PRIORITY_ID"));
                    workRequest.setWorkRequestPriority(rs.getString("WORK_REQUEST_PRIORITY"));
                    workRequest.setWorkRequestOwningDeptId(rs.getString("WORK_REQUEST_OWNING_DEPT_ID"));
                    workRequest.setWorkRequestOwningDept(rs.getString("WORK_REQUEST_OWNING_DEPT"));
                    workRequest.setExpectedResolutionDate(rs.getString("EXPECTED_RESOLUTION_DATE"));
                    workRequest.setWipEntityId(rs.getString("WIP_ENTITY_ID"));
                    workRequest.setWipEntityName(rs.getString("WIP_ENTITY_NAME"));
                    workRequest.setAttributeCategory(rs.getString("ATTRIBUTE_CATEGORY"));
                    workRequest.setWorkRequestAutoApprove(rs.getString("WORK_REQUEST_AUTO_APPROVE"));
                    workRequest.setWorkRequestTypeId(rs.getString("WORK_REQUEST_TYPE_ID"));
                    workRequest.setWorkRequestType(rs.getString("WORK_REQUEST_TYPE"));
                    workRequest.setWorkRequestCreatedBy(rs.getString("WORK_REQUEST_CREATED_BY"));
                    workRequest.setAssetNumberDescription(rs.getString("ASSET_NUMBER_DESCRIPTION"));
                    workRequest.setAssetLocationId(rs.getString("ASSET_LOCATION_ID"));
                    workRequest.setAssetLocation(rs.getString("ASSET_LOCATION"));
                    workRequest.setAssetCategoryId(rs.getString("ASSET_CATEGORY_ID"));
                    workRequest.setExpectedStartDate(rs.getString("EXPECTED_START_DATE"));
                    workRequest.setAssetCriticality(rs.getString("ASSET_CRITICALITY"));

                    if (employeeDetails != null) {
                        workRequest.setCreatedByName(employeeDetails.getUserName()); // Assuming you want to use the employee's full name
                    } else {
                        workRequest.setCreatedByName(""); // Fallback value if employee details are not found
                    }

                    if (createdByName != null) {
                        workRequest.setWorkRequestCreatedByName(createdByName.getUserName()); // Assuming you want to use the employee's full name
                    } else {
                        workRequest.setWorkRequestCreatedByName(""); // Fallback value if employee details are not found
                    }

                    if (assetNumber != null && assetNumber.getAssetNumber().equals(rs.getString("ASSET_NUMBER"))) {
                        workRequest.setAssetGroupId(assetNumber.getAssetGroupId().toString()); // Set assetGroup from Asset to WorkRequest
                        workRequest.setAssetGroup(assetNumber.getAssetGroup()); // Set assetGroup from Asset to WorkRequest
                    }

                    return workRequest;
                }

        );
        System.out.println("WorkRequest List = "+results.size());

        return new ResponseModel<>(true, "Success", results);
    }


    public ResponseModel<List<String>> getSearchWorkRequestNumber(String workRequestNumber) {
        ResponseModel<List<String>> response;

        try {
            StringBuilder sql = new StringBuilder("SELECT DISTINCT WORK_REQUEST_NUMBER FROM APPS.WIP_EAM_WORK_REQUESTS_V ");

            List<Object> params = new ArrayList<>();

            if (workRequestNumber != null && !workRequestNumber.trim().isEmpty()) {
                sql.append("WHERE WORK_REQUEST_NUMBER LIKE ? ");
                params.add("%" + workRequestNumber + "%");
            }

            sql.append("ORDER BY CREATION_DATE DESC ");

            if (workRequestNumber == null || workRequestNumber.trim().isEmpty()) {
                // When no input is provided, fetch the last 50 rows
                sql.append("OFFSET 0 ROWS FETCH NEXT 50 ROWS ONLY");
            } else {
                // When input is provided, limit the results accordingly
                sql.append("FETCH FIRST 50 ROWS ONLY");
            }

            List<String> workRequestList = jdbcTemplate.queryForList(sql.toString(), params.toArray(), String.class);

            if (!workRequestList.isEmpty()) {
                response = new ResponseModel<>(true, "WorkRequestNumber(s) found.", workRequestList);
                System.out.println("Total WorkRequestNumber(s) = " + workRequestList.size());
            } else {
                response = new ResponseModel<>(true, "No WorkRequestNumber found for the given name.", null);
            }
        } catch (Exception e) {
            response = new ResponseModel<>(false, "An error occurred while retrieving the records.", null);
            e.printStackTrace();
        }

        return response;
    }



//    public ResponseModel<List<GetWorkRequestListEAM>> getSearchFilters(String workRequestNumber, String assetNumber, String department, String status, String additionalDescription) {
//
//        StringBuilder sql = new StringBuilder("SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V WHERE 1=1");
//
//        List<Object> params = new ArrayList<>();
//
//        if (workRequestNumber!=null) {
//            sql.append(" AND WORK_REQUEST_NUMBER = ?");
//            params.add(workRequestNumber);
//        }
//        if (assetNumber!=null) {
//            sql.append(" AND ASSET_NUMBER = ?");
//            params.add(assetNumber);
//        }
//        if (department!=null) {
//            sql.append(" AND WORK_REQUEST_OWNING_DEPT = ?");
//            params.add(department);
//        }
//        if (status!=null) {
//            sql.append(" AND WORK_REQUEST_STATUS = ?");
//            params.add(status);
//        }
//        if (additionalDescription!=null) {
//            sql.append(" AND DESCRIPTION = ?");
//            params.add(additionalDescription);
//        }
//
//
//        List<GetWorkRequestListEAM> results = jdbcTemplate.query(sql.toString(), params.toArray(),
//                (rs, rowNum) -> {
//
//                    FNDUser createdByName = fndUserRepository.findByUserId(Integer.parseInt(rs.getString("CREATED_BY")!=null?rs.getString("CREATED_BY"):"0")).orElse(null);
//
//
//                    GetWorkRequestListEAM workRequest = new GetWorkRequestListEAM();
//
//                    workRequest.setWrNumber(rs.getString("WORK_REQUEST_NUMBER"));
//                    workRequest.setAssetNumber(rs.getString("ASSET_NUMBER"));
//                    workRequest.setCreationDate(rs.getString("CREATION_DATE"));
//                    workRequest.setOwningDepartment(rs.getString("WORK_REQUEST_OWNING_DEPT"));
//                    workRequest.setRequestType(rs.getString("WORK_REQUEST_TYPE"));
//
//                    if (createdByName != null) {
//                        workRequest.setCreatedBy(createdByName.getUserName());
//                    } else {
//                        workRequest.setCreatedBy("");
//                    }
//
//                    return workRequest;
//                });
//
//        return new ResponseModel<>(true, "Success", results);
//    }

//    public ResponseModel<List<GetWorkRequestListEAM>> getSearchFilters(String workRequestNumber, String assetNumber, String department, String status, String additionalDescription) {
//
//        StringBuilder sql = new StringBuilder("SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V WHERE 1=1");
//
//        List<Object> params = new ArrayList<>();
//
//        boolean hasFilters = false;
//
//        if (workRequestNumber != null) {
//            sql.append(" AND WORK_REQUEST_NUMBER = ?");
//            params.add(workRequestNumber);
//            hasFilters = true;
//        }
//        if (assetNumber != null) {
//            sql.append(" AND ASSET_NUMBER = ?");
//            params.add(assetNumber);
//            hasFilters = true;
//        }
//        if (department != null) {
//            sql.append(" AND WORK_REQUEST_OWNING_DEPT = ?");
//            params.add(department);
//            hasFilters = true;
//        }
//        if (status != null) {
//            sql.append(" AND WORK_REQUEST_STATUS = ?");
//            params.add(status);
//            hasFilters = true;
//        }
//        if (additionalDescription != null) {
//            sql.append(" AND DESCRIPTION = ?");
//            params.add(additionalDescription);
//            hasFilters = true;
//        }
//
//        if (!hasFilters) {
//            sql.append(" ORDER BY CREATION_DATE DESC OFFSET 0 ROWS FETCH NEXT 50 ROWS ONLY");
//        }
//
//        List<GetWorkRequestListEAM> results = jdbcTemplate.query(sql.toString(), params.toArray(),
//                (rs, rowNum) -> {
//
//                    FNDUser createdByName = fndUserRepository.findByUserId(Integer.parseInt(rs.getString("CREATED_BY")!=null?rs.getString("CREATED_BY"):"0")).orElse(null);
//
//                    GetWorkRequestListEAM workRequest = new GetWorkRequestListEAM();
//                    workRequest.setWrNumber(rs.getString("WORK_REQUEST_NUMBER"));
//                    workRequest.setAssetNumber(rs.getString("ASSET_NUMBER"));
//                    workRequest.setCreationDate(rs.getString("CREATION_DATE"));
//                    workRequest.setOwningDepartment(rs.getString("WORK_REQUEST_OWNING_DEPT"));
//                    workRequest.setRequestType(rs.getString("WORK_REQUEST_TYPE"));
//                    if (createdByName != null) {
//                        workRequest.setCreatedBy(createdByName.getUserName());
//                    } else {
//                        workRequest.setCreatedBy("");
//                    }
//
//                    return workRequest;
//                });
//
//        return new ResponseModel<>(true, "Success", results);
//    }


//    public ResponseModel<List<GetWorkRequestListEAM>> getSearchFilters(
//            String workRequestNumber, String assetNumber, String department, String status, String additionalDescription) {
//
//        Logger logger = LoggerFactory.getLogger(getClass());
//
//        // Start building the SQL query
//        StringBuilder sql = new StringBuilder("SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V WHERE 1=1");
//
//        // List to hold query parameters
//        List<Object> params = new ArrayList<>();
//
//        // Boolean flag to check if filters are applied
//        boolean hasFilters = false;
//
//        // Append conditions based on provided parameters
//        if (workRequestNumber != null && !workRequestNumber.trim().isEmpty()) {
//            sql.append(" AND WORK_REQUEST_NUMBER = ?");
//            params.add(workRequestNumber);
//            hasFilters = true;
//        }
//        if (assetNumber != null && !assetNumber.trim().isEmpty()) {
//            sql.append(" AND ASSET_NUMBER = ?");
//            params.add(assetNumber);
//            hasFilters = true;
//        }
//        if (department != null && !department.trim().isEmpty()) {
//            sql.append(" AND WORK_REQUEST_OWNING_DEPT = ?");
//            params.add(department);
//            hasFilters = true;
//        }
//        if (status != null && !status.trim().isEmpty()) {
//            sql.append(" AND WORK_REQUEST_STATUS = ?");
//            params.add(status);
//            hasFilters = true;
//        }
//        if (additionalDescription != null && !additionalDescription.trim().isEmpty()) {
//            sql.append(" AND DESCRIPTION = ?");
//            params.add(additionalDescription);
//            hasFilters = true;
//        }
//
//        // If no filters were applied, add pagination
//        if (!hasFilters) {
//            sql.append(" ORDER BY CREATION_DATE DESC OFFSET 0 ROWS FETCH NEXT 50 ROWS ONLY");
//        }
//
//        // Log the final SQL query and parameters
//        logger.debug("Executing SQL query: {}", sql.toString());
//        logger.debug("With parameters: {}", params);
//
//        // Execute the query
//        List<GetWorkRequestListEAM> results = jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
//
//            // Fetch the createdBy user from the repository
//            FNDUser createdByName = fndUserRepository.findByUserId(
//                            Integer.parseInt(rs.getString("CREATED_BY") != null ? rs.getString("CREATED_BY") : "0"))
//                    .orElse(null);
//
//            // Map the result set to GetWorkRequestListEAM object
//            GetWorkRequestListEAM workRequest = new GetWorkRequestListEAM();
//            workRequest.setWrNumber(rs.getString("WORK_REQUEST_NUMBER"));
//            workRequest.setAssetNumber(rs.getString("ASSET_NUMBER"));
//            workRequest.setCreationDate(rs.getString("CREATION_DATE"));
//            workRequest.setOwningDepartment(rs.getString("WORK_REQUEST_OWNING_DEPT"));
//            workRequest.setRequestType(rs.getString("WORK_REQUEST_TYPE"));
//            workRequest.setCreatedBy(createdByName != null ? createdByName.getUserName() : "");
//
//            return workRequest;
//        });
//
//        // Return the response model with the query results
//        return new ResponseModel<>(true, "Success", results);
//    }


    public ResponseModel<List<GetWorkRequestListEAM>> getSearchFilters(
            String workRequestNumber, String assetNumber, String department, String status, String additionalDescription) {

        Logger logger = LoggerFactory.getLogger(getClass());

        // Start building the SQL query
        StringBuilder sql = new StringBuilder("SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V WHERE 1=1");

        // List to hold query parameters
        List<Object> params = new ArrayList<>();

        // Boolean flag to check if filters are applied
        boolean hasFilters = false;

        // Append conditions based on provided parameters
        if (workRequestNumber != null && !workRequestNumber.trim().isEmpty()) {
            sql.append(" AND WORK_REQUEST_NUMBER = ?");
            params.add(workRequestNumber);
            hasFilters = true;
        }
        if (assetNumber != null && !assetNumber.trim().isEmpty()) {
            sql.append(" AND ASSET_NUMBER = ?");
            params.add(assetNumber);
            hasFilters = true;
        }
        if (department != null && !department.trim().isEmpty()) {
            sql.append(" AND WORK_REQUEST_OWNING_DEPT = ?");
            params.add(department);
            hasFilters = true;
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND WORK_REQUEST_STATUS = ?");
            params.add(status);
            hasFilters = true;
        }
        if (additionalDescription != null && !additionalDescription.trim().isEmpty()) {
            sql.append(" AND DESCRIPTION = ?");
            params.add(additionalDescription);
            hasFilters = true;
        }

        // If only status is provided, limit the number of results
        if (hasFilters && status != null && !status.trim().isEmpty() && (workRequestNumber == null || assetNumber == null || department == null || additionalDescription == null)) {
            sql.append(" ORDER BY CREATION_DATE DESC FETCH FIRST 100 ROWS ONLY");
        } else if (!hasFilters) {
            sql.append(" ORDER BY CREATION_DATE DESC OFFSET 0 ROWS FETCH NEXT 50 ROWS ONLY");
        }




        // Log the final SQL query and parameters
        logger.debug("Executing SQL query: {}", sql.toString());
        logger.debug("With parameters: {}", params);

        List<GetWorkRequestListEAM> results;
        try {
            // Execute the query
            results = jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
                // Fetch the createdBy user from the repository
                FNDUser createdByName = fndUserRepository.findByUserId(
                                Integer.parseInt(rs.getString("CREATED_BY") != null ? rs.getString("CREATED_BY") : "0"))
                        .orElse(null);

                // Map the result set to GetWorkRequestListEAM object
                GetWorkRequestListEAM workRequest = new GetWorkRequestListEAM();
                workRequest.setWrNumber(rs.getString("WORK_REQUEST_NUMBER"));
                workRequest.setAssetNumber(rs.getString("ASSET_NUMBER"));
                workRequest.setCreationDate(rs.getString("CREATION_DATE"));
                workRequest.setOwningDepartment(rs.getString("WORK_REQUEST_OWNING_DEPT"));
                workRequest.setRequestType(rs.getString("WORK_REQUEST_TYPE"));
                workRequest.setWorkRequestStatus(rs.getString("WORK_REQUEST_STATUS"));
                workRequest.setCreatedBy(createdByName != null ? createdByName.getUserName() : "");

                return workRequest;
            });
        } catch (Exception e) {
            logger.error("Error executing SQL query", e);
            // Handle the error and return a response indicating failure
            return new ResponseModel<>(false, "Error executing query", Collections.emptyList());
        }

        // Return the response model with the query results
        return new ResponseModel<>(true, "Success", results);
    }




}
