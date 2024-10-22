package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;


import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Organization;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Role;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.RoleRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.RoleSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService extends CrudService<Role,Integer> {

    @Autowired
    private RoleRepository roleRepository;
    @Value("${pagination.default-size}")
    private int defaultSize;


    @Override
    public CrudRepository repository() {
        return this.roleRepository;
    }

    @Override
    public void validateAdd(Role data) {
        try{
            Optional<Role> existingRole = roleRepository.findByRoleName(data.getRoleName());
            if (existingRole.isPresent()) {
                throw new IllegalArgumentException("Role name already exists: " + data.getRoleName());
            }
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(Role data) {
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

    public ResponseModel<List<Role>> list(Boolean isActive, String roleName, String roleDescription, String requestPage) {
        try {
            RoleSpecificationBuilder builder = new RoleSpecificationBuilder();
            if(isActive!=null)builder.with("isActive",":",isActive);

           // List<Role> results = roleRepository.findAll(builder.build());
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());

//            var count = departmentRepository.findAll().size();
            Page<Role> results = roleRepository.findByRoleNameAndRoleDescriptionAndIsActive(roleName, roleDescription,isActive, pageRequest);

            List<Role> roleList = roleRepository.findAll();
            var totalCount = String.valueOf(roleList.size());
            var filteredCount = String.valueOf(results.getContent().size());
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount+ " Records found & " + filteredCount + " Filtered", results.getContent().reversed());
            }
        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }
}
