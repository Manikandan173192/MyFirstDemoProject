package com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository;


import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> , JpaSpecificationExecutor<Organization> {
    Optional<Organization> findByOrganizationCode(String organizationCode);

    Optional<Organization> findByOrganizationId(Integer organizationId);

    @Query("SELECT o FROM Organization o WHERE (:organizationCode IS NULL OR o.organizationCode LIKE %:organizationCode%) AND (:organizationDescription IS NULL OR o.organizationDescription LIKE %:organizationDescription%) AND (:isActive IS NULL OR o.isActive = :isActive)")
    Page<Organization> findByOrganizationCodeAndOrganizationDescriptionAndIsActive(String organizationCode, String organizationDescription, Boolean isActive, PageRequest pageRequest);
}


