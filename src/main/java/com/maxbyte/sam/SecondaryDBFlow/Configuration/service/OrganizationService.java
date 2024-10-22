package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Department;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Organization;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.DepartmentRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.OrganizationRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.OrganizationSpecificationBuilder;
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
public class OrganizationService extends CrudService<Organization,Integer> {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Value("${pagination.default-size}")
    private int defaultSize;
    @Override
    public CrudRepository repository() {
        return this.organizationRepository;
    }


    //bug fixed unique name
    @Override
    public void validateAdd(Organization data) {
        try{
            Optional<Organization> existingOrganization = organizationRepository.findByOrganizationCode(data.getOrganizationCode());
            if (existingOrganization.isPresent()) {
                throw new IllegalArgumentException("Organization code already exists: " + data.getOrganizationCode());
            }
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(Organization data) {
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

    public ResponseModel<List<Organization>> list( String organizationCode, String organizationDescription,Boolean isActive, String requestPage) {

        try {
            OrganizationSpecificationBuilder builder = new OrganizationSpecificationBuilder();
            var count = organizationRepository.findAll().size();
            if(isActive!=null)builder.with("isActive",":",isActive);


            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());

//            var count = departmentRepository.findAll().size();
            Page<Organization> results = organizationRepository.findByOrganizationCodeAndOrganizationDescriptionAndIsActive(organizationCode, organizationDescription,isActive, pageRequest);

            List<Organization> orgList = organizationRepository.findAll();
            var totalCount = String.valueOf(orgList.size());
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

    //    @Scheduled(cron = "0 */1 * * * *") // Run every minute
    public void updateOrganizationData() {
        // Fetch all departments from the local repository
        List<Department> departments = departmentRepository.findAll();

        for (Department department : departments) {
            // Look for existing organizations by organization code
            Optional<Organization> existingOrganization = organizationRepository.findByOrganizationCode(department.getOrganizationCode());

            Organization organization;
            if (existingOrganization.isPresent()) {
                // Update existing organization
                organization = existingOrganization.get();
                organization.setOrganizationCode(department.getOrganizationCode());
                organization.setOrganizationDescription(department.getDepartmentDescription());
                organization.setIsActive(true);
                System.out.println("Updated Organization with OrganizationCode = " + organization.getOrganizationCode());
            } else {
                // Insert new organization
                organization = new Organization();
                organization.setOrganizationId(department.getOrganizationId()); // If auto-generated, this line can be removed
                organization.setOrganizationCode(department.getOrganizationCode());
                organization.setOrganizationDescription(department.getDepartmentDescription());
                organization.setIsActive(true);
                System.out.println("Inserted new Organization with OrganizationCode = " + organization.getOrganizationCode());
            }

            // Save the organization to the repository
            organizationRepository.save(organization);
        }
    }

}