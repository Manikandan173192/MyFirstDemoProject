package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Organization;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.OrganizationService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/organization")
public class OrganizationController extends CrudController<Organization,Integer> {

    @Autowired
    private OrganizationService organizationService;

    @Override
    public ServiceInterface service() {
        return organizationService;
    }

    @GetMapping("")
    public ResponseModel<List<Organization>> list(@RequestParam(required = false)String organizationCode,
                                                  @RequestParam(required = false)String organizationDescription,
                                                  @RequestParam(required = false)Boolean isActive,
                                                  @RequestParam(required = false, defaultValue = "0") String requestPage){
        return this.organizationService.list(organizationCode,organizationDescription,isActive,requestPage);
    }


}