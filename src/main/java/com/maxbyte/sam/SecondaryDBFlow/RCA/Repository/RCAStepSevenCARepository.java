package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFiveDCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSevenCA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RCAStepSevenCARepository extends JpaRepository<RCAStepSevenCA, Integer> {

    List<RCAStepSevenCA> findByRcaNumber(String rcaNumber);
    @Modifying
    @Transactional
    void deleteByCaId(Integer caId);

    RCAStepSevenCA findAllByRcaNumber(String rcaNumber);
}
