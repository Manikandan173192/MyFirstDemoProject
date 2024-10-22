package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSAction;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSCause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface FMEAUSActionRepository extends JpaRepository<FMEAUSAction, Integer> {
    List<FMEAUSAction> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSAction> findByActionId(Integer actionIdToAddOrUpdate);

    @Transactional
    void deleteByFmeaNumberAndActionId(String fmeaNumber, Integer entityId);
}
