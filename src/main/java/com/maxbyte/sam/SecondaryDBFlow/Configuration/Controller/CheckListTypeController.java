package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.CheckListType;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CheckListTypeService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/checklist")
public class CheckListTypeController extends CrudController<CheckListType,Integer> {
    @Autowired
    CheckListTypeService checkListTypeService;
    @Override
    public ServiceInterface service() {
        return  checkListTypeService;
    }

    @GetMapping("")
    public ResponseModel<List<CheckListType>> list(@RequestParam(required = false) Boolean isActive){
        return this.checkListTypeService.list(isActive);
    }
}
