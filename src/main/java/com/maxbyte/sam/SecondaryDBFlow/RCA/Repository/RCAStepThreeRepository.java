package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepThree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RCAStepThreeRepository extends JpaRepository<RCAStepThree, Integer> {
    List<RCAStepThree> findByRcaNumber(String rcaNo);

    void deleteByRcaNumber(String rcaNumber);
}
