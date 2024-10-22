package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;


import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.AssetGroup;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetGroupRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.AssetGroupSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.OperationsResourceInstanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AssetGroupService extends CrudService<AssetGroup,Integer> implements CommandLineRunner {

    @Autowired
    private AssetGroupRepository assetGroupRepository;
    @Autowired
    private AssetRepository assetRepository;
    @Value("${pagination.default-size}")
    private int defaultSize;
    @Override
    public CrudRepository repository() {
        return this.assetGroupRepository;
    }

    @Override
    public void validateAdd(AssetGroup data) {
        try{
            Optional<AssetGroup> existingAssetGroup = assetGroupRepository.findByAssetGroupAndOrganizationCode(
                    data.getAssetGroup(), data.getOrganizationCode()
            );
            if (existingAssetGroup.isPresent()) {
                throw new IllegalArgumentException("AssetGroup name already exists for this organization: " + data.getAssetGroup());
            }
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(AssetGroup data) {
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

    public ResponseModel<List<AssetGroup>> list(String assetGroup,String organizationCode,Boolean isActive,String assetGroupDescription,String requestPage) {

        try {
            AssetGroupSpecificationBuilder builder = new AssetGroupSpecificationBuilder();
            if(assetGroup!=null)builder.with("assetGroup",":",assetGroup);
            if(assetGroupDescription!=null)builder.with("assetGroupDescription",":",assetGroupDescription);
            if(organizationCode!=null)builder.with("organizationCode",":",organizationCode);
            if(isActive!=null)builder.with("isActive",":",isActive);

            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<AssetGroup> results = assetGroupRepository.findByOrganizationCodeAndIsActiveAndAssetGroupAndAssetGroupDescription(assetGroup, organizationCode, isActive,assetGroupDescription, pageRequest);
            List<AssetGroup> assetGroupList = assetGroupRepository.findAll();
            var totalCount = String.valueOf(assetGroupList.size());
            var filteredCount = String.valueOf(results.getContent().size());
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", results.getContent());
            }
        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }

    }
//    public ResponseModel<List<AssetGroup>> list(String organizationCode, Boolean isActive) {
//        try {
//            List<AssetGroup> results = assetGroupRepository.findByOrganizationCodeAndIsActive(organizationCode, isActive);
//            return new ResponseModel<>(true, "Records Found", results);
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Records not found", null);
//        }
//    }

    @Override
    public void run(String... args) throws Exception {
        // Fetch all assets from the local Asset table
        List<Asset> assetList = assetRepository.findAll();

        // Loop through each asset and update or insert into the AssetGroup table
        for (Asset asset : assetList) {
            Optional<AssetGroup> assetGroupOptional = assetGroupRepository.findByAssetGroupId(asset.getAssetGroupId());

            AssetGroup assetGroup;
            if (assetGroupOptional.isPresent()) {
                // Existing AssetGroup record found, check for updates
                assetGroup = assetGroupOptional.get();

                // Compare lastUpdateDate of Asset and updatedOn of AssetGroup
                if (asset.getLastUpdateDate() != null && (assetGroup.getUpdatedOn() == null || asset.getLastUpdateDate().isAfter(assetGroup.getUpdatedOn()))) {
                    // Update existing AssetGroup record only if asset has a more recent update
                    assetGroup.setAssetGroupId(asset.getAssetGroupId());
                    assetGroup.setAssetGroup(asset.getAssetGroup());
                    assetGroup.setAssetGroupDescription(asset.getAssetGroupDescription());
                    assetGroup.setOrganizationCode(asset.getOrganizationCode());
                    assetGroup.setIsActive(true);
                    assetGroup.setUpdatedOn(LocalDateTime.now()); // Update the updatedOn field with the current timestamp

                    assetGroupRepository.save(assetGroup); // Save updated record
                    System.out.println("Updated AssetGroup with AssetGroupId = " + assetGroup.getAssetGroupId());
                } else {
//                    System.out.println("No update required for AssetGroup with AssetGroupId = " + assetGroup.getAssetGroupId());
                }
            } else {
                // Insert new AssetGroup record if not present
                assetGroup = new AssetGroup();
                assetGroup.setAssetGroupId(asset.getAssetGroupId());
                assetGroup.setAssetGroup(asset.getAssetGroup());
                assetGroup.setAssetGroupDescription(asset.getAssetGroupDescription());
                assetGroup.setOrganizationCode(asset.getOrganizationCode());
                assetGroup.setIsActive(true);
                assetGroup.setUpdatedOn(LocalDateTime.now()); // Set the updatedOn field to the current timestamp

                assetGroupRepository.save(assetGroup); // Save new record
                System.out.println("Inserted new AssetGroup with AssetGroupId = " + assetGroup.getAssetGroupId());
            }
        }
    }




}