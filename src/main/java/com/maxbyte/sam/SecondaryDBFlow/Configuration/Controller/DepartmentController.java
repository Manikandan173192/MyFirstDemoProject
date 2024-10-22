package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;


import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Department;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.DepartmentService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/department")
public class DepartmentController extends CrudController<Department,Integer> {

    @Autowired
    private DepartmentService departmentService;
    @Override
    public ServiceInterface service() {
        return departmentService;
    }

    @GetMapping("")
    public ResponseModel<List<Department>> list(
                                                @RequestParam(required = false)String department,
                                                @RequestParam(required = false)String departmentDescription,
                                                @RequestParam(required = false)String organizationCode,
                                                @RequestParam(required = false, defaultValue = "0") String requestPage){
        return this.departmentService.list( department, departmentDescription,organizationCode, requestPage);
    }




}