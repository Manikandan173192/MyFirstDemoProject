package com.maxbyte.sam.OracleDBFlow.Repository;

import com.maxbyte.sam.OracleDBFlow.Entity.MasterAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterAssetRepository extends JpaRepository<MasterAsset, Integer> {

    Optional<MasterAsset> findByAssetNumber(String assetNumber);
}
