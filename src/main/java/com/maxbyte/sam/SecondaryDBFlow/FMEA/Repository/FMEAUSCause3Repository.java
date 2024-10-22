package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;


import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSCause3;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSCause3Repository extends JpaRepository<FMEAUSCause3,Integer> {
    List<FMEAUSCause3> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSCause3> findByCauseIdThree(Integer causeIdThreeToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndCauseIdThree(String fmeaNumber, Integer entityId);
}
