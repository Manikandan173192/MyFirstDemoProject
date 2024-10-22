//package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;
//
//import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
//import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.CompleteWorkOrderOperationsApi;
//
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.HttpStatusCodeException;
//import org.springframework.web.client.RestTemplate;
//
//
//import java.util.Collections;
//
//
//@Service
//public class CompleteWorkOrderOperationsService {
//
//    private final String url = "http://10.36.213.83:9053/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";
//
//    public ResponseEntity<ResponseModel<String>> completeWorkOrderOperations(CompleteWorkOrderOperationsApi data) {
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//
//            // Setting up the request headers
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//            // Setting up the request body with MultiValueMap
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            body.add("P_BO_IDENTIFIER", "EAM");
//            body.add("P_API_VERSION_NUMBER", 1);
//            body.add("P_INIT_MSG_LIST", "1");
//            body.add("P_COMMIT", "Y");
//
//            // Add fields with null values or from the request object
//            body.add("P_EAM_WO_REC", null);
//            body.add("P_EAM_OP_TBL", null);
//            body.add("P_EAM_OP_NETWORK_TBL", null);
//            body.add("P_EAM_RES_TBL", null);
//            body.add("P_EAM_RES_INST_TBL", null);
//            body.add("P_EAM_SUB_RES_TBL", null);
//            body.add("P_EAM_RES_USAGE_TBL", null);
//            body.add("P_EAM_MAT_REQ_TBL", null);
//            body.add("P_EAM_DIRECT_ITEMS_TBL", null);
//            body.add("P_EAM_WO_COMP_REC", null);
//            body.add("P_EAM_WO_QUALITY_TBL", null);
//            body.add("P_EAM_METER_READING_TBL", null);
//            body.add("P_EAM_COUNTER_PROP_TBL", null);
//            body.add("P_EAM_WO_COMP_REBUILD_TBL", null);
//            body.add("P_EAM_WO_COMP_MR_READ_TBL", null);
//
//            // Add the P_EAM_OP_COMP_TBL field with nested structure
//            MultiValueMap<String, Object> opCompTblItem = new LinkedMultiValueMap<>();
//            opCompTblItem.add("HEADER_ID", data.getWipEntityId());
//            opCompTblItem.add("BATCH_ID", 1);
//            opCompTblItem.add("ROW_ID", null);
//            opCompTblItem.add("TRANSACTION_ID", null);
//            opCompTblItem.add("TRANSACTION_DATE", "formattedDate"); // Replace with actual formatted date if needed
//            opCompTblItem.add("WIP_ENTITY_ID", data.getWipEntityId());
//            opCompTblItem.add("ORGANIZATION_ID", data.getOrganizationId());
//            opCompTblItem.add("OPERATION_SEQ_NUM", data.getOperationsSeqNum());
//            opCompTblItem.add("DEPARTMENT_ID", null);
//            opCompTblItem.add("REFERENCE", data.getReference());
//            opCompTblItem.add("RECONCILIATION_CODE", null);
//            opCompTblItem.add("ACCT_PERIOD_ID", null);
//            opCompTblItem.add("QA_COLLECTION_ID", null);
//            opCompTblItem.add("ACTUAL_START_DATE", data.getActualStartDate());
//            opCompTblItem.add("ACTUAL_END_DATE", data.getActualEndDate());
//            opCompTblItem.add("ACTUAL_DURATION", data.getActualDuration());
//            opCompTblItem.add("SHUTDOWN_START_DATE", data.getShutdownStartDate());
//            opCompTblItem.add("SHUTDOWN_END_DATE", data.getShutdownEndDate());
//            opCompTblItem.add("SHUTDOWN_END_DATE", data.getShutdownEndDate());
//            opCompTblItem.add("HANDOVER_OPERATION_SEQ_NUM", null);
//            opCompTblItem.add("REASON_ID", null);
//            opCompTblItem.add("VENDOR_CONTACT_ID", null);
//            opCompTblItem.add("VENDOR_ID", null);
//            opCompTblItem.add("VENDOR_SITE_ID", null);
//            opCompTblItem.add("TRANSACTION_REFERENCE", data.getReference());
//            opCompTblItem.add("ATTRIBUTE_CATEGORY", null);
//            opCompTblItem.add("ATTRIBUTE1", null);
//            opCompTblItem.add("ATTRIBUTE2", null);
//            opCompTblItem.add("ATTRIBUTE3", null);
//            opCompTblItem.add("ATTRIBUTE4", null);
//            opCompTblItem.add("ATTRIBUTE5", null);
//            opCompTblItem.add("ATTRIBUTE6", null);
//            opCompTblItem.add("ATTRIBUTE7", null);
//            opCompTblItem.add("ATTRIBUTE8", null);
//            opCompTblItem.add("ATTRIBUTE9", null);
//            opCompTblItem.add("ATTRIBUTE10", null);
//            opCompTblItem.add("ATTRIBUTE11", null);
//            opCompTblItem.add("ATTRIBUTE12", null);
//            opCompTblItem.add("ATTRIBUTE13", null);
//            opCompTblItem.add("ATTRIBUTE14", null);
//            opCompTblItem.add("ATTRIBUTE15", null);
//            opCompTblItem.add("REQUEST_ID", null);
//            opCompTblItem.add("PROGRAM_UPDATE_DATE", null);
//            opCompTblItem.add("PROGRAM_APPLICATION_ID", null);
//            opCompTblItem.add("PROGRAM_ID", null);
//            opCompTblItem.add("RETURN_STATUS", null);
//            opCompTblItem.add("TRANSACTION_TYPE", 4);
//
//            MultiValueMap<String, Object> opCompTbl = new LinkedMultiValueMap<>();
//            opCompTbl.add("P_EAM_OP_COMP_TBL_ITEM", Collections.singletonList(opCompTblItem));
//
//            body.add("P_EAM_OP_COMP_TBL", opCompTbl);
//
//            body.add("P_EAM_REQUEST_TBL", null);
//            body.add("P_EAM_PERMIT_TBL", null);
//            body.add("P_EAM_PERMIT_WO_ASSOC_TBL", null);
//            body.add("P_EAM_WORK_CLEARANCE_TBL", null);
//            body.add("P_EAM_WC_WO_ASSOC_TBL", null);
//            body.add("P_DEBUG", "N");
//            body.add("P_OUTPUT_DIR", "/usr/tmp");
//            body.add("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
//            body.add("P_DEBUG_FILE_MODE", "w");
//
//            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
//            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//            // Print the response
//            String response = responseEntity.getBody();
//            System.out.println("Response: " + response);
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new ResponseModel<>(true, "Response printed successfully.", response));
//
//        } catch (HttpStatusCodeException ex) {
//            System.out.println("HTTP Error: " + ex.getStatusCode().toString());
//            return ResponseEntity.status(ex.getStatusCode())
//                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getStatusCode().toString(), null));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseModel<>(false, "Unexpected error occurred.", null));
//        }
//    }
//}
//
//
//newwwwwwwwwwwwwwwwwwwwwwwwwwwwww

