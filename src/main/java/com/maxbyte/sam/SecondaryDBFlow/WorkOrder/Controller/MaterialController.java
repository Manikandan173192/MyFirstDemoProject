package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddMaterialRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetMaterialList;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.MaterialInvenoryItemRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.MaterialInventoryListRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.Material;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workOrder/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping("/materialList")
    public ResponseModel<List<Material>> list(@RequestParam(required = false) String workOrderNumber){
        return materialService.list(workOrderNumber);
    }
    @PostMapping("/addOrUpdateMaterial")
    public ResponseModel<String> addMaterial(@RequestBody AddMaterialRequest addMaterialRequest){
        return materialService.addMaterial(addMaterialRequest);
    }

    @GetMapping("/materialInventoryList")
    public ResponseModel<List<MaterialInventoryListRequest>> listDD(@RequestParam(required = false)String itemType, @RequestParam(required = false)String organizationId, @RequestParam(required = false)String itemCode){
        return this.materialService.listDD(itemType,organizationId,itemCode);
    }

    @GetMapping("/getMaterialByWipEntityId")
    public  ResponseModel<List<GetMaterialList>> listOperation(@RequestParam String wipEntityId){
        return materialService.listMaterial(wipEntityId);
    }
}
