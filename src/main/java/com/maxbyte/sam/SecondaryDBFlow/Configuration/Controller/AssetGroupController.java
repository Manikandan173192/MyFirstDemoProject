package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.AssetGroup;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.AssetGroupService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/assetGroup")
public class AssetGroupController extends CrudController<AssetGroup,Integer> {

    @Autowired
    private AssetGroupService assetGroupService;
    @Override
    public ServiceInterface service() {
        return assetGroupService;
    }

    @GetMapping("")
    public ResponseModel<List<AssetGroup>> list(@RequestParam(required = false)String assetGroup,
                                                @RequestParam(required = false)String organizationCode,
                                                @RequestParam(required = false)Boolean isActive,
                                                @RequestParam(required = false)String assetGroupDescription,
                                                @RequestParam(required = false, defaultValue = "0") String requestPage){
        return this.assetGroupService.list(assetGroup,organizationCode,isActive,assetGroupDescription,requestPage);
    }

}
