//package com.maxbyte.sam.OracleDBFlow.Service;
//
//import com.maxbyte.sam.OracleDBFlow.Entity.MasterAsset;
//import com.maxbyte.sam.OracleDBFlow.Entity.MasterUserInfo;
//import com.maxbyte.sam.OracleDBFlow.Entity.MasterWorkRequest;
//import com.maxbyte.sam.OracleDBFlow.Repository.MasterWorkRequestRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.EmployeeDetails;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.EmployeeDetailsRepository;
//import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Entity.WorkRequest;
//import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Repository.WorkRequestRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class MasterWorkRequestService /*implements CommandLineRunner*/ {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private MasterWorkRequestRepository masterWorkRequestRepository;
//    @Autowired
//    private WorkRequestRepository workRequestRepository;
//    @Autowired
//    private AssetRepository assetRepository;
////    @Autowired
////    private MasterUserInfo masterUserInfo;
//    @Autowired
//    private EmployeeDetailsRepository employeeDetailsRepository;
//
//
//    @Scheduled(cron = "0 */2 * * * *")
//    public void addMasterAssetGroupDataAutomaticallys(){
//        List<MasterWorkRequest> masterWorkRequests = masterWorkRequestRepository.findAll();
//
//        for(MasterWorkRequest masterWorkRequest :masterWorkRequests){
//
//            Optional<WorkRequest> optionalWorkRequest = workRequestRepository.findByRowId(masterWorkRequest.getRowId());
//            Asset assetDetails = assetRepository.findByAssetGroupId(masterWorkRequest.getAssetGroupId());
//
//            WorkRequest workRequest;
//            if (optionalWorkRequest.isPresent()) {
//                workRequest = optionalWorkRequest.get();
//                workRequest.setRowId(masterWorkRequest.getRowId());
//                workRequest.setWorkRequestId(masterWorkRequest.getWorkRequestId());
//                workRequest.setWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
//                workRequest.setDescription(masterWorkRequest.getDescription());
//                workRequest.setLastUpdateDate(masterWorkRequest.getLastUpdateDate());
//                workRequest.setLastUpdatedBy(masterWorkRequest.getLastUpdatedBy());
//                workRequest.setCreationDate(masterWorkRequest.getCreationDate());
//                workRequest.setCreatedBy(masterWorkRequest.getCreatedBy());
//                workRequest.setLastUpdateLogin(masterWorkRequest.getLastUpdateLogin());
//                workRequest.setAssetNumber(masterWorkRequest.getAssetNumber());
//                workRequest.setAssetGroupId(masterWorkRequest.getAssetGroupId());
//                // Set assetGroup value if assetGroupId matches
//                if (assetDetails != null && assetDetails.getAssetGroupId().equals(masterWorkRequest.getAssetGroupId())) {
//                    workRequest.setAssetGroup(assetDetails.getAssetGroup()); // Set assetGroup from Asset to WorkRequest
//                } else {
//                    workRequest.setAssetGroup(""); // Fallback value if asset details are not found or IDs do not match
//                }
//                workRequest.setOrganizationId(masterWorkRequest.getOrganizationId());
//                workRequest.setWorkRequestStatusId(masterWorkRequest.getWorkRequestStatusId());
//                workRequest.setWorkRequestStatus(masterWorkRequest.getWorkRequestStatus());
//                workRequest.setWorkRequestPriorityId(masterWorkRequest.getWorkRequestPriorityId());
//                workRequest.setWorkRequestPriority(masterWorkRequest.getWorkRequestPriority());
//                workRequest.setWorkRequestOwningDeptId(masterWorkRequest.getWorkRequestOwningDeptId());
//                workRequest.setWorkRequestOwningDept(masterWorkRequest.getWorkRequestOwningDept());
//                workRequest.setExpectedResolutionDate(masterWorkRequest.getExpectedResolutionDate());
//                workRequest.setWipEntityId(masterWorkRequest.getWipEntityId());
//                workRequest.setWipEntityName(masterWorkRequest.getWipEntityName());
//                workRequest.setAttributeCategory(masterWorkRequest.getAttributeCategory());
//                workRequest.setWorkRequestAutoApprove(masterWorkRequest.getWorkRequestAutoApprove());
//                workRequest.setWorkRequestTypeId(masterWorkRequest.getWorkRequestTypeId());
//                workRequest.setWorkRequestType(masterWorkRequest.getWorkRequestType());
//                workRequest.setWorkRequestCreatedBy(masterWorkRequest.getWorkRequestCreatedBy());
//                workRequest.setAssetNumberDescription(masterWorkRequest.getAssetNumberDescription());
//                workRequest.setAssetLocationId(masterWorkRequest.getAssetLocationId());
//                workRequest.setAssetLocation(masterWorkRequest.getAssetLocation());
//                workRequest.setAssetCategoryId(masterWorkRequest.getAssetCategoryId());
//                workRequest.setExpectedStartDate(masterWorkRequest.getExpectedStartDate());
//                workRequest.setAssetCriticality(masterWorkRequest.getAssetCriticality());
//
//                System.out.println("Updated assetGroup with AssetGroupId = " + workRequest.getWorkRequestId());
//            } else {
//                workRequest = new WorkRequest();
//                workRequest.setRowId(masterWorkRequest.getRowId());
//                workRequest.setWorkRequestId(masterWorkRequest.getWorkRequestId());
//                workRequest.setWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
//                workRequest.setDescription(masterWorkRequest.getDescription());
//                workRequest.setLastUpdateDate(masterWorkRequest.getLastUpdateDate());
//                workRequest.setLastUpdatedBy(masterWorkRequest.getLastUpdatedBy());
//                workRequest.setCreationDate(masterWorkRequest.getCreationDate());
//                workRequest.setCreatedBy(masterWorkRequest.getCreatedBy());
//                workRequest.setLastUpdateLogin(masterWorkRequest.getLastUpdateLogin());
//                workRequest.setAssetNumber(masterWorkRequest.getAssetNumber());
//                workRequest.setAssetGroupId(masterWorkRequest.getAssetGroupId());
//                // Set assetGroup value if assetGroupId matches
//                if (assetDetails != null && assetDetails.getAssetGroupId().equals(masterWorkRequest.getAssetGroupId())) {
//                    workRequest.setAssetGroup(assetDetails.getAssetGroup()); // Set assetGroup from Asset to WorkRequest
//                } else {
//                    workRequest.setAssetGroup(""); // Fallback value if asset details are not found or IDs do not match
//                }
//                workRequest.setOrganizationId(masterWorkRequest.getOrganizationId());
//                workRequest.setWorkRequestStatusId(masterWorkRequest.getWorkRequestStatusId());
//                workRequest.setWorkRequestStatus(masterWorkRequest.getWorkRequestStatus());
//                workRequest.setWorkRequestPriorityId(masterWorkRequest.getWorkRequestPriorityId());
//                workRequest.setWorkRequestPriority(masterWorkRequest.getWorkRequestPriority());
//                workRequest.setWorkRequestOwningDeptId(masterWorkRequest.getWorkRequestOwningDeptId());
//                workRequest.setWorkRequestOwningDept(masterWorkRequest.getWorkRequestOwningDept());
//                workRequest.setExpectedResolutionDate(masterWorkRequest.getExpectedResolutionDate());
//                workRequest.setWipEntityId(masterWorkRequest.getWipEntityId());
//                workRequest.setWipEntityName(masterWorkRequest.getWipEntityName());
//                workRequest.setAttributeCategory(masterWorkRequest.getAttributeCategory());
//                workRequest.setWorkRequestAutoApprove(masterWorkRequest.getWorkRequestAutoApprove());
//                workRequest.setWorkRequestTypeId(masterWorkRequest.getWorkRequestTypeId());
//                workRequest.setWorkRequestType(masterWorkRequest.getWorkRequestType());
//                workRequest.setWorkRequestCreatedBy(masterWorkRequest.getWorkRequestCreatedBy());
//                workRequest.setAssetNumberDescription(masterWorkRequest.getAssetNumberDescription());
//                workRequest.setAssetLocationId(masterWorkRequest.getAssetLocationId());
//                workRequest.setAssetLocation(masterWorkRequest.getAssetLocation());
//                workRequest.setAssetCategoryId(masterWorkRequest.getAssetCategoryId());
//                workRequest.setExpectedStartDate(masterWorkRequest.getExpectedStartDate());
//                workRequest.setAssetCriticality(masterWorkRequest.getAssetCriticality());
//                //  System.out.println("Inserted new assetGroup with assetGroupId = " + assetGroup1.getAssetGroupId());
//            }
//
//            workRequestRepository.save(workRequest);
//
//        }
//
//    }
//
//
//
////    @Override
////    public void run(String... args) throws Exception {
////        // Step 1: Get the latest record's creation date from our OracleDB repository
////        Optional<MasterWorkRequest> latestRecordOpt = masterWorkRequestRepository.findTopByOrderByCreationDateDesc();
////        LocalDateTime lastCreationDateTime = latestRecordOpt.map(MasterWorkRequest::getCreationDate).orElse(null);
////
////        // Convert LocalDateTime to Timestamp to avoid precision issues
////        Timestamp lastCreationTimestamp = (lastCreationDateTime != null) ? Timestamp.valueOf(lastCreationDateTime) : null;
////
////        // Step 2: Query the external APPS.WIP_EAM_WORK_REQUESTS_V table for records created after or at our latest creation date
////        String sql = "SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V WHERE CREATION_DATE >= ?";
////
////        List<MasterWorkRequest> newRecords;
////
////        if (lastCreationTimestamp != null) {
////            newRecords = jdbcTemplate.query(
////                    sql,
////                    new Object[]{lastCreationTimestamp},
////                    BeanPropertyRowMapper.newInstance(MasterWorkRequest.class)
////            );
////        } else {
////            // If there's no last creation timestamp, fetch all records
////            newRecords = jdbcTemplate.query(
////                    "SELECT * FROM APPS.WIP_EAM_WORK_REQUESTS_V",
////                    BeanPropertyRowMapper.newInstance(MasterWorkRequest.class)
////            );
////        }
////
////        // Step 3: Save the fetched records to both local OracleDB and SAM DB
////        if (newRecords.isEmpty()) {
////            System.out.println("********************No new Work Requests found.");
////        } else {
////            System.out.println("********************New Work Requests found: " + newRecords.size());
////
////            for (MasterWorkRequest masterWorkRequest : newRecords) {
////                // Save to local OracleDB
////                boolean existsInMaster = masterWorkRequestRepository.existsByWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
////                if (!existsInMaster) {
////                    masterWorkRequestRepository.save(masterWorkRequest);
////                    System.out.println("Saved Work Request in Master DB: " + masterWorkRequest.getWorkRequestNumber());
////                } else {
////                    System.out.println("Skipped duplicate Work Request in Master DB: " + masterWorkRequest.getWorkRequestNumber());
////                }
////
////
////
////                EmployeeDetails employeeDetails = employeeDetailsRepository.findById(masterWorkRequest.getCreatedBy()).orElse(null);
////                EmployeeDetails createdByName = employeeDetailsRepository.findById(masterWorkRequest.getWorkRequestCreatedBy()).orElse(null);
////
////                // Save to SAM DB
////                boolean existsInSam = workRequestRepository.existsByWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
////                if (!existsInSam) {
////
////                    WorkRequest workRequest = new WorkRequest();
////                    workRequest.setRowId(masterWorkRequest.getRowId());
////                    workRequest.setWorkRequestId(masterWorkRequest.getWorkRequestId());
////                    workRequest.setWorkRequestNumber(masterWorkRequest.getWorkRequestNumber());
////                    workRequest.setDescription(masterWorkRequest.getDescription());
////                    workRequest.setLastUpdateDate(masterWorkRequest.getLastUpdateDate()); // Ensure proper mapping
////                    workRequest.setLastUpdatedBy(masterWorkRequest.getLastUpdatedBy());
////                    workRequest.setCreationDate(masterWorkRequest.getCreationDate());
////                    workRequest.setCreatedBy(masterWorkRequest.getCreatedBy());// Assuming createdBy corresponds to P_USER_ID
//////                    workRequest.setCreatedByName(employeeDetails.get);
////
////                    if (employeeDetails != null) {
////                        workRequest.setCreatedByName(employeeDetails.getFullName()); // Assuming you want to use the employee's full name
////                    } else {
////                        workRequest.setCreatedByName(""); // Fallback value if employee details are not found
////                    }
////
////                    if (createdByName != null) {
////                        workRequest.setWorkRequestCreatedByName(createdByName.getFullName()); // Assuming you want to use the employee's full name
////                    } else {
////                        workRequest.setWorkRequestCreatedByName(""); // Fallback value if employee details are not found
////                    }
////
////                    workRequest.setLastUpdateLogin(masterWorkRequest.getLastUpdateLogin());
////                    workRequest.setAssetNumber(masterWorkRequest.getAssetNumber());
////                    workRequest.setAssetGroupId(masterWorkRequest.getAssetGroupId());
////                    workRequest.setOrganizationId(masterWorkRequest.getOrganizationId());
////                    workRequest.setWorkRequestStatusId(masterWorkRequest.getWorkRequestStatusId());
////                    workRequest.setWorkRequestStatus(masterWorkRequest.getWorkRequestStatus());
////                    workRequest.setWorkRequestPriorityId(masterWorkRequest.getWorkRequestPriorityId());
////                    workRequest.setWorkRequestPriority(masterWorkRequest.getWorkRequestPriority());
////                    workRequest.setWorkRequestOwningDeptId(masterWorkRequest.getWorkRequestOwningDeptId());
////                    workRequest.setWorkRequestOwningDept(masterWorkRequest.getWorkRequestOwningDept());
////                    workRequest.setExpectedResolutionDate(masterWorkRequest.getExpectedResolutionDate());
////                    workRequest.setWipEntityId(masterWorkRequest.getWipEntityId());
////                    workRequest.setWipEntityName(masterWorkRequest.getWipEntityName());
////                    workRequest.setAttributeCategory(masterWorkRequest.getAttributeCategory());
////                    workRequest.setAttribute1(masterWorkRequest.getAttribute1());
////                    workRequest.setAttribute2(masterWorkRequest.getAttribute2());
////                    workRequest.setAttribute3(masterWorkRequest.getAttribute3());
////                    workRequest.setAttribute4(masterWorkRequest.getAttribute4());
////                    workRequest.setAttribute5(masterWorkRequest.getAttribute5());
////                    workRequest.setAttribute6(masterWorkRequest.getAttribute6());
////                    workRequest.setAttribute7(masterWorkRequest.getAttribute7());
////                    workRequest.setAttribute8(masterWorkRequest.getAttribute8());
////                    workRequest.setAttribute9(masterWorkRequest.getAttribute9());
////                    workRequest.setAttribute10(masterWorkRequest.getAttribute10());
////                    workRequest.setAttribute11(masterWorkRequest.getAttribute11());
////                    workRequest.setAttribute12(masterWorkRequest.getAttribute12());
////                    workRequest.setAttribute13(masterWorkRequest.getAttribute13());
////                    workRequest.setAttribute14(masterWorkRequest.getAttribute14());
////                    workRequest.setAttribute15(masterWorkRequest.getAttribute15());
////                    workRequest.setWorkRequestAutoApprove(masterWorkRequest.getWorkRequestAutoApprove());
////                    workRequest.setWorkRequestTypeId(masterWorkRequest.getWorkRequestTypeId());
////                    workRequest.setWorkRequestType(masterWorkRequest.getWorkRequestType());
////                    workRequest.setWorkRequestCreatedBy(masterWorkRequest.getWorkRequestCreatedBy());
////                    workRequest.setAssetNumberDescription(masterWorkRequest.getAssetNumberDescription());
////                    workRequest.setAssetLocationId(masterWorkRequest.getAssetLocationId());
////                    workRequest.setAssetLocation(masterWorkRequest.getAssetLocation());
////                    workRequest.setAssetCategoryId(masterWorkRequest.getAssetCategoryId());
////                    workRequest.setExpectedStartDate(masterWorkRequest.getExpectedStartDate());
////                    workRequest.setAssetCriticality(masterWorkRequest.getAssetCriticality());
////
////                    // Save the new record in SAM database
////                    workRequestRepository.save(workRequest);
////                    System.out.println("Saved Work Request in SAM DB: " + workRequest.getWorkRequestNumber());
////                } else {
////                    System.out.println("Skipped duplicate Work Request in SAM DB: " + masterWorkRequest.getWorkRequestNumber());
////                }
////            }
////        }
////    }
//
//}
