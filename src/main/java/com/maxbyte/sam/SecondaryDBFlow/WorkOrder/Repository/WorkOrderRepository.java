package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder,Integer> {
    Optional<WorkOrder> findByRowId(String rowId);
}