//package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
//import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.CompleteWorkOrderOperationsApi;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpStatusCodeException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class CompleteWorkOrderOperationsService {
//
////    private final String url = "http://10.36.213.83:9053/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";
//
//    public ResponseEntity<ResponseModel<String>> completeWorkOrderOperations(CompleteWorkOrderOperationsApi data) {
//        try {
//            String url = "http://10.36.213.83:9053/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";
//            RestTemplate restTemplate = new RestTemplate();
//            ObjectMapper objectMapper = new ObjectMapper();
//
//            // Setting up the request headers
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//            // Setting up the request body with HashMap
//            Map<String, Object> body = new HashMap<>();
//            body.put("P_BO_IDENTIFIER", "EAM");
//            body.put("P_API_VERSION_NUMBER", 1);
//            body.put("P_INIT_MSG_LIST", "1");
//            body.put("P_COMMIT", "Y");
//
//            // Add fields with null values or from the request object
//            body.put("P_EAM_WO_REC", null);
//            body.put("P_EAM_OP_TBL", null);
//            body.put("P_EAM_OP_NETWORK_TBL", null);
//            body.put("P_EAM_RES_TBL", null);
//            body.put("P_EAM_RES_INST_TBL", null);
//            body.put("P_EAM_SUB_RES_TBL", null);
//            body.put("P_EAM_RES_USAGE_TBL", null);
//            body.put("P_EAM_MAT_REQ_TBL", null);
//            body.put("P_EAM_DIRECT_ITEMS_TBL", null);
//            body.put("P_EAM_WO_COMP_REC", null);
//            body.put("P_EAM_WO_QUALITY_TBL", null);
//            body.put("P_EAM_METER_READING_TBL", null);
//            body.put("P_EAM_COUNTER_PROP_TBL", null);
//            body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
//            body.put("P_EAM_WO_COMP_MR_READ_TBL", null);
//
//            // Add the P_EAM_OP_COMP_TBL field with nested structure
//            Map<String, Object> opCompTblItem = new HashMap<>();
//            opCompTblItem.put("HEADER_ID", data.getWipEntityId());
//            opCompTblItem.put("BATCH_ID", 1);
//            opCompTblItem.put("ROW_ID", null);
//            opCompTblItem.put("TRANSACTION_ID", null);
//            opCompTblItem.put("TRANSACTION_DATE", data.getActualStartDate()); // Replace with actual formatted date if needed
//            opCompTblItem.put("WIP_ENTITY_ID", data.getWipEntityId());
//            opCompTblItem.put("ORGANIZATION_ID", data.getOrganizationId());
//            opCompTblItem.put("OPERATION_SEQ_NUM", data.getOperationsSeqNum());
//            opCompTblItem.put("DEPARTMENT_ID", null);
//            opCompTblItem.put("REFERENCE", data.getReference());
//            opCompTblItem.put("RECONCILIATION_CODE", null);
//            opCompTblItem.put("ACCT_PERIOD_ID", null);
//            opCompTblItem.put("QA_COLLECTION_ID", null);
//            opCompTblItem.put("ACTUAL_START_DATE", data.getActualStartDate());
//            opCompTblItem.put("ACTUAL_END_DATE", data.getActualEndDate());
//            opCompTblItem.put("ACTUAL_DURATION", data.getActualDuration());
//            opCompTblItem.put("SHUTDOWN_START_DATE", data.getShutdownStartDate());
//            opCompTblItem.put("SHUTDOWN_END_DATE", data.getShutdownEndDate());
//            opCompTblItem.put("HANDOVER_OPERATION_SEQ_NUM", null);
//            opCompTblItem.put("REASON_ID", null);
//            opCompTblItem.put("VENDOR_CONTACT_ID", null);
//            opCompTblItem.put("VENDOR_ID", null);
//            opCompTblItem.put("VENDOR_SITE_ID", null);
//            opCompTblItem.put("TRANSACTION_REFERENCE", data.getReference());
//            opCompTblItem.put("ATTRIBUTE_CATEGORY", null);
//            opCompTblItem.put("ATTRIBUTE1", null);
//            opCompTblItem.put("ATTRIBUTE2", null);
//            opCompTblItem.put("ATTRIBUTE3", null);
//            opCompTblItem.put("ATTRIBUTE4", null);
//            opCompTblItem.put("ATTRIBUTE5", null);
//            opCompTblItem.put("ATTRIBUTE6", null);
//            opCompTblItem.put("ATTRIBUTE7", null);
//            opCompTblItem.put("ATTRIBUTE8", null);
//            opCompTblItem.put("ATTRIBUTE9", null);
//            opCompTblItem.put("ATTRIBUTE10", null);
//            opCompTblItem.put("ATTRIBUTE11", null);
//            opCompTblItem.put("ATTRIBUTE12", null);
//            opCompTblItem.put("ATTRIBUTE13", null);
//            opCompTblItem.put("ATTRIBUTE14", null);
//            opCompTblItem.put("ATTRIBUTE15", null);
//            opCompTblItem.put("REQUEST_ID", null);
//            opCompTblItem.put("PROGRAM_UPDATE_DATE", null);
//            opCompTblItem.put("PROGRAM_APPLICATION_ID", null);
//            opCompTblItem.put("PROGRAM_ID", null);
//            opCompTblItem.put("RETURN_STATUS", null);
//            opCompTblItem.put("TRANSACTION_TYPE", 4);
//
//            body.put("P_EAM_OP_COMP_TBL_ITEM", Collections.singletonList(opCompTblItem));
//            body.put("P_EAM_OP_COMP_TBL", Collections.singletonList(opCompTblItem));
//
//            body.put("P_EAM_REQUEST_TBL", null);
//            body.put("P_EAM_PERMIT_TBL", null);
//            body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
//            body.put("P_EAM_WORK_CLEARANCE_TBL", null);
//            body.put("P_EAM_WC_WO_ASSOC_TBL", null);
//            body.put("P_DEBUG", "N");
//            body.put("P_OUTPUT_DIR", "/usr/tmp");
//            body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
//            body.put("P_DEBUG_FILE_MODE", "w");
//
//            // Convert body to JSON
//            String jsonBody = objectMapper.writeValueAsString(body);
//
//            System.out.println(jsonBody);
//
//            HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body, headers);
//
//            System.out.println("Entity entry = "+entity);
//            // Make the HTTP request
//            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
//
//            System.out.println("ResponseEntity = "+responseEntity);
//            // Print the response
//            String response = responseEntity.getBody();
//            System.out.println("Response: " + response);
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(new ResponseModel<>(true, "Response printed successfully.", response));
//
//        } catch (HttpStatusCodeException ex) {
//            System.out.println("HTTP Error: " + ex.getStatusCode().toString());
//            return ResponseEntity.status(ex.getStatusCode())
//                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getStatusCode().toString(), null));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseModel<>(false, "Unexpected error occurred.", null));
//        }
//    }
////}

