package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepEight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MOCStepEightRepository extends JpaRepository<MOCStepEight , Integer> {
    List<MOCStepEight> findByMocNumber(String mocNumber);

    @Query("SELECT m FROM MOCStepEight m WHERE m.operationalHead = :username AND (m.approverStatus IS NULL OR m.approverStatus = 0)")
    List<MOCStepEight> findByApproverName(String username);
}
