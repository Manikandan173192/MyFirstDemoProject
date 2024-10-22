package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;


import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Role;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.RoleService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/role")
public class RoleController extends CrudController<Role,Integer> {

    @Autowired
    private RoleService roleService;


    @Override
    public ServiceInterface service() {
        return roleService;
    }

    @GetMapping("")
    public ResponseModel<List<Role>> list(@RequestParam(required = false)String roleName,
                                          @RequestParam(required = false)String roleDescription,
                                          @RequestParam(required = false) Boolean isActive,
                                          @RequestParam(required = false, defaultValue = "0") String requestPage){
        return this.roleService.list(isActive,roleName,roleDescription,requestPage);
    }


}