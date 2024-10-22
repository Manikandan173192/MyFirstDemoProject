package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Department;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetGroupRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.DepartmentRepository;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetWorkOrderListByWorkOrderNo;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetWorkOrderListEAM;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.WorkOrderResponse;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.*;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import java.time.format.DateTimeFormatter;

import static org.hibernate.validator.internal.engine.messageinterpolation.el.RootResolver.FORMATTER;

@Service
public class FinalService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AssetGroupRepository assetGroupRepository;

    @Autowired
    private BasicDetailsRepository basicDetailsRepository;
    @Autowired
    private AdditionalDetailsRepository additionalDetailsRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private OperationChildRepository operationChildRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialChildRepository materialChildRepository;
    @Autowired
    private WorkClearanceRepository workClearanceRepository;
    @Autowired
    private WorkClearanceChildRepository workClearanceChildRepository;
    @Autowired
    private WorkPermitRepository workPermitRepository;
    @Autowired
    private WorkPermitChildRepository workPermitChildRepository;

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    //All Json code
    public ResponseEntity<ResponseModel<String>> getWorkOrderListMain(String workOrderNumber) {
        try {
            String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";
//            String url = "http://ocitestekaayansoa.adityabirla.com:80/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";


            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            BasicDetails basicDetails = basicDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            AdditionalDetails additionalDetails = additionalDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            List<Operation> operationList = operationRepository.findByWorkOrderNumber(workOrderNumber);
            List<Material> materialList = materialRepository.findByWorkOrderNumber(workOrderNumber);

            if (basicDetails != null || additionalDetailsRepository != null || operationList != null || materialList != null) {

                // EAM_FAILURE_ENTRY_RECORD
                //MultiValueMap<String, Object> failureEntryRecord = new LinkedMultiValueMap<>();
                Map<String, Object> failureEntryRecord = new HashMap<>();
                failureEntryRecord.put("FAILURE_ID", null);
                failureEntryRecord.put("FAILURE_DATE", null);
                failureEntryRecord.put("SOURCE_TYPE", null);
                failureEntryRecord.put("SOURCE_ID", null);
                failureEntryRecord.put("OBJECT_TYPE", null);
                failureEntryRecord.put("OBJECT_ID", null);
                failureEntryRecord.put("MAINT_ORGANIZATION_ID", null);
                failureEntryRecord.put("CURRENT_ORGANIZATION_ID", null);
                failureEntryRecord.put("DEPARTMENT_ID", null);
                failureEntryRecord.put("AREA_ID", null);
                failureEntryRecord.put("TRANSACTION_TYPE", null);
                failureEntryRecord.put("SOURCE_NAME", null);

                // EAM_FAILURE_CODES_TBL
                //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
                Map<String, Object> failureCodesTblItem = new HashMap<>();
                failureCodesTblItem.put("FAILURE_ID", null);
                failureCodesTblItem.put("FAILURE_ENTRY_ID", null);
                failureCodesTblItem.put("COMBINATION_ID", null);
                failureCodesTblItem.put("FAILURE_CODE", null);
                failureCodesTblItem.put("CAUSE_CODE", null);
                failureCodesTblItem.put("RESOLUTION_CODE", null);
                failureCodesTblItem.put("COMMENTS", null);
                failureCodesTblItem.put("TRANSACTION_TYPE", null);

                //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
                Map<String, Object> failureCodesTbl = new HashMap<>();
                failureCodesTbl.put("EAM_FAILURE_CODES_TBL_ITEM",Collections.singletonList(failureCodesTblItem));

                // EAM_Operation
                //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
                Map<String, Object> operationItem = new HashMap<>();
                operationItem.put("HEADER_ID", null);
                operationItem.put("BATCH_ID", null);
                operationItem.put("ROW_ID", null);
                operationItem.put("WIP_ENTITY_ID", null);
                operationItem.put("ORGANIZATION_ID", null);
                operationItem.put("OPERATION_SEQ_NUM", null);
                operationItem.put("STANDARD_OPERATION_ID", null);
                operationItem.put("DEPARTMENT_ID", null);
                operationItem.put("OPERATION_SEQUENCE_ID", null);
                operationItem.put("DESCRIPTION", null);
                operationItem.put("MINIMUM_TRANSFER_QUANTITY", null);
                operationItem.put("COUNT_POINT_TYPE", null);
                operationItem.put("BACKFLUSH_FLAG", null);
                operationItem.put("SHUTDOWN_TYPE", null);
                operationItem.put("START_DATE", null);
                operationItem.put("COMPLETION_DATE", null);
                operationItem.put("ATTRIBUTE1", null);
                operationItem.put("ATTRIBUTE2", null);
                operationItem.put("ATTRIBUTE3", null);
                operationItem.put("ATTRIBUTE4", null);
                operationItem.put("ATTRIBUTE5", null);
                operationItem.put("ATTRIBUTE6", null);
                operationItem.put("ATTRIBUTE7", null);
                operationItem.put("ATTRIBUTE8", null);
                operationItem.put("ATTRIBUTE9", null);
                operationItem.put("ATTRIBUTE10", null);
                operationItem.put("ATTRIBUTE11", null);
                operationItem.put("ATTRIBUTE12", null);
                operationItem.put("ATTRIBUTE13", null);
                operationItem.put("ATTRIBUTE14", null);
                operationItem.put("ATTRIBUTE15", null);
                operationItem.put("LONG_DESCRIPTION", null);
                operationItem.put("REQUEST_ID", null);
                operationItem.put("PROGRAM_APPLICATION_ID", null);
                operationItem.put("PROGRAM_ID", null);
                operationItem.put("RETURN_STATUS", null);
                operationItem.put("TRANSACTION_TYPE", null);
                operationItem.put("X_POS", null);
                operationItem.put("Y_POS", null);
                operationItem.put("COMP_REQUIRED_FLAG", null);

                //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
                Map<String, Object> operationTBL = new HashMap<>();
                operationTBL.put("P_EAM_OP_TBL_ITEM",Collections.singletonList(operationItem));

                // EAM_Resource
                //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
                Map<String, Object> resourceItem = new HashMap<>();
                resourceItem.put("HEADER_ID", null);
                resourceItem.put("BATCH_ID", null);
                resourceItem.put("ROW_ID", null);
                resourceItem.put("WIP_ENTITY_ID", null);
                resourceItem.put("ORGANIZATION_ID", null);
                resourceItem.put("OPERATION_SEQ_NUM", null);
                resourceItem.put("RESOURCE_SEQ_NUM", null);
                resourceItem.put("RESOURCE_ID", null);
                resourceItem.put("UOM_CODE", null);
                resourceItem.put("BASIS_TYPE", null);
                resourceItem.put("USAGE_RATE_OR_AMOUNT", null);
                resourceItem.put("ACTIVITY_ID", null);
                resourceItem.put("SCHEDULED_FLAG", 1);
                resourceItem.put("FIRM_FLAG", null);
                resourceItem.put("ASSIGNED_UNITS", null);
                resourceItem.put("MAXIMUM_ASSIGNED_UNITS", null);
                resourceItem.put("AUTOCHARGE_TYPE", null);
                resourceItem.put("STANDARD_RATE_FLAG", null);
                resourceItem.put("APPLIED_RESOURCE_UNITS", null);
                resourceItem.put("APPLIED_RESOURCE_VALUE", null);
                resourceItem.put("START_DATE", null);
                resourceItem.put("COMPLETION_DATE", null);
                resourceItem.put("SCHEDULE_SEQ_NUM", null);
                resourceItem.put("SUBSTITUTE_GROUP_NUM", null);
                resourceItem.put("REPLACEMENT_GROUP_NUM", null);
                resourceItem.put("ATTRIBUTE_CATEGORY", null);
                resourceItem.put("ATTRIBUTE1", null);
                resourceItem.put("ATTRIBUTE2", null);
                resourceItem.put("ATTRIBUTE3", null);
                resourceItem.put("ATTRIBUTE4", null);
                resourceItem.put("ATTRIBUTE5", null);
                resourceItem.put("ATTRIBUTE6", null);
                resourceItem.put("ATTRIBUTE7", null);
                resourceItem.put("ATTRIBUTE8", null);
                resourceItem.put("ATTRIBUTE9", null);
                resourceItem.put("ATTRIBUTE10", null);
                resourceItem.put("ATTRIBUTE11", null);
                resourceItem.put("ATTRIBUTE12", null);
                resourceItem.put("ATTRIBUTE13", null);
                resourceItem.put("ATTRIBUTE14", null);
                resourceItem.put("ATTRIBUTE15", null);
                resourceItem.put("DEPARTMENT_ID", null);
                resourceItem.put("REQUEST_ID", null);
                resourceItem.put("PROGRAM_APPLICATION_ID", null);
                resourceItem.put("PROGRAM_ID", null);
                resourceItem.put("RETURN_STATUS", null);
                resourceItem.put("TRANSACTION_TYPE", null);

                //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
                Map<String, Object> resourceTBL = new HashMap<>();
                resourceTBL.put("P_EAM_RES_TBL_ITEM",Collections.singletonList(resourceItem));

                // EAM_Instance
                //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
                Map<String, Object> instanceItem = new HashMap<>();
                instanceItem.put("HEADER_ID", null);
                instanceItem.put("BATCH_ID", null);
                instanceItem.put("ROW_ID", null);
                instanceItem.put("WIP_ENTITY_ID", null);
                instanceItem.put("ORGANIZATION_ID", null);
                instanceItem.put("OPERATION_SEQ_NUM", null);
                instanceItem.put("RESOURCE_SEQ_NUM", null);
                instanceItem.put("INSTANCE_ID", null);
                instanceItem.put("SERIAL_NUMBER", null);
                instanceItem.put("START_DATE", null);
                instanceItem.put("COMPLETION_DATE", null);
                instanceItem.put("TOP_LEVEL_BATCH_ID", null);
                instanceItem.put("RETURN_STATUS", null);
                instanceItem.put("TRANSACTION_TYPE", null);

                //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
                Map<String, Object> instanceTBL = new HashMap<>();
                instanceTBL.put("P_EAM_RES_INST_TBL_ITEM",Collections.singletonList(instanceItem));


                // EAM_Material
                //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
                Map<String, Object> materialItem = new HashMap<>();
                materialItem.put("HEADER_ID", null);
                materialItem.put("BATCH_ID", null);
                materialItem.put("ROW_ID", null);
                materialItem.put("WIP_ENTITY_ID", null);
                materialItem.put("ORGANIZATION_ID", null);
                materialItem.put("OPERATION_SEQ_NUM", null);
                materialItem.put("INVENTORY_ITEM_ID", null);
                materialItem.put("QUANTITY_PER_ASSEMBLY", null);
                materialItem.put("DEPARTMENT_ID", null);
                materialItem.put("WIP_SUPPLY_TYPE", 1);
                materialItem.put("DATE_REQUIRED", null);
                materialItem.put("REQUIRED_QUANTITY", null);
                materialItem.put("REQUESTED_QUANTITY", null);
                materialItem.put("RELEASED_QUANTITY", null);
                materialItem.put("QUANTITY_ISSUED", null);
                materialItem.put("SUPPLY_SUBINVENTORY", null);
                materialItem.put("SUPPLY_LOCATOR_ID", null);
                materialItem.put("MRP_NET_FLAG", null);
                materialItem.put("MPS_REQUIRED_QUANTITY", null);
                materialItem.put("MPS_DATE_REQUIRED", null);
                materialItem.put("COMPONENT_SEQUENCE_ID", null);
                materialItem.put("COMMENTS", null);
                materialItem.put("ATTRIBUTE_CATEGORY", null);
                materialItem.put("ATTRIBUTE1", null);
                materialItem.put("ATTRIBUTE2", null);
                materialItem.put("ATTRIBUTE3", null);
                materialItem.put("ATTRIBUTE4", null);
                materialItem.put("ATTRIBUTE5", null);
                materialItem.put("ATTRIBUTE6", null);
                materialItem.put("ATTRIBUTE7", null);
                materialItem.put("ATTRIBUTE8", null);
                materialItem.put("ATTRIBUTE9", null);
                materialItem.put("ATTRIBUTE10", null);
                materialItem.put("ATTRIBUTE11", null);
                materialItem.put("ATTRIBUTE12", null);
                materialItem.put("ATTRIBUTE13", null);
                materialItem.put("ATTRIBUTE14", null);
                materialItem.put("ATTRIBUTE15", null);
                materialItem.put("AUTO_REQUEST_MATERIAL", null);
                materialItem.put("SUGGESTED_VENDOR_NAME", null);
                materialItem.put("VENDOR_ID", null);
                materialItem.put("UNIT_PRICE", null);
                materialItem.put("REQUEST_ID", null);
                materialItem.put("PROGRAM_APPLICATION_ID", null);
                materialItem.put("PROGRAM_ID", null);
                materialItem.put("PROGRAM_UPDATE_DATE", null);
                materialItem.put("RETURN_STATUS", null);
                materialItem.put("TRANSACTION_TYPE", null);
                materialItem.put("INVOKE_ALLOCATIONS_API", null);
                materialItem.put("SUGGESTED_VENDOR_SITE", null);
                materialItem.put("SUGGESTED_VENDOR_SITE_ID", null);
                materialItem.put("SUGGESTED_VENDOR_CONTACT", null);
                materialItem.put("SUGGESTED_VENDOR_CONTACT_ID", null);
                materialItem.put("SUGGESTED_VENDOR_PHONE", null);

                //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
                Map<String, Object> materialTBL = new HashMap<>();
                materialTBL.put("P_EAM_MAT_REQ_TBL_ITEM",Collections.singletonList(materialItem));



                //EAM_WO_REC
                //MultiValueMap<String, Object> eamWoRec = new LinkedMultiValueMap<>();
                Map<String, Object> eamWoRec = new HashMap<>();
                eamWoRec.put("HEADER_ID", 1);
                eamWoRec.put("BATCH_ID", 1);
                eamWoRec.put("ROW_ID", null);
                eamWoRec.put("WIP_ENTITY_NAME", null);
                eamWoRec.put("ORGANIZATION_ID", 1759);
                eamWoRec.put("DESCRIPTION", "test");
                eamWoRec.put("ASSET_NUMBER", "HF/HM/FEX/FCRS");
                eamWoRec.put("ASSET_GROUP_ID", 141548);
                eamWoRec.put("REBUILD_ITEM_ID", 178428);
                eamWoRec.put("REBUILD_SERIAL_NUMBER", "UA-AR-S-GB0001");
                eamWoRec.put("MAINTENANCE_OBJECT_ID",73100);
                eamWoRec.put("MAINTENANCE_OBJECT_TYPE", 3);
                eamWoRec.put("MAINTENANCE_OBJECT_SOURCE", 1);
                eamWoRec.put("EAM_LINEAR_LOCATION_ID", null);
                eamWoRec.put("CLASS_CODE", "UARBHM01");
                eamWoRec.put("ASSET_ACTIVITY_ID", null);
                eamWoRec.put("ACTIVITY_TYPE", null);
                eamWoRec.put("ACTIVITY_CAUSE", null);
                eamWoRec.put("ACTIVITY_SOURCE", null);
                eamWoRec.put("WORK_ORDER_TYPE", "190");
                eamWoRec.put("STATUS_TYPE", "17");
                eamWoRec.put("JOB_QUANTITY", null);
                eamWoRec.put("DATE_RELEASED", null);
                eamWoRec.put("OWNING_DEPARTMENT", "2002");
                eamWoRec.put("PRIORITY","20");
                eamWoRec.put("REQUESTED_START_DATE", null);
                eamWoRec.put("DUE_DATE", null);
                eamWoRec.put("SHUTDOWN_TYPE", "2");
                eamWoRec.put("PLANNER_TYPE", "20");
                eamWoRec.put("FIRM_PLANNED_FLAG", "1");
                eamWoRec.put("NOTIFICATION_REQUIRED", additionalDetails!=null?additionalDetails.getNotificationRequired():null);
                eamWoRec.put("TAGOUT_REQUIRED", additionalDetails!=null?additionalDetails.getTagoutRequired():null);
                eamWoRec.put("PLAN_MAINTENANCE", null);
                eamWoRec.put("PROJECT_ID", null);
                eamWoRec.put("TASK_ID", null);
                eamWoRec.put("END_ITEM_UNIT_NUMBER", null);
                eamWoRec.put("SCHEDULE_GROUP_ID", null);
                eamWoRec.put("BOM_REVISION_DATE", null);
                eamWoRec.put("ROUTING_REVISION_DATE", null);
                eamWoRec.put("ALTERNATE_ROUTING_DESIGNATOR", null);
                eamWoRec.put("ALTERNATE_BOM_DESIGNATOR", null);
                eamWoRec.put("ROUTING_REVISION", null);
                eamWoRec.put("BOM_REVISION", null);
                eamWoRec.put("MANUAL_REBUILD_FLAG", null);
                eamWoRec.put("PM_SCHEDULE_ID", null);
                eamWoRec.put("WIP_SUPPLY_TYPE", null);
                eamWoRec.put("MATERIAL_ACCOUNT", null);
                eamWoRec.put("MATERIAL_OVERHEAD_ACCOUNT", null);
                eamWoRec.put("RESOURCE_ACCOUNT", null);
                eamWoRec.put("OUTSIDE_PROCESSING_ACCOUNT", null);
                eamWoRec.put("MATERIAL_VARIANCE_ACCOUNT", null);
                eamWoRec.put("RESOURCE_VARIANCE_ACCOUNT", null);
                eamWoRec.put("OUTSIDE_PROC_VARIANCE_ACCOUNT", null);
                eamWoRec.put("STD_COST_ADJUSTMENT_ACCOUNT", null);
                eamWoRec.put("OVERHEAD_ACCOUNT", null);
                eamWoRec.put("OVERHEAD_VARIANCE_ACCOUNT", null);
                eamWoRec.put("SCHEDULED_START_DATE", "2024-08-27T00:00:03.650+0530");
                eamWoRec.put("SCHEDULED_COMPLETION_DATE", "2024-08-28T23:59:59.650+0530");
                eamWoRec.put("PM_SUGGESTED_START_DATE", null);
                eamWoRec.put("PM_SUGGESTED_END_DATE", null);
                eamWoRec.put("PM_BASE_METER_READING", null);
                eamWoRec.put("PM_BASE_METER", null);
                eamWoRec.put("COMMON_BOM_SEQUENCE_ID", null);
                eamWoRec.put("COMMON_ROUTING_SEQUENCE_ID", null);
                eamWoRec.put("PO_CREATION_TIME", null);
                eamWoRec.put("GEN_OBJECT_ID", null);
                eamWoRec.put("USER_DEFINED_STATUS_ID", null);
                eamWoRec.put("PENDING_FLAG", null);
                eamWoRec.put("MATERIAL_SHORTAGE_CHECK_DATE", null);
                eamWoRec.put("MATERIAL_SHORTAGE_FLAG", null);
                eamWoRec.put("WORKFLOW_TYPE", null);
                eamWoRec.put("WARRANTY_CLAIM_STATUS", null);
                eamWoRec.put("CYCLE_ID", null);
                eamWoRec.put("SEQ_ID", null);
                eamWoRec.put("DS_SCHEDULED_FLAG", null);
                eamWoRec.put("WARRANTY_ACTIVE", null);
                eamWoRec.put("ASSIGNMENT_COMPLETE", null);
                eamWoRec.put("ATTRIBUTE_CATEGORY", null);
                eamWoRec.put("ATTRIBUTE1", null);
                eamWoRec.put("ATTRIBUTE2", additionalDetails!=null?additionalDetails.getInformDepartments():null);
                eamWoRec.put("ATTRIBUTE3", additionalDetails!=null?additionalDetails.getSafetyPermit():null);
                eamWoRec.put("ATTRIBUTE4", null);
                eamWoRec.put("ATTRIBUTE5", null);
                eamWoRec.put("ATTRIBUTE6", null);
                eamWoRec.put("ATTRIBUTE7", null);
                eamWoRec.put("ATTRIBUTE8", null);
                eamWoRec.put("ATTRIBUTE9", null);
                eamWoRec.put("ATTRIBUTE10", null);
                eamWoRec.put("ATTRIBUTE11", null);
                eamWoRec.put("ATTRIBUTE12", null);
                eamWoRec.put("ATTRIBUTE13", null);
                eamWoRec.put("ATTRIBUTE14", null);
                eamWoRec.put("ATTRIBUTE15", null);
                eamWoRec.put("MATERIAL_ISSUE_BY_MO", null);
                eamWoRec.put("ISSUE_ZERO_COST_FLAG", null);
                eamWoRec.put("REPORT_TYPE", null);
                eamWoRec.put("ACTUAL_CLOSE_DATE", null);
                eamWoRec.put("SUBMISSION_DATE", null);
                eamWoRec.put("USER_ID", null);
                eamWoRec.put("RESPONSIBILITY_ID", null);
                eamWoRec.put("REQUEST_ID", null);
                eamWoRec.put("PROGRAM_ID", null);
                eamWoRec.put("PROGRAM_APPLICATION_ID", null);
                eamWoRec.put("SOURCE_LINE_ID", null);
                eamWoRec.put("SOURCE_CODE", null);
                eamWoRec.put("VALIDATE_STRUCTURE", null);
                eamWoRec.put("RETURN_STATUS", null);
                eamWoRec.put("TRANSACTION_TYPE", 1);
                eamWoRec.put("FAILURE_CODE_REQUIRED", null);
                eamWoRec.put("EAM_FAILURE_ENTRY_RECORD", failureEntryRecord);
                eamWoRec.put("EAM_FAILURE_CODES_TBL", failureCodesTbl);
                eamWoRec.put("TARGET_START_DATE", null);
                eamWoRec.put("TARGET_COMPLETION_DATE", null);

                // Process Operations, Resources, and Instances
                List<OperationChild> opList = new ArrayList<>();
                List<OperationChild> resourceList = new ArrayList<>();
                List<OperationChild> instanceList = new ArrayList<>();

                for (Operation operation : operationList) {
                    for (OperationChild operationChild : operation.getOperationList()) {
                        if (operationChild.getOperationSequence() != null || operationChild.getDescription() != null || operationChild.getDepartmentId() != null) {
                            opList.add(operationChild);
                            // operationChild.setOperationsTransactionType("Y");

                        }
                        if (operationChild.getResourceSequence() != null || operationChild.getResourceId() != null) {
                            resourceList.add(operationChild);
//                            operationChild.setResourcesTransactionType("Y");

                        }
                        if (operationChild.getInstanceId() != null || operationChild.getInstanceName() != null) {
                            instanceList.add(operationChild);
//                            operationChild.setInstancesTransactionType("Y");

                        }
                    }
                }

                List<MaterialChild> mcList = new ArrayList<>();

                for (Material material : materialList) {
                    for (MaterialChild materialChild : material.getMaterialList()) {
                        if (materialChild.getOperationSequence() != null) {
                            mcList.add(materialChild);
                        }

                    }
                }

                //EAM_WO_REC
                Map<String, Object> requestData = new HashMap<>();
                requestData.put("HEADER_ID", null);
                requestData.put("BATCH_ID", null);
                requestData.put("ROW_ID", null);
                requestData.put("WIP_ENTITY_NAME", null);
                requestData.put("ORGANIZATION_ID", 1763);
                requestData.put("REQUEST_TYPE", 1);
                //requestData.put("REQUEST_ID", Integer.parseInt(basicDetails.getWorkRequestNo()));
                //requestData.put("REQUEST_NUMBER", Integer.parseInt(basicDetails.getWorkRequestNo()));
                requestData.put("REQUEST_ID", "318344");
                requestData.put("REQUEST_NUMBER", "318344");
                requestData.put("ATTRIBUTE_CATEGORY", null);
                requestData.put("ATTRIBUTE1", null);
                requestData.put("ATTRIBUTE4", null);
                requestData.put("ATTRIBUTE5", null);
                requestData.put("ATTRIBUTE6", null);
                requestData.put("ATTRIBUTE7", null);
                requestData.put("ATTRIBUTE8", null);
                requestData.put("ATTRIBUTE9", null);
                requestData.put("ATTRIBUTE10", null);
                requestData.put("ATTRIBUTE11", null);
                requestData.put("ATTRIBUTE12", null);
                requestData.put("ATTRIBUTE13", null);
                requestData.put("ATTRIBUTE14", null);
                requestData.put("ATTRIBUTE15", null);
                requestData.put("PROGRAM_ID", null);
                requestData.put("PROGRAM_REQUEST_ID", null);
                requestData.put("PROGRAM_UPDATE_DATE", null);
                requestData.put("PROGRAM_APPLICATION_ID", null);
                requestData.put("WORK_REQUEST_STATUS_ID", null);
                requestData.put("SERVICE_ASSOC_ID", null);
                requestData.put("RETURN_STATUS", null);
                requestData.put("TRANSACTION_TYPE", 1);


                //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
                Map<String, Object> requestArrayData = new HashMap<>();
                requestArrayData.put("P_EAM_REQUEST_TBL_ITEM",Collections.singletonList(requestData));


                //MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                Map<String, Object> body = new HashMap<>();
                body.put("P_BO_IDENTIFIER", "EAM");
                body.put("P_API_VERSION_NUMBER", 1);
                body.put("P_INIT_MSG_LIST", "1");
                body.put("P_COMMIT", "Y");
                body.put("P_EAM_WO_REC", eamWoRec);
                //body.put("P_EAM_OP_TBL", operationTBL);
                body.put("P_EAM_OP_TBL", null);
                body.put("P_EAM_OP_NETWORK_TBL", null);
                //body.put("P_EAM_RES_TBL", resourceTBL);
                body.put("P_EAM_RES_TBL", null);
                //body.put("P_EAM_RES_INST_TBL", instanceTBL);
                body.put("P_EAM_RES_INST_TBL", null);
                body.put("P_EAM_SUB_RES_TBL", null);
                body.put("P_EAM_RES_USAGE_TBL", null);
                //body.put("P_EAM_MAT_REQ_TBL", materialTBL);
                body.put("P_EAM_MAT_REQ_TBL", null);
                body.put("P_EAM_DIRECT_ITEMS_TBL", null);
                body.put("P_EAM_WO_COMP_REC", null);
                body.put("P_EAM_WO_QUALITY_TBL", null);
                body.put("P_EAM_METER_READING_TBL", null);
                body.put("P_EAM_COUNTER_PROP_TBL", null);
                body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
                body.put("P_EAM_WO_COMP_MR_READ_TBL", null);
                body.put("P_EAM_OP_COMP_TBL", null);
                body.put("P_EAM_REQUEST_TBL", requestArrayData);
                //body.put("P_EAM_REQUEST_TBL", null);
                body.put("P_EAM_PERMIT_TBL", null);
                body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
                body.put("P_EAM_WORK_CLEARANCE_TBL", null);
                body.put("P_EAM_WC_WO_ASSOC_TBL", null);
                body.put("P_DEBUG", "N");
                body.put("P_OUTPUT_DIR", "/usr/tmp");
                body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
                body.put("P_DEBUG_FILE_MODE", "w");


                ObjectMapper objectMapper1 = new ObjectMapper();
                String json = objectMapper1.writeValueAsString(body);

                System.out.println(json);

                //HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

                System.out.println("Called responseEntity = "+entity);
                ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                System.out.println("Called responseEntity = "+responseEntity);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    String response = responseEntity.getBody();
                    if (response != null && !response.isEmpty()) {
                        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                        JsonNode jsonNode = objectMapper.readTree(response);
//                        String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();
                        String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();
                        String wipEntityName = null;
                        if (jsonNode.has("WIP_ENTITY_NAME")) {
                            JsonNode wipEntityNode = jsonNode.get("WIP_ENTITY_NAME");
                            if (wipEntityNode.has("$")) {
                                wipEntityName = wipEntityNode.get("$").asText();
                            }
                        }

                        if ("S".equals(responseStatus) && wipEntityName != null && !wipEntityName.isEmpty()) {
                           // String requestNumber = jsonNode.get("P_WORK_REQUEST_NUMBER").asText();
                            return ResponseEntity.status(HttpStatus.OK)
                                    .body(new ResponseModel<>(true, "WorkOrder Created Successfully !!",wipEntityName));
                        }
                        else {
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
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel<>(false, "No details found for the given work order number.", null));
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






//Working Code
public ResponseEntity<ResponseModel<String>> addWorkOrder1(String workOrderNumber) {
    try {
        String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

        // String url = "http://ocitestekaayansoa.adityabirla.com:80/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        BasicDetails basicDetails = basicDetailsRepository.findByWorkOrderNumber(workOrderNumber);
        AdditionalDetails additionalDetails = additionalDetailsRepository.findByWorkOrderNumber(workOrderNumber);
        List<Operation> operationList = operationRepository.findByWorkOrderNumber(workOrderNumber);
        List<Material> materialList = materialRepository.findByWorkOrderNumber(workOrderNumber);

        if (basicDetails == null && additionalDetails == null && operationList.isEmpty() && materialList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel<>(false, "No details found for the given work order number.", null));
        }

        if (basicDetails == null) {
            System.out.println("BasicDetails is null for workOrderNumber: " + workOrderNumber);
        } else {
            System.out.println("BasicDetails: " + basicDetails.toString());
        }

        if (additionalDetails == null) {
            System.out.println("AdditionalDetails is null for workOrderNumber: " + workOrderNumber);
        } else {
            System.out.println("AdditionalDetails: " + additionalDetails.toString());
        }


        Map<String, Object> body = new HashMap<>();
        body.put("P_BO_IDENTIFIER", "EAM");
        body.put("P_API_VERSION_NUMBER", 1);
        body.put("P_INIT_MSG_LIST", "1");
        body.put("P_COMMIT", "Y");

        // EAM Work Order Record
        Map<String, Object> eamWoRec = new HashMap<>();
        eamWoRec.put("HEADER_ID", 1);
        eamWoRec.put("BATCH_ID", 1);
        eamWoRec.put("ROW_ID", null);
//        eamWoRec.put("WIP_ENTITY_NAME",  workOrderNumber.contains("TEMPWO") ? null:workOrderNumber);
//        eamWoRec.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
        eamWoRec.put("WIP_ENTITY_NAME",null);
        eamWoRec.put("ORGANIZATION_ID", basicDetails != null ? basicDetails.getOrganizationId() : null);
        eamWoRec.put("DESCRIPTION", basicDetails != null ? basicDetails.getDescription() : null);
        eamWoRec.put("ASSET_NUMBER", null);
        //eamWoRec.put("ASSET_NUMBER", basicDetails != null ? basicDetails.getAssetNumber() :null);
        //eamWoRec.put("ASSET_GROUP_ID", basicDetails != null ? basicDetails.getAssetGroupId() :null);
        eamWoRec.put("ASSET_GROUP_ID", null);
        eamWoRec.put("REBUILD_ITEM_ID", basicDetails != null ? basicDetails.getRebuildItemId() : null);
        eamWoRec.put("REBUILD_SERIAL_NUMBER", basicDetails != null ? basicDetails.getRebuildSerialNo() : null);
        eamWoRec.put("MAINTENANCE_OBJECT_ID", basicDetails != null ? basicDetails.getMaintenanceObjectId() : null);
        eamWoRec.put("MAINTENANCE_OBJECT_TYPE", basicDetails != null ? basicDetails.getMaintenanceObjectType() : null);
        eamWoRec.put("MAINTENANCE_OBJECT_SOURCE", 1);
        eamWoRec.put("CLASS_CODE", basicDetails != null ? basicDetails.getWipAccountingClassCode() : null);
        eamWoRec.put("WORK_ORDER_TYPE", basicDetails != null ? basicDetails.getWoType() : null);
        eamWoRec.put("STATUS_TYPE", basicDetails != null ? basicDetails.getStatusType() : null);
        eamWoRec.put("PRIORITY", basicDetails != null ? basicDetails.getPriority() : null);
        eamWoRec.put("SHUTDOWN_TYPE", basicDetails != null ? basicDetails.getShutdownType() : null);
        eamWoRec.put("FIRM_PLANNED_FLAG", basicDetails != null ? basicDetails.getFirmPlannedType() : null);
        eamWoRec.put("SCHEDULED_START_DATE", basicDetails != null ? basicDetails.getScheduledStartDate() : null);
        eamWoRec.put("SCHEDULED_COMPLETION_DATE", basicDetails != null ? basicDetails.getScheduledEndDate() : null);
        eamWoRec.put("NOTIFICATION_REQUIRED", additionalDetails != null ? additionalDetails.getNotificationRequired() : null);
        eamWoRec.put("TAGOUT_REQUIRED", additionalDetails != null ? additionalDetails.getTagoutRequired() : null);
        eamWoRec.put("ATTRIBUTE3", additionalDetails != null ? additionalDetails.getSafetyPermit() : null);
        eamWoRec.put("EAM_LINEAR_LOCATION_ID", null);
        eamWoRec.put("ASSET_ACTIVITY_ID", null);
        eamWoRec.put("DUE_DATE", null);
        eamWoRec.put("ACTIVITY_TYPE", null);
        eamWoRec.put("ACTIVITY_CAUSE", null);
        eamWoRec.put("ACTIVITY_SOURCE", null);
        eamWoRec.put("JOB_QUANTITY", null);
        eamWoRec.put("DATE_RELEASED", null);
        eamWoRec.put("OWNING_DEPARTMENT", basicDetails != null ? basicDetails.getDepartmentId() : null);
        eamWoRec.put("REQUESTED_START_DATE", null);
        eamWoRec.put("PLANNER_TYPE", basicDetails != null ? basicDetails.getPlannerType() : null);
        eamWoRec.put("PLAN_MAINTENANCE", null);
        eamWoRec.put("PROJECT_ID", null);
        eamWoRec.put("TASK_ID", null);
        eamWoRec.put("END_ITEM_UNIT_NUMBER", null);
        eamWoRec.put("SCHEDULE_GROUP_ID", null);
        eamWoRec.put("BOM_REVISION_DATE", null);
        eamWoRec.put("ROUTING_REVISION_DATE", null);
        eamWoRec.put("ALTERNATE_ROUTING_DESIGNATOR", null);
        eamWoRec.put("ALTERNATE_BOM_DESIGNATOR", null);
        eamWoRec.put("ROUTING_REVISION", null);
        eamWoRec.put("BOM_REVISION", null);
        eamWoRec.put("MANUAL_REBUILD_FLAG", null);
        eamWoRec.put("PM_SCHEDULE_ID", null);
        eamWoRec.put("WIP_SUPPLY_TYPE", null);
        eamWoRec.put("MATERIAL_ACCOUNT", null);
        eamWoRec.put("MATERIAL_OVERHEAD_ACCOUNT", null);
        eamWoRec.put("RESOURCE_ACCOUNT", null);
        eamWoRec.put("OUTSIDE_PROCESSING_ACCOUNT", null);
        eamWoRec.put("MATERIAL_VARIANCE_ACCOUNT", null);
        eamWoRec.put("RESOURCE_VARIANCE_ACCOUNT", null);
        eamWoRec.put("OUTSIDE_PROC_VARIANCE_ACCOUNT", null);
        eamWoRec.put("STD_COST_ADJUSTMENT_ACCOUNT", null);
        eamWoRec.put("OVERHEAD_ACCOUNT", null);
        eamWoRec.put("OVERHEAD_VARIANCE_ACCOUNT", null);
        eamWoRec.put("PM_SUGGESTED_START_DATE", null);
        eamWoRec.put("PM_SUGGESTED_END_DATE", null);
        eamWoRec.put("PM_BASE_METER_READING", null);
        eamWoRec.put("PM_BASE_METER", null);
        eamWoRec.put("COMMON_BOM_SEQUENCE_ID", null);
        eamWoRec.put("COMMON_ROUTING_SEQUENCE_ID", null);
        eamWoRec.put("PO_CREATION_TIME", null);
        eamWoRec.put("GEN_OBJECT_ID", null);
        eamWoRec.put("USER_DEFINED_STATUS_ID", null);
        eamWoRec.put("PENDING_FLAG", null);
        eamWoRec.put("MATERIAL_SHORTAGE_CHECK_DATE", null);
        eamWoRec.put("MATERIAL_SHORTAGE_FLAG", null);
        eamWoRec.put("WORKFLOW_TYPE", null);
        eamWoRec.put("WARRANTY_CLAIM_STATUS", null);
        eamWoRec.put("CYCLE_ID", null);
        eamWoRec.put("SEQ_ID", null);
        eamWoRec.put("DS_SCHEDULED_FLAG", null);
        eamWoRec.put("WARRANTY_ACTIVE", null);
        eamWoRec.put("ASSIGNMENT_COMPLETE", null);
        eamWoRec.put("ATTRIBUTE_CATEGORY", null);
        eamWoRec.put("ATTRIBUTE1", null);
        eamWoRec.put("ATTRIBUTE2", null);
        eamWoRec.put("ATTRIBUTE4", null);
        eamWoRec.put("ATTRIBUTE5", null);
        eamWoRec.put("ATTRIBUTE6", null);
        eamWoRec.put("ATTRIBUTE7", null);
        eamWoRec.put("ATTRIBUTE8", null);
        eamWoRec.put("ATTRIBUTE9", null);
        eamWoRec.put("ATTRIBUTE10", null);
        eamWoRec.put("ATTRIBUTE11", null);
        eamWoRec.put("ATTRIBUTE12", null);
        eamWoRec.put("ATTRIBUTE13", null);
        eamWoRec.put("ATTRIBUTE14", null);
        eamWoRec.put("ATTRIBUTE15", null);
        eamWoRec.put("MATERIAL_ISSUE_BY_MO", null);
        eamWoRec.put("ISSUE_ZERO_COST_FLAG", null);
        eamWoRec.put("REPORT_TYPE", null);
        eamWoRec.put("ACTUAL_CLOSE_DATE", null);
        eamWoRec.put("SUBMISSION_DATE", null);
        eamWoRec.put("USER_ID", null);
        eamWoRec.put("RESPONSIBILITY_ID", null);
        eamWoRec.put("REQUEST_ID", null);
        eamWoRec.put("PROGRAM_ID", null);
        eamWoRec.put("PROGRAM_APPLICATION_ID", null);
        eamWoRec.put("SOURCE_LINE_ID", null);
        eamWoRec.put("SOURCE_CODE", null);
        eamWoRec.put("VALIDATE_STRUCTURE", null);
        eamWoRec.put("RETURN_STATUS", null);
        eamWoRec.put("TRANSACTION_TYPE", 1);
        eamWoRec.put("FAILURE_CODE_REQUIRED", null);


        body.put("P_EAM_WO_REC", eamWoRec);


        // EAM_FAILURE_ENTRY_RECORD
        //MultiValueMap<String, Object> failureEntryRecord = new LinkedMultiValueMap<>();
        Map<String, Object> failureEntryRecord = new HashMap<>();
        failureEntryRecord.put("FAILURE_ID", null);
        failureEntryRecord.put("FAILURE_DATE", null);
        failureEntryRecord.put("SOURCE_TYPE", null);
        failureEntryRecord.put("SOURCE_ID", null);
        failureEntryRecord.put("OBJECT_TYPE", null);
        failureEntryRecord.put("OBJECT_ID", null);
        failureEntryRecord.put("MAINT_ORGANIZATION_ID", null);
        failureEntryRecord.put("CURRENT_ORGANIZATION_ID", null);
        failureEntryRecord.put("DEPARTMENT_ID", null);
        failureEntryRecord.put("AREA_ID", null);
        failureEntryRecord.put("TRANSACTION_TYPE", null);
        failureEntryRecord.put("SOURCE_NAME", null);

        eamWoRec.put("EAM_FAILURE_ENTRY_RECORD", failureEntryRecord);


        // EAM_FAILURE_CODES_TBL
        //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
        Map<String, Object> failureCodesTblItem = new HashMap<>();
        failureCodesTblItem.put("FAILURE_ID", null);
        failureCodesTblItem.put("FAILURE_ENTRY_ID", null);
        failureCodesTblItem.put("COMBINATION_ID", null);
        failureCodesTblItem.put("FAILURE_CODE", null);
        failureCodesTblItem.put("CAUSE_CODE", null);
        failureCodesTblItem.put("RESOLUTION_CODE", null);
        failureCodesTblItem.put("COMMENTS", null);
        failureCodesTblItem.put("TRANSACTION_TYPE", null);

        //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
        Map<String, Object> failureCodesTbl = new HashMap<>();
        failureCodesTbl.put("EAM_FAILURE_CODES_TBL_ITEM", Collections.singletonList(failureCodesTblItem));

        eamWoRec.put("EAM_FAILURE_CODES_TBL", failureCodesTbl);

        eamWoRec.put("TARGET_START_DATE", null);
        eamWoRec.put("TARGET_COMPLETION_DATE", null);


        // Process Operations, Resources, and Instances
        List<OperationChild> opList = new ArrayList<>();
        List<OperationChild> resourceList = new ArrayList<>();
        List<OperationChild> instanceList = new ArrayList<>();

        for (Operation operation : operationList) {
            for (OperationChild operationChild : operation.getOperationList()) {
                if (operationChild.getOperationSequence() != null || operationChild.getDescription() != null || operationChild.getDepartmentId() != null) {
                    opList.add(operationChild);
                }
                if (operationChild.getResourceSequence() != null || operationChild.getResourceId() != null) {
                    resourceList.add(operationChild);
                }
                if (operationChild.getInstanceId() != null || operationChild.getInstanceName() != null) {
                    instanceList.add(operationChild);
                }
            }
        }

        //Operation
        List<Map<String, Object>> operationItemList = new ArrayList<>();


        for (OperationChild operation : opList) {

            Map<String, Object> operationItem = new HashMap<>();
            operationItem.put("OPERATION_SEQUENCE_NUM", operation.getOperationSequence());
            operationItem.put("DEPARTMENT_ID", operation.getDepartmentId());
            operationItem.put("DESCRIPTION", operation.getDescription());
            operationItem.put("START_DATE", operation.getStartDate());
            operationItem.put("COMPLETION_DATE", operation.getCompletionDate());
            operationItem.put("TRANSACTION_TYPE", 1);
            operationItem.put("ORGANIZATION_ID", operation.getOrganizationId());

            operationItemList.add(operationItem);
        }


        Map<String, Object> operationTBL = new HashMap<>();
        operationTBL.put("P_EAM_OP_TBL_ITEM", operationItemList);

        //body.put("P_EAM_OP_TBL", operationTBL);


        body.put("P_EAM_OP_TBL", null);
        body.put("P_EAM_RES_TBL", null);
        body.put("P_EAM_RES_INST_TBL", null);

        List<MaterialChild> mcList = new ArrayList<>();
        for (Material material : materialList) {
            for (MaterialChild materialChild : material.getMaterialList()) {
                if (materialChild.getOperationSequence() != null) {
                    mcList.add(materialChild);
                }
            }
        }

        //body.put("P_EAM_MAT_REQ_TBL", mcList);

        // Set null values for other fields that are not needed
        body.put("P_EAM_OP_NETWORK_TBL", null);
        body.put("P_EAM_DIRECT_ITEMS_TBL", null);
        body.put("P_EAM_WO_COMP_REC", null);
        body.put("P_EAM_WO_QUALITY_TBL", null);
        body.put("P_EAM_METER_READING_TBL", null);
        body.put("P_EAM_COUNTER_PROP_TBL", null);
        body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
        body.put("P_EAM_WO_COMP_MR_READ_TBL", null);
        body.put("P_EAM_OP_COMP_TBL", null);
        body.put("P_EAM_PERMIT_TBL", null);
        body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
        body.put("P_EAM_WORK_CLEARANCE_TBL", null);
        body.put("P_EAM_WC_WO_ASSOC_TBL", null);
        body.put("P_EAM_RES_USAGE_TBL", null);
        body.put("P_EAM_SUB_RES_TBL", null);
        body.put("P_DEBUG", "N");
        body.put("P_OUTPUT_DIR", "/usr/tmp");
        body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
        body.put("P_DEBUG_FILE_MODE", "w");


        Map<String, Object> requestTableItem = new HashMap<>();
        requestTableItem.put("HEADER_ID", null);
        requestTableItem.put("BATCH_ID", null);
        requestTableItem.put("ROW_ID", null);
        requestTableItem.put("WIP_ENTITY_NAME", null);
//        requestTableItem.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
        requestTableItem.put("ORGANIZATION_ID", basicDetails.getOrganizationId());
        requestTableItem.put("ORGANIZATION_CODE", null);
        requestTableItem.put("REQUEST_TYPE", 1);
        requestTableItem.put("REQUEST_ID", basicDetails.getWorkRequestNo());
        requestTableItem.put("REQUEST_NUMBER", basicDetails.getWorkRequestNo());
        requestTableItem.put("ATTRIBUTE_CATEGORY", null);
        requestTableItem.put("ATTRIBUTE1", null);
        requestTableItem.put("ATTRIBUTE2", null);
        requestTableItem.put("ATTRIBUTE3", null);
        requestTableItem.put("ATTRIBUTE4", null);
        requestTableItem.put("ATTRIBUTE5", null);
        requestTableItem.put("ATTRIBUTE6", null);
        requestTableItem.put("ATTRIBUTE7", null);
        requestTableItem.put("ATTRIBUTE8", null);
        requestTableItem.put("ATTRIBUTE9", null);
        requestTableItem.put("ATTRIBUTE10", null);
        requestTableItem.put("ATTRIBUTE11", null);
        requestTableItem.put("ATTRIBUTE12", null);
        requestTableItem.put("ATTRIBUTE13", null);
        requestTableItem.put("ATTRIBUTE14", null);
        requestTableItem.put("ATTRIBUTE15", null);
        requestTableItem.put("PROGRAM_ID", null);
        requestTableItem.put("PROGRAM_REQUEST_ID", null);
        requestTableItem.put("PROGRAM_UPDATE_DATE", null);
        requestTableItem.put("PROGRAM_APPLICATION_ID", null);
        requestTableItem.put("WORK_REQUEST_STATUS_ID", null);
        requestTableItem.put("SERVICE_ASSOC_ID", null);
        requestTableItem.put("RETURN_STATUS", null);
        requestTableItem.put("TRANSACTION_TYPE", 1);

        Map<String, Object> requestTBL = new HashMap<>();
        requestTBL.put("P_EAM_REQUEST_TBL_ITEM", Collections.singletonList(requestTableItem));


        body.put("P_EAM_REQUEST_TBL", requestTBL);

        ObjectMapper objectMapper1 = new ObjectMapper();
        String json1 = objectMapper1.writeValueAsString(body);

        System.out.println(json1);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        System.out.println("Entity records = " + entity);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        System.out.println("ResponseEntity = " + responseEntity);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String response = responseEntity.getBody();

            if (response != null && !response.isEmpty()) {
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                JsonNode jsonNode = objectMapper.readTree(response);
                String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();
                JsonNode eamWoRecNode = jsonNode.get("X_EAM_WO_REC");

                String wipEntityName = null;
                if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_NAME")) {
                    JsonNode wipEntityNode = eamWoRecNode.get("WIP_ENTITY_NAME");
                    if (wipEntityNode.has("$")) {
                        wipEntityName = wipEntityNode.get("$").asText();
                    }
                }

                String wipEntityId = null;
                if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_ID")) {
                    JsonNode wipEntityIdNode = eamWoRecNode.get("WIP_ENTITY_ID");
                    if (wipEntityIdNode.has("$")) {
                        wipEntityId = wipEntityIdNode.get("$").asText();
                    }
                }

                if ("S".equals(responseStatus) && wipEntityName != null && !wipEntityName.isEmpty()) {
                    // String wipEntityName = jsonNode.get("WIP_ENTITY_NAME").asText();

                   /* if (basicDetails != null) {
                        basicDetails.setWorkOrderNumber(wipEntityName);
                        basicDetails.setWipEntityName(wipEntityName);
                        basicDetails.setWipEntityId(wipEntityId);
                        basicDetailsRepository.save(basicDetails);
                    }
                    if (additionalDetails != null) {
                        additionalDetails.setWorkOrderNumber(wipEntityName);
                        additionalDetailsRepository.save(additionalDetails);
                    }

                    if (operationList != null && !operationList.isEmpty()) {
                        Operation operation = operationList.getFirst();
                        operation.setWorkOrderNumber(wipEntityName);
                        operationRepository.save(operation);
                        for (OperationChild operationChild : operation.getOperationList()) {
                            operationChild.setWorkOrderNumber(wipEntityName);
                            operationChild.setWipEntityId(wipEntityId);
                            operationChildRepository.save(operationChild);
                        }
                    }

                    if (materialList != null && !materialList.isEmpty()) {
                        Material material = materialList.getFirst();
                        material.setWorkOrderNumber(wipEntityName);
                        materialRepository.save(material);
                        for (MaterialChild materialChild : material.getMaterialList()) {
                            materialChild.setWorkOrderNumber(wipEntityName);
                            materialChild.setWipEntityId(wipEntityId);
                            materialChildRepository.save(materialChild);
                        }
                    }*/


                    /*if (basicDetails != null) {
                        basicDetailsRepository.delete(basicDetails);
                    }

                    if (additionalDetails != null) {
                        additionalDetailsRepository.delete(additionalDetails);
                    }

                    if (operationList != null && !operationList.isEmpty()) {
                        for (Operation operation : operationList) {
                            List<OperationChild> operationChildren = operation.getOperationList();
                            if (operationChildren != null && !operationChildren.isEmpty()) {
                                operationChildRepository.deleteAll(operationChildren);
                            }
                            operationRepository.delete(operation);
                        }
                    }
                    if (materialList != null && !materialList.isEmpty()) {
                        for (Material material : materialList) {
                            List<MaterialChild> materialChildren = material.getMaterialList();
                            if (materialChildren != null && !materialChildren.isEmpty()) {
                                materialChildRepository.deleteAll(materialChildren);
                            }
                            materialRepository.delete(material);
                        }
                    }*/

                    return ResponseEntity.ok(new ResponseModel<>(true, "WorkOrder Created Successfully !!", wipEntityName));

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
                .body(new ResponseModel<>(false, "HTTP Error: " + ex.getMessage(), null));
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseModel<>(false, "An unexpected error occurred: " + ex.getMessage(), null));
    }
}

///// old code
    public ResponseEntity<ResponseModel<String>> addWorkOrder(String workOrderNumber) {
        try {
            String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

            // String url = "http://ocitestekaayansoa.adityabirla.com:80/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            BasicDetails basicDetails = basicDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            AdditionalDetails additionalDetails = additionalDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            List<Operation> operationList = operationRepository.findByWorkOrderNumber(workOrderNumber);
            List<Material> materialList = materialRepository.findByWorkOrderNumber(workOrderNumber);

            if (basicDetails == null && additionalDetails == null && operationList.isEmpty() && materialList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel<>(false, "No details found for the given work order number.", null));
            }

            if (basicDetails == null) {
                System.out.println("BasicDetails is null for workOrderNumber: " + workOrderNumber);
            } else {
                System.out.println("BasicDetails: " + basicDetails.toString());
            }

            if (additionalDetails == null) {
                System.out.println("AdditionalDetails is null for workOrderNumber: " + workOrderNumber);
            } else {
                System.out.println("AdditionalDetails: " + additionalDetails.toString());
            }


            Map<String, Object> body = new HashMap<>();
            body.put("P_BO_IDENTIFIER", "EAM");
            body.put("P_API_VERSION_NUMBER", 1);
            body.put("P_INIT_MSG_LIST", "1");
            body.put("P_COMMIT", "Y");

            // EAM Work Order Record
            Map<String, Object> eamWoRec = new HashMap<>();
            eamWoRec.put("HEADER_ID", 1);
            eamWoRec.put("BATCH_ID", 1);
            eamWoRec.put("ROW_ID", null);
//            eamWoRec.put("WIP_ENTITY_NAME",  workOrderNumber.contains("TEMPWO") ? null:workOrderNumber);
//            eamWoRec.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
            eamWoRec.put("WIP_ENTITY_NAME", null);
            eamWoRec.put("ORGANIZATION_ID", basicDetails != null ? basicDetails.getOrganizationId() : null);
            eamWoRec.put("DESCRIPTION", basicDetails != null ? basicDetails.getDescription() : null);
//            eamWoRec.put("ASSET_NUMBER", basicDetails != null ? basicDetails.getAssetNumber() :null);
//            eamWoRec.put("ASSET_GROUP_ID", basicDetails != null ? basicDetails.getAssetGroupId() :null);
            eamWoRec.put("ASSET_NUMBER", null);
            eamWoRec.put("ASSET_GROUP_ID", null);
            /*eamWoRec.put("REBUILD_ITEM_ID",  null);
            eamWoRec.put("REBUILD_SERIAL_NUMBER", null);*/
            eamWoRec.put("REBUILD_ITEM_ID", basicDetails != null ? basicDetails.getRebuildItemId() : null);
            eamWoRec.put("REBUILD_SERIAL_NUMBER", basicDetails != null ? basicDetails.getRebuildSerialNo() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_ID", basicDetails != null ? basicDetails.getMaintenanceObjectId() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_TYPE", basicDetails != null ? basicDetails.getMaintenanceObjectType() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_SOURCE", 1);
            eamWoRec.put("CLASS_CODE", basicDetails != null ? basicDetails.getWipAccountingClassCode() : null);
            eamWoRec.put("WORK_ORDER_TYPE", basicDetails != null ? basicDetails.getWoType() : null);
            eamWoRec.put("STATUS_TYPE", basicDetails != null ? basicDetails.getStatusType() : null);
            eamWoRec.put("PRIORITY", basicDetails != null ? basicDetails.getPriority() : null);
            eamWoRec.put("SHUTDOWN_TYPE", basicDetails != null ? basicDetails.getShutdownType() : null);
            eamWoRec.put("FIRM_PLANNED_FLAG", basicDetails != null ? basicDetails.getFirmPlannedType() : null);
            eamWoRec.put("SCHEDULED_START_DATE", basicDetails != null ? basicDetails.getScheduledStartDate() : null);
            eamWoRec.put("SCHEDULED_COMPLETION_DATE", basicDetails != null ? basicDetails.getScheduledEndDate() : null);
            eamWoRec.put("NOTIFICATION_REQUIRED", additionalDetails != null ? additionalDetails.getNotificationRequired() : null);
            eamWoRec.put("TAGOUT_REQUIRED", additionalDetails != null ? additionalDetails.getTagoutRequired() : null);
            eamWoRec.put("ATTRIBUTE3", additionalDetails != null ? additionalDetails.getSafetyPermit() : null);
            eamWoRec.put("EAM_LINEAR_LOCATION_ID", null);
            eamWoRec.put("ASSET_ACTIVITY_ID", null);
            eamWoRec.put("DUE_DATE", null);
            eamWoRec.put("ACTIVITY_TYPE", null);
            eamWoRec.put("ACTIVITY_CAUSE", null);
            eamWoRec.put("ACTIVITY_SOURCE", null);
            eamWoRec.put("JOB_QUANTITY", null);
            eamWoRec.put("DATE_RELEASED", null);
            eamWoRec.put("OWNING_DEPARTMENT", basicDetails != null ? basicDetails.getDepartmentId() : null);
            eamWoRec.put("REQUESTED_START_DATE", null);
            eamWoRec.put("PLANNER_TYPE", basicDetails != null ? basicDetails.getPlannerType() : null);
            eamWoRec.put("PLAN_MAINTENANCE", null);
            eamWoRec.put("PROJECT_ID", null);
            eamWoRec.put("TASK_ID", null);
            eamWoRec.put("END_ITEM_UNIT_NUMBER", null);
            eamWoRec.put("SCHEDULE_GROUP_ID", null);
            eamWoRec.put("BOM_REVISION_DATE", null);
            eamWoRec.put("ROUTING_REVISION_DATE", null);
            eamWoRec.put("ALTERNATE_ROUTING_DESIGNATOR", null);
            eamWoRec.put("ALTERNATE_BOM_DESIGNATOR", null);
            eamWoRec.put("ROUTING_REVISION", null);
            eamWoRec.put("BOM_REVISION", null);
            eamWoRec.put("MANUAL_REBUILD_FLAG", null);
            eamWoRec.put("PM_SCHEDULE_ID", null);
            eamWoRec.put("WIP_SUPPLY_TYPE", null);
            eamWoRec.put("MATERIAL_ACCOUNT", null);
            eamWoRec.put("MATERIAL_OVERHEAD_ACCOUNT", null);
            eamWoRec.put("RESOURCE_ACCOUNT", null);
            eamWoRec.put("OUTSIDE_PROCESSING_ACCOUNT", null);
            eamWoRec.put("MATERIAL_VARIANCE_ACCOUNT", null);
            eamWoRec.put("RESOURCE_VARIANCE_ACCOUNT", null);
            eamWoRec.put("OUTSIDE_PROC_VARIANCE_ACCOUNT", null);
            eamWoRec.put("STD_COST_ADJUSTMENT_ACCOUNT", null);
            eamWoRec.put("OVERHEAD_ACCOUNT", null);
            eamWoRec.put("OVERHEAD_VARIANCE_ACCOUNT", null);
            eamWoRec.put("PM_SUGGESTED_START_DATE", null);
            eamWoRec.put("PM_SUGGESTED_END_DATE", null);
            eamWoRec.put("PM_BASE_METER_READING", null);
            eamWoRec.put("PM_BASE_METER", null);
            eamWoRec.put("COMMON_BOM_SEQUENCE_ID", null);
            eamWoRec.put("COMMON_ROUTING_SEQUENCE_ID", null);
            eamWoRec.put("PO_CREATION_TIME", null);
            eamWoRec.put("GEN_OBJECT_ID", null);
            eamWoRec.put("USER_DEFINED_STATUS_ID", null);
            eamWoRec.put("PENDING_FLAG", null);
            eamWoRec.put("MATERIAL_SHORTAGE_CHECK_DATE", null);
            eamWoRec.put("MATERIAL_SHORTAGE_FLAG", null);
            eamWoRec.put("WORKFLOW_TYPE", null);
            eamWoRec.put("WARRANTY_CLAIM_STATUS", null);
            eamWoRec.put("CYCLE_ID", null);
            eamWoRec.put("SEQ_ID", null);
            eamWoRec.put("DS_SCHEDULED_FLAG", null);
            eamWoRec.put("WARRANTY_ACTIVE", null);
            eamWoRec.put("ASSIGNMENT_COMPLETE", null);
            eamWoRec.put("ATTRIBUTE_CATEGORY", null);
            eamWoRec.put("ATTRIBUTE1", null);
            eamWoRec.put("ATTRIBUTE2", "Y");
//            eamWoRec.put("ATTRIBUTE2", workOrderNumber.contains("TEMPWO") ? null:additionalDetails.getInformDepartments());
            eamWoRec.put("ATTRIBUTE4", null);
            eamWoRec.put("ATTRIBUTE5", null);
            eamWoRec.put("ATTRIBUTE6", null);
            eamWoRec.put("ATTRIBUTE7", null);
            eamWoRec.put("ATTRIBUTE8", null);
            eamWoRec.put("ATTRIBUTE9", null);
            eamWoRec.put("ATTRIBUTE10", null);
            eamWoRec.put("ATTRIBUTE11", null);
            eamWoRec.put("ATTRIBUTE12", null);
            eamWoRec.put("ATTRIBUTE13", null);
            eamWoRec.put("ATTRIBUTE14", null);
            eamWoRec.put("ATTRIBUTE15", null);
            eamWoRec.put("MATERIAL_ISSUE_BY_MO", null);
            eamWoRec.put("ISSUE_ZERO_COST_FLAG", null);
            eamWoRec.put("REPORT_TYPE", null);
            eamWoRec.put("ACTUAL_CLOSE_DATE", null);
            eamWoRec.put("SUBMISSION_DATE", null);
            eamWoRec.put("USER_ID", null);
            eamWoRec.put("RESPONSIBILITY_ID", null);
            eamWoRec.put("REQUEST_ID", null);
            eamWoRec.put("PROGRAM_ID", null);
            eamWoRec.put("PROGRAM_APPLICATION_ID", null);
            eamWoRec.put("SOURCE_LINE_ID", null);
            eamWoRec.put("SOURCE_CODE", null);
            eamWoRec.put("VALIDATE_STRUCTURE", null);
            eamWoRec.put("RETURN_STATUS", null);
            eamWoRec.put("TRANSACTION_TYPE", 1);
            eamWoRec.put("FAILURE_CODE_REQUIRED", null);


            body.put("P_EAM_WO_REC", eamWoRec);
            // EAM_FAILURE_ENTRY_RECORD
            //MultiValueMap<String, Object> failureEntryRecord = new LinkedMultiValueMap<>();
            Map<String, Object> failureEntryRecord = new HashMap<>();
            failureEntryRecord.put("FAILURE_ID", null);
            failureEntryRecord.put("FAILURE_DATE", null);
            failureEntryRecord.put("SOURCE_TYPE", null);
            failureEntryRecord.put("SOURCE_ID", null);
            failureEntryRecord.put("OBJECT_TYPE", null);
            failureEntryRecord.put("OBJECT_ID", null);
            failureEntryRecord.put("MAINT_ORGANIZATION_ID", null);
            failureEntryRecord.put("CURRENT_ORGANIZATION_ID", null);
            failureEntryRecord.put("DEPARTMENT_ID", null);
            failureEntryRecord.put("AREA_ID", null);
            failureEntryRecord.put("TRANSACTION_TYPE", null);
            failureEntryRecord.put("SOURCE_NAME", null);

            eamWoRec.put("EAM_FAILURE_ENTRY_RECORD", failureEntryRecord);


            // EAM_FAILURE_CODES_TBL
            //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
            Map<String, Object> failureCodesTblItem = new HashMap<>();
            failureCodesTblItem.put("FAILURE_ID", null);
            failureCodesTblItem.put("FAILURE_ENTRY_ID", null);
            failureCodesTblItem.put("COMBINATION_ID", null);
            failureCodesTblItem.put("FAILURE_CODE", null);
            failureCodesTblItem.put("CAUSE_CODE", null);
            failureCodesTblItem.put("RESOLUTION_CODE", null);
            failureCodesTblItem.put("COMMENTS", null);
            failureCodesTblItem.put("TRANSACTION_TYPE", null);

            //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
            Map<String, Object> failureCodesTbl = new HashMap<>();
            failureCodesTbl.put("EAM_FAILURE_CODES_TBL_ITEM", Collections.singletonList(failureCodesTblItem));

            eamWoRec.put("EAM_FAILURE_CODES_TBL", failureCodesTbl);

            eamWoRec.put("TARGET_START_DATE", null);
            eamWoRec.put("TARGET_COMPLETION_DATE", null);


            // Process Operations, Resources, and Instances
            List<OperationChild> opList = new ArrayList<>();
            List<OperationChild> resourceList = new ArrayList<>();
            List<OperationChild> instanceList = new ArrayList<>();

            for (Operation operation : operationList) {
                for (OperationChild operationChild : operation.getOperationList()) {
                    if (operationChild.getOperationSequence() != null || operationChild.getDescription() != null || operationChild.getDepartmentId() != null) {
                        opList.add(operationChild);
                    }
                    if (operationChild.getResourceSequence() != null || operationChild.getResourceId() != null) {
                        resourceList.add(operationChild);
                    }
                    if (operationChild.getInstanceId() != null || operationChild.getInstanceName() != null) {
                        instanceList.add(operationChild);
                    }
                }
            }

           /* body.put("P_EAM_OP_TBL", null);
            body.put("P_EAM_RES_TBL", null);
            body.put("P_EAM_RES_INST_TBL", null);*/

            List<MaterialChild> mcList = new ArrayList<>();
            for (Material material : materialList) {
                for (MaterialChild materialChild : material.getMaterialList()) {
                    if (materialChild.getOperationSequence() != null) {
                        mcList.add(materialChild);
                    }
                }
            }

            //body.put("P_EAM_MAT_REQ_TBL", null);

            // Set null values for other fields that are not needed
            body.put("P_EAM_OP_NETWORK_TBL", null);
            body.put("P_EAM_DIRECT_ITEMS_TBL", null);
            body.put("P_EAM_WO_COMP_REC", null);
            body.put("P_EAM_WO_QUALITY_TBL", null);
            body.put("P_EAM_METER_READING_TBL", null);
            body.put("P_EAM_COUNTER_PROP_TBL", null);
            body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
            body.put("P_EAM_WO_COMP_MR_READ_TBL", null);
            body.put("P_EAM_OP_COMP_TBL", null);
            body.put("P_EAM_PERMIT_TBL", null);
            body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
            body.put("P_EAM_WORK_CLEARANCE_TBL", null);
            body.put("P_EAM_WC_WO_ASSOC_TBL", null);
            body.put("P_EAM_RES_USAGE_TBL", null);
            body.put("P_EAM_SUB_RES_TBL", null);
            body.put("P_DEBUG", "N");
            body.put("P_OUTPUT_DIR", "/usr/tmp");
            body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
            body.put("P_DEBUG_FILE_MODE", "w");


            Map<String, Object> requestTableItem = new HashMap<>();
            requestTableItem.put("HEADER_ID", null);
            requestTableItem.put("BATCH_ID", null);
            requestTableItem.put("ROW_ID", null);
            requestTableItem.put("WIP_ENTITY_NAME", null);
//            requestTableItem.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
            requestTableItem.put("ORGANIZATION_ID", basicDetails.getOrganizationId());
           // requestTableItem.put("ORGANIZATION_CODE", null);
            requestTableItem.put("REQUEST_TYPE", 1);
            requestTableItem.put("REQUEST_ID", basicDetails.getWorkRequestNo());
            requestTableItem.put("REQUEST_NUMBER", basicDetails.getWorkRequestNo());
            requestTableItem.put("ATTRIBUTE_CATEGORY", null);
            requestTableItem.put("ATTRIBUTE1", null);
            requestTableItem.put("ATTRIBUTE2", null);
            requestTableItem.put("ATTRIBUTE3", null);
            requestTableItem.put("ATTRIBUTE4", null);
            requestTableItem.put("ATTRIBUTE5", null);
            requestTableItem.put("ATTRIBUTE6", null);
            requestTableItem.put("ATTRIBUTE7", null);
            requestTableItem.put("ATTRIBUTE8", null);
            requestTableItem.put("ATTRIBUTE9", null);
            requestTableItem.put("ATTRIBUTE10", null);
            requestTableItem.put("ATTRIBUTE11", null);
            requestTableItem.put("ATTRIBUTE12", null);
            requestTableItem.put("ATTRIBUTE13", null);
            requestTableItem.put("ATTRIBUTE14", null);
            requestTableItem.put("ATTRIBUTE15", null);
            requestTableItem.put("PROGRAM_ID", null);
            requestTableItem.put("PROGRAM_REQUEST_ID", null);
            requestTableItem.put("PROGRAM_UPDATE_DATE", null);
            requestTableItem.put("PROGRAM_APPLICATION_ID", null);
            requestTableItem.put("WORK_REQUEST_STATUS_ID", null);
            requestTableItem.put("SERVICE_ASSOC_ID", null);
            requestTableItem.put("RETURN_STATUS", null);
            requestTableItem.put("TRANSACTION_TYPE", 1);

            Map<String, Object> requestTBL = new HashMap<>();
            requestTBL.put("P_EAM_REQUEST_TBL_ITEM", Collections.singletonList(requestTableItem));


            body.put("P_EAM_REQUEST_TBL", requestTBL);
            //body.put("P_EAM_REQUEST_TBL", null);

            ObjectMapper objectMapper1 = new ObjectMapper();
            String json1 = objectMapper1.writeValueAsString(body);

            System.out.println(json1);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            System.out.println("Entity records = " + entity);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println("ResponseEntity = " + responseEntity);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String response = responseEntity.getBody();

                if (response != null && !response.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                    JsonNode jsonNode = objectMapper.readTree(response);
                    String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();
                    JsonNode eamWoRecNode = jsonNode.get("X_EAM_WO_REC");

                    String wipEntityName = null;
                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_NAME")) {
                        JsonNode wipEntityNode = eamWoRecNode.get("WIP_ENTITY_NAME");
                        if (wipEntityNode.has("$")) {
                            wipEntityName = wipEntityNode.get("$").asText();
                        }
                    }

                    String wipEntityId = null;
                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_ID")) {
                        JsonNode wipEntityIdNode = eamWoRecNode.get("WIP_ENTITY_ID");
                        if (wipEntityIdNode.has("$")) {
                            wipEntityId = wipEntityIdNode.get("$").asText();
                        }
                    }

                    if ("S".equals(responseStatus) && wipEntityName != null && !wipEntityName.isEmpty()) {
                        // String wipEntityName = jsonNode.get("WIP_ENTITY_NAME").asText();

                       /* if (basicDetails != null) {
                            basicDetails.setWorkOrderNumber(wipEntityName);
                            basicDetails.setWipEntityName(wipEntityName);
                            basicDetails.setWipEntityId(wipEntityId);
                            basicDetailsRepository.save(basicDetails);
                        }
                        if (additionalDetails != null) {
                            additionalDetails.setWorkOrderNumber(wipEntityName);
                            additionalDetailsRepository.save(additionalDetails);
                        }

                        if (operationList != null && !operationList.isEmpty()) {
                            Operation operation = operationList.getFirst();
                            operation.setWorkOrderNumber(wipEntityName);
                            operationRepository.save(operation);
                            for (OperationChild operationChild : operation.getOperationList()) {
                                operationChild.setWorkOrderNumber(wipEntityName);
                                operationChild.setWipEntityId(wipEntityId);
                                operationChildRepository.save(operationChild);
                            }
                        }

                        if (materialList != null && !materialList.isEmpty()) {
                            Material material = materialList.getFirst();
                            material.setWorkOrderNumber(wipEntityName);
                            materialRepository.save(material);
                            for (MaterialChild materialChild : material.getMaterialList()) {
                                materialChild.setWorkOrderNumber(wipEntityName);
                                materialChild.setWipEntityId(wipEntityId);
                                materialChildRepository.save(materialChild);
                            }
                        }
*/

                        if (basicDetails != null) {
                            basicDetailsRepository.delete(basicDetails);
                        }

                        if (additionalDetails != null) {
                            additionalDetailsRepository.delete(additionalDetails);
                        }

                        if (operationList != null && !operationList.isEmpty()) {
                            for (Operation operation : operationList) {
                                List<OperationChild> operationChildren = operation.getOperationList();
                                if (operationChildren != null && !operationChildren.isEmpty()) {
                                    operationChildRepository.deleteAll(operationChildren);
                                }
                                operationRepository.delete(operation);
                            }
                        }
                        if (materialList != null && !materialList.isEmpty()) {
                            for (Material material : materialList) {
                                List<MaterialChild> materialChildren = material.getMaterialList();
                                if (materialChildren != null && !materialChildren.isEmpty()) {
                                    materialChildRepository.deleteAll(materialChildren);
                                }
                                materialRepository.delete(material);
                            }
                        }

                        return ResponseEntity.ok(new ResponseModel<>(true, "WorkOrder Created Successfully !!", wipEntityName));

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
                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel<>(false, "An unexpected error occurred: " + ex.getMessage(), null));
        }
    }



/*
    public ResponseEntity<ResponseModel<String>> addWorkOrder(String workOrderNumber) {
        try {
            String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

            // String url = "http://ocitestekaayansoa.adityabirla.com:80/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            BasicDetails basicDetails = basicDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            AdditionalDetails additionalDetails = additionalDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            List<Operation> operationList = operationRepository.findByWorkOrderNumber(workOrderNumber);
            List<Material> materialList = materialRepository.findByWorkOrderNumber(workOrderNumber);

            if (basicDetails == null && additionalDetails == null && operationList.isEmpty() && materialList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel<>(false, "No details found for the given work order number.", null));
            }

            if (basicDetails == null) {
                System.out.println("BasicDetails is null for workOrderNumber: " + workOrderNumber);
            } else {
                System.out.println("BasicDetails: " + basicDetails.toString());
            }

            if (additionalDetails == null) {
                System.out.println("AdditionalDetails is null for workOrderNumber: " + workOrderNumber);
            } else {
                System.out.println("AdditionalDetails: " + additionalDetails.toString());
            }


            Map<String, Object> body = new HashMap<>();
            body.put("P_BO_IDENTIFIER", "EAM");
            body.put("P_API_VERSION_NUMBER", 1);
            body.put("P_INIT_MSG_LIST", "1");
            body.put("P_COMMIT", "Y");

            // EAM Work Order Record
            Map<String, Object> eamWoRec = new HashMap<>();
            eamWoRec.put("HEADER_ID", 1);
            eamWoRec.put("BATCH_ID", 1);
            eamWoRec.put("ROW_ID", null);
//        eamWoRec.put("WIP_ENTITY_NAME",  workOrderNumber.contains("TEMPWO") ? null:workOrderNumber);
//        eamWoRec.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
            eamWoRec.put("WIP_ENTITY_NAME",null);
            eamWoRec.put("ORGANIZATION_ID", basicDetails != null ? basicDetails.getOrganizationId() : null);
            eamWoRec.put("DESCRIPTION", basicDetails != null ? basicDetails.getDescription() : null);
            eamWoRec.put("ASSET_NUMBER", null);
            //eamWoRec.put("ASSET_NUMBER", basicDetails != null ? basicDetails.getAssetNumber() :null);
            //eamWoRec.put("ASSET_GROUP_ID", basicDetails != null ? basicDetails.getAssetGroupId() :null);
            eamWoRec.put("ASSET_GROUP_ID", null);
            eamWoRec.put("REBUILD_ITEM_ID", basicDetails != null ? basicDetails.getRebuildItemId() : null);
            eamWoRec.put("REBUILD_SERIAL_NUMBER", basicDetails != null ? basicDetails.getRebuildSerialNo() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_ID", basicDetails != null ? basicDetails.getMaintenanceObjectId() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_TYPE", basicDetails != null ? basicDetails.getMaintenanceObjectType() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_SOURCE", 1);
            eamWoRec.put("CLASS_CODE", basicDetails != null ? basicDetails.getWipAccountingClassCode() : null);
            eamWoRec.put("WORK_ORDER_TYPE", basicDetails != null ? basicDetails.getWoType() : null);
            eamWoRec.put("STATUS_TYPE", basicDetails != null ? basicDetails.getStatusType() : null);
            eamWoRec.put("PRIORITY", basicDetails != null ? basicDetails.getPriority() : null);
            eamWoRec.put("SHUTDOWN_TYPE", basicDetails != null ? basicDetails.getShutdownType() : null);
            eamWoRec.put("FIRM_PLANNED_FLAG", basicDetails != null ? basicDetails.getFirmPlannedType() : null);
            eamWoRec.put("SCHEDULED_START_DATE", basicDetails != null ? basicDetails.getScheduledStartDate() : null);
            eamWoRec.put("SCHEDULED_COMPLETION_DATE", basicDetails != null ? basicDetails.getScheduledEndDate() : null);
            eamWoRec.put("NOTIFICATION_REQUIRED", additionalDetails != null ? additionalDetails.getNotificationRequired() : null);
            eamWoRec.put("TAGOUT_REQUIRED", additionalDetails != null ? additionalDetails.getTagoutRequired() : null);
            eamWoRec.put("ATTRIBUTE3", additionalDetails != null ? additionalDetails.getSafetyPermit() : null);
            eamWoRec.put("EAM_LINEAR_LOCATION_ID", null);
            eamWoRec.put("ASSET_ACTIVITY_ID", null);
            eamWoRec.put("DUE_DATE", null);
            eamWoRec.put("ACTIVITY_TYPE", null);
            eamWoRec.put("ACTIVITY_CAUSE", null);
            eamWoRec.put("ACTIVITY_SOURCE", null);
            eamWoRec.put("JOB_QUANTITY", null);
            eamWoRec.put("DATE_RELEASED", null);
            eamWoRec.put("OWNING_DEPARTMENT", basicDetails != null ? basicDetails.getDepartmentId() : null);
            eamWoRec.put("REQUESTED_START_DATE", null);
            eamWoRec.put("PLANNER_TYPE", basicDetails != null ? basicDetails.getPlannerType() : null);
            eamWoRec.put("PLAN_MAINTENANCE", null);
            eamWoRec.put("PROJECT_ID", null);
            eamWoRec.put("TASK_ID", null);
            eamWoRec.put("END_ITEM_UNIT_NUMBER", null);
            eamWoRec.put("SCHEDULE_GROUP_ID", null);
            eamWoRec.put("BOM_REVISION_DATE", null);
            eamWoRec.put("ROUTING_REVISION_DATE", null);
            eamWoRec.put("ALTERNATE_ROUTING_DESIGNATOR", null);
            eamWoRec.put("ALTERNATE_BOM_DESIGNATOR", null);
            eamWoRec.put("ROUTING_REVISION", null);
            eamWoRec.put("BOM_REVISION", null);
            eamWoRec.put("MANUAL_REBUILD_FLAG", null);
            eamWoRec.put("PM_SCHEDULE_ID", null);
            eamWoRec.put("WIP_SUPPLY_TYPE", null);
            eamWoRec.put("MATERIAL_ACCOUNT", null);
            eamWoRec.put("MATERIAL_OVERHEAD_ACCOUNT", null);
            eamWoRec.put("RESOURCE_ACCOUNT", null);
            eamWoRec.put("OUTSIDE_PROCESSING_ACCOUNT", null);
            eamWoRec.put("MATERIAL_VARIANCE_ACCOUNT", null);
            eamWoRec.put("RESOURCE_VARIANCE_ACCOUNT", null);
            eamWoRec.put("OUTSIDE_PROC_VARIANCE_ACCOUNT", null);
            eamWoRec.put("STD_COST_ADJUSTMENT_ACCOUNT", null);
            eamWoRec.put("OVERHEAD_ACCOUNT", null);
            eamWoRec.put("OVERHEAD_VARIANCE_ACCOUNT", null);
            eamWoRec.put("PM_SUGGESTED_START_DATE", null);
            eamWoRec.put("PM_SUGGESTED_END_DATE", null);
            eamWoRec.put("PM_BASE_METER_READING", null);
            eamWoRec.put("PM_BASE_METER", null);
            eamWoRec.put("COMMON_BOM_SEQUENCE_ID", null);
            eamWoRec.put("COMMON_ROUTING_SEQUENCE_ID", null);
            eamWoRec.put("PO_CREATION_TIME", null);
            eamWoRec.put("GEN_OBJECT_ID", null);
            eamWoRec.put("USER_DEFINED_STATUS_ID", null);
            eamWoRec.put("PENDING_FLAG", null);
            eamWoRec.put("MATERIAL_SHORTAGE_CHECK_DATE", null);
            eamWoRec.put("MATERIAL_SHORTAGE_FLAG", null);
            eamWoRec.put("WORKFLOW_TYPE", null);
            eamWoRec.put("WARRANTY_CLAIM_STATUS", null);
            eamWoRec.put("CYCLE_ID", null);
            eamWoRec.put("SEQ_ID", null);
            eamWoRec.put("DS_SCHEDULED_FLAG", null);
            eamWoRec.put("WARRANTY_ACTIVE", null);
            eamWoRec.put("ASSIGNMENT_COMPLETE", null);
            eamWoRec.put("ATTRIBUTE_CATEGORY", null);
            eamWoRec.put("ATTRIBUTE1", null);
            eamWoRec.put("ATTRIBUTE2", null);
            eamWoRec.put("ATTRIBUTE4", null);
            eamWoRec.put("ATTRIBUTE5", null);
            eamWoRec.put("ATTRIBUTE6", null);
            eamWoRec.put("ATTRIBUTE7", null);
            eamWoRec.put("ATTRIBUTE8", null);
            eamWoRec.put("ATTRIBUTE9", null);
            eamWoRec.put("ATTRIBUTE10", null);
            eamWoRec.put("ATTRIBUTE11", null);
            eamWoRec.put("ATTRIBUTE12", null);
            eamWoRec.put("ATTRIBUTE13", null);
            eamWoRec.put("ATTRIBUTE14", null);
            eamWoRec.put("ATTRIBUTE15", null);
            eamWoRec.put("MATERIAL_ISSUE_BY_MO", null);
            eamWoRec.put("ISSUE_ZERO_COST_FLAG", null);
            eamWoRec.put("REPORT_TYPE", null);
            eamWoRec.put("ACTUAL_CLOSE_DATE", null);
            eamWoRec.put("SUBMISSION_DATE", null);
            eamWoRec.put("USER_ID", null);
            eamWoRec.put("RESPONSIBILITY_ID", null);
            eamWoRec.put("REQUEST_ID", null);
            eamWoRec.put("PROGRAM_ID", null);
            eamWoRec.put("PROGRAM_APPLICATION_ID", null);
            eamWoRec.put("SOURCE_LINE_ID", null);
            eamWoRec.put("SOURCE_CODE", null);
            eamWoRec.put("VALIDATE_STRUCTURE", null);
            eamWoRec.put("RETURN_STATUS", null);
            eamWoRec.put("TRANSACTION_TYPE", 1);
            eamWoRec.put("FAILURE_CODE_REQUIRED", null);


            body.put("P_EAM_WO_REC", eamWoRec);


            // EAM_FAILURE_ENTRY_RECORD
            //MultiValueMap<String, Object> failureEntryRecord = new LinkedMultiValueMap<>();
            Map<String, Object> failureEntryRecord = new HashMap<>();
            failureEntryRecord.put("FAILURE_ID", null);
            failureEntryRecord.put("FAILURE_DATE", null);
            failureEntryRecord.put("SOURCE_TYPE", null);
            failureEntryRecord.put("SOURCE_ID", null);
            failureEntryRecord.put("OBJECT_TYPE", null);
            failureEntryRecord.put("OBJECT_ID", null);
            failureEntryRecord.put("MAINT_ORGANIZATION_ID", null);
            failureEntryRecord.put("CURRENT_ORGANIZATION_ID", null);
            failureEntryRecord.put("DEPARTMENT_ID", null);
            failureEntryRecord.put("AREA_ID", null);
            failureEntryRecord.put("TRANSACTION_TYPE", null);
            failureEntryRecord.put("SOURCE_NAME", null);

            eamWoRec.put("EAM_FAILURE_ENTRY_RECORD", failureEntryRecord);


            // EAM_FAILURE_CODES_TBL
            //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
            Map<String, Object> failureCodesTblItem = new HashMap<>();
            failureCodesTblItem.put("FAILURE_ID", null);
            failureCodesTblItem.put("FAILURE_ENTRY_ID", null);
            failureCodesTblItem.put("COMBINATION_ID", null);
            failureCodesTblItem.put("FAILURE_CODE", null);
            failureCodesTblItem.put("CAUSE_CODE", null);
            failureCodesTblItem.put("RESOLUTION_CODE", null);
            failureCodesTblItem.put("COMMENTS", null);
            failureCodesTblItem.put("TRANSACTION_TYPE", null);

            //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
            Map<String, Object> failureCodesTbl = new HashMap<>();
            failureCodesTbl.put("EAM_FAILURE_CODES_TBL_ITEM", Collections.singletonList(failureCodesTblItem));

            eamWoRec.put("EAM_FAILURE_CODES_TBL", failureCodesTbl);

            eamWoRec.put("TARGET_START_DATE", null);
            eamWoRec.put("TARGET_COMPLETION_DATE", null);


            // Process Operations, Resources, and Instances
            List<OperationChild> opList = new ArrayList<>();
            List<OperationChild> resourceList = new ArrayList<>();
            List<OperationChild> instanceList = new ArrayList<>();

            for (Operation operation : operationList) {
                for (OperationChild operationChild : operation.getOperationList()) {
                    if (operationChild.getOperationSequence() != null || operationChild.getDescription() != null || operationChild.getDepartmentId() != null) {
                        opList.add(operationChild);
                    }
                    if (operationChild.getResourceSequence() != null || operationChild.getResourceId() != null) {
                        resourceList.add(operationChild);
                    }
                    if (operationChild.getInstanceId() != null || operationChild.getInstanceName() != null) {
                        instanceList.add(operationChild);
                    }
                }
            }

            //body.put("P_EAM_OP_TBL", opList);
            //body.put("P_EAM_RES_TBL", resourceList);
            //body.put("P_EAM_RES_INST_TBL", instanceList);

            List<MaterialChild> mcList = new ArrayList<>();
            for (Material material : materialList) {
                for (MaterialChild materialChild : material.getMaterialList()) {
                    if (materialChild.getOperationSequence() != null) {
                        mcList.add(materialChild);
                    }
                }
            }

            //body.put("P_EAM_MAT_REQ_TBL", mcList);

            // Set null values for other fields that are not needed
            body.put("P_EAM_OP_NETWORK_TBL", null);
            body.put("P_EAM_DIRECT_ITEMS_TBL", null);
            body.put("P_EAM_WO_COMP_REC", null);
            body.put("P_EAM_WO_QUALITY_TBL", null);
            body.put("P_EAM_METER_READING_TBL", null);
            body.put("P_EAM_COUNTER_PROP_TBL", null);
            body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
            body.put("P_EAM_WO_COMP_MR_READ_TBL", null);
            body.put("P_EAM_OP_COMP_TBL", null);
            body.put("P_EAM_PERMIT_TBL", null);
            body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
            body.put("P_EAM_WORK_CLEARANCE_TBL", null);
            body.put("P_EAM_WC_WO_ASSOC_TBL", null);
            body.put("P_EAM_RES_USAGE_TBL", null);
            body.put("P_EAM_SUB_RES_TBL", null);
            body.put("P_DEBUG", "N");
            body.put("P_OUTPUT_DIR", "/usr/tmp");
            body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
            body.put("P_DEBUG_FILE_MODE", "w");


            Map<String, Object> requestTableItem = new HashMap<>();
            requestTableItem.put("HEADER_ID", null);
            requestTableItem.put("BATCH_ID", null);
            requestTableItem.put("ROW_ID", null);
            requestTableItem.put("WIP_ENTITY_NAME", null);
//        requestTableItem.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
            requestTableItem.put("ORGANIZATION_ID", basicDetails.getOrganizationId());
            requestTableItem.put("ORGANIZATION_CODE", null);
            requestTableItem.put("REQUEST_TYPE", 1);
            requestTableItem.put("REQUEST_ID", basicDetails.getWorkRequestNo());
            requestTableItem.put("REQUEST_NUMBER", basicDetails.getWorkRequestNo());
            requestTableItem.put("ATTRIBUTE_CATEGORY", null);
            requestTableItem.put("ATTRIBUTE1", null);
            requestTableItem.put("ATTRIBUTE2", null);
            requestTableItem.put("ATTRIBUTE3", null);
            requestTableItem.put("ATTRIBUTE4", null);
            requestTableItem.put("ATTRIBUTE5", null);
            requestTableItem.put("ATTRIBUTE6", null);
            requestTableItem.put("ATTRIBUTE7", null);
            requestTableItem.put("ATTRIBUTE8", null);
            requestTableItem.put("ATTRIBUTE9", null);
            requestTableItem.put("ATTRIBUTE10", null);
            requestTableItem.put("ATTRIBUTE11", null);
            requestTableItem.put("ATTRIBUTE12", null);
            requestTableItem.put("ATTRIBUTE13", null);
            requestTableItem.put("ATTRIBUTE14", null);
            requestTableItem.put("ATTRIBUTE15", null);
            requestTableItem.put("PROGRAM_ID", null);
            requestTableItem.put("PROGRAM_REQUEST_ID", null);
            requestTableItem.put("PROGRAM_UPDATE_DATE", null);
            requestTableItem.put("PROGRAM_APPLICATION_ID", null);
            requestTableItem.put("WORK_REQUEST_STATUS_ID", null);
            requestTableItem.put("SERVICE_ASSOC_ID", null);
            requestTableItem.put("RETURN_STATUS", null);
            requestTableItem.put("TRANSACTION_TYPE", 1);

            Map<String, Object> requestTBL = new HashMap<>();
            requestTBL.put("P_EAM_REQUEST_TBL_ITEM", Collections.singletonList(requestTableItem));


            body.put("P_EAM_REQUEST_TBL", requestTBL);

            ObjectMapper objectMapper1 = new ObjectMapper();
            String json1 = objectMapper1.writeValueAsString(body);

            System.out.println(json1);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            System.out.println("Entity records = " + entity);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println("ResponseEntity = " + responseEntity);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String response = responseEntity.getBody();

                if (response != null && !response.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                    JsonNode jsonNode = objectMapper.readTree(response);
                    String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();
                    JsonNode eamWoRecNode = jsonNode.get("X_EAM_WO_REC");

                    String wipEntityName = null;
                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_NAME")) {
                        JsonNode wipEntityNode = eamWoRecNode.get("WIP_ENTITY_NAME");
                        if (wipEntityNode.has("$")) {
                            wipEntityName = wipEntityNode.get("$").asText();
                        }
                    }

                    String wipEntityId = null;
                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_ID")) {
                        JsonNode wipEntityIdNode = eamWoRecNode.get("WIP_ENTITY_ID");
                        if (wipEntityIdNode.has("$")) {
                            wipEntityId = wipEntityIdNode.get("$").asText();
                        }
                    }

                    if ("S".equals(responseStatus) && wipEntityName != null && !wipEntityName.isEmpty()) {
                        // String wipEntityName = jsonNode.get("WIP_ENTITY_NAME").asText();

                   */
/* if (basicDetails != null) {
                        basicDetails.setWorkOrderNumber(wipEntityName);
                        basicDetails.setWipEntityName(wipEntityName);
                        basicDetails.setWipEntityId(wipEntityId);
                        basicDetailsRepository.save(basicDetails);
                    }
                    if (additionalDetails != null) {
                        additionalDetails.setWorkOrderNumber(wipEntityName);
                        additionalDetailsRepository.save(additionalDetails);
                    }

                    if (operationList != null && !operationList.isEmpty()) {
                        Operation operation = operationList.getFirst();
                        operation.setWorkOrderNumber(wipEntityName);
                        operationRepository.save(operation);
                        for (OperationChild operationChild : operation.getOperationList()) {
                            operationChild.setWorkOrderNumber(wipEntityName);
                            operationChild.setWipEntityId(wipEntityId);
                            operationChildRepository.save(operationChild);
                        }
                    }

                    if (materialList != null && !materialList.isEmpty()) {
                        Material material = materialList.getFirst();
                        material.setWorkOrderNumber(wipEntityName);
                        materialRepository.save(material);
                        for (MaterialChild materialChild : material.getMaterialList()) {
                            materialChild.setWorkOrderNumber(wipEntityName);
                            materialChild.setWipEntityId(wipEntityId);
                            materialChildRepository.save(materialChild);
                        }
                    }*//*



                        if (basicDetails != null) {
                            basicDetailsRepository.delete(basicDetails);
                        }

                        if (additionalDetails != null) {
                            additionalDetailsRepository.delete(additionalDetails);
                        }

                        if (operationList != null && !operationList.isEmpty()) {
                            for (Operation operation : operationList) {
                                List<OperationChild> operationChildren = operation.getOperationList();
                                if (operationChildren != null && !operationChildren.isEmpty()) {
                                    operationChildRepository.deleteAll(operationChildren);
                                }
                                operationRepository.delete(operation);
                            }
                        }
                        if (materialList != null && !materialList.isEmpty()) {
                            for (Material material : materialList) {
                                List<MaterialChild> materialChildren = material.getMaterialList();
                                if (materialChildren != null && !materialChildren.isEmpty()) {
                                    materialChildRepository.deleteAll(materialChildren);
                                }
                                materialRepository.delete(material);
                            }
                        }

                        return ResponseEntity.ok(new ResponseModel<>(true, "WorkOrder Created Successfully !!", wipEntityName));

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
                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel<>(false, "An unexpected error occurred: " + ex.getMessage(), null));
        }
    }
*/




//    public ResponseEntity<ResponseModel<String>> addWorkOrder(String workOrderNumber) {
//        try {
//            String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";
//
//            // String url = "http://ocitestekaayansoa.adityabirla.com:80/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";
//
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//            BasicDetails basicDetails = basicDetailsRepository.findByWorkOrderNumber(workOrderNumber);
//            AdditionalDetails additionalDetails = additionalDetailsRepository.findByWorkOrderNumber(workOrderNumber);
//            List<Operation> operationList = operationRepository.findByWorkOrderNumber(workOrderNumber);
//            List<Material> materialList = materialRepository.findByWorkOrderNumber(workOrderNumber);
//
//            if (basicDetails == null && additionalDetails == null && operationList.isEmpty() && materialList.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ResponseModel<>(false, "No details found for the given work order number.", null));
//            }
//
//            if (basicDetails == null) {
//                System.out.println("BasicDetails is null for workOrderNumber: " + workOrderNumber);
//            } else {
//                System.out.println("BasicDetails: " + basicDetails.toString());
//            }
//
//            if (additionalDetails == null) {
//                System.out.println("AdditionalDetails is null for workOrderNumber: " + workOrderNumber);
//            } else {
//                System.out.println("AdditionalDetails: " + additionalDetails.toString());
//            }
//
//
//            Map<String, Object> body = new HashMap<>();
//            body.put("P_BO_IDENTIFIER", "EAM");
//            body.put("P_API_VERSION_NUMBER", 1);
//            body.put("P_INIT_MSG_LIST", "1");
//            body.put("P_COMMIT", "Y");
//
//            // EAM Work Order Record
//            Map<String, Object> eamWoRec = new HashMap<>();
//            eamWoRec.put("HEADER_ID", 1);
//            eamWoRec.put("BATCH_ID", 1);
//            eamWoRec.put("ROW_ID", null);
////        eamWoRec.put("WIP_ENTITY_NAME",  workOrderNumber.contains("TEMPWO") ? null:workOrderNumber);
////        eamWoRec.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
//            eamWoRec.put("WIP_ENTITY_NAME",null);
//            eamWoRec.put("ORGANIZATION_ID", 1759);
//            eamWoRec.put("DESCRIPTION", "test");
//            eamWoRec.put("ASSET_NUMBER", null);
//            //eamWoRec.put("ASSET_NUMBER", basicDetails != null ? basicDetails.getAssetNumber() :null);
//            //eamWoRec.put("ASSET_GROUP_ID", basicDetails != null ? basicDetails.getAssetGroupId() :null);
//            eamWoRec.put("ASSET_GROUP_ID", null);
//            eamWoRec.put("REBUILD_ITEM_ID", 505521);
//            eamWoRec.put("REBUILD_SERIAL_NUMBER", "UP1-P1-E-PU-Z-L4-R54-007");
//            eamWoRec.put("MAINTENANCE_OBJECT_ID", 74803);
//            eamWoRec.put("MAINTENANCE_OBJECT_TYPE", 3);
//            eamWoRec.put("MAINTENANCE_OBJECT_SOURCE", 1);
//            eamWoRec.put("CLASS_CODE", "undefined");
//            eamWoRec.put("WORK_ORDER_TYPE", "70");
//            eamWoRec.put("STATUS_TYPE", "17");
//            eamWoRec.put("PRIORITY", "40");
//            eamWoRec.put("SHUTDOWN_TYPE", "2");
//            eamWoRec.put("FIRM_PLANNED_FLAG", "2");
//            eamWoRec.put("SCHEDULED_START_DATE", "2024-09-10T11:17:20.000");
//            eamWoRec.put("SCHEDULED_COMPLETION_DATE", "2024-10-10T11:17:20.000");
//            eamWoRec.put("NOTIFICATION_REQUIRED", "N");
//            eamWoRec.put("TAGOUT_REQUIRED","N");
//            eamWoRec.put("ATTRIBUTE3", "Y");
//            eamWoRec.put("EAM_LINEAR_LOCATION_ID", null);
//            eamWoRec.put("ASSET_ACTIVITY_ID", null);
//            eamWoRec.put("DUE_DATE", null);
//            eamWoRec.put("ACTIVITY_TYPE", null);
//            eamWoRec.put("ACTIVITY_CAUSE", null);
//            eamWoRec.put("ACTIVITY_SOURCE", null);
//            eamWoRec.put("JOB_QUANTITY", null);
//            eamWoRec.put("DATE_RELEASED", null);
//            eamWoRec.put("OWNING_DEPARTMENT", 2002);
//            eamWoRec.put("REQUESTED_START_DATE", null);
//            eamWoRec.put("PLANNER_TYPE", basicDetails != null ? basicDetails.getPlannerType() : null);  ///
//            eamWoRec.put("PLAN_MAINTENANCE", null);
//            eamWoRec.put("PROJECT_ID", null);
//            eamWoRec.put("TASK_ID", null);
//            eamWoRec.put("END_ITEM_UNIT_NUMBER", null);
//            eamWoRec.put("SCHEDULE_GROUP_ID", null);
//            eamWoRec.put("BOM_REVISION_DATE", null);
//            eamWoRec.put("ROUTING_REVISION_DATE", null);
//            eamWoRec.put("ALTERNATE_ROUTING_DESIGNATOR", null);
//            eamWoRec.put("ALTERNATE_BOM_DESIGNATOR", null);
//            eamWoRec.put("ROUTING_REVISION", null);
//            eamWoRec.put("BOM_REVISION", null);
//            eamWoRec.put("MANUAL_REBUILD_FLAG", null);
//            eamWoRec.put("PM_SCHEDULE_ID", null);
//            eamWoRec.put("WIP_SUPPLY_TYPE", null);
//            eamWoRec.put("MATERIAL_ACCOUNT", null);
//            eamWoRec.put("MATERIAL_OVERHEAD_ACCOUNT", null);
//            eamWoRec.put("RESOURCE_ACCOUNT", null);
//            eamWoRec.put("OUTSIDE_PROCESSING_ACCOUNT", null);
//            eamWoRec.put("MATERIAL_VARIANCE_ACCOUNT", null);
//            eamWoRec.put("RESOURCE_VARIANCE_ACCOUNT", null);
//            eamWoRec.put("OUTSIDE_PROC_VARIANCE_ACCOUNT", null);
//            eamWoRec.put("STD_COST_ADJUSTMENT_ACCOUNT", null);
//            eamWoRec.put("OVERHEAD_ACCOUNT", null);
//            eamWoRec.put("OVERHEAD_VARIANCE_ACCOUNT", null); ///
//            eamWoRec.put("PM_SUGGESTED_START_DATE", null);
//            eamWoRec.put("PM_SUGGESTED_END_DATE", null);
//            eamWoRec.put("PM_BASE_METER_READING", null);
//            eamWoRec.put("PM_BASE_METER", null);
//            eamWoRec.put("COMMON_BOM_SEQUENCE_ID", null);
//            eamWoRec.put("COMMON_ROUTING_SEQUENCE_ID", null);
//            eamWoRec.put("PO_CREATION_TIME", null);
//            eamWoRec.put("GEN_OBJECT_ID", null);
//            eamWoRec.put("USER_DEFINED_STATUS_ID", null);
//            eamWoRec.put("PENDING_FLAG", null);
//            eamWoRec.put("MATERIAL_SHORTAGE_CHECK_DATE", null);
//            eamWoRec.put("MATERIAL_SHORTAGE_FLAG", null);
//            eamWoRec.put("WORKFLOW_TYPE", null);
//            eamWoRec.put("WARRANTY_CLAIM_STATUS", null);
//            eamWoRec.put("CYCLE_ID", null);
//            eamWoRec.put("SEQ_ID", null);
//            eamWoRec.put("DS_SCHEDULED_FLAG", null);
//            eamWoRec.put("WARRANTY_ACTIVE", null);
//            eamWoRec.put("ASSIGNMENT_COMPLETE", null);
//            eamWoRec.put("ATTRIBUTE_CATEGORY", null);
//            eamWoRec.put("ATTRIBUTE1", null);
//            eamWoRec.put("ATTRIBUTE2", "Y");
//            eamWoRec.put("ATTRIBUTE4", null);
//            eamWoRec.put("ATTRIBUTE5", null);
//            eamWoRec.put("ATTRIBUTE6", null);
//            eamWoRec.put("ATTRIBUTE7", null);
//            eamWoRec.put("ATTRIBUTE8", null);
//            eamWoRec.put("ATTRIBUTE9", null);
//            eamWoRec.put("ATTRIBUTE10", null);
//            eamWoRec.put("ATTRIBUTE11", null);
//            eamWoRec.put("ATTRIBUTE12", null);
//            eamWoRec.put("ATTRIBUTE13", null);
//            eamWoRec.put("ATTRIBUTE14", null);
//            eamWoRec.put("ATTRIBUTE15", null);
//            eamWoRec.put("MATERIAL_ISSUE_BY_MO", null);
//            eamWoRec.put("ISSUE_ZERO_COST_FLAG", null);
//            eamWoRec.put("REPORT_TYPE", null);
//            eamWoRec.put("ACTUAL_CLOSE_DATE", null);
//            eamWoRec.put("SUBMISSION_DATE", null);
//            eamWoRec.put("USER_ID", null);
//            eamWoRec.put("RESPONSIBILITY_ID", null);
//            eamWoRec.put("REQUEST_ID", null);
//            eamWoRec.put("PROGRAM_ID", null);
//            eamWoRec.put("PROGRAM_APPLICATION_ID", null);
//            eamWoRec.put("SOURCE_LINE_ID", null);
//            eamWoRec.put("SOURCE_CODE", null);
//            eamWoRec.put("VALIDATE_STRUCTURE", null);
//            eamWoRec.put("RETURN_STATUS", null);
//            eamWoRec.put("TRANSACTION_TYPE", 1);
//            eamWoRec.put("FAILURE_CODE_REQUIRED", null);
//
//
//            body.put("P_EAM_WO_REC", eamWoRec);
//
//
//            // EAM_FAILURE_ENTRY_RECORD
//            //MultiValueMap<String, Object> failureEntryRecord = new LinkedMultiValueMap<>();
//            Map<String, Object> failureEntryRecord = new HashMap<>();
//            failureEntryRecord.put("FAILURE_ID", null);
//            failureEntryRecord.put("FAILURE_DATE", null);
//            failureEntryRecord.put("SOURCE_TYPE", null);
//            failureEntryRecord.put("SOURCE_ID", null);
//            failureEntryRecord.put("OBJECT_TYPE", null);
//            failureEntryRecord.put("OBJECT_ID", null);
//            failureEntryRecord.put("MAINT_ORGANIZATION_ID", null);
//            failureEntryRecord.put("CURRENT_ORGANIZATION_ID", null);
//            failureEntryRecord.put("DEPARTMENT_ID", null);
//            failureEntryRecord.put("AREA_ID", null);
//            failureEntryRecord.put("TRANSACTION_TYPE", null);
//            failureEntryRecord.put("SOURCE_NAME", null);
//
//            eamWoRec.put("EAM_FAILURE_ENTRY_RECORD", failureEntryRecord);
//
//
//            // EAM_FAILURE_CODES_TBL
//            //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
//            Map<String, Object> failureCodesTblItem = new HashMap<>();
//            failureCodesTblItem.put("FAILURE_ID", null);
//            failureCodesTblItem.put("FAILURE_ENTRY_ID", null);
//            failureCodesTblItem.put("COMBINATION_ID", null);
//            failureCodesTblItem.put("FAILURE_CODE", null);
//            failureCodesTblItem.put("CAUSE_CODE", null);
//            failureCodesTblItem.put("RESOLUTION_CODE", null);
//            failureCodesTblItem.put("COMMENTS", null);
//            failureCodesTblItem.put("TRANSACTION_TYPE", null);
//
//            //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
//            Map<String, Object> failureCodesTbl = new HashMap<>();
//            failureCodesTbl.put("EAM_FAILURE_CODES_TBL_ITEM", Collections.singletonList(failureCodesTblItem));
//
//            eamWoRec.put("EAM_FAILURE_CODES_TBL", failureCodesTbl);
//
//            eamWoRec.put("TARGET_START_DATE", null);
//            eamWoRec.put("TARGET_COMPLETION_DATE", null);
//
//
//            // Process Operations, Resources, and Instances
//            List<OperationChild> opList = new ArrayList<>();
//            List<OperationChild> resourceList = new ArrayList<>();
//            List<OperationChild> instanceList = new ArrayList<>();
//
//            for (Operation operation : operationList) {
//                for (OperationChild operationChild : operation.getOperationList()) {
//                    if (operationChild.getOperationSequence() != null || operationChild.getDescription() != null || operationChild.getDepartmentId() != null) {
//                        opList.add(operationChild);
//                    }
//                    if (operationChild.getResourceSequence() != null || operationChild.getResourceId() != null) {
//                        resourceList.add(operationChild);
//                    }
//                    if (operationChild.getInstanceId() != null || operationChild.getInstanceName() != null) {
//                        instanceList.add(operationChild);
//                    }
//                }
//            }
//
//            body.put("P_EAM_OP_TBL", opList);
//            body.put("P_EAM_RES_TBL", resourceList);
//            body.put("P_EAM_RES_INST_TBL", instanceList);
//
//            List<MaterialChild> mcList = new ArrayList<>();
//            for (Material material : materialList) {
//                for (MaterialChild materialChild : material.getMaterialList()) {
//                    if (materialChild.getOperationSequence() != null) {
//                        mcList.add(materialChild);
//                    }
//                }
//            }
//
//            //body.put("P_EAM_MAT_REQ_TBL", mcList);
//
//            // Set null values for other fields that are not needed
//            body.put("P_EAM_OP_NETWORK_TBL", null);
//            body.put("P_EAM_DIRECT_ITEMS_TBL", null);
//            body.put("P_EAM_WO_COMP_REC", null);
//            body.put("P_EAM_WO_QUALITY_TBL", null);
//            body.put("P_EAM_METER_READING_TBL", null);
//            body.put("P_EAM_COUNTER_PROP_TBL", null);
//            body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
//            body.put("P_EAM_WO_COMP_MR_READ_TBL", null);
//            body.put("P_EAM_OP_COMP_TBL", null);
//            body.put("P_EAM_PERMIT_TBL", null);
//            body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
//            body.put("P_EAM_WORK_CLEARANCE_TBL", null);
//            body.put("P_EAM_WC_WO_ASSOC_TBL", null);
//            body.put("P_EAM_RES_USAGE_TBL", null);
//            body.put("P_EAM_SUB_RES_TBL", null);
//            body.put("P_DEBUG", "N");
//            body.put("P_OUTPUT_DIR", "/usr/tmp");
//            body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
//            body.put("P_DEBUG_FILE_MODE", "w");
//
//
//            Map<String, Object> requestTableItem = new HashMap<>();
//            requestTableItem.put("HEADER_ID", null);
//            requestTableItem.put("BATCH_ID", null);
//            requestTableItem.put("ROW_ID", null);
//            requestTableItem.put("WIP_ENTITY_NAME", null);
////        requestTableItem.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
//            requestTableItem.put("ORGANIZATION_ID", basicDetails.getOrganizationId());
//            requestTableItem.put("ORGANIZATION_CODE", null);
//            requestTableItem.put("REQUEST_TYPE", 1);
//            requestTableItem.put("REQUEST_ID", basicDetails.getWorkRequestNo());
//            requestTableItem.put("REQUEST_NUMBER", basicDetails.getWorkRequestNo());
//            requestTableItem.put("ATTRIBUTE_CATEGORY", null);
//            requestTableItem.put("ATTRIBUTE1", null);
//            requestTableItem.put("ATTRIBUTE2", null);
//            requestTableItem.put("ATTRIBUTE3", null);
//            requestTableItem.put("ATTRIBUTE4", null);
//            requestTableItem.put("ATTRIBUTE5", null);
//            requestTableItem.put("ATTRIBUTE6", null);
//            requestTableItem.put("ATTRIBUTE7", null);
//            requestTableItem.put("ATTRIBUTE8", null);
//            requestTableItem.put("ATTRIBUTE9", null);
//            requestTableItem.put("ATTRIBUTE10", null);
//            requestTableItem.put("ATTRIBUTE11", null);
//            requestTableItem.put("ATTRIBUTE12", null);
//            requestTableItem.put("ATTRIBUTE13", null);
//            requestTableItem.put("ATTRIBUTE14", null);
//            requestTableItem.put("ATTRIBUTE15", null);
//            requestTableItem.put("PROGRAM_ID", null);
//            requestTableItem.put("PROGRAM_REQUEST_ID", null);
//            requestTableItem.put("PROGRAM_UPDATE_DATE", null);
//            requestTableItem.put("PROGRAM_APPLICATION_ID", null);
//            requestTableItem.put("WORK_REQUEST_STATUS_ID", null);
//            requestTableItem.put("SERVICE_ASSOC_ID", null);
//            requestTableItem.put("RETURN_STATUS", null);
//            requestTableItem.put("TRANSACTION_TYPE", 1);
//
//            Map<String, Object> requestTBL = new HashMap<>();
//            requestTBL.put("P_EAM_REQUEST_TBL_ITEM", Collections.singletonList(requestTableItem));
//
//
//            body.put("P_EAM_REQUEST_TBL", requestTBL);
//
//            ObjectMapper objectMapper1 = new ObjectMapper();
//            String json1 = objectMapper1.writeValueAsString(body);
//
//            System.out.println(json1);
//            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
//            System.out.println("Entity records = " + entity);
//            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//            System.out.println("ResponseEntity = " + responseEntity);
//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                String response = responseEntity.getBody();
//
//                if (response != null && !response.isEmpty()) {
//                    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
//                    JsonNode jsonNode = objectMapper.readTree(response);
//                    String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();
//                    JsonNode eamWoRecNode = jsonNode.get("X_EAM_WO_REC");
//
//                    String wipEntityName = null;
//                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_NAME")) {
//                        JsonNode wipEntityNode = eamWoRecNode.get("WIP_ENTITY_NAME");
//                        if (wipEntityNode.has("$")) {
//                            wipEntityName = wipEntityNode.get("$").asText();
//                        }
//                    }
//
//                    String wipEntityId = null;
//                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_ID")) {
//                        JsonNode wipEntityIdNode = eamWoRecNode.get("WIP_ENTITY_ID");
//                        if (wipEntityIdNode.has("$")) {
//                            wipEntityId = wipEntityIdNode.get("$").asText();
//                        }
//                    }
//
//                    if ("S".equals(responseStatus) && wipEntityName != null && !wipEntityName.isEmpty()) {
//                        // String wipEntityName = jsonNode.get("WIP_ENTITY_NAME").asText();
//
//                   /* if (basicDetails != null) {
//                        basicDetails.setWorkOrderNumber(wipEntityName);
//                        basicDetails.setWipEntityName(wipEntityName);
//                        basicDetails.setWipEntityId(wipEntityId);
//                        basicDetailsRepository.save(basicDetails);
//                    }
//                    if (additionalDetails != null) {
//                        additionalDetails.setWorkOrderNumber(wipEntityName);
//                        additionalDetailsRepository.save(additionalDetails);
//                    }
//
//                    if (operationList != null && !operationList.isEmpty()) {
//                        Operation operation = operationList.getFirst();
//                        operation.setWorkOrderNumber(wipEntityName);
//                        operationRepository.save(operation);
//                        for (OperationChild operationChild : operation.getOperationList()) {
//                            operationChild.setWorkOrderNumber(wipEntityName);
//                            operationChild.setWipEntityId(wipEntityId);
//                            operationChildRepository.save(operationChild);
//                        }
//                    }
//
//                    if (materialList != null && !materialList.isEmpty()) {
//                        Material material = materialList.getFirst();
//                        material.setWorkOrderNumber(wipEntityName);
//                        materialRepository.save(material);
//                        for (MaterialChild materialChild : material.getMaterialList()) {
//                            materialChild.setWorkOrderNumber(wipEntityName);
//                            materialChild.setWipEntityId(wipEntityId);
//                            materialChildRepository.save(materialChild);
//                        }
//                    }*/
//
//
//                        if (basicDetails != null) {
//                            basicDetailsRepository.delete(basicDetails);
//                        }
//
//                        if (additionalDetails != null) {
//                            additionalDetailsRepository.delete(additionalDetails);
//                        }
//
//                        if (operationList != null && !operationList.isEmpty()) {
//                            for (Operation operation : operationList) {
//                                List<OperationChild> operationChildren = operation.getOperationList();
//                                if (operationChildren != null && !operationChildren.isEmpty()) {
//                                    operationChildRepository.deleteAll(operationChildren);
//                                }
//                                operationRepository.delete(operation);
//                            }
//                        }
//                        if (materialList != null && !materialList.isEmpty()) {
//                            for (Material material : materialList) {
//                                List<MaterialChild> materialChildren = material.getMaterialList();
//                                if (materialChildren != null && !materialChildren.isEmpty()) {
//                                    materialChildRepository.deleteAll(materialChildren);
//                                }
//                                materialRepository.delete(material);
//                            }
//                        }
//
//                        return ResponseEntity.ok(new ResponseModel<>(true, "WorkOrder Created Successfully !!", wipEntityName));
//
//                    } else {
//                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                .body(new ResponseModel<>(false, "Failed to process the response.", null));
//                    }
//                } else {
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                            .body(new ResponseModel<>(false, "Empty response from the server.", null));
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(new ResponseModel<>(false, "Failed to connect to the server.", null));
//            }
//        } catch (HttpStatusCodeException ex) {
//            return ResponseEntity.status(ex.getStatusCode())
//                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getMessage(), null));
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseModel<>(false, "An unexpected error occurred: " + ex.getMessage(), null));
//        }
//    }



    public ResponseEntity<ResponseModel<String>> updateWorkOrder(String workOrderNumber) {
        try {
            String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

            // String url = "http://ocitestekaayansoa.adityabirla.com:80/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


            BasicDetails basicDetails = basicDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            AdditionalDetails additionalDetails = additionalDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            List<Operation> operationList = operationRepository.findByWorkOrderNumber(workOrderNumber);
            List<Material> materialList = materialRepository.findByWorkOrderNumber(workOrderNumber);

            if (basicDetails == null && additionalDetails == null && operationList.isEmpty() && materialList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel<>(false, "No details found for the given work order number.", null));
            }

            if (basicDetails == null) {
                System.out.println("BasicDetails is null for workOrderNumber: " + workOrderNumber);
            } else {
                System.out.println("BasicDetails: " + basicDetails.toString());
            }

            if (additionalDetails == null) {
                System.out.println("AdditionalDetails is null for workOrderNumber: " + workOrderNumber);
            } else {
                System.out.println("AdditionalDetails: " + additionalDetails.toString());
            }


            Map<String, Object> body = new HashMap<>();
            body.put("P_BO_IDENTIFIER", "EAM");
            body.put("P_API_VERSION_NUMBER", 1);
            body.put("P_INIT_MSG_LIST", "1");
            body.put("P_COMMIT", "Y");

            // EAM Work Order Record
            Map<String, Object> eamWoRec = new HashMap<>();
            eamWoRec.put("HEADER_ID", 1);
            eamWoRec.put("BATCH_ID", 1);
            eamWoRec.put("ROW_ID", null);
            eamWoRec.put("WIP_ENTITY_NAME",workOrderNumber);
            eamWoRec.put("WIP_ENTITY_ID", basicDetails.getWipEntityId());
            eamWoRec.put("ORGANIZATION_ID", basicDetails != null ? basicDetails.getOrganizationId() : null);
            eamWoRec.put("DESCRIPTION", basicDetails != null ? basicDetails.getDescription() : null);
            eamWoRec.put("ASSET_NUMBER", null);
            //eamWoRec.put("ASSET_NUMBER", basicDetails != null ? basicDetails.getAssetNumber() :null);
            //eamWoRec.put("ASSET_GROUP_ID", basicDetails != null ? basicDetails.getAssetGroupId() :null);
            eamWoRec.put("ASSET_GROUP_ID", null);
            eamWoRec.put("REBUILD_ITEM_ID", basicDetails != null ? basicDetails.getRebuildItemId() : null);
            eamWoRec.put("REBUILD_SERIAL_NUMBER", basicDetails != null ? basicDetails.getRebuildSerialNo() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_ID", basicDetails != null ? basicDetails.getMaintenanceObjectId() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_TYPE", basicDetails != null ? basicDetails.getMaintenanceObjectType() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_SOURCE", 1);
            eamWoRec.put("CLASS_CODE", basicDetails != null ? basicDetails.getWipAccountingClassCode() : null);
            eamWoRec.put("WORK_ORDER_TYPE", basicDetails != null ? basicDetails.getWoType() : null);
            eamWoRec.put("STATUS_TYPE", basicDetails != null ? basicDetails.getStatusType() : null);
            eamWoRec.put("PRIORITY", basicDetails != null ? basicDetails.getPriority() : null);
            eamWoRec.put("SHUTDOWN_TYPE", basicDetails != null ? basicDetails.getShutdownType() : null);
            eamWoRec.put("FIRM_PLANNED_FLAG", basicDetails != null ? basicDetails.getFirmPlannedType() : null);
            eamWoRec.put("SCHEDULED_START_DATE", basicDetails != null ? basicDetails.getScheduledStartDate() : null);
            eamWoRec.put("SCHEDULED_COMPLETION_DATE", basicDetails != null ? basicDetails.getScheduledEndDate() : null);
            eamWoRec.put("NOTIFICATION_REQUIRED", additionalDetails != null ? additionalDetails.getNotificationRequired() : null);
            eamWoRec.put("TAGOUT_REQUIRED", additionalDetails != null ? additionalDetails.getTagoutRequired() : null);
            eamWoRec.put("ATTRIBUTE3", additionalDetails != null ? additionalDetails.getSafetyPermit() : null);
            eamWoRec.put("EAM_LINEAR_LOCATION_ID", null);
            eamWoRec.put("ASSET_ACTIVITY_ID", null);
            eamWoRec.put("DUE_DATE", null);
            eamWoRec.put("ACTIVITY_TYPE", null);
            eamWoRec.put("ACTIVITY_CAUSE", null);
            eamWoRec.put("ACTIVITY_SOURCE", null);
            eamWoRec.put("JOB_QUANTITY", null);
            eamWoRec.put("DATE_RELEASED", null);
            eamWoRec.put("OWNING_DEPARTMENT", basicDetails != null ? basicDetails.getDepartmentId() : null);
            eamWoRec.put("REQUESTED_START_DATE", null);
            eamWoRec.put("PLANNER_TYPE", basicDetails != null ? basicDetails.getPlannerType() : null);
            eamWoRec.put("PLAN_MAINTENANCE", null);
            eamWoRec.put("PROJECT_ID", null);
            eamWoRec.put("TASK_ID", null);
            eamWoRec.put("END_ITEM_UNIT_NUMBER", null);
            eamWoRec.put("SCHEDULE_GROUP_ID", null);
            eamWoRec.put("BOM_REVISION_DATE", null);
            eamWoRec.put("ROUTING_REVISION_DATE", null);
            eamWoRec.put("ALTERNATE_ROUTING_DESIGNATOR", null);
            eamWoRec.put("ALTERNATE_BOM_DESIGNATOR", null);
            eamWoRec.put("ROUTING_REVISION", null);
            eamWoRec.put("BOM_REVISION", null);
            eamWoRec.put("MANUAL_REBUILD_FLAG", null);
            eamWoRec.put("PM_SCHEDULE_ID", null);
            eamWoRec.put("WIP_SUPPLY_TYPE", null);
            eamWoRec.put("MATERIAL_ACCOUNT", null);
            eamWoRec.put("MATERIAL_OVERHEAD_ACCOUNT", null);
            eamWoRec.put("RESOURCE_ACCOUNT", null);
            eamWoRec.put("OUTSIDE_PROCESSING_ACCOUNT", null);
            eamWoRec.put("MATERIAL_VARIANCE_ACCOUNT", null);
            eamWoRec.put("RESOURCE_VARIANCE_ACCOUNT", null);
            eamWoRec.put("OUTSIDE_PROC_VARIANCE_ACCOUNT", null);
            eamWoRec.put("STD_COST_ADJUSTMENT_ACCOUNT", null);
            eamWoRec.put("OVERHEAD_ACCOUNT", null);
            eamWoRec.put("OVERHEAD_VARIANCE_ACCOUNT", null);
            eamWoRec.put("PM_SUGGESTED_START_DATE", null);
            eamWoRec.put("PM_SUGGESTED_END_DATE", null);
            eamWoRec.put("PM_BASE_METER_READING", null);
            eamWoRec.put("PM_BASE_METER", null);
            eamWoRec.put("COMMON_BOM_SEQUENCE_ID", null);
            eamWoRec.put("COMMON_ROUTING_SEQUENCE_ID", null);
            eamWoRec.put("PO_CREATION_TIME", null);
            eamWoRec.put("GEN_OBJECT_ID", null);
            eamWoRec.put("USER_DEFINED_STATUS_ID", null);
            eamWoRec.put("PENDING_FLAG", null);
            eamWoRec.put("MATERIAL_SHORTAGE_CHECK_DATE", null);
            eamWoRec.put("MATERIAL_SHORTAGE_FLAG", null);
            eamWoRec.put("WORKFLOW_TYPE", null);
            eamWoRec.put("WARRANTY_CLAIM_STATUS", null);
            eamWoRec.put("CYCLE_ID", null);
            eamWoRec.put("SEQ_ID", null);
            eamWoRec.put("DS_SCHEDULED_FLAG", null);
            eamWoRec.put("WARRANTY_ACTIVE", null);
            eamWoRec.put("ASSIGNMENT_COMPLETE", null);
            eamWoRec.put("ATTRIBUTE_CATEGORY", null);
            eamWoRec.put("ATTRIBUTE1", null);
            eamWoRec.put("ATTRIBUTE2", additionalDetails.getInformDepartments());
            eamWoRec.put("ATTRIBUTE4", null);
            eamWoRec.put("ATTRIBUTE5", null);
            eamWoRec.put("ATTRIBUTE6", null);
            eamWoRec.put("ATTRIBUTE7", null);
            eamWoRec.put("ATTRIBUTE8", null);
            eamWoRec.put("ATTRIBUTE9", null);
            eamWoRec.put("ATTRIBUTE10", null);
            eamWoRec.put("ATTRIBUTE11", null);
            eamWoRec.put("ATTRIBUTE12", null);
            eamWoRec.put("ATTRIBUTE13", null);
            eamWoRec.put("ATTRIBUTE14", null);
            eamWoRec.put("ATTRIBUTE15", null);
            eamWoRec.put("MATERIAL_ISSUE_BY_MO", null);
            eamWoRec.put("ISSUE_ZERO_COST_FLAG", null);
            eamWoRec.put("REPORT_TYPE", null);
            eamWoRec.put("ACTUAL_CLOSE_DATE", null);
            eamWoRec.put("SUBMISSION_DATE", null);
            eamWoRec.put("USER_ID", null);
            eamWoRec.put("RESPONSIBILITY_ID", null);
            eamWoRec.put("REQUEST_ID", null);
            eamWoRec.put("PROGRAM_ID", null);
            eamWoRec.put("PROGRAM_APPLICATION_ID", null);
            eamWoRec.put("SOURCE_LINE_ID", null);
            eamWoRec.put("SOURCE_CODE", null);
            eamWoRec.put("VALIDATE_STRUCTURE", null);
            eamWoRec.put("RETURN_STATUS", null);
            eamWoRec.put("TRANSACTION_TYPE", 1);
            eamWoRec.put("FAILURE_CODE_REQUIRED", null);


            body.put("P_EAM_WO_REC", eamWoRec);


            // EAM_FAILURE_ENTRY_RECORD
            //MultiValueMap<String, Object> failureEntryRecord = new LinkedMultiValueMap<>();
            Map<String, Object> failureEntryRecord = new HashMap<>();
            failureEntryRecord.put("FAILURE_ID", null);
            failureEntryRecord.put("FAILURE_DATE", null);
            failureEntryRecord.put("SOURCE_TYPE", null);
            failureEntryRecord.put("SOURCE_ID", null);
            failureEntryRecord.put("OBJECT_TYPE", null);
            failureEntryRecord.put("OBJECT_ID", null);
            failureEntryRecord.put("MAINT_ORGANIZATION_ID", null);
            failureEntryRecord.put("CURRENT_ORGANIZATION_ID", null);
            failureEntryRecord.put("DEPARTMENT_ID", null);
            failureEntryRecord.put("AREA_ID", null);
            failureEntryRecord.put("TRANSACTION_TYPE", null);
            failureEntryRecord.put("SOURCE_NAME", null);

            eamWoRec.put("EAM_FAILURE_ENTRY_RECORD", failureEntryRecord);


            // EAM_FAILURE_CODES_TBL
            //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
            Map<String, Object> failureCodesTblItem = new HashMap<>();
            failureCodesTblItem.put("FAILURE_ID", null);
            failureCodesTblItem.put("FAILURE_ENTRY_ID", null);
            failureCodesTblItem.put("COMBINATION_ID", null);
            failureCodesTblItem.put("FAILURE_CODE", null);
            failureCodesTblItem.put("CAUSE_CODE", null);
            failureCodesTblItem.put("RESOLUTION_CODE", null);
            failureCodesTblItem.put("COMMENTS", null);
            failureCodesTblItem.put("TRANSACTION_TYPE", null);

            //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
            Map<String, Object> failureCodesTbl = new HashMap<>();
            failureCodesTbl.put("EAM_FAILURE_CODES_TBL_ITEM", Collections.singletonList(failureCodesTblItem));

            eamWoRec.put("EAM_FAILURE_CODES_TBL", failureCodesTbl);

            eamWoRec.put("TARGET_START_DATE", null);
            eamWoRec.put("TARGET_COMPLETION_DATE", null);


            // Process Operations, Resources, and Instances
            List<OperationChild> opList = new ArrayList<>();
            List<OperationChild> resourceList = new ArrayList<>();
            List<OperationChild> instanceList = new ArrayList<>();

            for (Operation operation : operationList) {
                for (OperationChild operationChild : operation.getOperationList()) {
                    if (operationChild.getOperationSequence() != null || operationChild.getDescription() != null || operationChild.getDepartmentId() != null) {
                        opList.add(operationChild);
                    }
                    if (operationChild.getResourceSequence() != null || operationChild.getResourceId() != null) {
                        resourceList.add(operationChild);
                    }
                    if (operationChild.getInstanceId() != null || operationChild.getInstanceName() != null) {
                        instanceList.add(operationChild);
                    }
                }
            }

            //body.put("P_EAM_OP_TBL", opList);
            //body.put("P_EAM_RES_TBL", resourceList);
            //body.put("P_EAM_RES_INST_TBL", instanceList);

            List<MaterialChild> mcList = new ArrayList<>();
            for (Material material : materialList) {
                for (MaterialChild materialChild : material.getMaterialList()) {
                    if (materialChild.getOperationSequence() != null) {
                        mcList.add(materialChild);
                    }
                }
            }

            //body.put("P_EAM_MAT_REQ_TBL", mcList);

            // Set null values for other fields that are not needed
            body.put("P_EAM_OP_NETWORK_TBL", null);
            body.put("P_EAM_DIRECT_ITEMS_TBL", null);
            body.put("P_EAM_WO_COMP_REC", null);
            body.put("P_EAM_WO_QUALITY_TBL", null);
            body.put("P_EAM_METER_READING_TBL", null);
            body.put("P_EAM_COUNTER_PROP_TBL", null);
            body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
            body.put("P_EAM_WO_COMP_MR_READ_TBL", null);
            body.put("P_EAM_OP_COMP_TBL", null);
            body.put("P_EAM_PERMIT_TBL", null);
            body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
            body.put("P_EAM_WORK_CLEARANCE_TBL", null);
            body.put("P_EAM_WC_WO_ASSOC_TBL", null);
            body.put("P_EAM_RES_USAGE_TBL", null);
            body.put("P_EAM_SUB_RES_TBL", null);
            body.put("P_DEBUG", "N");
            body.put("P_OUTPUT_DIR", "/usr/tmp");
            body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
            body.put("P_DEBUG_FILE_MODE", "w");


            Map<String, Object> requestTableItem = new HashMap<>();
            requestTableItem.put("HEADER_ID", null);
            requestTableItem.put("BATCH_ID", null);
            requestTableItem.put("ROW_ID", null);
            requestTableItem.put("WIP_ENTITY_NAME", null);
            requestTableItem.put("WIP_ENTITY_ID", basicDetails.getWipEntityId());
            requestTableItem.put("ORGANIZATION_ID", basicDetails.getOrganizationId());
            requestTableItem.put("ORGANIZATION_CODE", null);
            requestTableItem.put("REQUEST_TYPE", 1);
            requestTableItem.put("REQUEST_ID", basicDetails.getWorkRequestNo());
            requestTableItem.put("REQUEST_NUMBER", basicDetails.getWorkRequestNo());
            requestTableItem.put("ATTRIBUTE_CATEGORY", null);
            requestTableItem.put("ATTRIBUTE1", null);
            requestTableItem.put("ATTRIBUTE2", null);
            requestTableItem.put("ATTRIBUTE3", null);
            requestTableItem.put("ATTRIBUTE4", null);
            requestTableItem.put("ATTRIBUTE5", null);
            requestTableItem.put("ATTRIBUTE6", null);
            requestTableItem.put("ATTRIBUTE7", null);
            requestTableItem.put("ATTRIBUTE8", null);
            requestTableItem.put("ATTRIBUTE9", null);
            requestTableItem.put("ATTRIBUTE10", null);
            requestTableItem.put("ATTRIBUTE11", null);
            requestTableItem.put("ATTRIBUTE12", null);
            requestTableItem.put("ATTRIBUTE13", null);
            requestTableItem.put("ATTRIBUTE14", null);
            requestTableItem.put("ATTRIBUTE15", null);
            requestTableItem.put("PROGRAM_ID", null);
            requestTableItem.put("PROGRAM_REQUEST_ID", null);
            requestTableItem.put("PROGRAM_UPDATE_DATE", null);
            requestTableItem.put("PROGRAM_APPLICATION_ID", null);
            requestTableItem.put("WORK_REQUEST_STATUS_ID", null);
            requestTableItem.put("SERVICE_ASSOC_ID", null);
            requestTableItem.put("RETURN_STATUS", null);
            requestTableItem.put("TRANSACTION_TYPE", 1);

            Map<String, Object> requestTBL = new HashMap<>();
            requestTBL.put("P_EAM_REQUEST_TBL_ITEM", Collections.singletonList(requestTableItem));


            body.put("P_EAM_REQUEST_TBL", requestTBL);

            ObjectMapper objectMapper1 = new ObjectMapper();
            String json1 = objectMapper1.writeValueAsString(body);

            System.out.println(json1);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            System.out.println("Entity records = " + entity);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println("ResponseEntity = " + responseEntity);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String response = responseEntity.getBody();

                if (response != null && !response.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                    JsonNode jsonNode = objectMapper.readTree(response);
                    String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();
                    JsonNode eamWoRecNode = jsonNode.get("X_EAM_WO_REC");

                    String wipEntityName = null;
                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_NAME")) {
                        JsonNode wipEntityNode = eamWoRecNode.get("WIP_ENTITY_NAME");
                        if (wipEntityNode.has("$")) {
                            wipEntityName = wipEntityNode.get("$").asText();
                        }
                    }

                  /*  String wipEntityId = null;
                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_ID")) {
                        JsonNode wipEntityIdNode = eamWoRecNode.get("WIP_ENTITY_ID");
                        if (wipEntityIdNode.has("$")) {
                            wipEntityId = wipEntityIdNode.get("$").asText();
                        }
                    }*/

                    if ("S".equals(responseStatus)/* && wipEntityName != null && !wipEntityName.isEmpty()*/) {
                        // String wipEntityName = jsonNode.get("WIP_ENTITY_NAME").asText();

                      /*  if (basicDetails != null) {
                            basicDetails.setWorkOrderNumber(wipEntityName);
                            basicDetails.setWipEntityName(wipEntityName);
                            basicDetails.setWipEntityId(wipEntityId);
                            basicDetailsRepository.save(basicDetails);
                        }
                        if (additionalDetails != null) {
                            additionalDetails.setWorkOrderNumber(wipEntityName);
                            additionalDetailsRepository.save(additionalDetails);
                        }

                        if (operationList != null && !operationList.isEmpty()) {
                            Operation operation = operationList.getFirst();
                            operation.setWorkOrderNumber(wipEntityName);
                            operationRepository.save(operation);
                            for (OperationChild operationChild : operation.getOperationList()) {
                                operationChild.setWorkOrderNumber(wipEntityName);
                                operationChild.setWipEntityId(wipEntityId);
                                operationChildRepository.save(operationChild);
                            }
                        }

                        if (materialList != null && !materialList.isEmpty()) {
                            Material material = materialList.getFirst();
                            material.setWorkOrderNumber(wipEntityName);
                            materialRepository.save(material);
                            for (MaterialChild materialChild : material.getMaterialList()) {
                                materialChild.setWorkOrderNumber(wipEntityName);
                                materialChild.setWipEntityId(wipEntityId);
                                materialChildRepository.save(materialChild);
                            }
                        }*/


                        return ResponseEntity.ok(new ResponseModel<>(true, "WorkOrder Updated Successfully !!", wipEntityName));

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
                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel<>(false, "An unexpected error occurred: " + ex.getMessage(), null));
        }
    }







    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public ResponseModel<List<GetWorkOrderListEAM>> listWorkOrder(String startDateStr, String endDateStr, String organizationId) {

        LocalDateTime startDate = LocalDateTime.parse(startDateStr, FORMATTER);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr, FORMATTER);

        // Convert LocalDateTime to Timestamp
        Timestamp startTimestamp = Timestamp.valueOf(startDate);
        Timestamp endTimestamp = Timestamp.valueOf(endDate);


