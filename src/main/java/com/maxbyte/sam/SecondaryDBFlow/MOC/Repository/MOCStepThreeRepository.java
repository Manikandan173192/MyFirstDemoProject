package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepThree;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MOCStepThreeRepository extends JpaRepository<MOCStepThree,Integer> {

    List<MOCStepThree> findByMocNumber(String mocNumber);

    List<MOCStepThree> findByStepThreeId(Integer id);
    @Transactional
    void deleteByStepThreeId(Integer id);
}
