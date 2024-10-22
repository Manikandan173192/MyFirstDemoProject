package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSCause1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSCause1Repository extends JpaRepository<FMEAUSCause1,Integer> {
    List<FMEAUSCause1> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSCause1> findByCauseIdOne(Integer causeIdOneToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndCauseIdOne(String fmeaNumber, Integer entityId);
}
