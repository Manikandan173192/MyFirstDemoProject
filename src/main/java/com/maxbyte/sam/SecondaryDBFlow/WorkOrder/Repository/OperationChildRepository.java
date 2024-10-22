package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.OperationChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OperationChildRepository extends JpaRepository<OperationChild,Integer>, JpaSpecificationExecutor<OperationChild> {
    List<OperationChild> findByWorkOrderNumber(String workOrderNumber);

    @Transactional
    void deleteById(Integer id);
}
