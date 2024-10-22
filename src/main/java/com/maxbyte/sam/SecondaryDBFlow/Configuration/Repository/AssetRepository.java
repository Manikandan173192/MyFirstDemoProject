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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> , JpaSpecificationExecutor<Asset>{
    Optional<Asset> findByOrganizationCodeAndAssetGroupAndAssetNumber(String organizationCode, String assetGroup, String assetNumber);

    //List<Asset> findByAssetGroupAndOrganizationCodeAndIsActive(String assetGroup, String organizationCode,boolean isActive);
    @Query("SELECT a FROM Asset a WHERE (:assetGroup IS NULL OR a.assetGroup LIKE %:assetGroup%) AND (:organizationCode IS NULL OR a.organizationCode = :organizationCode) AND (:isActive IS NULL OR a.isActive = :isActive)")
    Page<Asset> findByAssetGroupAndOrganizationCodeAndIsActive(@Param("assetGroup") String assetGroup, @Param("organizationCode") String organizationCode, @Param("isActive") Boolean isActive, Pageable pageable);

//    @Query("SELECT a FROM Asset a WHERE (:assetGroup IS NULL OR a.assetGroup LIKE %:assetGroup%) AND (:assetDescription IS NULL OR a.assetDescription LIKE %:assetDescription%) AND (:assetNumber IS NULL OR a.assetNumber LIKE %:assetNumber%)AND (:organizationCode IS NULL OR a.organizationCode = :organizationCode) AND (:isActive IS NULL OR a.isActive = :isActive) AND  (:assetType IS NULL OR a.assetType = :assetType) AND (:department IS NULL OR a.department = :department) AND (:area IS NULL OR a.area = :area)")
//    Page<Asset> findByAssetGroupAndAssetDescriptionAndAssetNumberAndOrganizationCodeAndIsActive(@Param("assetGroup") String assetGroup,@Param("assetDescription") String assetDescription,@Param("assetNumber") String assetNumber, @Param("organizationCode") String organizationCode, @Param("isActive") Boolean isActive, Pageable pageable);

    @Query("SELECT a FROM Asset a WHERE (:assetGroup IS NULL OR a.assetGroup LIKE %:assetGroup%) AND (:assetDescription IS NULL OR a.assetDescription LIKE %:assetDescription%) AND (:assetNumber IS NULL OR a.assetNumber LIKE %:assetNumber%)AND (:organizationCode IS NULL OR a.organizationCode = :organizationCode) AND  (:assetType IS NULL OR a.assetType = :assetType) AND (:department IS NULL OR a.department = :department) AND (:area IS NULL OR a.area = :area) AND (:isActive IS NULL OR a.isActive = :isActive)")
    Page<Asset> findByAssetGroupAndAssetDescriptionAndAssetNumberAndOrganizationCodeAndAssetTypeAndDepartmentAndAreaAndIsActive(String assetGroup, String assetDescription, String assetNumber, String organizationCode, String assetType, String department, String area, Boolean isActive, PageRequest pageRequest);

//    @Query("SELECT a FROM Asset a WHERE (:assetGroup IS NULL OR a.assetGroup LIKE %:assetGroup%) AND (:assetDescription IS NULL OR a.assetDescription LIKE %:assetDescription%) AND (:assetNumber IS NULL OR a.assetNumber LIKE %:assetNumber%)AND (:organizationCode IS NULL OR a.organizationCode = :organizationCode) AND (:isActive IS NULL OR a.isActive = :isActive) AND (:assetType IS NULL OR a.assetType = :assetType)  AND (:department IS NULL OR a.department = :department)  AND (:area IS NULL OR a.area = :area)")
//    Page<Asset> findByAssetGroupAndAssetDescriptionAndAssetNumberAndOrganizationCodeAndIsActive(@Param("assetGroup") String assetGroup,@Param("assetDescription") String assetDescription,@Param("assetNumber") String assetNumber, @Param("organizationCode") String organizationCode, @Param("isActive") Boolean isActive,@Param("assetType") Boolean asset, Pageable pageable);

    @Query("SELECT a.wipAccountingClassCode FROM Asset a WHERE LOWER(a.wipAccountingClassCode) LIKE LOWER(CONCAT('%', :wipAccountingClassCode, '%'))")
    Page<String> findByWipAccountingClassCode(String wipAccountingClassCode, Pageable pageable);

    List<Asset> findByAssetGroupId(Integer assetGroupId);
    Asset findByAssetNumber(String assetNumber);

    @Query(value = "SELECT TOP 100 * FROM Asset " +
            "WHERE (:assetGroupName IS NULL OR ASSET_GROUP LIKE %:assetGroupName%) AND (:assetNumber IS NULL OR ASSET_NUMBER LIKE %:assetNumber%) AND (:assetDescription IS NULL OR ASSET_DESC LIKE %:assetDescription%) AND (:organizationId IS NULL OR ORGANIZATION_ID LIKE %:organizationId%)" +
            "ORDER BY CREATION_DATE DESC",        nativeQuery = true)
    List<Asset> findAssets(String assetGroupName,String assetNumber,String assetDescription,String organizationId);




    @Query(value = "SELECT * FROM APPS.XXHIL_EAM_ASSET_HIERARCHY_v WHERE LAST_UPDATE_DATE > :lastUpdateTime", nativeQuery = true)
    List<Object[]> findNewAssets(@Param("lastUpdateTime") LocalDateTime lastUpdateTime);

    @Query("SELECT MAX(a.lastUpdateDate) FROM Asset a")
    LocalDateTime findLatestUpdateTime();

    boolean existsByAssetNumber(String assetNumber);
}
