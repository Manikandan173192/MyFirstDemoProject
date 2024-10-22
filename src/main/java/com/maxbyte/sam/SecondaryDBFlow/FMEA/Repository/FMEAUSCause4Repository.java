package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSCause4;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSEffect4;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSCause4Repository extends JpaRepository<FMEAUSCause4, Integer> {
    List<FMEAUSCause4> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSCause4> findByCauseIdFour(Integer causeIdFourToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndCauseIdFour(String fmeaNumber, Integer entityId);
}
