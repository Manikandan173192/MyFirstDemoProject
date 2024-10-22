package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public abstract class CrudController<T,I> {

    public abstract ServiceInterface<T,I> service();
    /*@GetMapping("")
    public List<T> list() { return service().list() ; }*/

    @GetMapping("/{id}")
    public Optional<T> retrieve(@PathVariable I id) { return service().retrieve((I) id);}

    @PostMapping("")
    public ResponseModel add(@Valid @RequestBody T data) {
        try {
            return service().add(data) ;
        }
        catch(Exception e){
            return new ResponseModel<>(false,e.getMessage());
        }

    }

   /* @PutMapping("/{id}")
    public ResponseModel update(@Valid @RequestBody T data,@PathVariable I id) {
        try {
            return service().update(data,id) ;
        } catch(Exception e){
            return new ResponseModel<>(false,e.getMessage());
        }
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<T>> update(@Valid @RequestBody T data, @PathVariable I id) {
        try {
            ResponseModel<T> response = service().update(data, id);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel<>(false, "Error updating entity: " + e.getMessage(), null));
        }
    }

    @PatchMapping("{id}")
    public ResponseModel patchData(@Valid @RequestBody Map<Object,Object> data, @PathVariable I id){
        try {
            return service().patchData(data,id) ;
        } catch(Exception e){
            return new ResponseModel<>(false,e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseModel delete(@Valid @PathVariable I id) throws IOException { return service().delete((I) id);}
}
