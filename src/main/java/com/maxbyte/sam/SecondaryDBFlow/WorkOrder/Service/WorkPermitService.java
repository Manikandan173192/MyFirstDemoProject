package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddWorkPermitRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkPermit;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkPermitChild;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.WorkPermitRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.WorkPermitChildRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Specification.WorkPermitSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkPermitService {

    @Autowired
    private WorkPermitRepository workPermitRepository;
    @Autowired
    private WorkPermitChildRepository workPermitChildRepository;

    public ResponseModel<List<WorkPermit>> list(String workOrderNumber) {
        try {

            WorkPermitSpecificationBuilder builder=new WorkPermitSpecificationBuilder();
            if(workOrderNumber!=null)builder.with("workOrderNumber",":",workOrderNumber);

            List<WorkPermit> results = workPermitRepository.findAll(builder.build());
            return new ResponseModel<>(true, "Records found",results);

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }

    public ResponseModel<String> addWorkPermit(AddWorkPermitRequest addWorkPermitRequest) {
        try {
            List<WorkPermit> workPermitOneList = workPermitRepository.findByWorkOrderNumber(addWorkPermitRequest.getWorkOrderNumber());
            var actionApi = 0;
            if(!workPermitOneList.isEmpty()) {
                workPermitOneList.getFirst().setWorkOrderNumber(addWorkPermitRequest.getWorkOrderNumber());
                workPermitRepository.save(workPermitOneList.getFirst());

                var addedTableList = workPermitChildRepository.findByWorkOrderNumber(workPermitOneList.getFirst().getWorkOrderNumber());
                if (!addedTableList.isEmpty()) {
                    for (WorkPermitChild items : addedTableList) {
                        if (items.getWorkOrderNumber().equals(workPermitOneList.getFirst().getWorkOrderNumber())) {
                            workPermitChildRepository.deleteById(items.getId());
                        }
                    }
                }
                for (WorkPermitChild items : addWorkPermitRequest.getWorkPermitList()) {
                    var workPermitTwo = new WorkPermitChild();

                    workPermitTwo.setWorkOrderNumber(items.getWorkOrderNumber());
                    workPermitTwo.setWorkPermit(items.getWorkPermit());
                    workPermitTwo.setPermitType(items.getPermitType());
                    workPermitTwo.setStatus(items.getStatus());
                    workPermitTwo.setValidFrom(items.getValidFrom());
                    workPermitTwo.setValidTill(items.getValidTill());
                    workPermitTwo.setDescription(items.getDescription());
                    workPermitTwo.setCreatedOn(LocalDateTime.now());

                    workPermitChildRepository.save(workPermitTwo);

                }
                actionApi = 2;
            }else {
                var workPermitOne = new WorkPermit();
                workPermitOne.setWorkOrderNumber(addWorkPermitRequest.getWorkOrderNumber());
                workPermitOne.setWorkPermitList(addWorkPermitRequest.getWorkPermitList());
                workPermitRepository.save(workPermitOne);


                for (WorkPermitChild items : addWorkPermitRequest.getWorkPermitList()) {
                    var workPermitTwo = new WorkPermitChild();

                    workPermitTwo.setWorkOrderNumber(addWorkPermitRequest.getWorkOrderNumber());
                    workPermitTwo.setWorkPermit(items.getWorkPermit());
                    workPermitTwo.setPermitType(items.getPermitType());
                    workPermitTwo.setStatus(items.getStatus());
                    workPermitTwo.setValidFrom(items.getValidFrom());
                    workPermitTwo.setValidTill(items.getValidTill());
                    workPermitTwo.setDescription(items.getDescription());
                    workPermitTwo.setCreatedOn(LocalDateTime.now());

                    workPermitChildRepository.save(workPermitTwo);

                }
                actionApi = 1;
            }

            return new ResponseModel<>(true, actionApi == 1?"Work Permit Added Successfully":"Work Permit updated successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

}
