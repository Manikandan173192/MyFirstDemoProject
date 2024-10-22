package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFourCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOneTeams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RCAStepFourCARepository extends JpaRepository<RCAStepFourCA, Integer> {


    List<RCAStepFourCA> findByRcaNumber(String rcaNumber);

    @Transactional
    void deleteByRcaStepFourTableId(Integer id);
}
