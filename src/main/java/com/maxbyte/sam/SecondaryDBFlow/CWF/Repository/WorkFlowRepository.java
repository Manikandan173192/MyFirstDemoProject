package com.maxbyte.sam.SecondaryDBFlow.CWF.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkFlowRepository extends JpaRepository<WorkFlow,Integer>, JpaSpecificationExecutor<WorkFlow> {

    List<WorkFlow> findByWorkFlowNumber(String workFlowNumber);
    List<WorkFlow> findByWorkFlowName(String workFlowName);

}
