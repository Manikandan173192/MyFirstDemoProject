package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MOCStepTwoRepository extends JpaRepository<MOCStepTwo,Integer> , JpaSpecificationExecutor<MOCStepTwo> {
    List<MOCStepTwo> findByMocNumber(String mocNumber);
}