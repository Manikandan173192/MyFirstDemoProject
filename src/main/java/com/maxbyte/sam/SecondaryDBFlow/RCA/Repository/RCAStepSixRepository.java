package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFour;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RCAStepSixRepository extends JpaRepository<RCAStepSix, Integer> {
    List<RCAStepSix> findByRcaNumber(String rcaNo);
}
