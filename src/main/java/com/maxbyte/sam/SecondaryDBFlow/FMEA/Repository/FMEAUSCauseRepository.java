package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSCause;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface FMEAUSCauseRepository extends JpaRepository<FMEAUSCause, Integer> {
    List<FMEAUSCause> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSCause> findByCauseId(Integer causeIdToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndCauseId(String fmeaNumber, Integer entityId);
}
