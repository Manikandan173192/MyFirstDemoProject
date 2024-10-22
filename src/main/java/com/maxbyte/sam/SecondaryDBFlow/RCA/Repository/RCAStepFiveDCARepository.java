package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFiveDCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOneTeams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RCAStepFiveDCARepository extends JpaRepository<RCAStepFiveDCA, Integer> {

    List<RCAStepFiveDCA> findByRcaNumber(String rcaNumber);
    @Modifying
    @Transactional
    void deleteByDcaId(Integer dcaId);

}
