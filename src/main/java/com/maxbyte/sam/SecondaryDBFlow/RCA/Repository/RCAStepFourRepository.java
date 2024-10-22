package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepFour;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RCAStepFourRepository extends JpaRepository<RCAStepFour,Integer> , JpaSpecificationExecutor<RCAStepFour> {
    List<RCAStepFour> findByRcaNumber(String rcaNo);
}
