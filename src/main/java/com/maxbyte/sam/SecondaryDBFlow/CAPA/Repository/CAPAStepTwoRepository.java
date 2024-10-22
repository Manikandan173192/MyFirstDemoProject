package com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepOne;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CAPAStepTwoRepository extends JpaRepository<CAPAStepTwo, Integer> {
    List<CAPAStepTwo> findByCapaNumber(String capaNo);
}
