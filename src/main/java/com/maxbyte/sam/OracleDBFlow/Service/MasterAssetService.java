//package com.maxbyte.sam.OracleDBFlow.Service;
//
//import com.maxbyte.sam.OracleDBFlow.Entity.MasterAsset;
//import com.maxbyte.sam.OracleDBFlow.Repository.MasterAssetRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.AssetGroup;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetGroupRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class MasterAssetService implements CommandLineRunner {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private MasterAssetRepository masterAssetRepository;
//    @Autowired
//    private AssetRepository assetRepository;
//    @Autowired
//    private AssetGroupRepository assetGroupRepository;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        String sql = "SELECT * FROM apps.XXHIL_EAM_ASSET_HIERARCHY_V ";
//    //    String sql = "select * from apps.XXHIL_EAM_FND_USER_V OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY";
////      String sql = "SELECT * FROM apps.XXHIL_EAM_ASSET_HIERARCHY_V ORDER BY ASSET_NUMBER OFFSET 250000 ROWS FETCH NEXT 400000 ROWS ONLY";
//
////        String sql = "SELECT COUNT(*) FROM apps.XXHIL_EAM_ASSET_HIERARCHY_V";
////
////        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
////        System.out.println("Total Count of Asset = "+count);
//
//        List<MasterAsset> results = jdbcTemplate.query(sql,
//                BeanPropertyRowMapper.newInstance(MasterAsset.class));
////        students.forEach(System.out::println);
////        List<Map<String, Object>> results1 = jdbcTemplate.queryForList(sql);
////
////            results1.forEach(row -> {
////                row.forEach((column, value) -> {
////                    System.out.println(column + ": " + value);
////                });
////            });
//
//      //  System.out.println("The Total Assets are = "+results1.size());
//
//        for (MasterAsset assets : results) {
//
//            String assetNumber = assets.getAssetNumber();
//
//            Optional<MasterAsset> assetsOptional = masterAssetRepository.findByAssetNumber(assetNumber);
//
//            MasterAsset asset;
//            if (assetsOptional.isPresent()) {
//                //MasterAsset asset = new MasterAsset();
//                asset = assetsOptional.get();
////                asset.setAssetId(assets.getAssetId());
//                //asset.setAssetNumber(assets.getAssetNumber());
//                asset.setASSET_DESC(assets.getASSET_DESC());
//                asset.setSERIAL_NUMBER(assets.getSERIAL_NUMBER());
//                asset.setORGANIZATION_CODE(assets.getORGANIZATION_CODE());
//                asset.setORGANIZATION_ID(assets.getORGANIZATION_ID());
//                asset.setEAM_ITEM_TYPE(assets.getEAM_ITEM_TYPE());
//                asset.setASSET_GROUP_ID(assets.getASSET_GROUP_ID());
//                asset.setASSET_GROUP(assets.getASSET_GROUP());
//                asset.setASSET_GROUP_DESCRIPTION(assets.getASSET_GROUP_DESCRIPTION());
//                asset.setASSET_TYPE(assets.getASSET_TYPE());
//                asset.setASSET_CRITICALITY(assets.getASSET_CRITICALITY());
//                asset.setWIP_ACCOUNTING_CLASS_CODE(assets.getWIP_ACCOUNTING_CLASS_CODE());
//                asset.setAREA_ID(assets.getAREA_ID());
//                asset.setAREA(assets.getAREA());
//                asset.setOWNING_DEPARTMENT_ID(assets.getOWNING_DEPARTMENT_ID());
//                asset.setOWNING_DEPARTMENT(assets.getOWNING_DEPARTMENT());
//                asset.setCREATION_DATE(assets.getCREATION_DATE());
//                asset.setLAST_UPDATE_DATE(assets.getLAST_UPDATE_DATE());
//                asset.setPARENT_ASSET_NUMBER(assets.getPARENT_ASSET_NUMBER());
//                asset.setPARENT_SERIAL_NUMBER(assets.getPARENT_SERIAL_NUMBER());
//                asset.setPARENT_ASSET_GROUP(assets.getPARENT_ASSET_GROUP());
//                asset.setMAINTENANCE_OBJECT_TYPE(assets.getMAINTENANCE_OBJECT_TYPE());
//                asset.setMAINTENANCE_OBJECT_ID(assets.getMAINTENANCE_OBJECT_ID());
//                asset.setGEN_OBJECT_ID(assets.getGEN_OBJECT_ID());
//                asset.setINV_ORGANIZATION_CODE(assets.getINV_ORGANIZATION_CODE());
//
//
//            } else {
//                asset = new MasterAsset();
//
//                asset.setAssetNumber(assets.getAssetNumber());
//                asset.setASSET_DESC(assets.getASSET_DESC());
//                asset.setSERIAL_NUMBER(assets.getSERIAL_NUMBER());
//                asset.setORGANIZATION_CODE(assets.getORGANIZATION_CODE());
//                asset.setORGANIZATION_ID(assets.getORGANIZATION_ID());
//                asset.setEAM_ITEM_TYPE(assets.getEAM_ITEM_TYPE());
//                asset.setASSET_GROUP_ID(assets.getASSET_GROUP_ID());
//                asset.setASSET_GROUP(assets.getASSET_GROUP());
//                asset.setASSET_GROUP_DESCRIPTION(assets.getASSET_GROUP_DESCRIPTION());
//                asset.setASSET_TYPE(assets.getASSET_TYPE());
//                asset.setASSET_CRITICALITY(assets.getASSET_CRITICALITY());
//                asset.setWIP_ACCOUNTING_CLASS_CODE(assets.getWIP_ACCOUNTING_CLASS_CODE());
//                asset.setAREA_ID(assets.getAREA_ID());
//                asset.setAREA(assets.getAREA());
//                asset.setOWNING_DEPARTMENT_ID(assets.getOWNING_DEPARTMENT_ID());
//                asset.setOWNING_DEPARTMENT(assets.getOWNING_DEPARTMENT());
//                asset.setCREATION_DATE(assets.getCREATION_DATE());
//                asset.setLAST_UPDATE_DATE(assets.getLAST_UPDATE_DATE());
//                asset.setPARENT_ASSET_NUMBER(assets.getPARENT_ASSET_NUMBER());
//                asset.setPARENT_SERIAL_NUMBER(assets.getPARENT_SERIAL_NUMBER());
//                asset.setPARENT_ASSET_GROUP(assets.getPARENT_ASSET_GROUP());
//                asset.setMAINTENANCE_OBJECT_TYPE(assets.getMAINTENANCE_OBJECT_TYPE());
//                asset.setMAINTENANCE_OBJECT_ID(assets.getMAINTENANCE_OBJECT_ID());
//                asset.setGEN_OBJECT_ID(assets.getGEN_OBJECT_ID());
//                asset.setINV_ORGANIZATION_CODE(assets.getINV_ORGANIZATION_CODE());
//
//                // System.out.println("Asset with assetNumber " + assetNumber + " already exists.");
//
//            }
//            masterAssetRepository.save(asset);
//        }
//
//    }
//
////     @Scheduled(cron = "0 */5 * * * *") // Run every 5 minutes   (0 0 * * *) for 12am everyday
////    public void addMasterAssetDataAutomaticallys() {
////        List<MasterAsset> MasterAssetList = masterAssetRepository.findAll();
////
////        for (MasterAsset assets : MasterAssetList) {
////            Optional<Asset> assetOptional = assetRepository.findByOrganizationCodeAndAssetGroupAndAssetNumber(
////                    assets.getORGANIZATION_CODE(), assets.getASSET_GROUP(), assets.getAssetNumber());
////
////            Asset asset;
////            if (assetOptional.isPresent()) {
////                asset = assetOptional.get();
////                asset.setAssetDescription(assets.getASSET_GROUP_DESCRIPTION());
////                asset.setAssetType(assets.getASSET_TYPE());
////                asset.setSerialNumber(assets.getSERIAL_NUMBER());
////                asset.setAssetGroupId(assets.getASSET_GROUP_ID());
////                asset.setDepartmentId(assets.getOWNING_DEPARTMENT_ID());
////                asset.setDepartment(assets.getOWNING_DEPARTMENT());
////                asset.setArea(assets.getAREA());
////                asset.setIsActive(true);
////
////                System.out.println("Updated asset with assetNumber " + assets.getAssetNumber());
////            } else {
////                asset = new Asset();
//////                asset.setAssetId(assets.getAssetId());
////                asset.setAssetNumber(assets.getAssetNumber());
////                asset.setAssetDescription(assets.getASSET_GROUP());
////                asset.setAssetType(assets.getASSET_TYPE());
////                asset.setSerialNumber(assets.getSERIAL_NUMBER());
////                asset.setAssetGroupId(assets.getASSET_GROUP_ID());
////                asset.setAssetGroup(assets.getASSET_GROUP());
////                asset.setOrganizationCode(assets.getORGANIZATION_CODE());
////                asset.setDepartmentId(assets.getOWNING_DEPARTMENT_ID());
////                asset.setDepartment(assets.getOWNING_DEPARTMENT());
////                asset.setArea(assets.getAREA());
////                asset.setIsActive(true);
////                System.out.println("Inserted new asset with assetNumber " + assets.getAssetNumber());
////            }
////
////            assetRepository.save(asset);
////        }
////    }
//
//
////     @Scheduled(cron = "0 */1 * * * *") // Run every 5 minutes
////    public void addMasterAssetGroupDataAutomaticallys(){
////        List<MasterAsset> MasterAssetList = masterAssetRepository.findAll();
////
////        for(MasterAsset assetGroup :MasterAssetList){
////
////            Optional<AssetGroup> assetGroupOptional = assetGroupRepository.findByAssetGroupId(assetGroup.getASSET_GROUP_ID());
////
////            AssetGroup assetGroup1;
////            if (assetGroupOptional.isPresent()) {
////                assetGroup1 = assetGroupOptional.get();
////                assetGroup1.setAssetGroup(assetGroup.getASSET_GROUP());
////                assetGroup1.setAssetGroupDescription(assetGroup.getASSET_GROUP_DESCRIPTION());
////                assetGroup1.setOrganizationCode(assetGroup.getORGANIZATION_CODE());
////                assetGroup1.setIsActive(true);
////              //  System.out.println("Updated assetGroup with AssetGroupId = " + assetGroup1.getAssetGroupId());
////            } else {
////                assetGroup1 = new AssetGroup();
////                assetGroup1.setAssetGroupId(assetGroup.getASSET_GROUP_ID());
////                assetGroup1.setAssetGroup(assetGroup.getASSET_GROUP());
////                assetGroup1.setAssetGroupDescription(assetGroup.getASSET_GROUP_DESCRIPTION());
////                assetGroup1.setOrganizationCode(assetGroup.getORGANIZATION_CODE());
////                assetGroup1.setIsActive(true);
////              //  System.out.println("Inserted new assetGroup with assetGroupId = " + assetGroup1.getAssetGroupId());
////            }
////
////            assetGroupRepository.save(assetGroup1);
////
////        }
////
////    }
//
//}
