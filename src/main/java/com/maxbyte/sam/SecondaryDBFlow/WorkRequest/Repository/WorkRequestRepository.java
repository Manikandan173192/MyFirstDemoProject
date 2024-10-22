package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Repository;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.EmployeeDetails;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Entity.WorkRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkRequestRepository extends JpaRepository<WorkRequest,Integer>, JpaSpecificationExecutor<WorkRequest> {

//    List<WorkRequest> findByCreatedOnBetween(LocalDateTime startTime, LocalDateTime endTime);

//    WorkRequest findByWRNumber(String WRNumber);

    Optional<WorkRequest> findByRowId(String rowId);

//    @Query("SELECT wr FROM WorkRequest wr WHERE wr.workRequestNumber = :WRNumber OR wr.assetNumber = :assetNumber OR wr.workRequestOwningDeptId = :department OR wr.organizationId = :organizationId")
//    Page<WorkRequest> findByWrNumberAndAssetNumberAndDepartmentAndOrganizationId(String WRNumber, String assetNumber,String department,String organizationId, Pageable pageable);

    @Query("SELECT wr FROM WorkRequest wr WHERE " +
            "(:WRNumber IS NULL OR wr.workRequestNumber = :WRNumber) AND " +
            "(:assetNumber IS NULL OR wr.assetNumber = :assetNumber) AND " +
            "(:department IS NULL OR wr.workRequestOwningDeptId = :department) AND " +
            "(:organizationId IS NULL OR wr.organizationId = :organizationId) AND" +
            "(:workRequestStatus IS NULL OR wr.workRequestStatus = :workRequestStatus)")
    Page<WorkRequest> findByWrNumberAndAssetNumberAndDepartmentAndOrganizationIdAndWorkRequestStatus(
             String WRNumber,
             String assetNumber,
             String department,
             String organizationId,
             String workRequestStatus,
            Pageable pageable
    );


    WorkRequest findByAssetGroupId(Integer assetGroupId);

    boolean existsByWorkRequestNumber(String workRequestNumber);

    List<WorkRequest> findByCreationDateBetween(LocalDateTime from, LocalDateTime to);

    @Query("SELECT wr FROM WorkRequest wr WHERE wr.organizationId = :orgCode AND wr.creationDate BETWEEN :from AND :to")
    Page<WorkRequest> findByOrganizationIdAndCreationDateBetween(String orgCode, LocalDateTime from, LocalDateTime to, PageRequest pageRequest);

    Optional<WorkRequest> findByWorkRequestNumber(String workRequestNumber);
}
