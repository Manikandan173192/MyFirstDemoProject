package com.maxbyte.sam.SecondaryDBFlow.CWF.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWF;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CWFRepository extends JpaRepository<CWF,Integer>, JpaSpecificationExecutor<CWF> {
    List<CWF> findByDocumentNumber(String documentNumber);
    List<CWF> findByOrganizationCode(String orgCode);
    Page<CWF> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    List<CWF> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT c FROM CWF c WHERE c.status = 0")
    List<CWF> findOpenCWF();

    @Query("SELECT c FROM CWF c WHERE c.status = 1")
    List<CWF> findCompleteCWF();
    @Query("SELECT c FROM CWF c WHERE c.status = 2")
    List<CWF> findPendingCWF();
    @Query("SELECT c FROM CWF c WHERE c.status = 3")
    List<CWF> findRevertBackCWF();
    @Query("SELECT c FROM CWF c WHERE c.status = 4")
    List<CWF> findClosedCWF();
    Page<CWF> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<CWF> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    Page<CWF> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    @Query("SELECT DISTINCT c.area FROM CWF c")
    List<String> findByArea();

    @Query("SELECT c FROM CWF c WHERE c.status = 0 AND c.initiatorName = :initiatorName AND c.organizationCode = :organizationCode")
    List<CWF> findPending(String initiatorName, String organizationCode);

    @Query("SELECT c FROM CWF c WHERE c.status IN (1,2) AND c.organizationCode = :organizationCode AND " +
            "((c.firstApprover = :username AND (c.validateApprover1 IS NULL OR c.validateApprover1 = 0)) OR " +
            "(c.secondApprover = :username AND (c.validateApprover2 IS NULL OR c.validateApprover2 = 0)) OR " +
            "(c.thirdApprover = :username AND (c.validateApprover3 IS NULL OR c.validateApprover3 = 0)))")
    List<CWF> findByUsernameAndOrganizationCodeWithPendingStatus(String username, String organizationCode);

    //      DashBoard

    @Query("SELECT c FROM CWF c WHERE c.status = 0 AND c.organizationCode = :organizationCode AND " +
            "((c.initiatorName = :username) OR (c.firstApprover = :username) OR (c.secondApprover = :username) OR (c.thirdApprover = :username))")
    List<CWF> findOpenCWF(String username, String organizationCode);

    @Query("SELECT c FROM CWF c WHERE c.status = 1 AND c.organizationCode = :organizationCode AND " +
            "((c.initiatorName = :username) OR (c.firstApprover = :username) OR (c.secondApprover = :username) OR (c.thirdApprover = :username))")
    List<CWF> findCompleteCWF(String username, String organizationCode);

    @Query("SELECT c FROM CWF c WHERE c.status = 2 AND c.organizationCode = :organizationCode AND " +
            "((c.initiatorName = :username) OR (c.firstApprover = :username) OR (c.secondApprover = :username) OR (c.thirdApprover = :username))")
    List<CWF> findPendingCWF(String username, String organizationCode);

    @Query("SELECT c FROM CWF c WHERE c.status = 3 AND c.organizationCode = :organizationCode AND " +
            "((c.initiatorName = :username) OR (c.firstApprover = :username) OR (c.secondApprover = :username) OR (c.thirdApprover = :username))")
    List<CWF> findRevertBackCWF(String username, String organizationCode);

    @Query("SELECT c FROM CWF c WHERE c.status = 4 AND c.organizationCode = :organizationCode AND " +
            "((c.initiatorName = :username) OR (c.firstApprover = :username) OR (c.secondApprover = :username) OR (c.thirdApprover = :username))")
    List<CWF> findClosedCWF(String username, String organizationCode);
}
