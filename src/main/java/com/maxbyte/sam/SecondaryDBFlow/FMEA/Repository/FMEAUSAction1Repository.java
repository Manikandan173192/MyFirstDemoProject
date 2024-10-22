package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSAction;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSAction1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface FMEAUSAction1Repository extends JpaRepository<FMEAUSAction1, Integer> {

    List<FMEAUSAction1> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSAction1> findByActionIdOne(Integer actionIdToAddOrUpdate);

    @Transactional
    void deleteByFmeaNumberAndActionIdOne(String fmeaNumber, Integer entityId);
}
