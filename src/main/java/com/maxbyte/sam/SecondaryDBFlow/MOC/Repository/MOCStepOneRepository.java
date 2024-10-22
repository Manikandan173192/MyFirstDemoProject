package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MOCStepOneRepository extends JpaRepository<MOCStepOne, Integer> {
    List<MOCStepOne> findByMocNumber(String mocNumber);

    List<MOCStepOne> findByStepOneId(Integer id);
    @Transactional
    void deleteByStepOneId(Integer id);
    MOCStepOne findAllByMocNumber(String mocNumber);


}
