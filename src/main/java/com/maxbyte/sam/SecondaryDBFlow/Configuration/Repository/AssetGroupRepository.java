package com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.AssetGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssetGroupRepository extends JpaRepository<AssetGroup, Integer> , JpaSpecificationExecutor<AssetGroup> {
    Optional<AssetGroup> findByAssetGroupAndOrganizationCode(String assetGroup, String organizationCode);



    @Query("SELECT ag FROM AssetGroup ag WHERE ag.organizationCode = :organizationCode AND (:isActive IS NULL OR ag.isActive = :isActive)")
    List<AssetGroup> findByOrganizationCodeAndIsActive(@Param("organizationCode") String organizationCode, @Param("isActive") Boolean isActive);


//    @Query("SELECT ag FROM AssetGroup ag WHERE (:assetGroup IS NULL OR ag.assetGroup LIKE %:assetGroup%) OR (ag.organizationCode = :organizationCode) OR (:isActive IS NULL OR ag.isActive = :isActive) ")
//    Page<AssetGroup> findByOrganizationCodeAndIsActiveAndAssetGroup(@Param("assetGroup") String assetGroup,@Param("organizationCode") String organizationCode, @Param("isActive") Boolean isActive, Pageable pageable);

    @Query("SELECT ag FROM AssetGroup ag " +
            "WHERE (:assetGroup IS NULL OR ag.assetGroup LIKE %:assetGroup%) " +
            "AND (:organizationCode IS NULL OR ag.organizationCode = :organizationCode) " +
            "AND (:isActive IS NULL OR ag.isActive = :isActive)" +
            "AND (:assetGroupDescription IS NULL OR ag.assetGroupDescription LIKE %:assetGroupDescription%)")
    Page<AssetGroup> findByOrganizationCodeAndIsActiveAndAssetGroupAndAssetGroupDescription(
            @Param("assetGroup") String assetGroup,
            @Param("organizationCode") String organizationCode,
            @Param("isActive") Boolean isActive,
            @Param("assetGroupDescription") String assetGroupDescription,
            Pageable pageable
    );
    Optional<AssetGroup> findByAssetGroupId(Integer assetGroupId);

//    Page<AssetGroup> findByOrganizationCodeAndIsActiveAndAssetGroupAndAssetDescription(String assetGroup, String organizationCode, Boolean isActive, PageRequest pageRequest);
}
