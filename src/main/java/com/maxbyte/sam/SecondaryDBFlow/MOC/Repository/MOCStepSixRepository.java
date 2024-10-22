package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepSix;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MOCStepSixRepository extends JpaRepository<MOCStepSix, Integer> {
    List<MOCStepSix> findByMocNumber(String mocNumber);

    List<MOCStepSix> findByStepSixId(Integer id);
    @Transactional
    void deleteByStepSixId(Integer id);
}
