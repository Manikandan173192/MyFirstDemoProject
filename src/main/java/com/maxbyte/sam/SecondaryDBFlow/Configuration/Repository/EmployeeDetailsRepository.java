package com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.EmployeeDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Integer> {

    Optional<EmployeeDetails> findByEmpCodeAndEmailId(String empCode, String emailId);


//    @Query("SELECT e FROM EmployeeDetails e WHERE "
//            + "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR "
//            + "LOWER(e.middleName) LIKE LOWER(CONCAT('%', :name, '%')) OR "
//            + "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
//    Page<EmployeeDetails> findByName(String name, Pageable pageable);


    @Query("SELECT e FROM EmployeeDetails e WHERE "
            + "(LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL) OR "
            + "(LOWER(e.middleName) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL) OR "
            + "(LOWER(e.lastName) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL) OR"
            + "(LOWER(e.fullName) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL)")
    Page<EmployeeDetails> findByName(String name, Pageable pageable);

//    @Query("SELECT e FROM EmployeeDetails e WHERE "
//            + "(:name IS NULL OR ("
//            + "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR "
//            + "LOWER(e.middleName) LIKE LOWER(CONCAT('%', :name, '%')) OR "
//            + "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :name, '%')) OR "
//            + "LOWER(e.fullName) LIKE LOWER(CONCAT('%', :name, '%')))) "
//            + "AND ("
//            + "(:userType = 'HINDALCO INDUSTRIES LTD' AND LOWER(e.CONTRACTOR_FIRM_NAME) = LOWER(:userType)) "
//            + "OR (:userType = 'NON-HINDALCO INDUSTRIES LTD' AND LOWER(e.CONTRACTOR_FIRM_NAME) <> LOWER('HINDALCO INDUSTRIES LTD')))")
//    Page<EmployeeDetails> findByNameAndContractor(String name, String userType, Pageable pageable);

//    List<EmployeeDetails> findByCreatedBy();

    @Query("SELECT e FROM EmployeeDetails e WHERE "
            // Handle name search if name is not null
            + "(:name IS NULL OR ("
            + "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR "
            + "COALESCE(LOWER(e.middleName), '') LIKE LOWER(CONCAT('%', :name, '%')) OR "
            + "COALESCE(LOWER(e.lastName), '') LIKE LOWER(CONCAT('%', :name, '%')) OR "
            + "LOWER(e.fullName) LIKE LOWER(CONCAT('%', :name, '%')))) "
            // Handle organizationCode filtering if provided
            + "AND (:organizationCode IS NULL OR LOWER(e.organizationCode) = LOWER(:organizationCode)) "
            // Handle contractor type filtering
            + "AND (:userType IS NULL OR ("
            + "(:userType = 'HINDALCO INDUSTRIES LTD' AND LOWER(e.contractorFirmName) = LOWER(:userType)) "
            + "OR (:userType = 'NON-HINDALCO INDUSTRIES LTD' AND LOWER(e.contractorFirmName) <> LOWER('HINDALCO INDUSTRIES LTD'))))")
    Page<EmployeeDetails> findByNameAndContractorFirmNameAndOrganizationCode(
             String name,
             String userType,
             String organizationCode,
            Pageable pageable);

    @Query("SELECT MAX(e.lastUpdateDate) FROM EmployeeDetails e")
    LocalDateTime findLatestUpdateTime();


    @Query(value = "SELECT * FROM apps.XXHIL_EAM_CLMS_EMP_MAST_INT WHERE LAST_UPDATE_DATE > :lastUpdateTime", nativeQuery = true)
    List<Object[]> findNewEmployees(@Param("lastUpdateTime") LocalDateTime lastUpdateTime);

    boolean existsByEmpCodeAndEmailId(String empCode, String emailId);

}
