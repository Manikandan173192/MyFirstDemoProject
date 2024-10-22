package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSAction2;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSAction3;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSAction3Repository extends JpaRepository<FMEAUSAction3,Integer> {

    List<FMEAUSAction3> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSAction3> findByActionIdThree(Integer actionIdThreeToAddOrUpdate);

    @Transactional
    void deleteByFmeaNumberAndActionIdThree(String fmeaNumber, Integer entityId);
}
