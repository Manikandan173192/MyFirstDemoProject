package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddAdditionalDetailsRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.AdditionalDetails;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.AdditionalDetailsRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Specification.AdditionalDetailsSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AdditionalDetailsService {
    @Autowired
    private AdditionalDetailsRepository additionalDetailsRepository;


    public ResponseModel<List<AdditionalDetails>> list(String workOrderNumber) {
        try {
            AdditionalDetailsSpecificationBuilder builder = new AdditionalDetailsSpecificationBuilder();
            if(workOrderNumber!=null)builder.with("workOrderNumber",":",workOrderNumber);

            List<AdditionalDetails> results = additionalDetailsRepository.findAll(builder.build());
            return new ResponseModel<>(true, "Records found",results);

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }

    public ResponseModel<String> addOrUpdateAdditionalDetails(AddAdditionalDetailsRequest data) {
        try {
            AdditionalDetails additionalDetails = additionalDetailsRepository.findByWorkOrderNumber(data.getWorkOrderNumber());

            if (additionalDetails != null) {
                additionalDetails.setWorkOrderNumber(data.getWorkOrderNumber());
                additionalDetails.setOrganizationId(data.getOrganizationId());
                additionalDetails.setRebuildParent(data.getRebuildParent());
                additionalDetails.setActivityType(data.getActivityType());
                additionalDetails.setActivityCause(data.getActivityCause());
                additionalDetails.setActivitySource(data.getActivitySource());
                additionalDetails.setMaterialRequestIssue(data.getMaterialRequestIssue());
                additionalDetails.setPlanned(data.getPlanned());
                additionalDetails.setWarrantyStatus(data.getWarrantyStatus());
                additionalDetails.setWarrantyActive(data.getWarrantyActive());
                additionalDetails.setWarrantyExpirationDate(data.getWarrantyExpirationDate());
                additionalDetails.setTagoutRequired(data.getTagoutRequired());
                additionalDetails.setNotificationRequired(data.getNotificationRequired());
                additionalDetails.setContext(data.getContext());
                additionalDetails.setInformDepartments(data.getInformDepartments());
                additionalDetails.setSafetyPermit(data.getSafetyPermit());

                additionalDetailsRepository.save(additionalDetails);

                return new ResponseModel<>(true, "Updated Successfully ", null);
            }  else /*if (data.getWorkOrderNumber() != 0)*/ {
                additionalDetails = new AdditionalDetails();
                additionalDetails.setWorkOrderNumber(data.getWorkOrderNumber());
                additionalDetails.setOrganizationId(data.getOrganizationId());
                additionalDetails.setRebuildParent(data.getRebuildParent());
                additionalDetails.setActivityType(data.getActivityType());
                additionalDetails.setActivityCause(data.getActivityCause());
                additionalDetails.setActivitySource(data.getActivitySource());
                additionalDetails.setMaterialRequestIssue(data.getMaterialRequestIssue());
                additionalDetails.setPlanned(data.getPlanned());
                additionalDetails.setWarrantyStatus(data.getWarrantyStatus());
                additionalDetails.setWarrantyActive(data.getWarrantyActive());
                additionalDetails.setWarrantyExpirationDate(data.getWarrantyExpirationDate());
                additionalDetails.setTagoutRequired(data.getTagoutRequired());
                additionalDetails.setNotificationRequired(data.getNotificationRequired());
                additionalDetails.setContext(data.getContext());
                additionalDetails.setInformDepartments(data.getInformDepartments());
                additionalDetails.setSafetyPermit(data.getSafetyPermit());

                additionalDetailsRepository.save(additionalDetails);

                return new ResponseModel<>(true, "Added Successfully ", null);
            } /*else {
                return new ResponseModel<>(false, "Work order number not found", null);
            }*/
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to Add/Update", null);
        }
    }

}
