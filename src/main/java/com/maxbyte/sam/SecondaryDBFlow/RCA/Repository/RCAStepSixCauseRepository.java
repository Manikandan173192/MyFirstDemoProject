package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSixCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RCAStepSixCauseRepository extends JpaRepository<RCAStepSixCause, Integer> {
    List<RCAStepSixCause> findByRcaNumber(String rcaNumber);

    @Transactional
    void deleteByRcaStepSixCauseId(Integer id);
}
