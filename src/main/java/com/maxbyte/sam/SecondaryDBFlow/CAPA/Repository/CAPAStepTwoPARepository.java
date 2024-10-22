package com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepOneCA;
import com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity.CAPAStepTwoPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CAPAStepTwoPARepository extends JpaRepository<CAPAStepTwoPA, Integer> {

    List<CAPAStepTwoPA> findByCapaNumber(String capaNumber);
    @Modifying
    @Transactional
    void deleteByPaId(Integer paId);

}
