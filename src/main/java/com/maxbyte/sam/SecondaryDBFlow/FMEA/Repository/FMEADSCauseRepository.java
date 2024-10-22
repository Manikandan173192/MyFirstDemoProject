package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSCause;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAEffect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEADSCauseRepository extends JpaRepository<FMEADSCause, Integer> {

    List<FMEADSCause> findByFmeaNumber(String fmeaNumber);

    Optional<FMEADSCause> findByCauseId(Integer causeIdToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndCauseId(String fmeaNumber, Integer entityId);
}