//shivaa brooo
/*package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.CompleteWorkOrderOperationsApi;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CompleteWorkOrderOperationsService {

//    private final String url = "http://10.36.213.83:9053/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

    public ResponseEntity<ResponseModel<String>> completeWorkOrderOperations(CompleteWorkOrderOperationsApi data) {
        try {

            String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";
            RestTemplate restTemplate = new RestTemplate();

            // Setting up the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            // Setting up the request body with additional fields and null values
            Map<String, Object> body = new HashMap<>();
            body.put("P_BO_IDENTIFIER", "EAM");
            body.put("P_API_VERSION_NUMBER", 1);
            body.put("P_INIT_MSG_LIST", "1");
            body.put("P_COMMIT", "Y");

            // Add fields from the request object or null
            body.put("P_EAM_WO_REC", null);
            body.put("P_EAM_OP_TBL", null);
            body.put("P_EAM_OP_NETWORK_TBL", null);
            body.put("P_EAM_RES_TBL", null);
            body.put("P_EAM_RES_INST_TBL", null);
            body.put("P_EAM_SUB_RES_TBL", null);
            body.put("P_EAM_RES_USAGE_TBL", null);
            body.put("P_EAM_MAT_REQ_TBL", null);
            body.put("P_EAM_DIRECT_ITEMS_TBL", null);
            body.put("P_EAM_WO_COMP_REC", null);
            body.put("P_EAM_WO_QUALITY_TBL", null);
            body.put("P_EAM_METER_READING_TBL", null);
            body.put("P_EAM_COUNTER_PROP_TBL", null);
            body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
            body.put("P_EAM_WO_COMP_MR_READ_TBL", null);

            // Add the P_EAM_OP_COMP_TBL field with nested structure
            Map<String, Object> P_EAM_WO_COMP_REC = new HashMap<>();
            P_EAM_WO_COMP_REC.put("HEADER_ID", data.getWipEntityId());
            P_EAM_WO_COMP_REC.put("BATCH_ID", 1);
            P_EAM_WO_COMP_REC.put("ROW_ID", null);
            P_EAM_WO_COMP_REC.put("TRANSACTION_ID", null);
            P_EAM_WO_COMP_REC.put("TRANSACTION_DATE", data.getActualStartDate()); // No formatting
            P_EAM_WO_COMP_REC.put("WIP_ENTITY_ID", data.getWipEntityId());
            P_EAM_WO_COMP_REC.put("ORGANIZATION_ID", data.getOrganizationId());
            P_EAM_WO_COMP_REC.put("OPERATION_SEQ_NUM", data.getOperationsSeqNum());
            P_EAM_WO_COMP_REC.put("DEPARTMENT_ID", null);
            P_EAM_WO_COMP_REC.put("REFERENCE", data.getReference());
            P_EAM_WO_COMP_REC.put("RECONCILIATION_CODE", null);
            P_EAM_WO_COMP_REC.put("ACCT_PERIOD_ID", null);
            P_EAM_WO_COMP_REC.put("QA_COLLECTION_ID", null);
            P_EAM_WO_COMP_REC.put("ACTUAL_START_DATE", data.getActualStartDate());
            P_EAM_WO_COMP_REC.put("ACTUAL_END_DATE", data.getActualEndDate());
            P_EAM_WO_COMP_REC.put("ACTUAL_DURATION", data.getActualDuration());
            P_EAM_WO_COMP_REC.put("SHUTDOWN_START_DATE", data.getShutdownStartDate());
            P_EAM_WO_COMP_REC.put("SHUTDOWN_END_DATE", data.getShutdownEndDate());
            P_EAM_WO_COMP_REC.put("HANDOVER_OPERATION_SEQ_NUM", null);
            P_EAM_WO_COMP_REC.put("REASON_ID", null);
            P_EAM_WO_COMP_REC.put("VENDOR_CONTACT_ID", null);
            P_EAM_WO_COMP_REC.put("VENDOR_ID", null);
            P_EAM_WO_COMP_REC.put("VENDOR_SITE_ID", null);
            P_EAM_WO_COMP_REC.put("TRANSACTION_REFERENCE", data.getReference());
            P_EAM_WO_COMP_REC.put("ATTRIBUTE_CATEGORY", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE1", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE2", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE3", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE4", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE5", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE6", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE7", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE8", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE9", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE10", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE11", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE12", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE13", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE14", null);
            P_EAM_WO_COMP_REC.put("ATTRIBUTE15", null);
            P_EAM_WO_COMP_REC.put("REQUEST_ID", null);
            P_EAM_WO_COMP_REC.put("PROGRAM_UPDATE_DATE", null);
            P_EAM_WO_COMP_REC.put("PROGRAM_APPLICATION_ID", null);
            P_EAM_WO_COMP_REC.put("PROGRAM_ID", null);
            P_EAM_WO_COMP_REC.put("RETURN_STATUS", null);
            P_EAM_WO_COMP_REC.put("TRANSACTION_TYPE", 4);
            P_EAM_WO_COMP_REC.put("EAM_FAILURE_ENTRY_RECORD", null);

            Map<String, Object> opCompTbl = new HashMap<>();
            opCompTbl.put("P_EAM_OP_COMP_TBL_ITEM", Collections.singletonList(P_EAM_WO_COMP_REC));

            body.put("P_EAM_OP_COMP_TBL", opCompTbl);

            body.put("P_EAM_REQUEST_TBL", null);
            body.put("P_EAM_PERMIT_TBL", null);
            body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
            body.put("P_EAM_WORK_CLEARANCE_TBL", null);
            body.put("P_EAM_WC_WO_ASSOC_TBL", null);
            body.put("P_DEBUG", "N");
            body.put("P_OUTPUT_DIR", "/usr/tmp");
            body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
            body.put("P_DEBUG_FILE_MODE", "w");

            // Convert the body to JSON format
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String jsonBody = objectMapper.writeValueAsString(body);

            // Log JSON body for debugging
            System.out.println("Request JSON Body: " + jsonBody);

            // Send the request
            HttpEntity<Map<String,Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            // Log and print the response
            System.out.println("ResponseEntity = " + responseEntity);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String response = responseEntity.getBody();

                if (response != null && !response.isEmpty()) {
                    JsonNode jsonNode = objectMapper.readTree(response);
                    String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();

                    if ("S".equals(responseStatus)) {
                        return ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseModel<>(true, "Successfully Created!.", null));
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
            System.out.println("HTTP Error: " + ex.getStatusCode().toString());
            return ResponseEntity.status(ex.getStatusCode())
                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getStatusCode().toString(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel<>(false, "Unexpected error occurred.", null));
        }
    }
}*/

