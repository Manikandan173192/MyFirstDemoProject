package com.maxbyte.sam.SecondaryDBFlow.SA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IB;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface SARepository extends JpaRepository<SA, Integer> , JpaSpecificationExecutor<SA> {


    List<SA> findByAssetNumber(String assetNumber);

    List<SA> findByAssetNumberAndCheckListType(String assetNumber,String checkListType);


    @Modifying
    @Transactional
    List<SA>deleteByAssetNumber(String assetNumber);

    List<SA> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime from, LocalDateTime to);
    Page<SA> findByOrganizationCodeAndCreatedOnBetween(String orgCode, LocalDateTime from, LocalDateTime to, Pageable pageable);
    List<SA> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<SA> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime startTime, LocalDateTime endTime);
    List<SA> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime);

    Page<SA> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<SA> findByAreaAndCreatedOnBetween(String area, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);
    Page<SA> findByDepartmentAndCreatedOnBetween(String department, LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable);

    @Query("SELECT DISTINCT s.area FROM SA s")
    List<String> findByArea();

    @Query("SELECT s FROM SA s WHERE s.tStatus = 0  AND s.initiatorName = :initiatorName AND s.organizationCode = :organizationCode")
    List<SA> findPending(String initiatorName, String organizationCode);

    //          DashBoard

    @Query("SELECT r FROM SA r WHERE r.tStatus = 0")
    List<SA> findOpenSAs();

    @Query("SELECT r FROM SA r WHERE r.tStatus= 1")
    List<SA> findApproverPendingSAs();

    @Query("SELECT r FROM SA r WHERE r.tStatus = 2")
    List<SA> findApprovedSAs();

    @Query("SELECT s FROM SA s WHERE s.tStatus = 0 AND s.organizationCode = :organizationCode AND " +
            "s.approverName = :username AND (s.approverStatus IS NULL OR s.approverStatus = 0)")
    List<SA> findByUsernameAndOrganizationCodeWithPendingStatus(String username, String organizationCode);

    @Query("SELECT s FROM SA s WHERE s.tStatus = 0 AND s.organizationCode = :organizationCode AND " +
            "((s.initiatorName = :username) OR (s.approverName = :username))")
    List<SA> findOpenSAs(String username, String organizationCode);

    @Query("SELECT s FROM SA s WHERE s.tStatus = 1 AND s.organizationCode = :organizationCode AND " +
            "((s.initiatorName = :username) OR (s.approverName = :username))")
    List<SA> findApproverPendingSAs(String username, String organizationCode);

    @Query("SELECT s FROM SA s WHERE s.tStatus = 2 AND s.organizationCode = :organizationCode AND " +
            "((s.initiatorName = :username) OR (s.approverName = :username))")
    List<SA> findApprovedSAs(String username, String organizationCode);
}
