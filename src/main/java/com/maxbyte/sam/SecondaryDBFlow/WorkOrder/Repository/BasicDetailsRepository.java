package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.BasicDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicDetailsRepository extends JpaRepository<BasicDetails,Integer>, JpaSpecificationExecutor<BasicDetails> {

    BasicDetails findByWorkOrderNumber(String workOrderNumber);
}
