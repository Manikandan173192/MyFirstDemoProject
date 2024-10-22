package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepNine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MOCStepNineRepository extends JpaRepository<MOCStepNine, Integer> {
    List<MOCStepNine> findByMocNumber(String mocNumber);

    @Query("SELECT m FROM MOCStepNine m WHERE (m.plantHead = :username AND (m.mocPlantCloserStatus IS NULL OR m.mocPlantCloserStatus = 0)) " +
            "OR (m.unitHead = :username AND (m.mocUnitCloserStatus IS NULL OR m.mocUnitCloserStatus = 0))")
    List<MOCStepNine> findByApproverName(String username);
}
