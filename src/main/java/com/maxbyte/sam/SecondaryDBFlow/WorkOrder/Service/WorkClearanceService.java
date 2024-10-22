package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.maxbyte.sam.SecondaryDBFlow.Response.ImageResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddWorkClearanceRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkClearance;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkClearanceChild;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.WorkClearanceRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.WorkClearanceChildRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Specification.WorkClearanceSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WorkClearanceService {

    @Autowired
    private WorkClearanceChildRepository workClearanceChildRepository;
    @Autowired
    private WorkClearanceRepository workClearanceRepository;

    public ResponseModel<List<WorkClearance>> list(String workOrderNumber) {
        try {

            WorkClearanceSpecificationBuilder builder=new WorkClearanceSpecificationBuilder();
            if(workOrderNumber!=null)builder.with("workOrderNumber",":",workOrderNumber);

            List<WorkClearance> results = workClearanceRepository.findAll(builder.build());
            return new ResponseModel<>(true, "Records found",results);

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }

    public ResponseModel<String> addWorkClearance(AddWorkClearanceRequest addWorkClearanceRequest) {
        try {
            List<WorkClearance> workClearanceOneList = workClearanceRepository.findByWorkOrderNumber(addWorkClearanceRequest.getWorkOrderNumber());
            var actionApi = 0;
            if(!workClearanceOneList.isEmpty()) {
                workClearanceOneList.getFirst().setWorkOrderNumber(addWorkClearanceRequest.getWorkOrderNumber());
                workClearanceRepository.save(workClearanceOneList.getFirst());

                var addedTableList = workClearanceChildRepository.findByWorkOrderNumber(workClearanceOneList.getFirst().getWorkOrderNumber());
                if (!addedTableList.isEmpty()) {
                    for (WorkClearanceChild items : addedTableList) {
                        if (items.getWorkOrderNumber().equals(workClearanceOneList.getFirst().getWorkOrderNumber())) {
                            workClearanceChildRepository.deleteById(items.getId());
                        }
                    }
                }
                for (WorkClearanceChild items : addWorkClearanceRequest.getWorkClearanceList()) {
                    var workClearanceTwo = new WorkClearanceChild();

                    workClearanceTwo.setWorkOrderNumber(items.getWorkOrderNumber());
                    workClearanceTwo.setWorkClearance(items.getWorkClearance());
                    workClearanceTwo.setValidFrom(items.getValidFrom());
                    workClearanceTwo.setValidTill(items.getValidTill());
                    workClearanceTwo.setStartTime(items.getStartTime());
                    workClearanceTwo.setEndTime(items.getEndTime());
                    workClearanceTwo.setDescription(items.getDescription());
                    workClearanceTwo.setUploadAttachment(items.getUploadAttachment());
                    workClearanceTwo.setCreatedOn(LocalDateTime.now());

                    workClearanceChildRepository.save(workClearanceTwo);

                }
                actionApi = 2;
            }else {
                var workClearanceOne = new WorkClearance();
                workClearanceOne.setWorkOrderNumber(addWorkClearanceRequest.getWorkOrderNumber());
                workClearanceOne.setWorkClearanceList(addWorkClearanceRequest.getWorkClearanceList());
                workClearanceRepository.save(workClearanceOne);


                for (WorkClearanceChild items : addWorkClearanceRequest.getWorkClearanceList()) {
                    var workClearanceTwo = new WorkClearanceChild();

                    workClearanceTwo.setWorkOrderNumber(addWorkClearanceRequest.getWorkOrderNumber());
                    workClearanceTwo.setWorkClearance(items.getWorkClearance());
                    workClearanceTwo.setValidFrom(items.getValidFrom());
                    workClearanceTwo.setValidTill(items.getValidTill());
                    workClearanceTwo.setStartTime(items.getStartTime());
                    workClearanceTwo.setEndTime(items.getEndTime());
                    workClearanceTwo.setDescription(items.getDescription());
                    workClearanceTwo.setUploadAttachment(items.getUploadAttachment());
                    workClearanceTwo.setCreatedOn(LocalDateTime.now());

                    workClearanceChildRepository.save(workClearanceTwo);

                }
                actionApi = 1;
            }

            return new ResponseModel<>(true, actionApi == 1?"Work Clearance Added Successfully":"Work Clearance updated successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }


    public ResponseModel<ImageResponse> saveImageToStorage(String uploadDirectory, MultipartFile imageFile) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path uploadPath = Path.of(uploadDirectory);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        var imagePath = new ImageResponse();
        imagePath.setImagePath((uploadDirectory+"/"+uniqueFileName).replace("target/classes/static",""));
        return new ResponseModel<>(true, "Image Updated Successfully",imagePath);
    }

}
