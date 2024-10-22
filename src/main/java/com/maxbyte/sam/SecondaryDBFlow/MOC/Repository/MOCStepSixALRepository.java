package com.maxbyte.sam.SecondaryDBFlow.MOC.Repository;

import com.maxbyte.sam.SecondaryDBFlow.MOC.Entity.MOCStepSixAL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MOCStepSixALRepository extends JpaRepository<MOCStepSixAL, Integer> {
    List<MOCStepSixAL> findByMocNumber(String rcaNumber);

    @Modifying
    @Transactional
    List<MOCStepSixAL> deleteByStepSixALId(Integer mocId);

    MOCStepSixAL findByApproverNameAndMocNumber(String approverName, String mocNumber);

    @Query("SELECT m FROM MOCStepSixAL m WHERE m.approverName = :username AND (m.approvalStatus IS NULL OR m.approvalStatus = 0)")
    List<MOCStepSixAL> findByApproverName(String username);
}
