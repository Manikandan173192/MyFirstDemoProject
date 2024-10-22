package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;


import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.APIRequest.GetAssetList;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.AssetRequest;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.AssetSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.OperationsResourceInstanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssetService extends CrudService<Asset,Integer> {

    @Autowired
    private AssetRepository assetRepository;

    @Value("${pagination.default-size}")
    private int defaultSize;
    @Override
    public CrudRepository repository() {
        return this.assetRepository;
    }

    @Override
    public void validateAdd(Asset data) {
        try{
            Optional<Asset> existingAsset = assetRepository.findByOrganizationCodeAndAssetGroupAndAssetNumber(
                    data.getOrganizationCode(), data.getAssetGroup(), data.getAssetNumber());

            if (existingAsset.isPresent()) {
                throw new IllegalArgumentException("Asset with the same number already exists in the specified organization and asset group.");
            }
        } catch (IllegalArgumentException e) {
            throw e;
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(Asset data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }
    }

    @Override
    public void validateDelete(Integer id) {
        try{

        }
        catch(Error e){
            throw new Error(e);
        }
    }

    public ResponseModel<List<Asset>> list(String assetGroup, String assetDescription, String assetNumber, String organizationCode, String assetType, String department, String area, Boolean isActive,String requestPage) {

        try {

//            AssetSpecificationBuilder builder = new AssetSpecificationBuilder();
//            if (assetGroup != null) builder.with("assetGroup", ":", assetGroup);
//            if (assetDescription != null) builder.with("assetDescription", ":", assetDescription);
//            if (assetNumber != null) builder.with("assetNumber", ":", assetNumber);
//            if (organizationCode != null) builder.with("organizationCode", ":", organizationCode);
//            if (isActive != null) builder.with("isActive", ":", isActive);
//            if (assetType != null) builder.with("assetType", ":", assetType);
//            if (department != null) builder.with("department", ":", department);
//            if (area != null) builder.with("area", ":", area);

            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("creationDate").descending());
            Page<Asset> results = assetRepository.findByAssetGroupAndAssetDescriptionAndAssetNumberAndOrganizationCodeAndAssetTypeAndDepartmentAndAreaAndIsActive(assetGroup, assetDescription, assetNumber, organizationCode,assetType,  department,area, isActive, pageRequest);
            //List<Asset> results = assetRepository.findAll(builder.build());
            List<Asset> assetList = assetRepository.findAll();
            var totalCount = String.valueOf(assetList.size());
            var filteredCount = String.valueOf(results.getContent().size());
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", results.getContent().reversed());
            }
        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }

    }

    public ResponseModel<List<GetAssetList>> listAsset(String assetGroupName,String assetNumber,String assetDescription,String organizationId)
    {
        List<Asset> assets = assetRepository.findAssets(assetGroupName,assetNumber,assetDescription,organizationId);
        List<GetAssetList> assetList = assets.stream().map(asset -> {
            GetAssetList getAssetList = new GetAssetList();


            getAssetList.setId(asset.getId());
            getAssetList.setAssetNumber(asset.getAssetNumber());
            getAssetList.setAssetDescription(asset.getAssetDescription());
            getAssetList.setSerialNumber(asset.getSerialNumber());
            getAssetList.setAssetType(asset.getAssetType());
            getAssetList.setArea(asset.getArea());
            getAssetList.setDepartment(asset.getDepartment());
            getAssetList.setDepartmentId(asset.getDepartmentId());
            getAssetList.setMaintenanceObjectType(String.valueOf(asset.getMaintenanceObjectType()));
            getAssetList.setMaintenanceObjectId(String.valueOf(asset.getMaintenanceObjectId()));
            return getAssetList;
        }).collect(Collectors.toList());
        return new ResponseModel<>(true, "Asset List", assetList);
    }

    public ResponseModel<List<String>> listWipAccountingClassCode(String wipAccountingClassCode) {
        ResponseModel<List<String>> response;

        try {
            Pageable pageable = PageRequest.of(0, 100);
            Page<String> wipCodes = assetRepository.findByWipAccountingClassCode(wipAccountingClassCode, pageable);

            List<String> wipCodeList = wipCodes.getContent().stream().distinct()
                    .collect(Collectors.toList());
            if (!wipCodeList.isEmpty()) {
                response = new ResponseModel<>(true, "WIP Accounting Class Code found.", wipCodeList);
                System.out.println("Total wipAccountingClassCode = " + wipCodeList.size());
            } else {
                response = new ResponseModel<>(true, "WIP Accounting Class Code found for the given name.", null);
            }
        } catch (Exception e) {
            response = new ResponseModel<>(false, "An error occurred while retrieving the records.", null);
        }

        return response;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //    @Scheduled(cron = "0 */1 * * * *") // Run every 5 minutes
    public void fetchAndSaveAssets() throws Exception {
        try {
            // Get the latest record's last update date from our local database
            Optional<LocalDateTime> latestRecordOpt = Optional.ofNullable(assetRepository.findLatestUpdateTime());
            LocalDateTime lastUpdateDateTime = latestRecordOpt.orElse(null);

            // Convert LocalDateTime to Timestamp to avoid precision issues
            Timestamp lastUpdateTimestamp = (lastUpdateDateTime != null) ? Timestamp.valueOf(lastUpdateDateTime) : null;

            // Query the external APPS.XXHIL_EAM_ASSET_HIERARCHY_V table for records updated after or at our latest update date
            String sql = "SELECT * FROM APPS.XXHIL_EAM_ASSET_HIERARCHY_V WHERE LAST_UPDATE_DATE >= ?";

            List<Asset> newAssets;

            if (lastUpdateTimestamp != null) {
                newAssets = jdbcTemplate.query(
                        sql,
                        new Object[]{lastUpdateTimestamp},
                        BeanPropertyRowMapper.newInstance(Asset.class)
                );
            } else {
                // If there's no last update timestamp, fetch all records
                newAssets = jdbcTemplate.query(
                        "SELECT * FROM APPS.XXHIL_EAM_ASSET_HIERARCHY_V",
                        BeanPropertyRowMapper.newInstance(Asset.class)
                );
            }

            // Save the fetched records to the local database
            if (newAssets.isEmpty()) {
                System.out.println("********************No new Assets found.");
            } else {
                System.out.println("********************New Assets found: " + newAssets.size());

                for (Asset asset : newAssets) {
                    // Save to local database
                    boolean existsInLocal = assetRepository.existsByAssetNumber(asset.getAssetNumber());
                    if (!existsInLocal) {
                        assetRepository.save(asset);
                        System.out.println("Saved Asset in local DB: " + asset.getAssetNumber());
                    } else {
                        System.out.println("Skipped duplicate Asset in local DB: " + asset.getAssetNumber());
                    }
                }
            }
        } catch (Exception e) {
            // Log the error with full stack trace to identify the issue
            e.printStackTrace();
            System.err.println("Error fetching or updating asset data: " + e.getMessage());
        }
    }

}