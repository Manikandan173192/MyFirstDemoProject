package com.maxbyte.sam.SecondaryDBFlow.CWF.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlowConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkFlowConfigRepository extends JpaRepository<WorkFlowConfig,Integer> {
    List<WorkFlowConfig> findByWorkFlowNumber(String workFlowNumber);
}
