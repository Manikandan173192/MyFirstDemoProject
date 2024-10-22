package com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ServiceInterface<T,I> {
    public List<T> list();

    public Optional<T> retrieve(I id);

    public ResponseModel add(T data);

    public ResponseModel update(T data,I id);

    public ResponseModel patchData(Map<Object,Object> data, I id);

    public ResponseModel delete(I id);

    public void validateAdd(T data);

    public void validateEdit(T data);

    public void validateDelete(I id);
}