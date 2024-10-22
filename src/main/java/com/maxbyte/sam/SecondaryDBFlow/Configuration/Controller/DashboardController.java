package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.DashboardService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardCountService;

    @GetMapping("allList")
    public ResponseModel<List<Object>> list(){
        return this.dashboardCountService.getListCounts();
    }

    @GetMapping("list")
    public ResponseModel<List<Object>> list(String userName, String organizationCode){
        return this.dashboardCountService.getListCounts(userName,organizationCode);
    }
}
