package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.*;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.Operation;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.OperationChild;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.OperationRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.OperationChildRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Specification.OperationSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperationService {
    @Autowired
    private OperationChildRepository operationChildRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public ResponseModel<List<Operation>> list(String workOrderNumber) {
        try {

            OperationSpecificationBuilder builder=new OperationSpecificationBuilder();
            if(workOrderNumber!=null)builder.with("workOrderNumber",":",workOrderNumber);

            List<Operation> results = operationRepository.findAll(builder.build());
            return new ResponseModel<>(true, "Records found",results);

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }

    public ResponseModel<String> addOperation(AddOperationRequest addOperationRequest) {
        try {
            List<Operation> operationOneList = operationRepository.findByWorkOrderNumber(addOperationRequest.getWorkOrderNumber());
            var actionApi = 0;
            if(!operationOneList.isEmpty()) {
                operationOneList.getFirst().setWorkOrderNumber(addOperationRequest.getWorkOrderNumber());
                operationRepository.save(operationOneList.getFirst());

                var addedTableList = operationChildRepository.findByWorkOrderNumber(operationOneList.getFirst().getWorkOrderNumber());
                if (!addedTableList.isEmpty()) {
                    for (OperationChild items : addedTableList) {
                        if (items.getWorkOrderNumber().equals(operationOneList.getFirst().getWorkOrderNumber())) {
                            operationChildRepository.deleteById(items.getId());
                        }
                    }
                }
                for (OperationChild items : addOperationRequest.getOperationList()) {
                    var operationTwo = new OperationChild();

                    operationTwo.setWorkOrderNumber(items.getWorkOrderNumber());
                    operationTwo.setDepartmentId(items.getDepartmentId());
                    operationTwo.setDepartmentName(items.getDepartmentName());
                    operationTwo.setOrganizationId(items.getOrganizationId());
                    operationTwo.setWipEntityId(items.getWipEntityId());
                    operationTwo.setOperationSequence(items.getOperationSequence());
                    operationTwo.setDescription(items.getDescription());
                    operationTwo.setResourceSequence(items.getResourceSequence());
                    operationTwo.setResourceId(items.getResourceId());
                    operationTwo.setResourceCode(items.getResourceCode());
                    operationTwo.setUsage(items.getUsage());
                    operationTwo.setAssignedUnits(items.getAssignedUnits());
                    operationTwo.setInstanceId(items.getInstanceId());
                    operationTwo.setInstanceName(items.getInstanceName());
                    operationTwo.setStartDate(items.getStartDate());
                    operationTwo.setCompletionDate(items.getCompletionDate());
                    operationTwo.setDuration(items.getDuration());
                    operationTwo.setCreatedOn(LocalDateTime.now());
                    operationTwo.setOperationsTransactionType(items.getOperationsTransactionType());
                    operationTwo.setResourcesTransactionType(items.getResourcesTransactionType());
                    operationTwo.setInstancesTransactionType(items.getInstancesTransactionType());

                    operationChildRepository.save(operationTwo);

                }
                actionApi = 2;
            }else {
                var operationOne = new Operation();
                    operationOne.setWorkOrderNumber(addOperationRequest.getWorkOrderNumber());
                    operationOne.setOperationList(addOperationRequest.getOperationList());
                operationRepository.save(operationOne);

//                List<OperationOne> operationOneList1 = operationOneRepository.findAll();

                for (OperationChild items : addOperationRequest.getOperationList()) {
                    var operationTwo = new OperationChild();

                    operationTwo.setWorkOrderNumber(addOperationRequest.getWorkOrderNumber());
                    operationTwo.setDepartmentId(items.getDepartmentId());
                    operationTwo.setDepartmentName(items.getDepartmentName());
                    operationTwo.setOrganizationId(items.getOrganizationId());
                    operationTwo.setWipEntityId(items.getWipEntityId());
                    operationTwo.setOperationSequence(items.getOperationSequence());
                    operationTwo.setDescription(items.getDescription());
                    operationTwo.setResourceSequence(items.getResourceSequence());
                    operationTwo.setResourceId(items.getResourceId());
                    operationTwo.setResourceCode(items.getResourceCode());
                    operationTwo.setUsage(items.getUsage());
                    operationTwo.setAssignedUnits(items.getAssignedUnits());
                    operationTwo.setInstanceId(items.getInstanceId());
                    operationTwo.setInstanceName(items.getInstanceName());
                    operationTwo.setStartDate(items.getStartDate());
                    operationTwo.setCompletionDate(items.getCompletionDate());
                    operationTwo.setDuration(items.getDuration());
                    operationTwo.setCreatedOn(LocalDateTime.now());
                    operationTwo.setOperationsTransactionType(items.getOperationsTransactionType());
                    operationTwo.setResourcesTransactionType(items.getResourcesTransactionType());
                    operationTwo.setInstancesTransactionType(items.getInstancesTransactionType());

                    operationChildRepository.save(operationTwo);

                }
                actionApi = 1;
            }

            return new ResponseModel<>(true, actionApi == 1?"Operation Added Successfully":"Operation updated successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

//    public ResponseModel<List<OperationsResourceInstanceRequest>> listOperationsResourceInstances(String organizationId, String departmentId) {
//        String sql = "SELECT * FROM APPS.XXHIL_EAM_DEPT_RES_MAP_V WHERE ORGANIZATION_ID = ? AND DEPARTMENT_ID = ?";
//
//        System.out.println("Executing SQL: " + sql);
//        System.out.println("Parameters: organizationId=" + organizationId + ", departmentId=" + departmentId);
//
//        List<OperationsResourceInstanceRequest> results = jdbcTemplate.query(sql, new Object[]{organizationId, departmentId},
//                (rs, rowNum) -> {
//                    System.out.println("Called results = ");
//                    OperationsResourceInstanceRequest instance = new OperationsResourceInstanceRequest();
//                    instance.setOrganizationId(rs.getString("ORGANIZATION_ID"));
//                    instance.setOrganizationCode(rs.getString("ORGANIZATION_CODE"));
//                    instance.setDepartmentId(rs.getString("DEPARTMENT_ID"));
//                    instance.setDepartmentCode(rs.getString("DEPARTMENT_CODE"));
//                    instance.setDepartmentDescription(rs.getString("DEPARTMENT_DESCRIPTION"));
//                    instance.setResourceId(rs.getString("RESOURCE_ID"));
//                    instance.setResourceCode(rs.getString("RESOURCE_CODE"));
//                    instance.setCapacityUnits(rs.getString("CAPACITY_UNITS"));
//                    instance.setShareFromDepartment(rs.getString("SHARE_FROM_DEPARTMENT"));
//                    instance.setInstanceId(rs.getString("INSTANCE_ID"));
//                    instance.setOracleHrmsEmpNumber(rs.getString("ORACLE_HRMS_EMP_NUMBER"));
//                    instance.setClmsEmpNumber(rs.getString("CLMS_EMP_NUMBER"));
//                    instance.setFullName(rs.getString("FULL_NAME"));
//                    instance.setEmployeeOrContingent(rs.getString("EMPLOYEE_OR_CONTINGENT"));
//                    instance.setContractorFirmName(rs.getString("CONTRACTOR_FIRM_NAME"));
//                    instance.setSkill(rs.getString("SKILL"));
//                    instance.setSkillSpecialization1(rs.getString("SKILL_SPECIALIZATION1"));
//                    instance.setSkillSpecialization2(rs.getString("SKILL_SPECIALIZATION2"));
//                    instance.setSkillSpecialization3(rs.getString("SKILL_SPECIALIZATION3"));
//                    instance.setSkillSpecialization4(rs.getString("SKILL_SPECIALIZATION4"));
//                    instance.setHeightPassAvailable(rs.getString("HEIGHT_PASS_AVAILABLE"));
//                    instance.setHeightPassExpiryDate(String.valueOf(rs.getDate("HEIGHT_PASS_EXPIRY_DATE")));
//                    instance.setSpecialPassLicense1(rs.getString("SPECIAL_PASS_LICENSE1"));
//                    instance.setSpecialPassLicense1ExpDate(String.valueOf(rs.getDate("SPECIAL_PASS_LICENSE1_EXP_DATE")));
//                    instance.setSpecialPassLicense2(rs.getString("SPECIAL_PASS_LICENSE2"));
//                    instance.setSpecialPassLicense2ExpDate(String.valueOf(rs.getDate("SPECIAL_PASS_LICENSE2_EXP_DATE")));
//                    instance.setSpecialPassLicense3(rs.getString("SPECIAL_PASS_LICENSE3"));
//                    instance.setSpecialPassLicense3ExpDate(String.valueOf(rs.getDate("SPECIAL_PASS_LICENSE3_EXP_DATE")));
//                    instance.setEffectiveStartDate(String.valueOf(rs.getDate("EFFECTIVE_START_DATE")));
//                    instance.setEffectiveEndDate(String.valueOf(rs.getDate("EFFECTIVE_END_DATE")));
//                    instance.setEamDepartmentCode(rs.getString("EAM_DEPARTMENT_CODE"));
//                    instance.setEamDepartmentOptional1(rs.getString("EAM_DEPARTMENT_OPTIONAL1"));
//                    instance.setEamDepartmentOptional2(rs.getString("EAM_DEPARTMENT_OPTIONAL2"));
//                    instance.setEamDepartmentOptional3(rs.getString("EAM_DEPARTMENT_OPTIONAL3"));
//                    System.out.println("InStance = "+instance);
//                    return instance;
//
//                }
//        );
//
//        System.out.println("Number of records retrieved: " + results.size());
//
//        return new ResponseModel<>(true, "Success", results);
//    }

    /*public ResponseModel<List<OperationsResourceInstanceRequest>> listOperationsResourceInstances(String organizationId, String departmentId) {
        String sql = "SELECT ORGANIZATION_ID, ORGANIZATION_CODE, DEPARTMENT_ID, DEPARTMENT_CODE, DEPARTMENT_DESCRIPTION, " +
                "RESOURCE_ID, RESOURCE_CODE, INSTANCE_ID, FULL_NAME " +
                "FROM APPS.XXHIL_EAM_DEPT_RES_MAP_V WHERE ORGANIZATION_ID = ? AND DEPARTMENT_ID = ?" +
                "ORDER BY RESOURCE_ID DESC " +
                "FETCH FIRST 100 ROWS ONLY";;

        System.out.println("Executing SQL: " + sql);
        System.out.println("Parameters: organizationId=" + organizationId + ", departmentId=" + departmentId);

        List<OperationsResourceInstanceRequest> results = jdbcTemplate.query(sql, new Object[]{organizationId, departmentId},
                (rs, rowNum) -> {
                    OperationsResourceInstanceRequest instance = new OperationsResourceInstanceRequest();
                    instance.setOrganizationId(rs.getString("ORGANIZATION_ID"));
                    instance.setOrganizationCode(rs.getString("ORGANIZATION_CODE"));
                    instance.setDepartmentId(rs.getString("DEPARTMENT_ID"));
                    instance.setDepartmentCode(rs.getString("DEPARTMENT_CODE"));
                    instance.setDepartmentDescription(rs.getString("DEPARTMENT_DESCRIPTION"));
                    instance.setResourceId(rs.getString("RESOURCE_ID"));
                    instance.setResourceCode(rs.getString("RESOURCE_CODE"));
                    instance.setInstanceId(rs.getString("INSTANCE_ID"));
                    instance.setFullName(rs.getString("FULL_NAME"));
                    return instance;
                }
        );

        if (results.isEmpty()) {
            System.out.println("No records found for organizationId: " + organizationId + " and departmentId: " + departmentId);
            return new ResponseModel<>(false, "No records found for the given organizationId and departmentId", null);
        }

        return new ResponseModel<>(true, "Success", results);
    }*/

    //  MY code
    public ResponseModel<List<OperationsResourceInstanceRequest>> listOperationsResourceInstances(String organizationId, String departmentId) {
        // SQL query with LIKE operator for partial matching and ORDER BY with LIMIT clause
        String sql = "SELECT ORGANIZATION_ID, ORGANIZATION_CODE, DEPARTMENT_ID, DEPARTMENT_CODE, DEPARTMENT_DESCRIPTION, " +
                "RESOURCE_ID, RESOURCE_CODE, INSTANCE_ID, FULL_NAME " +
                "FROM APPS.XXHIL_EAM_DEPT_RES_MAP_V " +
                "WHERE ORGANIZATION_ID LIKE ? AND DEPARTMENT_ID LIKE ? " +
                "ORDER BY RESOURCE_ID DESC " +
                "FETCH FIRST 100 ROWS ONLY";

        // Prepare parameters with wildcard for partial matching
        String organizationIdPattern = "%" + organizationId + "%";
        String departmentIdPattern = "%" + departmentId + "%";

        System.out.println("Executing SQL: " + sql);
        System.out.println("Parameters: organizationId=" + organizationIdPattern + ", departmentId=" + departmentIdPattern);

        List<OperationsResourceInstanceRequest> results = jdbcTemplate.query(sql, new Object[]{organizationIdPattern, departmentIdPattern},
                (rs, rowNum) -> {
                    OperationsResourceInstanceRequest instance = new OperationsResourceInstanceRequest();
                    instance.setOrganizationId(rs.getString("ORGANIZATION_ID"));
                    instance.setOrganizationCode(rs.getString("ORGANIZATION_CODE"));
                    instance.setDepartmentId(rs.getString("DEPARTMENT_ID"));
                    instance.setDepartmentCode(rs.getString("DEPARTMENT_CODE"));
                    instance.setDepartmentDescription(rs.getString("DEPARTMENT_DESCRIPTION"));
                    instance.setResourceId(rs.getString("RESOURCE_ID"));
                    instance.setResourceCode(rs.getString("RESOURCE_CODE"));
                    instance.setInstanceId(rs.getString("INSTANCE_ID"));
                    instance.setFullName(rs.getString("FULL_NAME"));
                    return instance;
                }
        );

        if (results.isEmpty()) {
            System.out.println("No records found for organizationId containing: " + organizationId + " and departmentId containing: " + departmentId);
            return new ResponseModel<>(false, "No records found for the given organizationId and departmentId", null);
        }

        return new ResponseModel<>(true, "Success", results);
    }






    public ResponseModel<List<OperationsResourceDDRequest>> listOperationsResourceDD(String organizationId, String departmentId) {
        String sql = "SELECT * FROM APPS.XXHIL_EAM_DEPT_RES_MAP_V WHERE ORGANIZATION_ID = ? AND DEPARTMENT_ID = ?";

        System.out.println("Executing SQL: " + sql);
        System.out.println("Parameters: organizationId=" + organizationId + ", departmentId=" + departmentId);

        List<OperationsResourceDDRequest> results = jdbcTemplate.query(sql, new Object[]{organizationId, departmentId},
                (rs, rowNum) -> {
                    // Map only the required fields to the DTO
                    OperationsResourceDDRequest instance = new OperationsResourceDDRequest();
                    instance.setFullName(rs.getString("FULL_NAME"));
                    instance.setResourceCode(rs.getString("RESOURCE_CODE"));
                    return instance;
                }
        );

        System.out.println("Number of records retrieved: " + results.size());

        return new ResponseModel<>(true, "Success", results);
    }


//    public ResponseModel<List<WorkOrderOperationsResourcesRequest>> listWorkOrderOperationsResources(Integer wipEntityId) {
//        // SQL query to fetch all columns from the external table
//        String sql = "SELECT * FROM APPS.WIP_OPERATION_RESOURCES_V WHERE WIP_ENTITY_ID = ?";
//
//        System.out.println("Executing SQL: " + sql);
//        System.out.println("Parameter: wipEntityId=" + wipEntityId);
//
//        try {
//            // Execute the query and map each row to a DTO
//            List<WorkOrderOperationsResourcesRequest> results = jdbcTemplate.query(sql, new Object[]{wipEntityId},
//                    (rs, rowNum) -> {
//                        WorkOrderOperationsResourcesRequest dto = new WorkOrderOperationsResourcesRequest();
//                        // Map all columns to the DTO
//                        dto.setWipEntityId(rs.getString("WIP_ENTITY_ID"));
//                        dto.setOperationSeqNum(rs.getString("OPERATION_SEQ_NUM"));
//                        dto.setResourceSeqNum(rs.getString("RESOURCE_SEQ_NUM"));
//                        dto.setOrganizationId(rs.getString("ORGANIZATION_ID"));
//                        dto.setLastUpdateDate(String.valueOf(rs.getDate("LAST_UPDATE_DATE")));
//                        dto.setLastUpdatedBy(rs.getString("LAST_UPDATED_BY"));
//                        dto.setCreationDate(String.valueOf(rs.getDate("CREATION_DATE")));
//                        dto.setCreatedBy(rs.getString("CREATED_BY"));
//                        dto.setLastUpdateLogin(rs.getString("LAST_UPDATE_LOGIN"));
//                        dto.setInstanceId(rs.getString("INSTANCE_ID"));
//                        dto.setSerialNumber(rs.getString("SERIAL_NUMBER"));
//                        dto.setResourceId(rs.getString("RESOURCE_ID"));
//                        dto.setResourceType(rs.getString("RESOURCE_TYPE"));
//                        dto.setInstanceName(rs.getString("INSTANCE_NAME"));
//                        dto.setDescription(rs.getString("DESCRIPTION"));
//                        dto.setEffectiveStartDate(String.valueOf(rs.getDate("EFFECTIVE_START_DATE")));
//                        dto.setEffectiveEndDate(String.valueOf(rs.getDate("EFFECTIVE_END_DATE")));
//                        dto.setStartDate(String.valueOf(rs.getDate("START_DATE")));
//                        dto.setCompletionDate(String.valueOf(rs.getDate("COMPLETION_DATE")));
//                        dto.setRowId(rs.getString("ROW_ID"));
//                        dto.setBatchId(rs.getString("BATCH_ID"));
//                        return dto;
//                    }
//            );
//
//            // Handle the response based on whether records were found
//            if (results.isEmpty()) {
//                return new ResponseModel<>(false, "No records found", null);
//            } else {
//                String totalCount = String.valueOf(results.size());
//                return new ResponseModel<>(true, totalCount + " Records found", results);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseModel<>(false, "Records not found", null);
//        }
//    }

    public ResponseModel<List<WorkOrderOperationsResourcesRequest>> listWorkOrderOperationsResources(String wipEntityId) {
        // Query to fetch all columns from the external table
        String sql = "SELECT * FROM APPS.WIP_OPERATION_RESOURCES_V WHERE WIP_ENTITY_ID = ?";

        System.out.println("Executing SQL: " + sql);
        System.out.println("Parameter: wipEntityId=" + wipEntityId);

        try {
            // Execute the query to fetch column names
            List<String> columnNames = jdbcTemplate.query(
                    "SELECT COLUMN_NAME FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = 'WIP_OPERATION_RESOURCES_V' AND OWNER = 'APPS'",
                    (rs, rowNum) -> rs.getString("COLUMN_NAME")
            );

            // Construct SQL query with column names
            StringBuilder sqlBuilder = new StringBuilder("SELECT ");
            sqlBuilder.append(String.join(", ", columnNames));
            sqlBuilder.append(" FROM APPS.WIP_OPERATION_RESOURCES_V WHERE WIP_ENTITY_ID = ?");

            String finalSql = sqlBuilder.toString();
            System.out.println("Final SQL: " + finalSql);

            // Execute the constructed query and map each row to a DTO
            List<WorkOrderOperationsResourcesRequest> results = jdbcTemplate.query(finalSql, new Object[]{wipEntityId},
                    (rs, rowNum) -> {
                        WorkOrderOperationsResourcesRequest dto = new WorkOrderOperationsResourcesRequest();
                        // Map all columns dynamically
                        for (String column : columnNames) {
                            switch (column) {
                                case "WIP_ENTITY_ID":
                                    dto.setWipEntityId(rs.getString(column));
                                    break;
                                case "OPERATION_SEQ_NUM":
                                    dto.setOperationSeqNum(rs.getString(column));
                                    break;
                                case "RESOURCE_SEQ_NUM":
                                    dto.setResourceSeqNum(rs.getString(column));
                                    break;
                                case "ORGANIZATION_ID":
                                    dto.setOrganizationId(rs.getString(column));
                                    break;
                                case "LAST_UPDATE_DATE":
                                    dto.setLastUpdateDate(rs.getDate(column) != null ? rs.getDate(column).toString() : null);
                                    break;
                                case "LAST_UPDATED_BY":
                                    dto.setLastUpdatedBy(rs.getString(column));
                                    break;
                                case "CREATION_DATE":
                                    dto.setCreationDate(rs.getDate(column) != null ? rs.getDate(column).toString() : null);
                                    break;
                                case "CREATED_BY":
                                    dto.setCreatedBy(rs.getString(column));
                                    break;
                                case "LAST_UPDATE_LOGIN":
                                    dto.setLastUpdateLogin(rs.getString(column));
                                    break;
                                case "INSTANCE_ID":
                                    dto.setInstanceId(rs.getString(column));
                                    break;
                                case "SERIAL_NUMBER":
                                    dto.setSerialNumber(rs.getString(column));
                                    break;
                                case "RESOURCE_ID":
                                    dto.setResourceId(rs.getString(column));
                                    break;
                                case "RESOURCE_TYPE":
                                    dto.setResourceType(rs.getString(column));
                                    break;
                                case "INSTANCE_NAME":
                                    dto.setInstanceName(rs.getString(column));
                                    break;
                                case "DESCRIPTION":
                                    dto.setDescription(rs.getString(column));
                                    break;
                                case "EFFECTIVE_START_DATE":
                                    dto.setEffectiveStartDate(rs.getDate(column) != null ? rs.getDate(column).toString() : null);
                                    break;
                                case "EFFECTIVE_END_DATE":
                                    dto.setEffectiveEndDate(rs.getDate(column) != null ? rs.getDate(column).toString() : null);
                                    break;
                                case "START_DATE":
                                    dto.setStartDate(rs.getDate(column) != null ? rs.getDate(column).toString() : null);
                                    break;
                                case "COMPLETION_DATE":
                                    dto.setCompletionDate(rs.getDate(column) != null ? rs.getDate(column).toString() : null);
                                    break;
                                case "ROW_ID":
                                    dto.setRowId(rs.getString(column));
                                    break;
                                case "BATCH_ID":
                                    dto.setBatchId(rs.getString(column));
                                    break;
                                default:
                                    // Handle unexpected columns
                                    System.out.println("Unexpected column: " + column);
                            }
                        }
                        return dto;
                    }
            );

            // Handle the response based on whether records were found
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                String totalCount = String.valueOf(results.size());
                return new ResponseModel<>(true, totalCount + " Records found", results);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel<>(false, "Records not found", null);
        }
    }



    public ResponseModel<List<GetOperationList>> listOperation(String wipEntityId) {

        String sql = "SELECT op.*, res.*, inst.* " +
                "FROM APPS.WIP_OPERATIONS_V op " +
                "LEFT JOIN APPS.WIP_OPERATION_RESOURCES_V res " +
                "    ON op.WIP_ENTITY_ID = res.WIP_ENTITY_ID  " +
                "    AND op.OPERATION_SEQ_NUM = res.OPERATION_SEQ_NUM " +
                "LEFT JOIN APPS.WIP_OP_RESOURCE_INSTANCES_V inst  " +
                "    ON op.WIP_ENTITY_ID = inst.WIP_ENTITY_ID  " +
                "    AND op.OPERATION_SEQ_NUM = inst.OPERATION_SEQ_NUM " +
                "WHERE op.WIP_ENTITY_ID = ? ";


        List<GetOperationList> results = jdbcTemplate.query(sql, new Object[]{wipEntityId},
                (rs, rowNum) -> {

                    GetOperationList operationList = new GetOperationList();
                    operationList.setWipEntityName(rs.getString("WIP_ENTITY_ID"));
                    operationList.setOperationSeqNum(rs.getString("OPERATION_SEQ_NUM"));
                    operationList.setOperationSequenceId(rs.getString("OPERATION_SEQUENCE_ID"));
                    operationList.setDepartment(rs.getString("DEPARTMENT_CODE"));
                    operationList.setDepartmentId(rs.getString("DEPARTMENT_ID"));
                    operationList.setDescription(rs.getString("DESCRIPTION"));
                    operationList.setStartDate(rs.getString("FIRST_UNIT_START_DATE"));
                    operationList.setCompletionDate(rs.getString("LAST_UNIT_COMPLETION_DATE"));
                    operationList.setResourceSequenceNum(rs.getString("RESOURCE_SEQ_NUM"));
                    operationList.setResourceId(rs.getString("RESOURCE_ID"));
                    operationList.setResourceCode(rs.getString("RESOURCE_CODE"));
                    operationList.setUsage(rs.getString("USAGE_RATE_OR_AMOUNT"));
                    operationList.setAssignedUnits(rs.getString("ASSIGNED_UNITS"));
                    operationList.setInstanceId(rs.getString("INSTANCE_ID"));
                    operationList.setInstanceName(rs.getString("INSTANCE_NAME"));


                    return operationList;
                }
        );

        System.out.println("Operation List = " + results.size());

        return new ResponseModel<>(true, "Success", results);
    }

}
