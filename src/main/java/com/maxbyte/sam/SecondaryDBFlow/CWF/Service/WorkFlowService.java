package com.maxbyte.sam.SecondaryDBFlow.CWF.Service;

import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddWorkFlowConfigurationRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.APIRequest.AddWorkFlowRequest;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlow;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlowConfig;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlowConfiguration;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Repository.WorkFlowConfigRepository;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Repository.WorkFlowConfigurationRepository;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Repository.WorkFlowRepository;
import com.maxbyte.sam.SecondaryDBFlow.CWF.Specification.WorkFlowSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WorkFlowService extends CrudService<WorkFlow,Integer> {
    @Autowired
    private WorkFlowRepository workFlowRepository;
    @Override
    public CrudRepository repository() {
        return this.workFlowRepository;
    }

    @Autowired
    private WorkFlowConfigurationRepository workFlowConfigurationRepository;
    @Autowired
    private WorkFlowConfigRepository workFlowConfigRepository;

    @Override
    public void validateAdd(WorkFlow data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(WorkFlow data) {
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

    public ResponseModel<List<WorkFlow>> list(String workFlowName, Boolean isActive, String createdBy,String organizationCode) {

        try{
            WorkFlowSpecificationBuilder builder = new WorkFlowSpecificationBuilder();
            if(workFlowName!=null)builder.with("workFlowName",":",workFlowName);
            if(isActive!=null)builder.with("isActive","==",isActive);
            if(isActive!=null)builder.with("createdBy","==",createdBy);
            if(organizationCode!=null)builder.with("organizationCode","==",organizationCode);


            List<WorkFlow> results = workFlowRepository.findAll(builder.build());
            return new ResponseModel<>(true, "Records found",results.reversed());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }


    public ResponseModel addWorkFlow(AddWorkFlowRequest data) {
        try {
            List<WorkFlow> listData = workFlowRepository.findAll();

            WorkFlow workFlow = new WorkFlow();
            var workFlowNumber = "";


            LocalDateTime instance = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            String formattedStartDate = formatter.format(instance);

            if(listData.isEmpty()){
                workFlowNumber= "WK"+"_" + data.getOrganizationCode() + "_" + data.getDepartment() + "_" + formattedStartDate + "_" + 1;
                workFlow.setWorkFlowNumber(workFlowNumber);

            }else{
                workFlowNumber= "WK"+"_" + data.getOrganizationCode() + "_" + data.getDepartment() + "_" + formattedStartDate + "_" + (listData.get(listData.size()-1).getWorkFlowId()+1);
                workFlow.setWorkFlowNumber(workFlowNumber);
            }

            workFlow.setWorkFlowName(data.getWorkFlowName());
            workFlow.setOrganizationId(data.getOrganizationId());
            workFlow.setOrganizationCode(data.getOrganizationCode());
            workFlow.setDepartmentId(data.getDepartmentId());
            workFlow.setDepartment(data.getDepartment());
            workFlow.setInitiatorName(data.getInitiatorName());
            workFlow.setActive(true);

            WorkFlow workFlow1 = workFlowRepository.save(workFlow);


            return new ResponseModel(true,"Added Successfully ",workFlow1);
        } catch (Exception e) {
            return new ResponseModel(false,"Failed to Add",null);
        }
    }



    public ResponseModel<String> addWorkFlowConfig(AddWorkFlowConfigurationRequest addWorkFlowRequest) {
        try {
            List<WorkFlowConfig> workFlowList = workFlowConfigRepository.findByWorkFlowNumber(addWorkFlowRequest.getWorkFlowNumber());
            if(!workFlowList.isEmpty()){
                workFlowList.getFirst().setWorkFlowNumber(addWorkFlowRequest.getWorkFlowNumber());
                workFlowConfigRepository.save(workFlowList.getFirst());

                var addedTableList = workFlowConfigurationRepository.findByWorkFlowNumber(workFlowList.getFirst().getWorkFlowNumber());
                if (!addedTableList.isEmpty()) {
                    for (WorkFlowConfiguration items : addedTableList) {
                        if (items.getWorkFlowNumber().equals(workFlowList.getFirst().getWorkFlowNumber())) {
                            workFlowConfigurationRepository.deleteById(items.getId());
                        }
                    }
                }
                for (WorkFlowConfiguration items : addWorkFlowRequest.getWorkFlowConfigurationList()) {
                    var workFlowConfiguration = new WorkFlowConfiguration();

                    workFlowConfiguration.setField(items.getField());
                    workFlowConfiguration.setAttachment(items.isAttachment());
                    workFlowConfiguration.setWorkFlowNumber(workFlowList.getFirst().getWorkFlowNumber());

                    workFlowConfigurationRepository.save(workFlowConfiguration);
                }
            } else {
                var workFlowConfig = new WorkFlowConfig();
                workFlowConfig.setWorkFlowNumber(addWorkFlowRequest.getWorkFlowNumber());
                workFlowConfig.setWorkFlowConfigurations(addWorkFlowRequest.getWorkFlowConfigurationList());
                workFlowConfigRepository.save(workFlowConfig);

                List<WorkFlowConfig> workFlowConfigList = workFlowConfigRepository.findAll();

                for (WorkFlowConfiguration items : addWorkFlowRequest.getWorkFlowConfigurationList()) {
                    var workFlowConfiguration = new WorkFlowConfiguration();

                    workFlowConfiguration.setField(items.getField());
                    workFlowConfiguration.setAttachment(items.isAttachment());
                    workFlowConfiguration.setWorkFlowNumber(workFlowConfigList.getLast().getWorkFlowNumber());

                    workFlowConfigurationRepository.save(workFlowConfiguration);
                }

            }
            return new ResponseModel<>(true, "WorkFlow Added Successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

    public ResponseModel<WorkFlowConfig> getWorkFlowConfig(String workFlowNumber) {
        try {
            List<WorkFlowConfig> workFlowConfigList = workFlowConfigRepository.findByWorkFlowNumber(workFlowNumber);

            return new ResponseModel<>(true, "Records found", workFlowConfigList.getFirst());

        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }




}
