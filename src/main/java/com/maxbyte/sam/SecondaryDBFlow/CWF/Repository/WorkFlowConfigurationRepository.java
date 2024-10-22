package com.maxbyte.sam.SecondaryDBFlow.CWF.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CWF.Entity.WorkFlowConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WorkFlowConfigurationRepository extends JpaRepository<WorkFlowConfiguration,Integer> {

    List<WorkFlowConfiguration> findByWorkFlowNumber(String workFlowNumber);
    @Transactional
    void deleteById(Integer id);

}
