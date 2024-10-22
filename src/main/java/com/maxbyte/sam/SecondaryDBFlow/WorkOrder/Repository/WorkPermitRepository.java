package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkPermit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkPermitRepository extends JpaRepository<WorkPermit,Integer>, JpaSpecificationExecutor<WorkPermit> {
    List<WorkPermit> findByWorkOrderNumber(String workOrderNumber);
}
