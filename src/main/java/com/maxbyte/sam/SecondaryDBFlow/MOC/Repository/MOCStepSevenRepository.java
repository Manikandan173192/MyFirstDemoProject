package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepSeven;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MOCStepSevenRepository extends JpaRepository<MOCStepSeven, Integer> {
    List<MOCStepSeven> findByMocNumber(String rcaNumber);

    @Query("SELECT m FROM MOCStepSeven m WHERE (m.plantHead = :username AND (m.plantHeadApproverStatus IS NULL OR m.plantHeadApproverStatus = 0)) " +
            "OR (m.unitHead = :username AND (m.unitHeadApproverStatus IS NULL OR m.unitHeadApproverStatus = 0))")
    List<MOCStepSeven> findByApproverName(String username);

}
