package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkClearance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkClearanceRepository extends JpaRepository<WorkClearance,Integer>, JpaSpecificationExecutor<WorkClearance> {

    List<WorkClearance> findByWorkOrderNumber(String workOrderNumber);

}
