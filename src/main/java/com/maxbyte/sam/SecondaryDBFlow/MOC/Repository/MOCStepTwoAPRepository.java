package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepTwoAP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MOCStepTwoAPRepository extends JpaRepository<MOCStepTwoAP, Integer> {
    List<MOCStepTwoAP> findByMocNumber(String mocNumber);
    @Transactional
    void deleteByStepTwoAPId(Integer id);
}
