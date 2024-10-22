package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.APIRequest.GetAssetList;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.AssetService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController extends CrudController<Asset,Integer> {

    @Autowired
    private AssetService assetService;
    @Override
    public ServiceInterface service() {
        return assetService;
    }

    @GetMapping("")
    public ResponseModel<List<Asset>> list(@RequestParam(required = false)String assetGroup,
                                           @RequestParam(required = false)String assetDescription,
                                           @RequestParam(required = false)String assetNumber,
                                           @RequestParam(required = false)String organizationCode,
                                           @RequestParam(required = false)Boolean isActive,
                                           @RequestParam(required = false)String assetType,
                                           @RequestParam(required = false)String department,
                                           @RequestParam(required = false)String area,
                                          /* @RequestParam(required = false)String wipAccount,*/
                                           @RequestParam(required = false, defaultValue = "0") String requestPage){
        return this.assetService.list(assetGroup, assetDescription, assetNumber ,organizationCode,/*,wipAccount*/ assetType,  department,area, isActive, requestPage);
    }

    @GetMapping("/listAsset")
    public ResponseModel<List<GetAssetList>> listAsset(@RequestParam(required = false) String assetGroupName,
                                                       @RequestParam(required = false) String assetNumber,
                                                       @RequestParam(required = false) String assetDescription,
                                                       @RequestParam(required = false) String organizationId)
    {
        return assetService.listAsset(assetGroupName,assetNumber,assetDescription,organizationId);
    }

    @GetMapping("/getWipAccountingClassCode")
    public ResponseModel<List<String>> listWipAccounting(@RequestParam(required = false) String wipAccountingClassCode){
        return assetService.listWipAccountingClassCode(wipAccountingClassCode);
    }


}
