package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.EmployeeDetails;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.EmployeeDetailsService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employeeDetails")
public class EmployeeDetailsController {
    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    @GetMapping("/employee")
    public ResponseModel<List<EmployeeDetails>> searchByName(@RequestParam String name,
                                                             @RequestParam String userType,
                                                             @RequestParam String organizationCode) {
        return employeeDetailsService.list(name,userType, organizationCode);
    }
}
