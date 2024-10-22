package com.maxbyte.sam.OracleDBFlow.Repository;

import com.maxbyte.sam.OracleDBFlow.Entity.MasterAssetActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MasterAssetActivityRepository  extends JpaRepository<MasterAssetActivity,Integer> {
    Optional<MasterAssetActivity> findByAssetActivityId(Integer assetActivityId);
}
