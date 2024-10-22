package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.WorkPermitChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WorkPermitChildRepository extends JpaRepository<WorkPermitChild,Integer> {

    List<WorkPermitChild> findByWorkOrderNumber(String workOrderNumber);

    @Transactional
    void deleteById(Integer id);

}