//        String sql = "SELECT * FROM APPS.EAM_WORK_ORDERS_V " +
//                "WHERE (CREATION_DATE BETWEEN ? AND ?) " +
//                "AND (ORGANIZATION_ID = ?) " +
//                "ORDER BY CREATION_DATE DESC";
//        String sql = "SELECT * FROM (" +
//                "    SELECT * FROM APPS.EAM_WORK_ORDERS_V" +
//                "    WHERE CREATION_DATE BETWEEN ? AND ?" +
//                "    AND ORGANIZATION_ID = ?" +
//                "    ORDER BY CREATION_DATE DESC" +
//                ") WHERE ROWNUM <= 100 " +
//                "ORDER BY CREATION_DATE DESC";

        String sql = "SELECT WIP_ENTITY_NAME, ASSET_NUMBER, ASSET_GROUP_ID, CREATION_DATE, SCHEDULED_START_DATE, SCHEDULED_COMPLETION_DATE, OWNING_DEPARTMENT_CODE, WORK_ORDER_STATUS " +
                "FROM (" +
                "    SELECT WIP_ENTITY_NAME, ASSET_NUMBER, ASSET_GROUP_ID, CREATION_DATE, SCHEDULED_START_DATE, SCHEDULED_COMPLETION_DATE, OWNING_DEPARTMENT_CODE, WORK_ORDER_STATUS " +
                "    FROM APPS.EAM_WORK_ORDERS_V " +
                "    WHERE CREATION_DATE BETWEEN ? AND ? " +
                "    AND ORGANIZATION_ID = ? " +
                "    ORDER BY CREATION_DATE DESC" +
                ") WHERE ROWNUM <= 100 " +
                "ORDER BY CREATION_DATE DESC";





        List<GetWorkOrderListEAM> results = jdbcTemplate.query(sql, new Object[]{startTimestamp, endTimestamp, organizationId},
                (rs, rowNum) -> {

                    var assetGroupId = rs.getString("ASSET_GROUP_ID");


                    GetWorkOrderListEAM workOrder = new GetWorkOrderListEAM();

                    workOrder.setWipEntityName(rs.getString("WIP_ENTITY_NAME"));
                    workOrder.setAssetNumber(rs.getString("ASSET_NUMBER"));
                    workOrder.setAssetGroupId(rs.getString("ASSET_GROUP_ID"));
                    workOrder.setCreationDate(rs.getString("CREATION_DATE"));
                    workOrder.setScheduledStartDate(rs.getString("SCHEDULED_START_DATE"));
                    workOrder.setScheduledCompletionDate(rs.getString("SCHEDULED_COMPLETION_DATE"));
                    workOrder.setOwningDepartment(rs.getString("OWNING_DEPARTMENT_CODE"));
                    workOrder.setWorkOrderStatus(rs.getString("WORK_ORDER_STATUS"));

                    if(assetGroupId!=null){
                        var assetGroupName = assetGroupRepository.findByAssetGroupId(Integer.parseInt(assetGroupId));
                        if(assetGroupName.isPresent()){
                            workOrder.setAssetGroup(assetGroupName.get().getAssetGroup());
                        }else{
                            workOrder.setAssetGroup("");
                        }
                    }

                    return workOrder;
                }

        );

        System.out.println("WorkOrder List = "+results.size());

        return new ResponseModel<>(true, "Success", results);
    }

