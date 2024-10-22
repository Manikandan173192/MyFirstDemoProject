package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddBasicDetailsRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.BasicDetails;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.BasicDetailsRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Specification.BasicDetailsSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BasicDetailsService {
    @Autowired
    private BasicDetailsRepository basicDetailsRepository;


    public ResponseModel<List<BasicDetails>> list(String workOrderNumber) {
        try {
            BasicDetailsSpecificationBuilder builder = new BasicDetailsSpecificationBuilder();
            if (workOrderNumber != null) builder.with("workOrderNumber", ":", workOrderNumber);

            List<BasicDetails> results = basicDetailsRepository.findAll(builder.build());
            return new ResponseModel<>(true, "Records found", results);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }


   /* public ResponseModel<String> addOrUpdateBasicDetails(AddBasicDetailsRequest data) {
        try {
            if (data.getWorkOrderNo() =="") {
                List<BasicDetails> listData = basicDetailsRepository.findAll();

                BasicDetails basicDetails = new BasicDetails();

                var tempWONumber = "";

                if (listData.isEmpty()) {
                    tempWONumber = "TEMP" + 1;
                    basicDetails.setWorkOrderNumber(tempWONumber);

                } else {
                    tempWONumber = "TEMP" + (listData.get(listData.size() - 1).getId() + 1);
                    basicDetails.setWorkOrderNumber(tempWONumber);
                }

                basicDetails.setWipEntityId(data.getWipEntityId());
                basicDetails.setOrganizationId(data.getOrganizationId());
                basicDetails.setAssetNumber(data.getAssetNumber());
                basicDetails.setAssetGroupId(data.getAssetGroupId());
                basicDetails.setAssetType(data.getAssetType());
                basicDetails.setRebuildItemId(data.getRebuildItemId());
                basicDetails.setRebuildSerialNo(data.getRebuildSerialNumber());
                basicDetails.setDepartmentId(data.getDepartmentId());
                basicDetails.setAssetActivity(data.getAssetActivity());
                basicDetails.setWipAccountingClassCode(data.getWipAccountingClassCode());
                basicDetails.setScheduledStartDate(data.getScheduledStartDate());
                basicDetails.setScheduledEndDate(data.getScheduledEndDate());
                basicDetails.setWorkRequestNo(data.getWrRequestNo());
                basicDetails.setPriority(data.getPriority());
                basicDetails.setDuration(data.getDuration());
                basicDetails.setPlannerType(data.getPlannerType());
                basicDetails.setWoType(data.getWoType());
                basicDetails.setShutdownType(data.getShutdownType());
                basicDetails.setFirmPlannedType(data.getFirmPlannedType());
                basicDetails.setStatusType(data.getStatusType());
                basicDetails.setDescription(data.getDescription());
                basicDetails.setMaintenanceObjectId(data.getMaintenanceObjectId());
                basicDetails.setMaintenanceObjectType(data.getMaintenanceObjectType());
                basicDetails.setWorkRequestId(data.getWrRequestNo());
                basicDetails.setWorkRequestType(data.getWorkRequestType());


                basicDetailsRepository.save(basicDetails);

                return new ResponseModel<>(true, "Added Successfully ", tempWONumber);
            } else if (data.getWorkOrderNo().contains("WO")) {

                BasicDetails basicDetails = new BasicDetails();
                basicDetails.setWorkOrderNumber(data.getWorkOrderNo());
                basicDetails.setWipEntityId(data.getWipEntityId());
                basicDetails.setOrganizationId(data.getOrganizationId());
                basicDetails.setAssetNumber(data.getAssetNumber());
                basicDetails.setAssetGroupId(data.getAssetGroupId());
                basicDetails.setAssetType(data.getAssetType());
                basicDetails.setRebuildItemId(data.getRebuildItemId());
                basicDetails.setRebuildSerialNo(data.getRebuildSerialNumber());
                basicDetails.setDepartmentId(data.getDepartmentId());
                basicDetails.setAssetActivity(data.getAssetActivity());
                basicDetails.setWipAccountingClassCode(data.getWipAccountingClassCode());
                basicDetails.setScheduledStartDate(data.getScheduledStartDate());
                basicDetails.setScheduledEndDate(data.getScheduledEndDate());
                basicDetails.setWorkRequestNo(data.getWrRequestNo());
                basicDetails.setPriority(data.getPriority());
                basicDetails.setDuration(data.getDuration());
                basicDetails.setPlannerType(data.getPlannerType());
                basicDetails.setWoType(data.getWoType());
                basicDetails.setShutdownType(data.getShutdownType());
                basicDetails.setFirmPlannedType(data.getFirmPlannedType());
                basicDetails.setStatusType(data.getStatusType());
                basicDetails.setDescription(data.getDescription());
                basicDetails.setMaintenanceObjectId(data.getMaintenanceObjectId());
                basicDetails.setMaintenanceObjectType(data.getMaintenanceObjectType());
                basicDetails.setWorkRequestId(data.getWrRequestNo());
                basicDetails.setWorkRequestType(data.getWorkRequestType());
                basicDetails.setWipEntityName(data.getWorkOrderNo());


                basicDetailsRepository.save(basicDetails);
                return new ResponseModel<>(true, "Added Successfully ", data.getWorkOrderNo());
            } else {
                BasicDetails basicDetails = basicDetailsRepository.findByWorkOrderNumber(data.getWorkOrderNo());
                if (basicDetails != null) {

                    basicDetails.setWorkOrderNumber(data.getWorkOrderNo());
                    basicDetails.setWipEntityId(data.getWipEntityId());
                    basicDetails.setOrganizationId(data.getOrganizationId());
                    basicDetails.setAssetNumber(data.getAssetNumber());
                    basicDetails.setAssetGroupId(data.getAssetGroupId());
                    basicDetails.setAssetType(data.getAssetType());
                    basicDetails.setRebuildItemId(data.getRebuildItemId());
                    basicDetails.setRebuildSerialNo(data.getRebuildSerialNumber());
                    basicDetails.setDepartmentId(data.getDepartmentId());
                    basicDetails.setAssetActivity(data.getAssetActivity());
                    basicDetails.setWipAccountingClassCode(data.getWipAccountingClassCode());
                    basicDetails.setScheduledStartDate(data.getScheduledStartDate());
                    basicDetails.setScheduledEndDate(data.getScheduledEndDate());
                    basicDetails.setWorkRequestNo(data.getWrRequestNo());
                    basicDetails.setPriority(data.getPriority());
                    basicDetails.setDuration(data.getDuration());
                    basicDetails.setPlannerType(data.getPlannerType());
                    basicDetails.setWoType(data.getWoType());
                    basicDetails.setShutdownType(data.getShutdownType());
                    basicDetails.setFirmPlannedType(data.getFirmPlannedType());
                    basicDetails.setStatusType(data.getStatusType());
                    basicDetails.setDescription(data.getDescription());
                    basicDetails.setMaintenanceObjectId(data.getMaintenanceObjectId());
                    basicDetails.setMaintenanceObjectType(data.getMaintenanceObjectType());
                    basicDetails.setWorkRequestId(data.getWrRequestNo());
                    basicDetails.setWorkRequestType(data.getWorkRequestType());

                    basicDetailsRepository.save(basicDetails);

                    return new ResponseModel<>(true, "Updated Successfully ", data.getWorkOrderNo());
                } else {
                    return new ResponseModel<>(false, "Work order number not found", null);
                }
            }
        } catch (Exception e) {
            return new ResponseModel(false, "Failed to Add", null);
        }
    }
*/


    public ResponseModel<String> addOrUpdateBasicDetails(AddBasicDetailsRequest data) {
        try {
            if(data.getWorkOrderNo() == "") {
                List<BasicDetails> listData = basicDetailsRepository.findAll();

                BasicDetails basicDetails = new BasicDetails();

                var tempWONumber = "";

                if (listData.isEmpty()) {
                    tempWONumber ="TEMP"+1;
                    basicDetails.setWorkOrderNumber(tempWONumber);

                } else {
                    tempWONumber = "TEMP"+(listData.get(listData.size() - 1).getId() + 1);
                    basicDetails.setWorkOrderNumber(tempWONumber);
                }

                basicDetails.setWipEntityId(data.getWipEntityId());
                basicDetails.setOrganizationId(data.getOrganizationId());
                basicDetails.setAssetNumber(data.getAssetNumber());
                basicDetails.setAssetGroupId(data.getAssetGroupId());
                basicDetails.setAssetType(data.getAssetType());
                basicDetails.setRebuildItemId(data.getRebuildItemId());
                basicDetails.setRebuildSerialNo(data.getRebuildSerialNumber());
                basicDetails.setDepartmentId(data.getDepartmentId());
                basicDetails.setAssetActivity(data.getAssetActivity());
                basicDetails.setWipAccountingClassCode(data.getWipAccountingClassCode());
                basicDetails.setScheduledStartDate(data.getScheduledStartDate());
                basicDetails.setScheduledEndDate(data.getScheduledEndDate());
                basicDetails.setWorkRequestNo(data.getWrRequestNo());
                basicDetails.setPriority(data.getPriority());
                basicDetails.setDuration(data.getDuration());
                basicDetails.setPlannerType(data.getPlannerType());
                basicDetails.setWoType(data.getWoType());
                basicDetails.setShutdownType(data.getShutdownType());
                basicDetails.setFirmPlannedType(data.getFirmPlannedType());
                basicDetails.setStatusType(data.getStatusType());
                basicDetails.setDescription(data.getDescription());
                basicDetails.setMaintenanceObjectId(data.getMaintenanceObjectId());
                basicDetails.setMaintenanceObjectType(data.getMaintenanceObjectType());
                basicDetails.setWorkRequestId(data.getWrRequestNo());
                basicDetails.setWorkRequestType(data.getWorkRequestType());


                basicDetailsRepository.save(basicDetails);

                return new ResponseModel<>(true, "Added Successfully ", tempWONumber);
            } else if (data.getWorkOrderNo().contains("WO")) {
                BasicDetails basicDetails = basicDetailsRepository.findByWorkOrderNumber(data.getWorkOrderNo());

                if(basicDetails == null){
                    BasicDetails newBasicDetails = new BasicDetails();
                    newBasicDetails.setWorkOrderNumber(data.getWorkOrderNo());
                    newBasicDetails.setWipEntityId(data.getWipEntityId());
                    newBasicDetails.setWipEntityName(data.getWorkOrderNo());
                    newBasicDetails.setOrganizationId(data.getOrganizationId());
                    newBasicDetails.setAssetNumber(data.getAssetNumber());
                    newBasicDetails.setAssetGroupId(data.getAssetGroupId());
                    newBasicDetails.setAssetType(data.getAssetType());
                    newBasicDetails.setRebuildItemId(data.getRebuildItemId());
                    newBasicDetails.setRebuildSerialNo(data.getRebuildSerialNumber());
                    newBasicDetails.setDepartmentId(data.getDepartmentId());
                    newBasicDetails.setAssetActivity(data.getAssetActivity());
                    newBasicDetails.setWipAccountingClassCode(data.getWipAccountingClassCode());
                    newBasicDetails.setScheduledStartDate(data.getScheduledStartDate());
                    newBasicDetails.setScheduledEndDate(data.getScheduledEndDate());
                    newBasicDetails.setWorkRequestNo(data.getWrRequestNo());
                    newBasicDetails.setPriority(data.getPriority());
                    newBasicDetails.setDuration(data.getDuration());
                    newBasicDetails.setPlannerType(data.getPlannerType());
                    newBasicDetails.setWoType(data.getWoType());
                    newBasicDetails.setShutdownType(data.getShutdownType());
                    newBasicDetails.setFirmPlannedType(data.getFirmPlannedType());
                    newBasicDetails.setStatusType(data.getStatusType());
                    newBasicDetails.setDescription(data.getDescription());
                    newBasicDetails.setMaintenanceObjectId(data.getMaintenanceObjectId());
                    newBasicDetails.setMaintenanceObjectType(data.getMaintenanceObjectType());
                    newBasicDetails.setWorkRequestId(data.getWrRequestNo());
                    newBasicDetails.setWorkRequestType(data.getWorkRequestType());

                    basicDetailsRepository.save(newBasicDetails);

                    return new ResponseModel<>(true, "Added Successfully ", data.getWorkOrderNo());

                }else{
                    basicDetails.setWorkOrderNumber(data.getWorkOrderNo());
                    basicDetails.setWipEntityId(data.getWipEntityId());
                    basicDetails.setWipEntityName(data.getWorkOrderNo());
                    basicDetails.setOrganizationId(data.getOrganizationId());
                    basicDetails.setAssetNumber(data.getAssetNumber());
                    basicDetails.setAssetGroupId(data.getAssetGroupId());
                    basicDetails.setAssetType(data.getAssetType());
                    basicDetails.setRebuildItemId(data.getRebuildItemId());
                    basicDetails.setRebuildSerialNo(data.getRebuildSerialNumber());
                    basicDetails.setDepartmentId(data.getDepartmentId());
                    basicDetails.setAssetActivity(data.getAssetActivity());
                    basicDetails.setWipAccountingClassCode(data.getWipAccountingClassCode());
                    basicDetails.setScheduledStartDate(data.getScheduledStartDate());
                    basicDetails.setScheduledEndDate(data.getScheduledEndDate());
                    basicDetails.setWorkRequestNo(data.getWrRequestNo());
                    basicDetails.setPriority(data.getPriority());
                    basicDetails.setDuration(data.getDuration());
                    basicDetails.setPlannerType(data.getPlannerType());
                    basicDetails.setWoType(data.getWoType());
                    basicDetails.setShutdownType(data.getShutdownType());
                    basicDetails.setFirmPlannedType(data.getFirmPlannedType());
                    basicDetails.setStatusType(data.getStatusType());
                    basicDetails.setDescription(data.getDescription());
                    basicDetails.setMaintenanceObjectId(data.getMaintenanceObjectId());
                    basicDetails.setMaintenanceObjectType(data.getMaintenanceObjectType());
                    basicDetails.setWorkRequestId(data.getWrRequestNo());
                    basicDetails.setWorkRequestType(data.getWorkRequestType());

                    basicDetailsRepository.save(basicDetails);

                    return new ResponseModel<>(true, "Updated Successfully ", data.getWorkOrderNo());

                }

            } else {
                BasicDetails basicDetails= basicDetailsRepository.findByWorkOrderNumber(data.getWorkOrderNo());
                if(basicDetails!=null) {

                    basicDetails.setWorkOrderNumber(data.getWorkOrderNo());
                    basicDetails.setWipEntityId(data.getWipEntityId());
                    basicDetails.setWipEntityName(data.getWorkOrderNo());
                    basicDetails.setOrganizationId(data.getOrganizationId());
                    basicDetails.setAssetNumber(data.getAssetNumber());
                    basicDetails.setAssetGroupId(data.getAssetGroupId());
                    basicDetails.setAssetType(data.getAssetType());
                    basicDetails.setRebuildItemId(data.getRebuildItemId());
                    basicDetails.setRebuildSerialNo(data.getRebuildSerialNumber());
                    basicDetails.setDepartmentId(data.getDepartmentId());
                    basicDetails.setAssetActivity(data.getAssetActivity());
                    basicDetails.setWipAccountingClassCode(data.getWipAccountingClassCode());
                    basicDetails.setScheduledStartDate(data.getScheduledStartDate());
                    basicDetails.setScheduledEndDate(data.getScheduledEndDate());
                    basicDetails.setWorkRequestNo(data.getWrRequestNo());
                    basicDetails.setPriority(data.getPriority());
                    basicDetails.setDuration(data.getDuration());
                    basicDetails.setPlannerType(data.getPlannerType());
                    basicDetails.setWoType(data.getWoType());
                    basicDetails.setShutdownType(data.getShutdownType());
                    basicDetails.setFirmPlannedType(data.getFirmPlannedType());
                    basicDetails.setStatusType(data.getStatusType());
                    basicDetails.setDescription(data.getDescription());
                    basicDetails.setMaintenanceObjectId(data.getMaintenanceObjectId());
                    basicDetails.setMaintenanceObjectType(data.getMaintenanceObjectType());
                    basicDetails.setWorkRequestId(data.getWrRequestNo());
                    basicDetails.setWorkRequestType(data.getWorkRequestType());

                    basicDetailsRepository.save(basicDetails);

                    return new ResponseModel<>(true, "Updated Successfully ", data.getWorkOrderNo());
                }else {
                    return new ResponseModel<>(false, "Work order number not found", null);
                }
            }
        } catch (Exception e) {
            return new ResponseModel(false,"Failed to Add",null);
        }
    }

}
