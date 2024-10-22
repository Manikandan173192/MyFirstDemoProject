package com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CAPARepository extends JpaRepository<CAPA, Integer> , JpaSpecificationExecutor<CAPA> {
    List<CAPA> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime);
    Page<CAPA> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    List<CAPA> findByCapaNumber(String capaNo);

    List<CAPA> findByOrganizationCode(String orgCode);

    @Query("SELECT r FROM CAPA r WHERE r.status = 0")
    List<CAPA> findOpenCAPAs();

    @Query("SELECT r FROM CAPA r WHERE r.status BETWEEN 1 AND 2")
    List<CAPA> findInProgressCAPAs();

    @Query("SELECT r FROM CAPA r WHERE r.status = 3")
    List<CAPA> findCompleteCAPAs();
    @Query("SELECT r FROM CAPA r WHERE r.status = 4")
    List<CAPA> findPendingCAPAs();
    @Query("SELECT r FROM CAPA r WHERE r.status = 5")
    List<CAPA> findRevertBackCAPAs();
    @Query("SELECT r FROM CAPA r WHERE r.status = 6")
    List<CAPA> findClosedCAPAs();
    Page<CAPA> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<CAPA> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    Page<CAPA> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    @Query("SELECT DISTINCT c.area FROM CAPA c")
    List<String> findByArea();

    Page<CAPA> findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(String organizationCode, String department, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<CAPA> findByOrganizationCodeAndDepartmentAndCreatedOnBetween(String organizationCode, String department, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<CAPA> findByOrganizationCodeAndAreaAndCreatedOnBetween(String organizationCode, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<CAPA> findByDepartmentAndAreaAndCreatedOnBetween(String department, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    @Query("SELECT c FROM CAPA c WHERE c.status BETWEEN 0 AND 2 AND c.createdBy = :createdBy AND c.organizationCode = :organizationCode")
    List<CAPA> findPending(String createdBy, String organizationCode);

    @Query("SELECT c FROM CAPA c WHERE c.status IN (3, 4) AND c.organizationCode = :organizationCode AND " +
            "((c.approver1Name = :username AND (c.approver1Status IS NULL OR c.approver1Status = 0)) OR " +
            "(c.approver2Name = :username AND (c.approver2Status IS NULL OR c.approver2Status = 0)) OR " +
            "(c.approver3Name = :username AND (c.approver3Status IS NULL OR c.approver3Status = 0)))")
    List<CAPA> findByUsernameAndOrganizationCodeWithPendingStatus(String username, String organizationCode);

    //      DashBoard
    @Query("SELECT c FROM CAPA c WHERE c.status = 0 AND c.organizationCode = :organizationCode AND " +
            "((c.approver1Name = :username) OR (c.approver2Name = :username) OR (c.approver3Name = :username))")
    List<CAPA> findOpenCAPAs(String username, String organizationCode);

    @Query("SELECT c FROM CAPA c WHERE c.status BETWEEN 1 AND 2 AND c.organizationCode = :organizationCode AND " +
            "((c.createdBy = :username) OR (c.approver1Name = :username) OR (c.approver2Name = :username) OR (c.approver3Name = :username))")
    List<CAPA> findInProgressCAPAs(String username, String organizationCode);

    @Query("SELECT c FROM CAPA c WHERE c.status = 3 AND c.organizationCode = :organizationCode AND " +
            "((c.createdBy = :username) OR (c.approver1Name = :username) OR (c.approver2Name = :username) OR (c.approver3Name = :username))")
    List<CAPA> findCompleteCAPAs(String username, String organizationCode);

    @Query("SELECT c FROM CAPA c WHERE c.status = 4 AND c.organizationCode = :organizationCode AND " +
            "((c.createdBy = :username) OR (c.approver1Name = :username) OR (c.approver2Name = :username) OR (c.approver3Name = :username))")
    List<CAPA> findPendingCAPAs(String username, String organizationCode);

    @Query("SELECT c FROM CAPA c WHERE c.status = 5 AND c.organizationCode = :organizationCode AND " +
            "((c.createdBy = :username) OR (c.approver1Name = :username) OR (c.approver2Name = :username) OR (c.approver3Name = :username))")
    List<CAPA> findRevertBackCAPAs(String username, String organizationCode);

    @Query("SELECT c FROM CAPA c WHERE c.status = 6 AND c.organizationCode = :organizationCode AND " +
            "((c.createdBy = :username) OR (c.approver1Name = :username) OR (c.approver2Name = :username) OR (c.approver3Name = :username))")
    List<CAPA> findClosedCAPAs(String username, String organizationCode);

}
