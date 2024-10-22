package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSFailureMode;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSFailureMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface FMEAUSFailureModeRepository extends JpaRepository<FMEAUSFailureMode, Integer> {

    List<FMEAUSFailureMode> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSFailureMode> findByFailureModeId(Integer failureModeIdToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndFailureModeId(String fmeaNumber, Integer entityId);
}
