package com.maxbyte.sam.SecondaryDBFlow.WRWOType.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Department;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Entity.WrWoType;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Service.WrWoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lookUp")
public class WrWoTypeController extends CrudController<WrWoType, Integer> {
    @Autowired
    private WrWoTypeService wrWoTypeService;

    @Override
    public ServiceInterface service() {
        return wrWoTypeService;
    }

    @GetMapping("/wrWoTypes")
    public ResponseModel<List<WrWoType>> list(
            @RequestParam(required = false) String NAME,
            @RequestParam(required = false) String LOOKUP_TYPE,
            @RequestParam(required = false) Integer LOOKUP_CODE,
            @RequestParam(required = false) String MEANING,
            @RequestParam(required = false) String DESCRIPTION) {

        return this.wrWoTypeService.list(NAME, LOOKUP_TYPE, LOOKUP_CODE, MEANING, DESCRIPTION);
    }
}