//    //rk new code--------------------------------
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//
//    public FinalService(NamedParameterJdbcTemplate namedParameterJdbcTemplate, AssetGroupRepository assetGroupRepository) {
//        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
//        this.assetGroupRepository = assetGroupRepository;
//    }
//
//    public ResponseModel<List<GetWorkOrderListEAM>> listWorkOrder(String startDateStr, String endDateStr, String organizationId) {
//        LocalDateTime startDate = LocalDateTime.parse(startDateStr, FORMATTER);
//        LocalDateTime endDate = LocalDateTime.parse(endDateStr, FORMATTER);
//
//        String sql = "SELECT * FROM (" +
//                "    SELECT WIP_ENTITY_NAME, ASSET_NUMBER, ASSET_GROUP_ID, CREATION_DATE, " +
//                "           SCHEDULED_START_DATE, SCHEDULED_COMPLETION_DATE, " +
//                "           OWNING_DEPARTMENT_CODE, WORK_ORDER_STATUS " +
//                "    FROM APPS.EAM_WORK_ORDERS_V " +
//                "    WHERE CREATION_DATE BETWEEN :startDate AND :endDate " +
//                "    AND ORGANIZATION_ID = :orgId " +
//                "    ORDER BY CREATION_DATE DESC" +
//                ") WHERE ROWNUM <= 100";
//
//        MapSqlParameterSource params = new MapSqlParameterSource()
//                .addValue("startDate", Timestamp.valueOf(startDate))
//                .addValue("endDate", Timestamp.valueOf(endDate))
//                .addValue("orgId", organizationId);
//
//        List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, params);
//
//        CompletableFuture<List<GetWorkOrderListEAM>> futureResults = CompletableFuture.supplyAsync(() ->
//                rows.parallelStream().map(row -> {
//                    GetWorkOrderListEAM workOrder = new GetWorkOrderListEAM();
//                    workOrder.setWipEntityName((String) row.get("WIP_ENTITY_NAME"));
//                    workOrder.setAssetNumber((String) row.get("ASSET_NUMBER"));
//                    workOrder.setAssetGroupId((String) row.get("ASSET_GROUP_ID"));
//                    workOrder.setCreationDate(row.get("CREATION_DATE").toString());
//                    workOrder.setScheduledStartDate(row.get("SCHEDULED_START_DATE") != null ? row.get("SCHEDULED_START_DATE").toString() : null);
//                    workOrder.setScheduledCompletionDate(row.get("SCHEDULED_COMPLETION_DATE") != null ? row.get("SCHEDULED_COMPLETION_DATE").toString() : null);
//                    workOrder.setOwningDepartment((String) row.get("OWNING_DEPARTMENT_CODE"));
//                    workOrder.setWorkOrderStatus((String) row.get("WORK_ORDER_STATUS"));
//
//                    String assetGroupId = (String) row.get("ASSET_GROUP_ID");
//                    if (assetGroupId != null) {
//                        assetGroupRepository.findByAssetGroupId(Integer.parseInt(assetGroupId))
//                                .ifPresentOrElse(
//                                        assetGroup -> workOrder.setAssetGroup(assetGroup.getAssetGroup()),
//                                        () -> workOrder.setAssetGroup("")
//                                );
//                    }
//
//                    return workOrder;
//                }).collect(Collectors.toList())
//        );
//
//        List<GetWorkOrderListEAM> results = futureResults.join();
//        System.out.println("WorkOrder List = " + results.size());
//
//        return new ResponseModel<>(true, "Success", results);
//    }


    //rk new code end---------------------------------------------
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>  Previous getWorkRequestNumber      <<<<<<<<<<<<<<<<<<<<<<<<<<<<
//    public ResponseModel<List<String>> getWorkRequestNumber(String workRequestStatus, String organizationId) {
//        ResponseModel<List<String>> response;
//        try {
//            String sql = "SELECT WORK_REQUEST_NUMBER FROM APPS.WIP_EAM_WORK_REQUESTS_v WHERE WORK_REQUEST_STATUS = ? AND ORGANIZATION_ID = ?";
//
//            Object[] params = {workRequestStatus, organizationId};
//
//            List<String> workRequestNumberList = jdbcTemplate.queryForList(sql, params, String.class);
//
//            if (!workRequestNumberList.isEmpty()) {
//                response = new ResponseModel<>(true, "workRequestNumber is found.", workRequestNumberList);
//                System.out.println("Total workRequestNumber(s) = " + workRequestNumberList.size());
//            } else {
//                response = new ResponseModel<>(true, "No workRequestNumber found for the given name.", null);
//            }
//        }catch (Exception e) {
//            response = new ResponseModel<>(false, "An error occurred while retrieving the records.", null);
//            e.printStackTrace();
//        }
//
//        return response;
//    }


    public ResponseModel<List<GetWorkOrderListByWorkOrderNo>> listWorkOrderWithWorkOrderNumber(String workOrderNumber) {
        String sql = "SELECT * FROM APPS.EAM_WORK_ORDERS_V WHERE WIP_ENTITY_NAME = ?";

        List<GetWorkOrderListByWorkOrderNo> results = jdbcTemplate.query(sql, new Object[]{workOrderNumber},
                (rs, rowNum) -> {


                    GetWorkOrderListByWorkOrderNo workOrder = new GetWorkOrderListByWorkOrderNo();
                    System.out.println("CAlled WorlOrder");
                    workOrder.setRowId(rs.getString("ROW_ID"));
                    workOrder.setSchedParentWipEntityName(rs.getString("SCHED_PARENT_WIP_ENTITY_NAME"));
                    workOrder.setActualStartDate(rs.getString("ACTUAL_START_DATE"));
                    workOrder.setWipEntityId(rs.getString("WIP_ENTITY_ID"));
                    workOrder.setWipEntityName(rs.getString("WIP_ENTITY_NAME"));
                    workOrder.setWeRowId(rs.getString("WE_ROW_ID"));
                    workOrder.setEntityType(rs.getString("ENTITY_TYPE"));
                    workOrder.setOrganizationId(rs.getString("ORGANIZATION_ID"));
                    workOrder.setLastUpdateDate(rs.getString("LAST_UPDATE_DATE"));
                    workOrder.setLastUpdatedBy(rs.getString("LAST_UPDATED_BY"));
                    workOrder.setCreationDate(rs.getString("CREATION_DATE"));
                    workOrder.setCreatedBy(rs.getString("CREATED_BY"));
                    workOrder.setLastUpdateLogin(rs.getString("LAST_UPDATE_LOGIN"));
                    workOrder.setDescription(rs.getString("DESCRIPTION"));
                    workOrder.setPrimaryItemId(rs.getString("PRIMARY_ITEM_ID"));
                    workOrder.setAssetActivity(rs.getString("ASSET_ACTIVITY"));
                    workOrder.setAssetGroupId(rs.getString("ASSET_GROUP_ID"));
                    workOrder.setAssetNumber(rs.getString("ASSET_NUMBER"));
                    workOrder.setAssetDescription(rs.getString("ASSET_DESCRIPTION"));
                    workOrder.setClassCode(rs.getString("CLASS_CODE"));
                    workOrder.setStatusType(rs.getString("STATUS_TYPE"));
                    workOrder.setPmScheduleId(rs.getString("PM_SCHEDULE_ID"));
                    workOrder.setManualRebuildFlag(rs.getString("MANUAL_REBUILD_FLAG"));
                    workOrder.setWorkOrderType(rs.getString("WORK_ORDER_TYPE"));
                    workOrder.setWorkOrderTypeDisp(rs.getString("WORK_ORDER_TYPE_DISP"));
                    workOrder.setMaterialAccount(rs.getString("MATERIAL_ACCOUNT"));
                    workOrder.setMaterialOverheadAccount(rs.getString("MATERIAL_OVERHEAD_ACCOUNT"));
                    workOrder.setResourceAccount(rs.getString("RESOURCE_ACCOUNT"));
                    workOrder.setOutsideProcessingAccount(rs.getString("OUTSIDE_PROCESSING_ACCOUNT"));
                    workOrder.setMaterialVarianceAccount(rs.getString("MATERIAL_VARIANCE_ACCOUNT"));
                    workOrder.setResourceVarianceAccount(rs.getString("RESOURCE_VARIANCE_ACCOUNT"));
                    workOrder.setOutsideProcVarianceAccount(rs.getString("OUTSIDE_PROC_VARIANCE_ACCOUNT"));
                    workOrder.setStdCostAdjustmentAccount(rs.getString("STD_COST_ADJUSTMENT_ACCOUNT"));
                    workOrder.setOverheadAccount(rs.getString("OVERHEAD_ACCOUNT"));
                    workOrder.setOverheadVarianceAccount(rs.getString("OVERHEAD_VARIANCE_ACCOUNT"));
                    workOrder.setScheduledStartDate(rs.getString("SCHEDULED_START_DATE"));
                    workOrder.setScheduledCompletionDate(rs.getString("SCHEDULED_COMPLETION_DATE"));
                    workOrder.setDateReleased(rs.getString("DATE_RELEASED"));
                    workOrder.setDateCompleted(rs.getString("DATE_COMPLETED"));
                    workOrder.setDateClosed(rs.getString("DATE_CLOSED"));
                    workOrder.setOwningDepartment(rs.getString("OWNING_DEPARTMENT"));
                    workOrder.setOwningDepartmentCode(rs.getString("OWNING_DEPARTMENT_CODE"));
                    workOrder.setActivityType(rs.getString("ACTIVITY_TYPE"));
                    workOrder.setActivityTypeDisp(rs.getString("ACTIVITY_TYPE_DISP"));
                    workOrder.setActivityCause(rs.getString("ACTIVITY_CAUSE"));
                    workOrder.setActivityCauseDisp(rs.getString("ACTIVITY_CAUSE_DISP"));
                    workOrder.setPriority(rs.getString("PRIORITY"));
                    workOrder.setPriorityDisp(rs.getString("PRIORITY_DISP"));
                    workOrder.setRequestedStartDate(rs.getString("REQUESTED_START_DATE"));
                    workOrder.setRequestedDueDate(rs.getString("REQUESTED_DUE_DATE"));
                    workOrder.setEstimationStatus(rs.getString("ESTIMATION_STATUS"));
                    workOrder.setNotificationRequired(rs.getString("NOTIFICATION_REQUIRED"));
                    workOrder.setShutdownType(rs.getString("SHUTDOWN_TYPE"));
                    workOrder.setShutdownTypeDisp(rs.getString("SHUTDOWN_TYPE_DISP"));
                    workOrder.setTagoutRequired(rs.getString("TAGOUT_REQUIRED"));
                    workOrder.setPlanMaintenance(rs.getString("PLAN_MAINTENANCE"));
                    workOrder.setFirmPlannedFlag(rs.getString("FIRM_PLANNED_FLAG"));
                    workOrder.setScheduleGroupId(rs.getString("SCHEDULE_GROUP_ID"));
                    workOrder.setScheduleGroupName(rs.getString("SCHEDULE_GROUP_NAME"));
                    workOrder.setProjectNumber(rs.getString("PROJECT_NUMBER"));
                    workOrder.setProjectName(rs.getString("PROJECT_NAME"));
                    workOrder.setProjectId(rs.getString("PROJECT_ID"));
                    workOrder.setTaskNumber(rs.getString("TASK_NUMBER"));
                    workOrder.setTaskName(rs.getString("TASK_NAME"));
                    workOrder.setTaskId(rs.getString("TASK_ID"));
                    workOrder.setParentWipEntityId(rs.getString("PARENT_WIP_ENTITY_ID"));
                    workOrder.setParentWipEntityName(rs.getString("PARENT_WIP_ENTITY_NAME"));
                    workOrder.setRebuildItemId(rs.getString("REBUILD_ITEM_ID"));
                    workOrder.setRebuildSerialNumber(rs.getString("REBUILD_SERIAL_NUMBER"));
                    workOrder.setBomReferenceId(rs.getString("BOM_REFERENCE_ID"));
                    workOrder.setRoutingReferenceId(rs.getString("ROUTING_REFERENCE_ID"));
                    workOrder.setCommonBomSequenceId(rs.getString("COMMON_BOM_SEQUENCE_ID"));
                    workOrder.setCommonRoutingSequenceId(rs.getString("COMMON_ROUTING_SEQUENCE_ID"));
                    workOrder.setAlternateBomDesignator(rs.getString("ALTERNATE_BOM_DESIGNATOR"));
                    workOrder.setBomRevision(rs.getString("BOM_REVISION"));
                    workOrder.setBomRevisionDate(rs.getString("BOM_REVISION_DATE"));
                    workOrder.setAlternateRoutingDesignator(rs.getString("ALTERNATE_ROUTING_DESIGNATOR"));
                    workOrder.setRoutingRevision(rs.getString("ROUTING_REVISION"));
                    workOrder.setRoutingRevisionDate(rs.getString("ROUTING_REVISION_DATE"));
                    workOrder.setCompletionSubinventory(rs.getString("COMPLETION_SUBINVENTORY"));
                    workOrder.setCompletionLocatorId(rs.getString("COMPLETION_LOCATOR_ID"));
                    workOrder.setLotNumber(rs.getString("LOT_NUMBER"));
                    workOrder.setDemandClass(rs.getString("DEMAND_CLASS"));
                    workOrder.setAttributeCategory(rs.getString("ATTRIBUTE_CATEGORY"));
                    workOrder.setAttribute1(rs.getString("ATTRIBUTE1"));
                    workOrder.setAttribute2(rs.getString("ATTRIBUTE2"));
                    workOrder.setAttribute3(rs.getString("ATTRIBUTE3"));
                    workOrder.setAttribute4(rs.getString("ATTRIBUTE4"));
                    workOrder.setAttribute5(rs.getString("ATTRIBUTE5"));
                    workOrder.setAttribute15(rs.getString("ATTRIBUTE15"));
                    workOrder.setInstanceId(rs.getString("INSTANCE_ID"));
                    workOrder.setInstanceNumber(rs.getString("INSTANCE_NUMBER"));
                    workOrder.setMaintenanceObjectSource(rs.getString("MAINTENANCE_OBJECT_SOURCE"));
                    workOrder.setMaintenanceObjectType(rs.getString("MAINTENANCE_OBJECT_TYPE"));
                    workOrder.setSource(rs.getString("SOURCE"));
                    workOrder.setMaintenanceObjectId(rs.getString("MAINTENANCE_OBJECT_ID"));
                    workOrder.setServiceRequestId(rs.getString("SERVICE_REQUEST_ID"));
                    workOrder.setServiceRequest(rs.getString("SERVICE_REQUEST"));
                    workOrder.setMaterialIssueByMo(rs.getString("MATERIAL_ISSUE_BY_MO"));
                    workOrder.setActivitySource(rs.getString("ACTIVITY_SOURCE"));
                    workOrder.setActivitySourceMeaning(rs.getString("ACTIVITY_SOURCE_MEANING"));
                    workOrder.setEamLinearLocationId(rs.getString("EAM_LINEAR_LOCATION_ID"));
                    workOrder.setPlannerMaintenance(rs.getString("PLANNER_MAINTENANCE"));
                    workOrder.setPlannerMaintenanceMeaning(rs.getString("PLANNER_MAINTENANCE_MEANING"));
                    workOrder.setPendingFlag(rs.getString("PENDING_FLAG"));
                    workOrder.setUserDefinedStatusId(rs.getString("USER_DEFINED_STATUS_ID"));
                    workOrder.setWorkOrderStatus(rs.getString("WORK_ORDER_STATUS"));
                    workOrder.setWorkOrderStatusPending(rs.getString("WORK_ORDER_STATUS_PENDING"));
                    workOrder.setMaterialShortageCheckDate(rs.getString("MATERIAL_SHORTAGE_CHECK_DATE"));
                    workOrder.setMaterialShortageFlag(rs.getString("MATERIAL_SHORTAGE_FLAG"));
                    workOrder.setMaterialShortageDisp(rs.getString("MATERIAL_SHORTAGE_DISP"));
                    workOrder.setWorkflowType(rs.getString("WORKFLOW_TYPE"));
                    workOrder.setCycleId(rs.getString("CYCLE_ID"));
                    workOrder.setSeqId(rs.getString("SEQ_ID"));
                    workOrder.setDsScheduledFlag(rs.getString("DS_SCHEDULED_FLAG"));
                    workOrder.setAssetSerialNumber(rs.getString("ASSET_SERIAL_NUMBER"));
                    workOrder.setMaintOrganizationId(rs.getString("MAINT_ORGANIZATION_ID"));
                    workOrder.setAssetRebuildGroup(rs.getString("ASSET_REBUILD_GROUP"));
                    workOrder.setPmSuggestedStartDate(rs.getString("PM_SUGGESTED_START_DATE"));
                    workOrder.setPmSuggestedEndDate(rs.getString("PM_SUGGESTED_END_DATE"));
                    workOrder.setFailureId(rs.getString("FAILURE_ID"));
                    workOrder.setFailureEntryId(rs.getString("FAILURE_ENTRY_ID"));
                    workOrder.setFailureCode(rs.getString("FAILURE_CODE"));
                    workOrder.setResolutionCode(rs.getString("RESOLUTION_CODE"));
                    workOrder.setFailureCodeRequired(rs.getString("FAILURE_CODE_REQUIRED"));
                    workOrder.setFailureDate(rs.getString("FAILURE_DATE"));
                    workOrder.setComments(rs.getString("COMMENTS"));
                    workOrder.setParentInstanceNumber(rs.getString("PARENT_INSTANCE_NUMBER"));
                    workOrder.setTargetStartDate(rs.getString("TARGET_START_DATE"));
                    workOrder.setTargetCompletionDate(rs.getString("TARGET_COMPLETION_DATE"));
                    workOrder.setCiiInstanceId(rs.getString("CII_INSTANCE_ID"));

                    Asset assetNumber = assetRepository.findByAssetNumber(rs.getString("ASSET_NUMBER")!=null?rs.getString("ASSET_NUMBER"):"0");

                    if (assetNumber != null && assetNumber.getAssetNumber().equals(rs.getString("ASSET_NUMBER"))) {
                         workOrder.setAssetGroup(assetNumber.getAssetGroup()); // Set assetGroup from Asset to WorkRequest
                    }
                  //  var departmentDescription = departmentRepository.findByEamDepartmentId(Integer.valueOf(rs.getString("OWNING_DEPARTMENT")!=null?rs.getString("OWNING_DEPARTMENT"):"0"));
                    var department = rs.getString("OWNING_DEPARTMENT");

                    if(department!=null){
                        var departmentName = departmentRepository.findByEamDepartmentId(Integer.parseInt(department));
                        if(departmentName.isPresent()){
                            workOrder.setOwningDepartmentDescription(departmentName.get().getDepartmentDescription());
                        }else{
                            workOrder.setOwningDepartmentDescription("");
                        }
                    }
                    var workOrderNo = rs.getString("WIP_ENTITY_NAME");

                    if(workOrderNo!=null){
                        var  workRequestNumber = basicDetailsRepository.findByWorkOrderNumber(workOrderNo);
                        if(workRequestNumber!=null/*workRequestNumber.equals(workOrderNo)*/){
                            workOrder.setWorkRequestNumber(workRequestNumber.getWorkRequestNo());
                            workOrder.setWorkRequestType(workRequestNumber.getWorkRequestType());

                        }else{
                            workOrder.setWorkRequestNumber("");
                            workOrder.setWorkRequestType("");


                        }
                    }

//                    BasicDetails workRequestNumber = basicDetailsRepository.findByWorkOrderNumber(rs.getString("WIP_ENTITY_NAME")!=null?rs.getString("WIP_ENTITY_NAME"):"0");
//
//                    if (workRequestNumber != null && workRequestNumber.getWorkRequestNo().equals(rs.getString("WIP_ENTITY_NAME"))) {
//                        workOrder.setWorkRequestNumber(workRequestNumber.getWorkRequestNo());
//                        workOrder.setWorkOrderType(workRequestNumber.getWorkRequestType());
//                    }

                    return workOrder;
                }

        );
        System.out.println("WorkOrder List = " + results.size());

        return new ResponseModel<>(true, "Success", results);
    }


