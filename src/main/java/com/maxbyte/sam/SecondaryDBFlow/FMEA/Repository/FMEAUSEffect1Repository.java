package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSEffect;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSEffect1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSEffect1Repository extends JpaRepository<FMEAUSEffect1,Integer > {
    List<FMEAUSEffect1> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSEffect1> findByEffectIdOne(Integer effectIdOne);

    @Transactional
    void deleteByFmeaNumberAndEffectIdOne(String fmeaNumber, Integer entityId);
}
