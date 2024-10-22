//package com.maxbyte.sam.OracleDBFlow.Service;
//
//import com.maxbyte.sam.OracleDBFlow.Entity.MasterAssetActivity;
//import com.maxbyte.sam.OracleDBFlow.Repository.MasterAssetActivityRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class MasterAssetActivityService implements CommandLineRunner {
//
//    @Autowired
//    private MasterAssetActivityRepository masterAssetActivityRepository;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Override
//    public void run(String... args) throws Exception {
//
//        String sql =" SELECT * FROM APPS.MTL_EAM_ASSET_ACTIVITIES_V ";
//        List<MasterAssetActivity> results = jdbcTemplate.query(sql,
//                BeanPropertyRowMapper.newInstance(MasterAssetActivity.class));
////        students.forEach(System.out::println);
//
//        System.out.println("The Total Asset Activity are = " + results.size());
//
//        for (MasterAssetActivity newActivity : results) {
//
//            Integer assetActivityId = newActivity.getAssetActivityId();
//
//            Optional<MasterAssetActivity> masterAssetActivityOptional = masterAssetActivityRepository.findByAssetActivityId(assetActivityId);
//
//            MasterAssetActivity activity;
//            if(masterAssetActivityOptional.isPresent()) {
//                activity= masterAssetActivityOptional.get();
//
//                activity.setRowId(newActivity.getRowId());
//                activity.setActivityAssociationId(newActivity.getActivityAssociationId());
//                activity.setOrganizationId(newActivity.getOrganizationId());
//                activity.setSerialNumber(newActivity.getSerialNumber());
//                activity.setActivity(newActivity.getActivity());
//                activity.setActivityDescription(newActivity.getActivityDescription());
//                activity.setInstanceNumber(newActivity.getInstanceNumber());
//                activity.setInventoryItemId(newActivity.getInventoryItemId());
//                activity.setStartDateActive(newActivity.getStartDateActive());
//                activity.setEndDateActive(newActivity.getEndDateActive());
//                activity.setPriority(newActivity.getPriority());
//                activity.setPriorityCode(newActivity.getPriorityCode());
//                activity.setActivityCause(newActivity.getActivityCause());
//                activity.setActivityCauseCode(newActivity.getActivityCauseCode());
//                activity.setActivityType(newActivity.getActivityType());
//                activity.setActivityTypeCode(newActivity.getActivityTypeCode());
//                activity.setOwningDepartment(newActivity.getOwningDepartment());
//                activity.setOwningDepartmentId(newActivity.getOwningDepartmentId());
//                activity.setTaggingRequiredFlag(newActivity.getTaggingRequiredFlag());
//                activity.setShutdownType(newActivity.getShutdownType());
//                activity.setShutdownTypeCode(newActivity.getShutdownTypeCode());
//                activity.setAccountingClassCode(newActivity.getAccountingClassCode());
//                activity.setLastUpdateDate(newActivity.getLastUpdateDate());
//                activity.setLastUpdatedBy(newActivity.getLastUpdatedBy());
//                activity.setCreationDate(newActivity.getCreationDate());
//                activity.setCreatedBy(newActivity.getCreatedBy());
//                activity.setLastUpdateLogin(newActivity.getLastUpdateLogin());
//                activity.setAttributeCategory(newActivity.getAttributeCategory());
//                activity.setAttribute1(newActivity.getAttribute1());
//                activity.setAttribute2(newActivity.getAttribute2());
//                activity.setAttribute3(newActivity.getAttribute3());
//                activity.setAttribute4(newActivity.getAttribute4());
//                activity.setAttribute5(newActivity.getAttribute5());
//                activity.setAttribute6(newActivity.getAttribute6());
//                activity.setAttribute7(newActivity.getAttribute7());
//                activity.setAttribute8(newActivity.getAttribute8());
//                activity.setAttribute9(newActivity.getAttribute9());
//                activity.setAttribute10(newActivity.getAttribute10());
//                activity.setAttribute11(newActivity.getAttribute11());
//                activity.setAttribute12(newActivity.getAttribute12());
//                activity.setAttribute13(newActivity.getAttribute13());
//                activity.setAttribute14(newActivity.getAttribute14());
//                activity.setAttribute15(newActivity.getAttribute15());
//                activity.setRequestId(newActivity.getRequestId());
//                activity.setProgramApplicationId(newActivity.getProgramApplicationId());
//                activity.setProgramId(newActivity.getProgramId());
//                activity.setProgramUpdateDate(newActivity.getProgramUpdateDate());
//                activity.setMaintenanceObjectId(newActivity.getMaintenanceObjectId());
//                activity.setMaintenanceObjectType(newActivity.getMaintenanceObjectType());
//                activity.setCreationOrganizationId(newActivity.getCreationOrganizationId());
//                activity.setTemplateFlag(newActivity.getTemplateFlag());
//                activity.setActivitySource(newActivity.getActivitySource());
//                activity.setActivitySourceCode(newActivity.getActivitySourceCode());
//                activity.setLastServiceStartDate(newActivity.getLastServiceStartDate());
//                activity.setLastServiceEndDate(newActivity.getLastServiceEndDate());
//                activity.setAssetRebuildGroup(newActivity.getAssetRebuildGroup());
//                activity.setEamItemType(newActivity.getEamItemType());
//                activity.setWorkOrderTypeDisp(newActivity.getWorkOrderTypeDisp());
//                activity.setWorkOrderType(newActivity.getWorkOrderType());
//                activity.setPlannerMaintenanceDisp(newActivity.getPlannerMaintenanceDisp());
//                activity.setPlannerMaintenance(newActivity.getPlannerMaintenance());
//                activity.setFirmPlannedFlag(newActivity.getFirmPlannedFlag());
//                activity.setPlanMaintenance(newActivity.getPlanMaintenance());
//                activity.setNotificationRequired(newActivity.getNotificationRequired());
//            } else {
//                // If the asset activity does not exist, create a new one
//                activity = new MasterAssetActivity();
//                activity.setRowId(newActivity.getRowId());
//                activity.setActivityAssociationId(newActivity.getActivityAssociationId());
//                activity.setOrganizationId(newActivity.getOrganizationId());
//                activity.setAssetActivityId(newActivity.getAssetActivityId());
//                activity.setActivity(newActivity.getActivity());
//                activity.setActivityDescription(newActivity.getActivityDescription());
//                activity.setInstanceNumber(newActivity.getInstanceNumber());
//                activity.setInventoryItemId(newActivity.getInventoryItemId());
//                activity.setSerialNumber(newActivity.getSerialNumber());
//                activity.setStartDateActive(newActivity.getStartDateActive());
//                activity.setEndDateActive(newActivity.getEndDateActive());
//                activity.setPriority(newActivity.getPriority());
//                activity.setPriorityCode(newActivity.getPriorityCode());
//                activity.setActivityCause(newActivity.getActivityCause());
//                activity.setActivityCauseCode(newActivity.getActivityCauseCode());
//                activity.setActivityType(newActivity.getActivityType());
//                activity.setActivityTypeCode(newActivity.getActivityTypeCode());
//                activity.setOwningDepartment(newActivity.getOwningDepartment());
//                activity.setOwningDepartmentId(newActivity.getOwningDepartmentId());
//                activity.setTaggingRequiredFlag(newActivity.getTaggingRequiredFlag());
//                activity.setShutdownType(newActivity.getShutdownType());
//                activity.setShutdownTypeCode(newActivity.getShutdownTypeCode());
//                activity.setAccountingClassCode(newActivity.getAccountingClassCode());
//                activity.setLastUpdateDate(newActivity.getLastUpdateDate());
//                activity.setLastUpdatedBy(newActivity.getLastUpdatedBy());
//                activity.setCreationDate(newActivity.getCreationDate());
//                activity.setCreatedBy(newActivity.getCreatedBy());
//                activity.setLastUpdateLogin(newActivity.getLastUpdateLogin());
//                activity.setAttributeCategory(newActivity.getAttributeCategory());
//                activity.setAttribute1(newActivity.getAttribute1());
//                activity.setAttribute2(newActivity.getAttribute2());
//                activity.setAttribute3(newActivity.getAttribute3());
//                activity.setAttribute4(newActivity.getAttribute4());
//                activity.setAttribute5(newActivity.getAttribute5());
//                activity.setAttribute6(newActivity.getAttribute6());
//                activity.setAttribute7(newActivity.getAttribute7());
//                activity.setAttribute8(newActivity.getAttribute8());
//                activity.setAttribute9(newActivity.getAttribute9());
//                activity.setAttribute10(newActivity.getAttribute10());
//                activity.setAttribute11(newActivity.getAttribute11());
//                activity.setAttribute12(newActivity.getAttribute12());
//                activity.setAttribute13(newActivity.getAttribute13());
//                activity.setAttribute14(newActivity.getAttribute14());
//                activity.setAttribute15(newActivity.getAttribute15());
//                activity.setRequestId(newActivity.getRequestId());
//                activity.setProgramApplicationId(newActivity.getProgramApplicationId());
//                activity.setProgramId(newActivity.getProgramId());
//                activity.setProgramUpdateDate(newActivity.getProgramUpdateDate());
//                activity.setMaintenanceObjectId(newActivity.getMaintenanceObjectId());
//                activity.setMaintenanceObjectType(newActivity.getMaintenanceObjectType());
//                activity.setCreationOrganizationId(newActivity.getCreationOrganizationId());
//                activity.setTemplateFlag(newActivity.getTemplateFlag());
//                activity.setActivitySource(newActivity.getActivitySource());
//                activity.setActivitySourceCode(newActivity.getActivitySourceCode());
//                activity.setLastServiceStartDate(newActivity.getLastServiceStartDate());
//                activity.setLastServiceEndDate(newActivity.getLastServiceEndDate());
//                activity.setAssetRebuildGroup(newActivity.getAssetRebuildGroup());
//                activity.setEamItemType(newActivity.getEamItemType());
//                activity.setWorkOrderTypeDisp(newActivity.getWorkOrderTypeDisp());
//                activity.setWorkOrderType(newActivity.getWorkOrderType());
//                activity.setPlannerMaintenanceDisp(newActivity.getPlannerMaintenanceDisp());
//                activity.setPlannerMaintenance(newActivity.getPlannerMaintenance());
//                activity.setFirmPlannedFlag(newActivity.getFirmPlannedFlag());
//                activity.setPlanMaintenance(newActivity.getPlanMaintenance());
//                activity.setNotificationRequired(newActivity.getNotificationRequired());
//            }
//
//            masterAssetActivityRepository.save(activity);
//
//        }
//    }
//
//
//}
