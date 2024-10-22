package com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSFunction;
import com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity.FMEADSProcess;


import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FMEADSProcessRepository extends JpaRepository<FMEADSProcess, Integer> {
    List<FMEADSProcess> findByFmeaNumber(String fmeaNumber);
    List<FMEADSProcess> findByProcessId(Integer processId);
  @Transactional
    void deleteByFmeaNumberAndProcessId(String fmeaNumber, Integer entityId);
//    List<FMEADSProcess> findByFunctionId(Integer id);
}
