package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSeven;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RCAStepSevenRepository extends JpaRepository<RCAStepSeven, Integer> {
    List<RCAStepSeven> findByRcaNumber(String rcaNo);
}
