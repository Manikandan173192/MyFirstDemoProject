package com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> , JpaSpecificationExecutor<Department> {
    Optional<Department> findByDepartment(String department);
    Optional<Department> findByDepartmentId(Integer department);

//    Page<Department> findByDepartmentAndDepartmentDescription(String department, String departmentDescription, PageRequest pageRequest);
    @Query("SELECT d FROM Department d WHERE (:department IS NULL OR d.department LIKE %:department%) AND (:departmentDescription IS NULL OR d.departmentDescription LIKE %:departmentDescription%) AND (:organizationCode IS NULL OR d.organizationCode LIKE %:organizationCode%)")
    Page<Department> findByDepartmentAndDepartmentDescriptionAndOrganizationCode(String department, String departmentDescription,String organizationCode,PageRequest pageRequest);

    Optional<Department> findByEamDepartmentId(Integer eamDepartmentId);


}