//    public ResponseModel<List<String>> getSearchWorkOrderNumber(String workOrderNumber) {
//        ResponseModel<List<String>> response;
//
//        try {
////            String sql = "SELECT DISTINCT WIP_ENTITY_NAME FROM APPS.EAM_WORK_ORDERS_V WHERE WIP_ENTITY_NAME LIKE ?";
//
//            String sql = "SELECT DISTINCT WIP_ENTITY_NAME FROM APPS.EAM_WORK_ORDERS_V " +
//                    "WHERE WIP_ENTITY_NAME LIKE ? " +
//                    "ORDER BY CREATION_DATE DESC " +
//                    "FETCH FIRST 50 ROWS ONLY";
//
//            List<String> workOrderList = jdbcTemplate.queryForList(sql, new Object[]{"%" + workOrderNumber + "%"}, String.class);
//
//            if (!workOrderList.isEmpty()) {
//                response = new ResponseModel<>(true, "WorkOrderNumber is found.", workOrderList);
//                System.out.println("Total WorkOrderNumber(s) = " + workOrderList.size());
//            } else {
//                response = new ResponseModel<>(true, "No WorkOrderNumber found for the given name.", null);
//            }
//        } catch (Exception e) {
//            response = new ResponseModel<>(false, "An error occurred while retrieving the records.", null);
//            e.printStackTrace();
//        }
//
//        return response;
//    }

