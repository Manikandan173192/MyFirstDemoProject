package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSAction;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEADSActionRepository extends JpaRepository<FMEADSAction, Integer> {
    List<FMEADSAction> findByFmeaNumber(String fmeaNumber);

    Optional<FMEADSAction> findByActionId(Integer actionIdToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndActionId(String fmeaNumber, Integer entityId);
}
