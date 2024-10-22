package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSFunction;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEAUSFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface FMEAUSFunctionRepository extends JpaRepository<FMEAUSFunction, Integer> {

    List<FMEAUSFunction> findByFmeaNumber(String fmeaNumber);

    Optional<FMEAUSFunction> findByFunctionId(Integer functionIdToAddOrUpdate);

    @Transactional
    void deleteByFmeaNumberAndFunctionId(String fmeaNumber, Integer entityId);
}
