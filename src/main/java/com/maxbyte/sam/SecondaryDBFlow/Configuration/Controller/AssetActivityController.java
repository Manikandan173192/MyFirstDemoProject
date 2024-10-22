package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.AssetActivity;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.AssetActivityService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Entity.WrWoType;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AssetActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workOrder")
public class AssetActivityController extends CrudController<AssetActivity,Integer> {
    @Autowired
    private AssetActivityService assetActivityService;
    @Override
    public ServiceInterface<AssetActivity, Integer> service() {
        return assetActivityService;
    }

//    @GetMapping("/assetActivity")
//    public ResponseModel<List<AssetActivity>> list(
//            @RequestParam(required = false) String activity,
//            @RequestParam(required = false) String activityType,
//            @RequestParam(required = false) Integer activityCause,
//            @RequestParam(required = false) String activitySource
////            ,@RequestParam(required = false) String DESCRIPTION
//            )
//            {
//
//        return this.assetActivityService.list(activity, activityType, activityCause, activitySource/*, DESCRIPTION*/);
//    }

//    @GetMapping("/assetActivity")
//    public ResponseModel<List<AssetActivityDTO>> getByOrganizationId(@RequestParam(required = false) Integer organizationId,
//                                                                     @RequestParam(required = false, defaultValue = "0") String requestPage) {
//        return assetActivityService.listByOrganizationId(organizationId,requestPage);
//    }

    @GetMapping("/assetActivity")
    public ResponseModel<List<AssetActivityDTO>> getByOrganizationId(@RequestParam(required = false) String serialNumber/*,
                                                                 @RequestParam(required = false, defaultValue = "0") *//*String requestPage*/) {
        return assetActivityService.listBySerialNumber(serialNumber/*,requestPage*/);
    }
}
