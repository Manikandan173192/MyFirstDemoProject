package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSAction1;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSAction2;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSAction2Repository extends JpaRepository<FMEAUSAction2,Integer> {

    List<FMEAUSAction2> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSAction2> findByActionIdTwo(Integer actionIdTwoToAddOrUpdate);

    @Transactional
    void deleteByFmeaNumberAndActionIdTwo(String fmeaNumber, Integer entityId);
}
