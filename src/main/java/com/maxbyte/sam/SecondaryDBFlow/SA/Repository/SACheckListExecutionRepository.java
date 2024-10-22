package com.maxbyte.sam.SecondaryDBFlow.SA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SACheckListExecution;
import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SAUpdateCheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface SACheckListExecutionRepository extends JpaRepository<SACheckListExecution, Integer>, JpaSpecificationExecutor<SACheckListExecution> {

    List<SACheckListExecution> findByCheckListTypeAndCheckListExecutionCreatedOn(String checkListType, LocalDateTime date);


    List<SACheckListExecution> findByCheckListTypeAndCheckListExecutionCreatedOnBetween(String weekly, LocalDateTime time, LocalDateTime mobCreatedOn);


    List<SACheckListExecution> findByAssetNumberAndCheckListType(String assetNumber, String checkListType);
}
