package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOne;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RCAStepTwoRepository extends JpaRepository<RCAStepTwo, Integer> {
    List<RCAStepTwo> findByRcaNumber(String rcaNo);

    RCAStepTwo findAllByRcaNumber(String rcaNumber);
}
