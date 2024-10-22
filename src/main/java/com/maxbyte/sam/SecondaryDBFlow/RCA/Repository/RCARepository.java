package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IB;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RCARepository extends JpaRepository<RCA, Integer> , JpaSpecificationExecutor<RCA> {

   // List<RCA> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime);
    Page<RCA> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    List<RCA> findByRcaNumber(String rcaNo);
    List<RCA> findByOrganizationCode(String orgCode);

    @Query("SELECT r FROM RCA r WHERE r.status = 0")
    List<RCA> findOpenRCAs();

    @Query("SELECT r FROM RCA r WHERE r.status BETWEEN 1 AND 7")
    List<RCA> findInProgressRCAs();

    @Query("SELECT r FROM RCA r WHERE r.status = 8")
    List<RCA> findCompleteRCAs();
    @Query("SELECT r FROM RCA r WHERE r.status = 9")
    List<RCA> findPendingRCAs();
    @Query("SELECT r FROM RCA r WHERE r.status = 10")
    List<RCA> findRevertBackRCAs();
    @Query("SELECT r FROM RCA r WHERE r.status = 11")
    List<RCA> findClosedRCAs();
    List<RCA> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<RCA> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime startTime, LocalDateTime endTime);
    List<RCA> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime);

    Page<RCA> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<RCA> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    Page<RCA> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable);


     @Query("SELECT DISTINCT r.area FROM RCA r")
     List<String> findByArea();

     Page<RCA> findByOrganizationCodeAndDepartmentAndCreatedOnBetween(String organizationCode, String department, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

     Page<RCA> findByOrganizationCodeAndAreaAndCreatedOnBetween(String organizationCode, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

     Page<RCA> findByDepartmentAndAreaAndCreatedOnBetween(String department, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

     Page<RCA> findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(String organizationCode, String department, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

     @Query("SELECT r FROM RCA r WHERE r.status BETWEEN 0 AND 7 AND r.createdBy = :createdBy AND r.organizationCode = :organizationCode")
     List<RCA> findPending(String createdBy, String organizationCode);

     @Query("SELECT r FROM RCA r WHERE r.status IN (8, 9) AND r.organizationCode = :organizationCode AND " +
             "((r.approver1Name = :username AND (r.approver1Status IS NULL OR r.approver1Status = 0)) OR " +
             "(r.approver2Name = :username AND (r.approver2Status IS NULL OR r.approver2Status = 0)) OR " +
             "(r.approver3Name = :username AND (r.approver3Status IS NULL OR r.approver3Status = 0)))")
     List<RCA> findByUsernameAndOrganizationCodeWithPendingStatus(String username, String organizationCode);

     //     DashBoard

     @Query("SELECT r FROM RCA r WHERE r.organizationCode = :organizationCode AND " +
             "((r.createdBy = :username) OR (r.approver1Name = :username) OR " +
             "(r.approver2Name = :username) OR (r.approver3Name = :username)) AND r.status = 0")
     List<RCA> findOpenRCAs(String username, String organizationCode);

     @Query("SELECT r FROM RCA r WHERE r.organizationCode = :organizationCode AND " +
             "((r.createdBy = :username) OR (r.approver1Name = :username) OR " +
             "(r.approver2Name = :username) OR (r.approver3Name = :username)) AND r.status BETWEEN 1 AND 7")
     List<RCA> findInProgressRCAs(String username, String organizationCode);

     @Query("SELECT r FROM RCA r WHERE r.organizationCode = :organizationCode AND " +
             "((r.createdBy = :username) OR (r.approver1Name = :username) OR " +
             "(r.approver2Name = :username) OR (r.approver3Name = :username)) AND r.status = 8")
     List<RCA> findCompleteRCAs(String username, String organizationCode);

     @Query("SELECT r FROM RCA r WHERE r.organizationCode = :organizationCode AND " +
             "((r.createdBy = :username) OR (r.approver1Name = :username) OR " +
             "(r.approver2Name = :username) OR (r.approver3Name = :username)) AND r.status = 9")
     List<RCA> findPendingRCAs(String username, String organizationCode);

     @Query("SELECT r FROM RCA r WHERE r.organizationCode = :organizationCode AND " +
             "((r.createdBy = :username) OR (r.approver1Name = :username) OR " +
             "(r.approver2Name = :username) OR (r.approver3Name = :username)) AND r.status = 10")
     List<RCA> findRevertBackRCAs(String username, String organizationCode);

     @Query("SELECT r FROM RCA r WHERE r.organizationCode = :organizationCode AND " +
             "((r.createdBy = :username) OR (r.approver1Name = :username) OR " +
             "(r.approver2Name = :username) OR (r.approver3Name = :username)) AND r.status = 11")
     List<RCA> findClosedRCAs(String username, String organizationCode);
}
