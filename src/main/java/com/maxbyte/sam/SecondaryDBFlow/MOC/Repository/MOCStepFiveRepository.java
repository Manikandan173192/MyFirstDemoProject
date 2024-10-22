package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepFive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MOCStepFiveRepository extends JpaRepository<MOCStepFive,Integer> , JpaSpecificationExecutor<MOCStepFive> {
    List<MOCStepFive> findByMocNumber(String mocNumber);

}
