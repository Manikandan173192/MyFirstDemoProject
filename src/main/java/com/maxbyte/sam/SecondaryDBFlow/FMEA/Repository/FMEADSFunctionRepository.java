package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSFunction;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSProcess;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAIsNotQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEADSFunctionRepository extends JpaRepository<FMEADSFunction , Integer> {
    List<FMEADSFunction> findByFmeaNumber(String fmeaNumber);

    Optional<FMEADSFunction> findByFunctionId(Integer functionIdToAddOrUpdate);

    @Transactional
    void deleteByFmeaNumberAndFunctionId(String fmeaNumber, Integer entityId);
//    List<FMEADSFunction> findByFunctionId(Integer id);
}