package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.CompleteWorkOrderOperationsApi;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CompleteWorkOrderOperationsService {

    private final String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

    public ResponseEntity<ResponseModel<String>> completeWorkOrderOperations(CompleteWorkOrderOperationsApi data) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Setting up the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            // Create request body
            Map<String, Object> body = createRequestBody(data);

            // Convert the body to JSON format
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String jsonBody = objectMapper.writeValueAsString(body);

            // Log JSON body for debugging
            System.out.println("Request JSON Body: " + jsonBody);

            // Send the request
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            // Log and print the response
            System.out.println("ResponseEntity = " + responseEntity);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String response = responseEntity.getBody();

                if (response != null && !response.isEmpty()) {
                    JsonNode jsonNode = objectMapper.readTree(response);
                    String responseStatus = jsonNode.get("X_RETURN_STATUS").asText();

                    if ("S".equals(responseStatus)) {
                        return ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseModel<>(true, "Successfully Created!.", null));
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
            System.out.println("HTTP Error: " + ex.getStatusCode().toString());
            return ResponseEntity.status(ex.getStatusCode())
                    .body(new ResponseModel<>(false, "HTTP Error: " + ex.getStatusCode().toString(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel<>(false, "Unexpected error occurred.", null));
        }
    }

    private Map<String, Object> createRequestBody(CompleteWorkOrderOperationsApi data) {
        Map<String, Object> body = new HashMap<>();
        body.put("P_BO_IDENTIFIER", "EAM");
        body.put("P_API_VERSION_NUMBER", 1);
        body.put("P_INIT_MSG_LIST", "1");
        body.put("P_COMMIT", "Y");

        // Initialize all other fields as null
        body.put("P_EAM_WO_REC", null);
        body.put("P_EAM_OP_TBL", null);
        body.put("P_EAM_OP_NETWORK_TBL", null);
        body.put("P_EAM_RES_TBL", null);
        body.put("P_EAM_RES_INST_TBL", null);
        body.put("P_EAM_SUB_RES_TBL", null);
        body.put("P_EAM_RES_USAGE_TBL", null);
        body.put("P_EAM_MAT_REQ_TBL", null);
        body.put("P_EAM_DIRECT_ITEMS_TBL", null);
        body.put("P_EAM_WO_COMP_REC", null);
        body.put("P_EAM_WO_QUALITY_TBL", null);
        body.put("P_EAM_METER_READING_TBL", null);
        body.put("P_EAM_COUNTER_PROP_TBL", null);
        body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
        body.put("P_EAM_WO_COMP_MR_READ_TBL", null);

        // Add the P_EAM_OP_COMP_TBL field with nested structure
        Map<String, Object> opCompTblItem = new HashMap<>();
        opCompTblItem.put("HEADER_ID", data.getWipEntityId() != null ? Long.parseLong(data.getWipEntityId()) : null); // Ensure it's a Long
        opCompTblItem.put("BATCH_ID", 1);
        opCompTblItem.put("ROW_ID", null);
        opCompTblItem.put("TRANSACTION_ID", null);
        opCompTblItem.put("TRANSACTION_DATE", "2024-08-29"/*data.getActualStartDate()*/); // No formatting
        opCompTblItem.put("WIP_ENTITY_ID", data.getWipEntityId());
        opCompTblItem.put("ORGANIZATION_ID", data.getOrganizationId());
        opCompTblItem.put("OPERATION_SEQ_NUM", data.getOperationsSeqNum());
        opCompTblItem.put("DEPARTMENT_ID", null);
        opCompTblItem.put("REFERENCE", data.getReference());
        opCompTblItem.put("RECONCILIATION_CODE", null);
        opCompTblItem.put("ACCT_PERIOD_ID", null);
        opCompTblItem.put("QA_COLLECTION_ID", null);
        opCompTblItem.put("ACTUAL_START_DATE", data.getActualStartDate());
        opCompTblItem.put("ACTUAL_END_DATE", data.getActualEndDate());
        opCompTblItem.put("ACTUAL_DURATION", data.getActualDuration());
        opCompTblItem.put("SHUTDOWN_START_DATE", data.getShutdownStartDate());
        opCompTblItem.put("SHUTDOWN_END_DATE", data.getShutdownEndDate());
        opCompTblItem.put("HANDOVER_OPERATION_SEQ_NUM", null);
        opCompTblItem.put("REASON_ID", null);
        opCompTblItem.put("VENDOR_CONTACT_ID", null);
        opCompTblItem.put("VENDOR_ID", null);
        opCompTblItem.put("VENDOR_SITE_ID", null);
        opCompTblItem.put("TRANSACTION_REFERENCE", data.getReference());
        opCompTblItem.put("ATTRIBUTE_CATEGORY", null);
        opCompTblItem.put("ATTRIBUTE1", null);
        opCompTblItem.put("ATTRIBUTE2", null);
        opCompTblItem.put("ATTRIBUTE3", null);
        opCompTblItem.put("ATTRIBUTE4", null);
        opCompTblItem.put("ATTRIBUTE5", null);
        opCompTblItem.put("ATTRIBUTE6", null);
        opCompTblItem.put("ATTRIBUTE7", null);
        opCompTblItem.put("ATTRIBUTE8", null);
        opCompTblItem.put("ATTRIBUTE9", null);
        opCompTblItem.put("ATTRIBUTE10", null);
        opCompTblItem.put("ATTRIBUTE11", null);
        opCompTblItem.put("ATTRIBUTE12", null);
        opCompTblItem.put("ATTRIBUTE13", null);
        opCompTblItem.put("ATTRIBUTE14", null);
        opCompTblItem.put("ATTRIBUTE15", null);
        opCompTblItem.put("REQUEST_ID", null);
        opCompTblItem.put("PROGRAM_UPDATE_DATE", null);
        opCompTblItem.put("PROGRAM_APPLICATION_ID", null);
        opCompTblItem.put("PROGRAM_ID", null);
        opCompTblItem.put("RETURN_STATUS", null);
        opCompTblItem.put("TRANSACTION_TYPE", 4);

        Map<String, Object> opCompTbl = new HashMap<>();
        opCompTbl.put("P_EAM_OP_COMP_TBL_ITEM", Collections.singletonList(opCompTblItem));

        body.put("P_EAM_OP_COMP_TBL", opCompTbl);

        body.put("P_EAM_REQUEST_TBL", null);
        body.put("P_EAM_PERMIT_TBL", null);
        body.put("P_EAM_PERMIT_WO_ASSOC_TBL", null);
        body.put("P_EAM_WORK_CLEARANCE_TBL", null);
        body.put("P_EAM_WC_WO_ASSOC_TBL", null);
        body.put("P_DEBUG", "N");
        body.put("P_OUTPUT_DIR", "/usr/tmp");
        body.put("P_DEBUG_FILENAME", "EAM_WO_DEBUG.log");
        body.put("P_DEBUG_FILE_MODE", "w");

        return body;
    }
}