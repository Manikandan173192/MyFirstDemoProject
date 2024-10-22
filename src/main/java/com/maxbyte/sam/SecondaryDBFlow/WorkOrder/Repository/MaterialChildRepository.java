package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.MaterialChild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialChildRepository extends JpaRepository<MaterialChild,Integer> {
    List<MaterialChild> findByWorkOrderNumber(String workOrderNumber);
}
