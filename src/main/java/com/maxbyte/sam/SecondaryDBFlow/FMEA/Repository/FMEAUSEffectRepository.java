package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSCause;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSEffect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface FMEAUSEffectRepository extends JpaRepository<FMEAUSEffect, Integer> {
    List<FMEAUSEffect> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSEffect> findByEffectId(Integer effectIdToAddOrUpdate);

    @Transactional
    void deleteByFmeaNumberAndEffectId(String fmeaNumber, Integer entityId);
}
