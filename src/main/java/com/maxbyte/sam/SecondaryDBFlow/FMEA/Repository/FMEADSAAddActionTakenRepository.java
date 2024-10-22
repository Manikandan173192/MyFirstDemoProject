package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSAction;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSAddActionTaken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEADSAAddActionTakenRepository extends JpaRepository<FMEADSAddActionTaken, Integer> {
    List<FMEADSAddActionTaken> findByFmeaNumber(String fmeaNumber);



    Optional<FMEADSAddActionTaken> findByFmeaNumberAndActionTakenId(String fmeaNumber, Integer actionTakenId);

    Optional<FMEADSAddActionTaken> findByActionTakenId(Integer actionIdToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndActionTakenId(String fmeaNumber, Integer entityId);
}
