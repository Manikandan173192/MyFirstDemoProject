package com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepOneCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepSevenCA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CAPAStepOneCARepository extends JpaRepository<CAPAStepOneCA, Integer> {

    List<CAPAStepOneCA> findByCapaNumber(String capaNumber);
    @Modifying
    @Transactional
    void deleteByCaId(Integer caId);

}
