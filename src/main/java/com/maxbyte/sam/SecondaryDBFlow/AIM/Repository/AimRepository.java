package com.maxbyte.sam.SecondaryDBFlow.AIM.Repository;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AimRepository extends JpaRepository<Aim, Integer> , JpaSpecificationExecutor<Aim> {

    List<Aim> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime);
    Page<Aim> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime, Pageable page);

    List<Aim> findByAimNumber(String aimNo);
    List<Aim> findByOrganizationCode(String orgCode);

    @Query("SELECT a FROM Aim a WHERE a.status = 0")
    List<Aim> findOpenAim();
    @Query("SELECT a FROM Aim a WHERE a.status = 2")
    List<Aim> findRevertBackAim();
    @Query("SELECT a FROM Aim a WHERE a.status = 1")
    List<Aim> findClosedAim();


    Page<Aim> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Aim> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    @Query("SELECT DISTINCT a.area FROM Aim a")
    List<String> findByArea();

    Page<Aim> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable);


    Page<Aim> findByOrganizationCodeAndDepartmentAndAreaAndCreatedOnBetween(String organizationCode, String department, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<Aim> findByOrganizationCodeAndDepartmentAndCreatedOnBetween(String organizationCode, String department, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<Aim> findByOrganizationCodeAndAreaAndCreatedOnBetween(String organizationCode, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<Aim> findByDepartmentAndAreaAndCreatedOnBetween(String department, String area, LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    @Query("SELECT a FROM Aim a WHERE a.status = 0  AND a.createdBy = :createdBy AND a.organizationCode = :organizationCode")
    List<Aim> findPending(String createdBy, String organizationCode);

    //      Dashboard
    @Query("SELECT a FROM Aim a WHERE a.status = 0 AND a.organizationCode = :organizationCode AND " +
            "a.approverName = :username AND (a.approverStatus IS NULL OR a.approverStatus = 0)")
    List<Aim> findByUsernameAndOrganizationCodeWithPendingStatus(String username, String organizationCode);

    @Query("SELECT a FROM Aim a WHERE a.status = 0 AND a.organizationCode = :organizationCode AND " +
            "((a.createdBy = :username) OR (a.approverName = :username))")
    List<Aim> findOpenAim(String username,String organizationCode);

    @Query("SELECT a FROM Aim a WHERE a.status = 2 AND a.organizationCode = :organizationCode AND " +
            "((a.createdBy = :username) OR (a.approverName = :username))")
    List<Aim> findRevertBackAim(String username, String organizationCode);

    @Query("SELECT a FROM Aim a WHERE a.status = 1 AND a.organizationCode = :organizationCode AND " +
            "((a.createdBy = :username) OR (a.approverName = :username))")
    List<Aim> findClosedAim(String username, String organizationCode);
}
