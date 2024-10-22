package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkClearanceChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WorkClearanceChildRepository extends JpaRepository<WorkClearanceChild,Integer> {
    List<WorkClearanceChild> findByWorkOrderNumber(String workOrderNumber);

    @Transactional
    void deleteById(Integer id);
}
