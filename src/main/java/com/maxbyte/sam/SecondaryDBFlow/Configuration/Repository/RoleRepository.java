package com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Organization;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> , JpaSpecificationExecutor<Role> {
    Optional<Role> findByRoleName(String roleName);

    @Query("SELECT r FROM Role r WHERE (:roleName IS NULL OR r.roleName LIKE %:roleName%) AND (:roleDescription IS NULL OR r.roleDescription LIKE %:roleDescription%) AND (:isActive IS NULL OR r.isActive = :isActive)")
    Page<Role> findByRoleNameAndRoleDescriptionAndIsActive(String roleName, String roleDescription, Boolean isActive, PageRequest pageRequest);

}
