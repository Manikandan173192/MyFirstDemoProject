package com.maxbyte.sam.SecondaryDBFlow.IB.Repository;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IB;
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
public interface IBEntityRepository extends JpaRepository<IB, Integer>, JpaSpecificationExecutor<IB> {


    /*@Query("SELECT COALESCE(MAX(e.id), 0) FROM IBEntity e")
    Integer findMaxId();*/

    List<IB> findByActive(boolean isActive);
    List<IB> findByIbNumber(String ibNo);
    List<IB> findByOrganization(String orgCode);
    //Page<IB> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime, Pageable page);

    List<IB> findByOrganizationAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime);
    Page<IB> findByOrganizationAndCreatedOnBetween(String orgCode, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    @Query("SELECT i FROM IB i WHERE i.status = 0")
    List<IB> findOpenIB();

    @Query("SELECT i FROM IB i WHERE i.status = 1")
    List<IB> findInProgressIB();

    @Query("SELECT i FROM IB i WHERE i.status = 2")
    List<IB> findCompleteIB();
    @Query("SELECT i FROM IB i WHERE i.status = 3")
    List<IB> findPendingIB();
    @Query("SELECT i FROM IB i WHERE i.status = 4")
    List<IB> findRevertBackIB();
    @Query("SELECT i FROM IB i WHERE i.status = 5")
    List<IB> findClosedIB();
    List<IB> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<IB> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime startTime, LocalDateTime endTime);
    List<IB> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime);

    Page<IB> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<IB> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    Page<IB> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable);
    @Query("SELECT DISTINCT i.area FROM IB i")
    List<String> findByArea();

    @Query("SELECT i FROM IB i WHERE i.status BETWEEN 0 AND 1 AND i.preparerName = :preparerName AND i.organization = :organizationCode")
    List<IB> findPending(String preparerName, String organizationCode);

    @Query("SELECT i FROM IB i WHERE i.status BETWEEN 0 AND 3 AND i.organization = :organizationCode AND " +
            "((i.approverName1 = :username AND (i.approverStatus1 IS NULL OR i.approverStatus1 = 0)) OR " +
            "(i.approverName2 = :username AND (i.approverStatus2 IS NULL OR i.approverStatus2 = 0)) OR " +
            "(i.approverName3 = :username AND (i.approverStatus3 IS NULL OR i.approverStatus3 = 0)))")
    List<IB> findByUsernameAndOrganizationCodeWithPendingStatus(String username, String organizationCode);

    //          DashBoard

    @Query("SELECT i FROM IB i WHERE i.status = 0 AND i.organization = :organizationCode AND " +
            "((i.preparerName = :username) OR (i.approverName1 = :username) OR (i.approverName2 = :username ) OR (i.approverName3 = :username ))")
    List<IB> findOpenIB(String username, String organizationCode);

    @Query("SELECT i FROM IB i WHERE i.status = 1 AND i.organization = :organizationCode AND " +
            "((i.preparerName = :username) OR (i.approverName1 = :username) OR (i.approverName2 = :username ) OR (i.approverName3 = :username ))")
    List<IB> findInProgressIB(String username, String organizationCode);

    @Query("SELECT i FROM IB i WHERE i.status = 2 AND i.organization = :organizationCode AND " +
            "((i.preparerName = :username) OR (i.approverName1 = :username) OR (i.approverName2 = :username ) OR (i.approverName3 = :username ))")
    List<IB> findCompleteIB(String username, String organizationCode);

    @Query("SELECT i FROM IB i WHERE i.status = 3 AND i.organization = :organizationCode AND " +
            "((i.preparerName = :username) OR (i.approverName1 = :username) OR (i.approverName2 = :username ) OR (i.approverName3 = :username ))")
    List<IB> findPendingIB(String username, String organizationCode);

    @Query("SELECT i FROM IB i WHERE i.status = 4 AND i.organization = :organizationCode AND " +
            "((i.preparerName = :username) OR (i.approverName1 = :username) OR (i.approverName2 = :username ) OR (i.approverName3 = :username ))")
    List<IB> findRevertBackIB(String username, String organizationCode);

    @Query("SELECT i FROM IB i WHERE i.status = 5 AND i.organization = :organizationCode AND " +
            "((i.preparerName = :username) OR (i.approverName1 = :username) OR (i.approverName2 = :username ) OR (i.approverName3 = :username ))")
    List<IB> findClosedIB(String username, String organizationCode);
    }
