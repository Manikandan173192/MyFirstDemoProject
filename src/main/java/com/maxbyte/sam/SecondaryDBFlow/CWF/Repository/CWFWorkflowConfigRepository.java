package com.maxbyte.sam.SecondaryDBFlow.CWF.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.CWFWorkFlowConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CWFWorkflowConfigRepository extends JpaRepository<CWFWorkFlowConfig,Integer> {
    List<CWFWorkFlowConfig> findByDocumentNumber(String documentNumber);

}
