package com.maxbyte.sam.SecondaryDBFlow.SA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SAUpdateCheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SAUpdateCheckListRepository extends JpaRepository<SAUpdateCheckList, Integer>, JpaSpecificationExecutor<SAUpdateCheckList> {
    List<SAUpdateCheckList> findByAssetNumber(String assetNumber);

    @Query("SELECT s FROM SAUpdateCheckList s WHERE s.assetNumber = :assetNumber AND s.checkListType = :checkListType AND s.tStatus = :tStatus")
    List<SAUpdateCheckList> findByAssetNumberAndCheckListTypeAndTStatus(
            @Param("assetNumber") String assetNumber,
            @Param("checkListType") String checkListType,
            @Param("tStatus") Integer tStatus
    );
    List<SAUpdateCheckList> findByAssetNumberAndCheckListType(String assetNumber, String checkListType);

    @Modifying
    @Transactional
    List<SAUpdateCheckList>deleteByAssetNumber(String assetNumber);
}
