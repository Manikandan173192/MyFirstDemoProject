package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSCause1;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSCause2;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSCause2Repository extends JpaRepository<FMEAUSCause2,Integer> {

    List<FMEAUSCause2> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSCause2> findByCauseIdTwo(Integer causeIdTwoToAddOrUpdate);
    @Transactional
    void deleteByFmeaNumberAndCauseIdTwo(String fmeaNumber, Integer entityId);
}
