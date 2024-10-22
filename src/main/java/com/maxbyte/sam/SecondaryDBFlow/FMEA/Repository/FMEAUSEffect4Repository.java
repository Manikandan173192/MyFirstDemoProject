package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSEffect4;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSEffect4Repository extends JpaRepository<FMEAUSEffect4, Integer> {
    List<FMEAUSEffect4> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSEffect4> findByEffectIdFour(Integer effectIdFour);

    @Transactional
    void deleteByFmeaNumberAndEffectIdFour(String fmeaNumber, Integer entityId);
}