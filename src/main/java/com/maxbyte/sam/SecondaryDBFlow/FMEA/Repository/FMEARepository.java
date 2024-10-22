package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;



import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPA;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEA;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FMEARepository extends JpaRepository<FMEA, Integer>, JpaSpecificationExecutor<FMEA> {
    List<FMEA> findByCreatedOnBetween(LocalDateTime startTime, LocalDateTime endTime);
    List<FMEA> findByFmeaNumber(String fmeaNumber);


    List<FMEA> findAllByFmeaNumber(String fmeaNumber);

    List<FMEA> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime from, LocalDateTime to);
    Page<FMEA> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime from, LocalDateTime to, Pageable pageable);

    List<FMEA> findByOrganizationCode(String organizationCode);


    @Query("SELECT r FROM FMEA r WHERE r.status = 0")

    List<FMEA> findOpenFMEAs();

    @Query("SELECT r FROM FMEA r WHERE r.status = 1")

    List<FMEA> findInProgressFMEAs();

    @Query("SELECT r FROM FMEA r WHERE r.status = 2")

    List<FMEA> findCompleteFMEAs();

    @Query("SELECT r FROM FMEA r WHERE r.status = 3")

    List<FMEA> findRevertBackFMEAs();

    @Query("SELECT r FROM FMEA r WHERE r.status = 4")

    List<FMEA> findClosedFMEAs();

    List<FMEA> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime startTime, LocalDateTime endTime);
    List<FMEA> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime);
    Page<FMEA> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<FMEA> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    Page<FMEA> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    @Query("SELECT DISTINCT f.area FROM FMEA f")
    List<String> findByArea();

    @Query("SELECT f FROM FMEA f WHERE f.status BETWEEN 0 AND 1  AND f.preparerName = :preparerName AND f.organizationCode = :organizationCode")
    List<FMEA> findPending(String preparerName, String organizationCode);

    //          DashBoard

    @Query("SELECT f FROM FMEA f WHERE f.status = 2 AND f.organizationCode = :organizationCode AND " +
            "f.approverName = :username AND (f.approverStatus IS NULL OR f.approverStatus = 0)")
    List<FMEA> findByUsernameAndOrganizationCodeWithPendingStatus(String username, String organizationCode);

    @Query("SELECT f FROM FMEA f WHERE f.status = 0 AND f.organizationCode = :organizationCode AND " +
            "((f.preparerName = :username) OR (f.approverName = :username))")
    List<FMEA> findOpenFMEAs(String username, String organizationCode);

    @Query("SELECT f FROM FMEA f WHERE f.status = 1 AND f.organizationCode = :organizationCode AND " +
            "((f.preparerName = :username) OR (f.approverName = :username))")
    List<FMEA> findInProgressFMEAs(String username, String organizationCode);

    @Query("SELECT f FROM FMEA f WHERE f.status = 2 AND f.organizationCode = :organizationCode AND " +
            "((f.preparerName = :username) OR (f.approverName = :username))")
    List<FMEA> findCompleteFMEAs(String username, String organizationCode);

    @Query("SELECT f FROM FMEA f WHERE f.status = 3 AND f.organizationCode = :organizationCode AND " +
            "((f.preparerName = :username) OR (f.approverName = :username))")
    List<FMEA> findRevertBackFMEAs(String username, String organizationCode);

    @Query("SELECT f FROM FMEA f WHERE f.status = 4 AND f.organizationCode = :organizationCode AND " +
            "((f.preparerName = :username) OR (f.approverName = :username))")
    List<FMEA> findClosedFMEAs(String username, String organizationCode);
}
