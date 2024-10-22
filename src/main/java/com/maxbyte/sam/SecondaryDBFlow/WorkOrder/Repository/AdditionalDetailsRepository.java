package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository;

import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.AdditionalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalDetailsRepository extends JpaRepository<AdditionalDetails,Integer>, JpaSpecificationExecutor<AdditionalDetails> {
    AdditionalDetails findByWorkOrderNumber(String workOrderNumber);

}
