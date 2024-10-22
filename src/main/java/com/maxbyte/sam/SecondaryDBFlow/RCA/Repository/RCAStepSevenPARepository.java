package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSevenCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSevenPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RCAStepSevenPARepository extends JpaRepository<RCAStepSevenPA, Integer> {

    List<RCAStepSevenPA> findByRcaNumber(String rcaNumber);
    @Modifying
    @Transactional
    void deleteByPaId(Integer paId);

    RCAStepSevenPA findAllByRcaNumber(String rcaNumber);
}
