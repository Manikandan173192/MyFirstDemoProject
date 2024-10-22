package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;


import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSEffect2;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSEffect2Repository extends JpaRepository<FMEAUSEffect2,Integer> {

    List<FMEAUSEffect2> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSEffect2> findByEffectIdTwo(Integer effectIdTwo);

    @Transactional
    void deleteByFmeaNumberAndEffectIdTwo(String fmeaNumber, Integer entityId);
}
