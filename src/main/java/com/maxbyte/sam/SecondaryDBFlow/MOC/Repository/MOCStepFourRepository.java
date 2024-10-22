package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepFour;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MOCStepFourRepository extends JpaRepository<MOCStepFour,Integer> {

    List<MOCStepFour> findByMocNumber(String mocNumber);

    List<MOCStepFour> findByStepFourId(Integer id);
    @Transactional
    void deleteByStepFourId(Integer id);

    MOCStepFour findAllByMocNumber(String mocNumber);
}
