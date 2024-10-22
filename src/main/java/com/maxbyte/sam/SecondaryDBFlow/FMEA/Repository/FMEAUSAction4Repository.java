package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSAction4;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEAUSAction4Repository extends JpaRepository<FMEAUSAction4, Integer> {
    List<FMEAUSAction4> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSAction4> findByActionIdFour(Integer actionIdFourToAddOrUpdate);

    @Transactional
    void deleteByFmeaNumberAndActionIdFour(String fmeaNumber, Integer entityId);


}
