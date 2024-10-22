package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RCAStepOneRepository extends JpaRepository<RCAStepOne, Integer> {
    List<RCAStepOne> findByRcaNumber(String rcaNo);
}
