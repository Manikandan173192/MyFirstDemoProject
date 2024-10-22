package com.maxbyte.sam.SecondaryDBFlow.SA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SACheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SACheckListRepository extends JpaRepository<SACheckList, Integer> {
    void deleteByAssetNumber(String assetNumber);
    @Transactional
    List<SACheckList> deleteByClId(Integer clId);
    List<SACheckList>findByAssetNumber(String assetNumber);
    List<SACheckList> findByAssetNumberAndType(String assetNumber, String checkType);

}
