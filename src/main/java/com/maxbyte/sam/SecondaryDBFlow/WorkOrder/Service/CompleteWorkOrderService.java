package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.CompleteWorkOrderApi;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.CompleteWorkOrderOperationsApi;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CompleteWorkOrderService {

    private final String url = "http://10.36.113.75:9054/soa-infra/resources/PM/WOCreateNUpdate/WO_CREATE_SERVICE/WO_CREATE_RP";

    public ResponseEntity<ResponseModel<String>> completeWorkOrder(CompleteWorkOrderApi data) {
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
                                .body(new ResponseModel<>(true, "Successfully Completed Work Order.", null));
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

    private Map<String, Object> createRequestBody(CompleteWorkOrderApi data) {
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

        // Adding P_EAM_WO_COMP_REC field with nested structure
        Map<String, Object> woCompRec = new HashMap<>();
        woCompRec.put("HEADER_ID", data.getWipEntityId());
        woCompRec.put("BATCH_ID", 1);
        woCompRec.put("ROW_ID", null);
        woCompRec.put("TRANSACTION_ID", null);
        woCompRec.put("TRANSACTION_DATE", data.getCurDate());
        woCompRec.put("WIP_ENTITY_ID", data.getWipEntityId());
        woCompRec.put("USER_STATUS_ID", 4);
        woCompRec.put("WIP_ENTITY_NAME", null);
        woCompRec.put("ORGANIZATION_ID", data.getOrganizationId());
        woCompRec.put("PARENT_WIP_ENTITY_ID", null);
        woCompRec.put("REFERENCE", null);
        woCompRec.put("RECONCILIATION_CODE", null);
        woCompRec.put("ACCT_PERIOD_ID", null);
        woCompRec.put("QA_COLLECTION_ID", null);
        woCompRec.put("ACTUAL_START_DATE", data.getActualStartDate());
        woCompRec.put("ACTUAL_END_DATE", data.getActualEndDate());
        woCompRec.put("ACTUAL_DURATION", null);
        woCompRec.put("PRIMARY_ITEM_ID", null);
        woCompRec.put("ASSET_GROUP_ID", null);
        woCompRec.put("REBUILD_ITEM_ID", null);
        woCompRec.put("ASSET_NUMBER", null);
        woCompRec.put("REBUILD_SERIAL_NUMBER", null);
        woCompRec.put("MANUAL_REBUILD_FLAG", null);
        woCompRec.put("REBUILD_JOB", null);
        woCompRec.put("COMPLETION_SUBINVENTORY", null);
        woCompRec.put("COMPLETION_LOCATOR_ID", null);
        woCompRec.put("LOT_NUMBER", null);
        woCompRec.put("SHUTDOWN_START_DATE", data.getActualStartDate());
        woCompRec.put("SHUTDOWN_END_DATE", data.getActualEndDate());
        woCompRec.put("ATTRIBUTE_CATEGORY", null);
        woCompRec.put("ATTRIBUTE1", data.getRcaApplicable());
        woCompRec.put("ATTRIBUTE2", null);
        woCompRec.put("ATTRIBUTE3", null);
        woCompRec.put("ATTRIBUTE4", data.getCapaApplicable());
        woCompRec.put("ATTRIBUTE5", null);
        woCompRec.put("ATTRIBUTE6", null);
        woCompRec.put("ATTRIBUTE7", null);
        woCompRec.put("ATTRIBUTE8", null);
        woCompRec.put("ATTRIBUTE9", null);
        woCompRec.put("ATTRIBUTE10", null);
        woCompRec.put("ATTRIBUTE11", null);
        woCompRec.put("ATTRIBUTE12", null);
        woCompRec.put("ATTRIBUTE13", null);
        woCompRec.put("ATTRIBUTE14", null);
        woCompRec.put("ATTRIBUTE15", null);
        woCompRec.put("REQUEST_ID", null);
        woCompRec.put("PROGRAM_UPDATE_DATE", null);
        woCompRec.put("PROGRAM_APPLICATION_ID", null);
        woCompRec.put("PROGRAM_ID", null);
        woCompRec.put("RETURN_STATUS", null);
        woCompRec.put("TRANSACTION_TYPE", 4);
        woCompRec.put("EAM_FAILURE_ENTRY_RECORD", null);
        woCompRec.put("EAM_FAILURE_CODES_TBL", null);

        body.put("P_EAM_WO_COMP_REC", woCompRec);

        // Add remaining fields as null
        body.put("P_EAM_WO_QUALITY_TBL", null);
        body.put("P_EAM_METER_READING_TBL", null);
        body.put("P_EAM_COUNTER_PROP_TBL", null);
        body.put("P_EAM_WO_COMP_REBUILD_TBL", null);
        body.put("P_EAM_WO_COMP_MR_READ_TBL", null);
        body.put("P_EAM_OP_COMP_TBL", null);
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
