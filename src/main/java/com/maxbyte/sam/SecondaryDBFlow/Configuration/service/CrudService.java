package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import lombok.SneakyThrows;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public abstract class  CrudService<T, I> implements ServiceInterface<T, I> {

    public abstract CrudRepository repository();
    public abstract void validateAdd(T data);
    public abstract void validateEdit(T data);
    public abstract void validateDelete(I id);

    public List<T> list(){
        List<T> data = new ArrayList<>();

        repository().findAll().forEach(e->data.add((T) e));
        return  data;
    }
    public Optional<T> retrieve(Integer id){
        return (Optional<T>) repository().findById(id);
    }


    public ResponseModel add(T data){
        try{
            validateAdd(data);
            T save =(T) repository().save(data);
            this.callBackAdd(save);
            return new ResponseModel<T>(true,"Added Successfully",save);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public ResponseModel update(T data,I id) {
        try{
            validateEdit(data);
            Optional<T> res =(Optional<T>) repository().findById(id);
            if(res.isPresent()){
                T save =(T) repository().save(data);
                this.callBackEdit(save);
                return new ResponseModel<T>(true,"Updated Successfully",save);
            }

            else throw new Exception("Entry Not Found");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public ResponseModel patchData(Map<Object,Object> fields, I id) {
        try{
            Optional<T> data=repository().findById(id);
            if(data.isPresent()) {
                fields.forEach((key, value) -> {
                    Field field = ReflectionUtils.findField(Object.class, (String) key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, data.get(), value);
                });
                T save =(T) repository().save(data.get());
                return new ResponseModel<T>(true,"Updated Successfully",save);
            }
            else throw new Exception("Entry Not Found");
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @SneakyThrows
    public ResponseModel delete(I id) {

        try {
            validateDelete(id);
            Optional<T> optionalData = (Optional<T>) repository().findById(id);
            if(optionalData.isPresent()) {
                repository().deleteById(id);
                this.callBackDelete(optionalData.get());
            }
            return new ResponseModel(true, "Deleted Successfully");
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    public void callBackAdd(T data) throws IOException {

    }
    public void callBackEdit(T data) throws IOException{

    }
    public void callBackDelete(T data) throws IOException{

    }
}
