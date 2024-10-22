package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepFiveDD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MOCStepFiveDDRepository extends JpaRepository<MOCStepFiveDD,Integer>, JpaSpecificationExecutor<MOCStepFiveDD> {

    List<MOCStepFiveDD> findByMocNumber(String mocNumber);
    @Transactional
    void deleteByMocStepFiveDDId(Integer id);
}
