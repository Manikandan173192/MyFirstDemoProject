package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFive;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RCAStepFiveRepository extends JpaRepository<RCAStepFive, Integer> {
    List<RCAStepFive> findByRcaNumber(String rcaNo);
}
