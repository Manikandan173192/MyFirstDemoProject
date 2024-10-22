package com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepOne;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSeven;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CAPAStepOneRepository extends JpaRepository<CAPAStepOne, Integer> {
    List<CAPAStepOne> findByCapaNumber(String capaNo);
}
