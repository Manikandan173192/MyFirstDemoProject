package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSEffect2;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSEffect3;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSEffect3Repository extends JpaRepository<FMEAUSEffect3,Integer> {

    List<FMEAUSEffect3> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSEffect3> findByEffectIdThree(Integer effectIdThree);

    @Transactional
    void deleteByFmeaNumberAndEffectIdThree(String fmeaNumber, Integer entityId);
}
