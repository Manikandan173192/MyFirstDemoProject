package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSFailureMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEADSFailureModeRepository extends JpaRepository<FMEADSFailureMode, Integer>, JpaSpecificationExecutor<FMEADSFailureMode> {

    List<FMEADSFailureMode> findByFmeaNumber(String fmeaNumber);

    Optional<FMEADSFailureMode> findByFailureModeId(Integer failureModeIdToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndFailureModeId(String fmeaNumber, Integer entityId);
}

