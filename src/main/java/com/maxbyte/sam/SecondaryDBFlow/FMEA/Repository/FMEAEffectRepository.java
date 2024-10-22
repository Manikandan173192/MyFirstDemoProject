package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSProcess;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAEffect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAEffectRepository extends JpaRepository<FMEAEffect , Integer> {
    List<FMEAEffect> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAEffect> findByEffectId(Integer effectIdToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndEffectId(String fmeaNumber, Integer entityId);
}