//    public ResponseModel<List<String>> getSearchWorkOrderNumber(String workOrderNumber) {
//        ResponseModel<List<String>> response;
//
//        try {
//            StringBuilder sql = new StringBuilder("SELECT DISTINCT WIP_ENTITY_NAME FROM APPS.EAM_WORK_ORDERS_V ");
//
//            List<Object> params = new ArrayList<>();
//
//            if (workOrderNumber != null && !workOrderNumber.trim().isEmpty()) {
//                sql.append("WHERE WIP_ENTITY_NAME LIKE ? ");
//                params.add("%" + workOrderNumber + "%");
//            }
//
//            sql.append("ORDER BY CREATION_DATE DESC ");
//
//            if (workOrderNumber == null || workOrderNumber.trim().isEmpty()) {
//                // When no input is provided, fetch the last 50 rows
//                sql.append("OFFSET 0 ROWS FETCH NEXT 50 ROWS ONLY");
//            } else {
//                // When input is provided, limit the results accordingly
//                sql.append("FETCH FIRST 50 ROWS ONLY");
//            }
//
//            List<String> workOrderList = jdbcTemplate.queryForList(sql.toString(), params.toArray(), String.class);
//
//            if (!workOrderList.isEmpty()) {
//                response = new ResponseModel<>(true, "WorkOrderNumber(s) found.", workOrderList);
//                System.out.println("Total WorkRequestNumber(s) = " + workOrderList.size());
//            } else {
//                response = new ResponseModel<>(true, "No WorkOrderNumber found for the given name.", null);
//            }
//        } catch (Exception e) {
//            response = new ResponseModel<>(false, "An error occurred while retrieving the records.", null);
//            e.printStackTrace();
//        }
//
//        return response;
//    }

    public ResponseModel<List<String>> getSearchWorkOrderNumber(String workOrderNumber) {
        ResponseModel<List<String>> response;
        try {
            StringBuilder sql = new StringBuilder("SELECT DISTINCT WIP_ENTITY_NAME FROM APPS.EAM_WORK_ORDERS_V ");

            List<Object> params = new ArrayList<>();

            if (workOrderNumber != null && !workOrderNumber.trim().isEmpty()) {
                sql.append("WHERE WIP_ENTITY_NAME LIKE ? ");
                params.add("%" + workOrderNumber + "%");
            }
            sql.append("ORDER BY CREATION_DATE DESC FETCH FIRST 50 ROWS ONLY");
            //sql.append("FETCH FIRST 50 ROWS ONLY");

            List<String> workOrderList = jdbcTemplate.queryForList(sql.toString(), params.toArray(), String.class);

            if (!workOrderList.isEmpty()) {
                response = new ResponseModel<>(true, "WorkOrderNumber is found.", workOrderList);
                System.out.println("Total WorkOrderNumber(s) = " + workOrderList.size());
            } else {
                response = new ResponseModel<>(true, "No WorkOrderNumber found for the given name.", null);
            }
        } catch (Exception e) {
            response = new ResponseModel<>(false, "An error occurred while retrieving the records.", null);
            e.printStackTrace();
        }

        return response;
    }


    public ResponseModel<List<GetWorkOrderListEAM>> getSearchFilters(String workOrderNumber, String assetNumber, String department, String status) {

        StringBuilder sql = new StringBuilder("SELECT * FROM APPS.EAM_WORK_ORDERS_V WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (workOrderNumber!=null) {
            sql.append(" AND WIP_ENTITY_NAME = ?");
            params.add(workOrderNumber);
        }
        if (assetNumber!=null) {
            sql.append(" AND ASSET_NUMBER = ?");
            params.add(assetNumber);
        }
        if (department!=null) {
            sql.append(" AND OWNING_DEPARTMENT_CODE = ?");
            params.add(department);
        }
        if (status!=null) {
            sql.append(" AND WORK_ORDER_STATUS = ?");
            params.add(status);
        }

        List<GetWorkOrderListEAM> results = jdbcTemplate.query(sql.toString(), params.toArray(),
                (rs, rowNum) -> {

                    var assetGroupId = rs.getString("ASSET_GROUP_ID");

                    GetWorkOrderListEAM workOrder = new GetWorkOrderListEAM();
                    workOrder.setWipEntityName(rs.getString("WIP_ENTITY_NAME"));
                    workOrder.setAssetNumber(rs.getString("ASSET_NUMBER"));
                    workOrder.setAssetGroupId(rs.getString("ASSET_GROUP_ID"));
                    workOrder.setCreationDate(rs.getString("CREATION_DATE"));
                    workOrder.setScheduledStartDate(rs.getString("SCHEDULED_START_DATE"));
                    workOrder.setScheduledCompletionDate(rs.getString("SCHEDULED_COMPLETION_DATE"));
                    workOrder.setOwningDepartment(rs.getString("OWNING_DEPARTMENT_CODE"));
                    workOrder.setWorkOrderStatus(rs.getString("WORK_ORDER_STATUS"));

                    if (assetGroupId != null) {
                        var assetGroupName = assetGroupRepository.findByAssetGroupId(Integer.parseInt(assetGroupId));
                        if (assetGroupName.isPresent()) {
                            workOrder.setAssetGroup(assetGroupName.get().getAssetGroup());
                        } else {
                            workOrder.setAssetGroup("");
                        }
                    }

                    return workOrder;
                });

        return new ResponseModel<>(true, "Success", results);
    }

    public ResponseModel<List<String>> getWorkRequestNumber(String workRequestStatus, String organizationId,String workRequestNumber) {
        ResponseModel<List<String>> response;
        try {

            StringBuilder sql = new StringBuilder("SELECT WORK_REQUEST_NUMBER FROM APPS.WIP_EAM_WORK_REQUESTS_v WHERE 1=1");

            List<Object> params = new ArrayList<>();

            if (workRequestStatus!=null) {
                sql.append(" AND WORK_REQUEST_STATUS = ?");
                params.add(workRequestStatus);
            }
            if (organizationId!=null) {
                sql.append(" AND ORGANIZATION_ID = ?");
                params.add(organizationId);
            }
            if (workRequestNumber!=null) {
                sql.append(" AND WORK_REQUEST_NUMBER LIKE ?");
                params.add("%" + workRequestNumber + "%");
            }
            sql.append("ORDER BY CREATION_DATE DESC FETCH FIRST 100 ROWS ONLY");

            List<String> workRequestNumberList = jdbcTemplate.queryForList(sql.toString(), params.toArray(), String.class);

            if (!workRequestNumberList.isEmpty()) {
                response = new ResponseModel<>(true, "workRequestNumber is found.", workRequestNumberList);
                System.out.println("Total workRequestNumber(s) = " + workRequestNumberList.size());
            } else {
                response = new ResponseModel<>(true, "No workRequestNumber found for the given name.", null);
            }
        }catch (Exception e) {
            response = new ResponseModel<>(false, "An error occurred while retrieving the records.", null);
            e.printStackTrace();
        }

        return response;
    }



    public ResponseEntity<ResponseModel<String>> myAddWorkOrder(String workOrderNumber) {
        try {
            String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

            // String url = "http://ocitestekaayansoa.adityabirla.com:80/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            BasicDetails basicDetails = basicDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            AdditionalDetails additionalDetails = additionalDetailsRepository.findByWorkOrderNumber(workOrderNumber);
            List<Operation> operationList = operationRepository.findByWorkOrderNumber(workOrderNumber);
            List<Material> materialList = materialRepository.findByWorkOrderNumber(workOrderNumber);

            if (basicDetails == null && additionalDetails == null && operationList.isEmpty() && materialList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel<>(false, "No details found for the given work order number.", null));
            }

            if (basicDetails == null) {
                System.out.println("BasicDetails is null for workOrderNumber: " + workOrderNumber);
            } else {
                System.out.println("BasicDetails: " + basicDetails.toString());
            }

            if (additionalDetails == null) {
                System.out.println("AdditionalDetails is null for workOrderNumber: " + workOrderNumber);
            } else {
                System.out.println("AdditionalDetails: " + additionalDetails.toString());
            }


            Map<String, Object> body = new HashMap<>();
            body.put("P_BO_IDENTIFIER", "EAM");
            body.put("P_API_VERSION_NUMBER", 1);
            body.put("P_INIT_MSG_LIST", "1");
            body.put("P_COMMIT", "Y");

            // EAM Work Order Record
            Map<String, Object> eamWoRec = new HashMap<>();
            eamWoRec.put("HEADER_ID", 1);
            eamWoRec.put("BATCH_ID", 1);
            eamWoRec.put("ROW_ID", null);
//            eamWoRec.put("WIP_ENTITY_NAME",  workOrderNumber.contains("TEMPWO") ? null:workOrderNumber);
//            eamWoRec.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
            eamWoRec.put("WIP_ENTITY_NAME", null);
            eamWoRec.put("ORGANIZATION_ID", basicDetails != null ? basicDetails.getOrganizationId() : null);
            eamWoRec.put("DESCRIPTION", basicDetails != null ? basicDetails.getDescription() : null);
//            eamWoRec.put("ASSET_NUMBER", basicDetails != null ? basicDetails.getAssetNumber() :null);
//            eamWoRec.put("ASSET_GROUP_ID", basicDetails != null ? basicDetails.getAssetGroupId() :null);
            eamWoRec.put("ASSET_NUMBER", null);
            eamWoRec.put("ASSET_GROUP_ID", null);
            /*eamWoRec.put("REBUILD_ITEM_ID",  null);
            eamWoRec.put("REBUILD_SERIAL_NUMBER", null);*/
            eamWoRec.put("REBUILD_ITEM_ID", basicDetails != null ? basicDetails.getRebuildItemId() : null);
            eamWoRec.put("REBUILD_SERIAL_NUMBER", basicDetails != null ? basicDetails.getRebuildSerialNo() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_ID", basicDetails != null ? basicDetails.getMaintenanceObjectId() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_TYPE", basicDetails != null ? basicDetails.getMaintenanceObjectType() : null);
            eamWoRec.put("MAINTENANCE_OBJECT_SOURCE", 1);
            eamWoRec.put("CLASS_CODE", basicDetails != null ? basicDetails.getWipAccountingClassCode() : null);
            eamWoRec.put("WORK_ORDER_TYPE", basicDetails != null ? basicDetails.getWoType() : null);
            eamWoRec.put("STATUS_TYPE", basicDetails != null ? basicDetails.getStatusType() : null);
            eamWoRec.put("PRIORITY", basicDetails != null ? basicDetails.getPriority() : null);
            eamWoRec.put("SHUTDOWN_TYPE", basicDetails != null ? basicDetails.getShutdownType() : null);
            eamWoRec.put("FIRM_PLANNED_FLAG", basicDetails != null ? basicDetails.getFirmPlannedType() : null);
            eamWoRec.put("SCHEDULED_START_DATE", basicDetails != null ? basicDetails.getScheduledStartDate() : null);
            eamWoRec.put("SCHEDULED_COMPLETION_DATE", basicDetails != null ? basicDetails.getScheduledEndDate() : null);
            eamWoRec.put("NOTIFICATION_REQUIRED", additionalDetails != null ? additionalDetails.getNotificationRequired() : null);
            eamWoRec.put("TAGOUT_REQUIRED", additionalDetails != null ? additionalDetails.getTagoutRequired() : null);
            eamWoRec.put("ATTRIBUTE3", additionalDetails != null ? additionalDetails.getSafetyPermit() : null);
            eamWoRec.put("EAM_LINEAR_LOCATION_ID", null);
            eamWoRec.put("ASSET_ACTIVITY_ID", null);
            eamWoRec.put("DUE_DATE", null);
            eamWoRec.put("ACTIVITY_TYPE", null);
            eamWoRec.put("ACTIVITY_CAUSE", null);
            eamWoRec.put("ACTIVITY_SOURCE", null);
            eamWoRec.put("JOB_QUANTITY", null);
            eamWoRec.put("DATE_RELEASED", null);
            eamWoRec.put("OWNING_DEPARTMENT", basicDetails != null ? basicDetails.getDepartmentId() : null);
            eamWoRec.put("REQUESTED_START_DATE", null);
            eamWoRec.put("PLANNER_TYPE", basicDetails != null ? basicDetails.getPlannerType() : null);
            eamWoRec.put("PLAN_MAINTENANCE", null);
            eamWoRec.put("PROJECT_ID", null);
            eamWoRec.put("TASK_ID", null);
            eamWoRec.put("END_ITEM_UNIT_NUMBER", null);
            eamWoRec.put("SCHEDULE_GROUP_ID", null);
            eamWoRec.put("BOM_REVISION_DATE", null);
            eamWoRec.put("ROUTING_REVISION_DATE", null);
            eamWoRec.put("ALTERNATE_ROUTING_DESIGNATOR", null);
            eamWoRec.put("ALTERNATE_BOM_DESIGNATOR", null);
            eamWoRec.put("ROUTING_REVISION", null);
            eamWoRec.put("BOM_REVISION", null);
            eamWoRec.put("MANUAL_REBUILD_FLAG", null);
            eamWoRec.put("PM_SCHEDULE_ID", null);
            eamWoRec.put("WIP_SUPPLY_TYPE", null);
            eamWoRec.put("MATERIAL_ACCOUNT", null);
            eamWoRec.put("MATERIAL_OVERHEAD_ACCOUNT", null);
            eamWoRec.put("RESOURCE_ACCOUNT", null);
            eamWoRec.put("OUTSIDE_PROCESSING_ACCOUNT", null);
            eamWoRec.put("MATERIAL_VARIANCE_ACCOUNT", null);
            eamWoRec.put("RESOURCE_VARIANCE_ACCOUNT", null);
            eamWoRec.put("OUTSIDE_PROC_VARIANCE_ACCOUNT", null);
            eamWoRec.put("STD_COST_ADJUSTMENT_ACCOUNT", null);
            eamWoRec.put("OVERHEAD_ACCOUNT", null);
            eamWoRec.put("OVERHEAD_VARIANCE_ACCOUNT", null);
            eamWoRec.put("PM_SUGGESTED_START_DATE", null);
            eamWoRec.put("PM_SUGGESTED_END_DATE", null);
            eamWoRec.put("PM_BASE_METER_READING", null);
            eamWoRec.put("PM_BASE_METER", null);
            eamWoRec.put("COMMON_BOM_SEQUENCE_ID", null);
            eamWoRec.put("COMMON_ROUTING_SEQUENCE_ID", null);
            eamWoRec.put("PO_CREATION_TIME", null);
            eamWoRec.put("GEN_OBJECT_ID", null);
            eamWoRec.put("USER_DEFINED_STATUS_ID", null);
            eamWoRec.put("PENDING_FLAG", null);
            eamWoRec.put("MATERIAL_SHORTAGE_CHECK_DATE", null);
            eamWoRec.put("MATERIAL_SHORTAGE_FLAG", null);
            eamWoRec.put("WORKFLOW_TYPE", null);
            eamWoRec.put("WARRANTY_CLAIM_STATUS", null);
            eamWoRec.put("CYCLE_ID", null);
            eamWoRec.put("SEQ_ID", null);
            eamWoRec.put("DS_SCHEDULED_FLAG", null);
            eamWoRec.put("WARRANTY_ACTIVE", null);
            eamWoRec.put("ASSIGNMENT_COMPLETE", null);
            eamWoRec.put("ATTRIBUTE_CATEGORY", null);
            eamWoRec.put("ATTRIBUTE1", null);
            eamWoRec.put("ATTRIBUTE2", "Y");
//            eamWoRec.put("ATTRIBUTE2", workOrderNumber.contains("TEMPWO") ? null:additionalDetails.getInformDepartments());
            eamWoRec.put("ATTRIBUTE4", null);
            eamWoRec.put("ATTRIBUTE5", null);
            eamWoRec.put("ATTRIBUTE6", null);
            eamWoRec.put("ATTRIBUTE7", null);
            eamWoRec.put("ATTRIBUTE8", null);
            eamWoRec.put("ATTRIBUTE9", null);
            eamWoRec.put("ATTRIBUTE10", null);
            eamWoRec.put("ATTRIBUTE11", null);
            eamWoRec.put("ATTRIBUTE12", null);
            eamWoRec.put("ATTRIBUTE13", null);
            eamWoRec.put("ATTRIBUTE14", null);
            eamWoRec.put("ATTRIBUTE15", null);
            eamWoRec.put("MATERIAL_ISSUE_BY_MO", null);
            eamWoRec.put("ISSUE_ZERO_COST_FLAG", null);
            eamWoRec.put("REPORT_TYPE", null);
            eamWoRec.put("ACTUAL_CLOSE_DATE", null);
            eamWoRec.put("SUBMISSION_DATE", null);
            eamWoRec.put("USER_ID", null);
            eamWoRec.put("RESPONSIBILITY_ID", null);
            eamWoRec.put("REQUEST_ID", null);
            eamWoRec.put("PROGRAM_ID", null);
            eamWoRec.put("PROGRAM_APPLICATION_ID", null);
            eamWoRec.put("SOURCE_LINE_ID", null);
            eamWoRec.put("SOURCE_CODE", null);
            eamWoRec.put("VALIDATE_STRUCTURE", null);
            eamWoRec.put("RETURN_STATUS", null);
            eamWoRec.put("TRANSACTION_TYPE", 1);
            eamWoRec.put("FAILURE_CODE_REQUIRED", null);


            body.put("P_EAM_WO_REC", eamWoRec);
            // EAM_FAILURE_ENTRY_RECORD
            //MultiValueMap<String, Object> failureEntryRecord = new LinkedMultiValueMap<>();
            Map<String, Object> failureEntryRecord = new HashMap<>();
            failureEntryRecord.put("FAILURE_ID", null);
            failureEntryRecord.put("FAILURE_DATE", null);
            failureEntryRecord.put("SOURCE_TYPE", null);
            failureEntryRecord.put("SOURCE_ID", null);
            failureEntryRecord.put("OBJECT_TYPE", null);
            failureEntryRecord.put("OBJECT_ID", null);
            failureEntryRecord.put("MAINT_ORGANIZATION_ID", null);
            failureEntryRecord.put("CURRENT_ORGANIZATION_ID", null);
            failureEntryRecord.put("DEPARTMENT_ID", null);
            failureEntryRecord.put("AREA_ID", null);
            failureEntryRecord.put("TRANSACTION_TYPE", null);
            failureEntryRecord.put("SOURCE_NAME", null);

            eamWoRec.put("EAM_FAILURE_ENTRY_RECORD", failureEntryRecord);


            // EAM_FAILURE_CODES_TBL
            //MultiValueMap<String, Object> failureCodesTblItem = new LinkedMultiValueMap<>();
            Map<String, Object> failureCodesTblItem = new HashMap<>();
            failureCodesTblItem.put("FAILURE_ID", null);
            failureCodesTblItem.put("FAILURE_ENTRY_ID", null);
            failureCodesTblItem.put("COMBINATION_ID", null);
            failureCodesTblItem.put("FAILURE_CODE", null);
            failureCodesTblItem.put("CAUSE_CODE", null);
            failureCodesTblItem.put("RESOLUTION_CODE", null);
            failureCodesTblItem.put("COMMENTS", null);
            failureCodesTblItem.put("TRANSACTION_TYPE", null);

            //MultiValueMap<String, Object> failureCodesTbl = new LinkedMultiValueMap<>();
            Map<String, Object> failureCodesTbl = new HashMap<>();
            failureCodesTbl.put("EAM_FAILURE_CODES_TBL_ITEM", Collections.singletonList(failureCodesTblItem));

            eamWoRec.put("EAM_FAILURE_CODES_TBL", failureCodesTbl);

            eamWoRec.put("TARGET_START_DATE", null);
            eamWoRec.put("TARGET_COMPLETION_DATE", null);


            // Process Operations, Resources, and Instances
            List<OperationChild> opList = new ArrayList<>();
            List<OperationChild> resourceList = new ArrayList<>();
            List<OperationChild> instanceList = new ArrayList<>();

            for (Operation operation : operationList) {
                for (OperationChild operationChild : operation.getOperationList()) {
                    if (operationChild.getOperationSequence() != null || operationChild.getDescription() != null || operationChild.getDepartmentId() != null) {
                        opList.add(operationChild);
                    }
                    if (operationChild.getResourceSequence() != null || operationChild.getResourceId() != null) {
                        resourceList.add(operationChild);
                    }
                    if (operationChild.getInstanceId() != null || operationChild.getInstanceName() != null) {
                        instanceList.add(operationChild);
                    }
                }
            }

           /* body.put("P_EAM_OP_TBL", null);
            body.put("P_EAM_RES_TBL", null);
            body.put("P_EAM_RES_INST_TBL", null);*/

            List<MaterialChild> mcList = new ArrayList<>();
            for (Material material : materialList) {
                for (MaterialChild materialChild : material.getMaterialList()) {
                    if (materialChild.getOperationSequence() != null) {
                        mcList.add(materialChild);
                    }
                }
            }

            //body.put("P_EAM_MAT_REQ_TBL", null);

            // Set null values for other fields that are not needed
            body.put("P_EAM_OP_NETWORK_TBL", null);
            body.put("P_EAM_DIRECT_ITEMS_TBL", null);
            body.put("P_EAM_WO_COMP_REC", null);
            body.put("P_EAM_WO_QUALITY_TBL", null);
            body.put("P_EAM_METER_READING_TBL", null);
            body.put("P_EAM_COUNTER_PROP_TBL", null);
            body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
            body.put("P_EAM_WO_COMP_MR_READ_TBL", null);
            body.put("P_EAM_OP_COMP_TBL", null);
            body.put("P_EAM_PERMIT_TBL", null);
            body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
            body.put("P_EAM_WORK_CLEARANCE_TBL", null);
            body.put("P_EAM_WC_WO_ASSOC_TBL", null);
            body.put("P_EAM_RES_USAGE_TBL", null);
            body.put("P_EAM_SUB_RES_TBL", null);
            body.put("P_DEBUG", "N");
            body.put("P_OUTPUT_DIR", "/usr/tmp");
            body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
            body.put("P_DEBUG_FILE_MODE", "w");


            Map<String, Object> requestTableItem = new HashMap<>();
            requestTableItem.put("HEADER_ID", null);
            requestTableItem.put("BATCH_ID", null);
            requestTableItem.put("ROW_ID", null);
            requestTableItem.put("WIP_ENTITY_NAME", null);
//            requestTableItem.put("WIP_ENTITY_ID", workOrderNumber.contains("TEMPWO") ? null:basicDetails.getWipEntityId());
            requestTableItem.put("ORGANIZATION_ID", basicDetails.getOrganizationId());
            // requestTableItem.put("ORGANIZATION_CODE", null);
            requestTableItem.put("REQUEST_TYPE", 1);
            requestTableItem.put("REQUEST_ID", basicDetails.getWorkRequestNo());
            requestTableItem.put("REQUEST_NUMBER", basicDetails.getWorkRequestNo());
            requestTableItem.put("ATTRIBUTE_CATEGORY", null);
            requestTableItem.put("ATTRIBUTE1", null);
            requestTableItem.put("ATTRIBUTE2", null);
            requestTableItem.put("ATTRIBUTE3", null);
            requestTableItem.put("ATTRIBUTE4", null);
            requestTableItem.put("ATTRIBUTE5", null);
            requestTableItem.put("ATTRIBUTE6", null);
            requestTableItem.put("ATTRIBUTE7", null);
            requestTableItem.put("ATTRIBUTE8", null);
            requestTableItem.put("ATTRIBUTE9", null);
            requestTableItem.put("ATTRIBUTE10", null);
            requestTableItem.put("ATTRIBUTE11", null);
            requestTableItem.put("ATTRIBUTE12", null);
            requestTableItem.put("ATTRIBUTE13", null);
            requestTableItem.put("ATTRIBUTE14", null);
            requestTableItem.put("ATTRIBUTE15", null);
            requestTableItem.put("PROGRAM_ID", null);
            requestTableItem.put("PROGRAM_REQUEST_ID", null);
            requestTableItem.put("PROGRAM_UPDATE_DATE", null);
            requestTableItem.put("PROGRAM_APPLICATION_ID", null);
            requestTableItem.put("WORK_REQUEST_STATUS_ID", null);
            requestTableItem.put("SERVICE_ASSOC_ID", null);
            requestTableItem.put("RETURN_STATUS", null);
            requestTableItem.put("TRANSACTION_TYPE", 1);

            Map<String, Object> requestTBL = new HashMap<>();
            requestTBL.put("P_EAM_REQUEST_TBL_ITEM", Collections.singletonList(requestTableItem));


            body.put("P_EAM_REQUEST_TBL", requestTBL);
            //body.put("P_EAM_REQUEST_TBL", null);

            ObjectMapper objectMapper1 = new ObjectMapper();
            String json1 = objectMapper1.writeValueAsString(body);

            System.out.println(json1);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            System.out.println("Entity records = " + entity);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            System.out.println("ResponseEntity = " + responseEntity);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String response = responseEntity.getBody();

                if (response != null && !response.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                    JsonNode jsonNode = objectMapper.readTree(response);
                    String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();
                    JsonNode eamWoRecNode = jsonNode.get("X_EAM_WO_REC");

                    String wipEntityName = null;
                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_NAME")) {
                        JsonNode wipEntityNode = eamWoRecNode.get("WIP_ENTITY_NAME");
                        if (wipEntityNode.has("$")) {
                            wipEntityName = wipEntityNode.get("$").asText();
                        }
                    }

                    String wipEntityId = null;
                    if (eamWoRecNode != null && eamWoRecNode.has("WIP_ENTITY_ID")) {
                        JsonNode wipEntityIdNode = eamWoRecNode.get("WIP_ENTITY_ID");
                        if (wipEntityIdNode.has("$")) {
                            wipEntityId = wipEntityIdNode.get("$").asText();
                        }
                    }

                    if ("S".equals(responseStatus) && wipEntityName != null && !wipEntityName.isEmpty()) {
                        // String wipEntityName = jsonNode.get("WIP_ENTITY_NAME").asText();

                       /* if (basicDetails != null) {
                            basicDetails.setWorkOrderNumber(wipEntityName);
                            basicDetails.setWipEntityName(wipEntityName);
                            basicDetails.setWipEntityId(wipEntityId);
                            basicDetailsRepository.save(basicDetails);
                        }
                        if (additionalDetails != null) {
                            additionalDetails.setWorkOrderNumber(wipEntityName);
                            additionalDetailsRepository.save(additionalDetails);
                        }

                        if (operationList != null && !operationList.isEmpty()) {
                            Operation operation = operationList.getFirst();
                            operation.setWorkOrderNumber(wipEntityName);
                            operationRepository.save(operation);
                            for (OperationChild operationChild : operation.getOperationList()) {
                                operationChild.setWorkOrderNumber(wipEntityName);
                                operationChild.setWipEntityId(wipEntityId);
                                operationChildRepository.save(operationChild);
                            }
                        }

                        if (materialList != null && !materialList.isEmpty()) {
                            Material material = materialList.getFirst();
                            material.setWorkOrderNumber(wipEntityName);
                            materialRepository.save(material);
                            for (MaterialChild materialChild : material.getMaterialList()) {
                                materialChild.setWorkOrderNumber(wipEntityName);
                                materialChild.setWipEntityId(wipEntityId);
                                materialChildRepository.save(materialChild);
                            }
                        }
*/

                        if (basicDetails != null) {
                            basicDetailsRepository.delete(basicDetails);
                        }

                        if (additionalDetails != null) {
                            additionalDetailsRepository.delete(additionalDetails);
                        }

                        if (operationList != null && !operationList.isEmpty()) {
                            for (Operation operation : operationList) {
                                List<OperationChild> operationChildren = operation.getOperationList();
                                if (operationChildren != null && !operationChildren.isEmpty()) {
                                    operationChildRepository.deleteAll(operationChildren);
                                }
                                operationRepository.delete(operation);
                            }
                        }
                        if (materialList != null && !materialList.isEmpty()) {
                            for (Material material : materialList) {
                                List<MaterialChild> materialChildren = material.getMaterialList();
                                if (materialChildren != null && !materialChildren.isEmpty()) {
                                    materialChildRepository.deleteAll(materialChildren);
                                }
                                materialRepository.delete(material);
                            }
                        }

                        return ResponseEntity.ok(new ResponseModel<>(true, "WorkOrder Created Successfully !!", wipEntityName));

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
                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getMessage(), null));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel<>(false, "An unexpected error occurred: " + ex.getMessage(), null));
        }
    }


}
