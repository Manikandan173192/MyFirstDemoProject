package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;


import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Department;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.DepartmentRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.DepartmentSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DepartmentService extends CrudService<Department,Integer> {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Value("${pagination.default-size}")
    private int defaultSize;

    @Override
    public CrudRepository repository() {
        return this.departmentRepository;
    }

    @Override
    public void validateAdd(Department data) {
        try {
            Optional<Department> existingDepartment = departmentRepository.findByDepartment(data.getDepartment());
            if (existingDepartment.isPresent()) {
                throw new IllegalArgumentException("Department already exists: " + data.getDepartment());
            }
        } catch (Error e) {
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(Department data) {
        try {
        } catch (Error e) {
            throw new Error(e);
        }
    }

    @Override
    public void validateDelete(Integer id) {
        try {

        } catch (Error e) {
            throw new Error(e);
        }
    }

    public ResponseModel<List<Department>> list(String department, String departmentDescription,String organizationCode, String requestPage) {

        try {
            DepartmentSpecificationBuilder builder = new DepartmentSpecificationBuilder();
            if (department != null) builder.with("department", ":", department);
            if (departmentDescription != null) builder.with("departmentDescription", ":", departmentDescription);

            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize);

//            var count = departmentRepository.findAll().size();
            Page<Department> results = departmentRepository.findByDepartmentAndDepartmentDescriptionAndOrganizationCode(department, departmentDescription,organizationCode, pageRequest);

            List<Department> deptList = departmentRepository.findAll();
            var totalCount = String.valueOf(deptList.size());
            var filteredCount = String.valueOf(results.getContent().size());
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", results.getContent().reversed());
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Records not found", null);
        }
    }


//

    ////////////////           updated code fetching from EAM             /////////////////

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //    @Scheduled(cron = "0 */1 * * * *") // Run every 1 minute
    public void fetchAndSaveDepartmentData() {
        String sql = "SELECT * FROM apps.XXHIL_EAM_DEPT_V";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> row : results) {
            // Handle potential null values
            Integer eamDepartmentId = (row.get("DEPARTMENT_ID") != null) ? ((BigDecimal) row.get("DEPARTMENT_ID")).intValueExact() : null;
            Integer locationId = (row.get("LOCATION_ID") != null) ? ((BigDecimal) row.get("LOCATION_ID")).intValueExact() : null;
            Integer organizationId = (row.get("ORGANIZATION_ID") != null) ? ((BigDecimal) row.get("ORGANIZATION_ID")).intValueExact() : null;

            Optional<Department> existingDepartmentOpt = departmentRepository.findByEamDepartmentId(eamDepartmentId);

            if (existingDepartmentOpt.isPresent()) {
                // Update the existing record
                Department existingDepartment = existingDepartmentOpt.get();
                existingDepartment.setDepartment((String) row.get("DEPARTMENT_CODE"));
                existingDepartment.setDepartmentDescription((String) row.get("DESCRIPTION"));
                existingDepartment.setLocationId(locationId);
                existingDepartment.setLocationCode((String) row.get("LOCATION_CODE"));
                existingDepartment.setLocationDescription((String) row.get("LOCATION_DESCRIPTION"));
                existingDepartment.setMaintCostCategoryValue((String) row.get("MAINT_COST_CATEGORY_VALUE"));
                existingDepartment.setOrganizationId(organizationId);
                existingDepartment.setOrganizationCode((String) row.get("ORGANIZATION_CODE"));

                departmentRepository.save(existingDepartment);
                System.out.println("Updated Department with eamDepartmentId = " + existingDepartment.getEamDepartmentId());
            } else {
                // Insert a new record
                Department newDepartment = new Department();
                newDepartment.setEamDepartmentId(eamDepartmentId);
                newDepartment.setDepartment((String) row.get("DEPARTMENT_CODE"));
                newDepartment.setDepartmentDescription((String) row.get("DESCRIPTION"));
                newDepartment.setLocationId(locationId);
                newDepartment.setLocationCode((String) row.get("LOCATION_CODE"));
                newDepartment.setLocationDescription((String) row.get("LOCATION_DESCRIPTION"));
                newDepartment.setMaintCostCategoryValue((String) row.get("MAINT_COST_CATEGORY_VALUE"));
                newDepartment.setOrganizationId(organizationId);
                newDepartment.setOrganizationCode((String) row.get("ORGANIZATION_CODE"));

                departmentRepository.save(newDepartment);
                System.out.println("Inserted new Department with eamDepartmentId = " + newDepartment.getEamDepartmentId());
            }
        }
    }

}

