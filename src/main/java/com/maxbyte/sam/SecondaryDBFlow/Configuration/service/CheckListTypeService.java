package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.CheckListType;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.CheckListTypeRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.CheckListTypeSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CheckListTypeService extends CrudService<CheckListType,Integer> {

    @Autowired
    CheckListTypeRepository checkListTypeRepository;
    @Override
    public CrudRepository repository() {
        return this.checkListTypeRepository;
    }

    @Override
    public void validateAdd(CheckListType data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(CheckListType data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateDelete(Integer id) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    public ResponseModel<List<CheckListType>> list(Boolean isActive) {
        try {
            CheckListTypeSpecificationBuilder builder = new CheckListTypeSpecificationBuilder ();
            if(isActive!=null)builder.with("isActive",":",isActive);

            List<CheckListType> results = checkListTypeRepository.findAll(builder.build());
            return new ResponseModel<>(true, "Records Found",results.reversed());

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }
}
