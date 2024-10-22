package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.AssetActivity;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.AssetActivityRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AssetActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetActivityService extends CrudService<AssetActivity, Integer> {
    @Autowired
    private AssetActivityRepository assetActivityRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public CrudRepository repository() {
        return null;
    }

    @Override
    public void validateAdd(AssetActivity data) {

    }

    @Override
    public void validateEdit(AssetActivity data) {

    }

    @Override
    public void validateDelete(Integer id) {

    }

    @Value("${pagination.default-size}")
    private int defaultSize;
//    public ResponseModel<List<AssetActivity>> list(String activity, String activityType, Integer activityCause,
//                                              String activitySource/*, String DESCRIPTION*/) {
//
//        try {
//            AssetActivitySpecificationBuilder builder = new AssetActivitySpecificationBuilder();
//            if (activity != null) builder.with("activity", ":", activity);
//            if (activityType != null) builder.with("activityType", ":", activityType);
//            if (activityCause != null) builder.with("activityCause", ":", activityCause);
//            if (activitySource != null) builder.with("activitySource", ":", activitySource);
////            if (DESCRIPTION != null) builder.with("DESCRIPTION", ":", DESCRIPTION);
//
//            List<AssetActivity> results = assetActivityRepository.findAll(builder.build());
//
//            return new ResponseModel<>(true, "Records Found and ", results);
//
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Records not found", null);
//        }
//    }

//    public ResponseModel<List<AssetActivity>> listByOrganizationId(Integer organizationId,  String requestPage) {
//        try {
//            Specification<AssetActivity> spec = (root, query, criteriaBuilder) -> {
//                query.multiselect(
//                        root.get("activityType"),
//                        root.get("activitySource"),
//                        root.get("activityCause")
//                );
//                return criteriaBuilder.equal(root.get("organizationId"), organizationId);
//            };
//
//            var pageRequestCount = 0;
//
//            if (requestPage.matches(".*\\d.*")) {
//                pageRequestCount = Integer.parseInt(requestPage);
//            } else {
//                pageRequestCount = 0;
//            }
//            // Create a PageRequest for pagination
////            Pageable pageable = PageRequest.of(page, size);
//            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize);
//            Page<AssetActivity> results = assetActivityRepository.findAll(spec, pageRequest);
//            List<AssetActivity> assetActivityList =assetActivityRepository.findAll();
//            var totalCount = String.valueOf(assetActivityList.size());
//            var filteredCount = String.valueOf(results.getContent().size());
//            if (results.isEmpty()) {
//                return new ResponseModel<>(false, "No records found", null);
//            } else {
//                return new ResponseModel<>(true, totalCount+ " Records found & " + filteredCount + " Filtered", results.getContent().reversed());
//            }
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Records not found", null);
//        }
//    }

//    public ResponseModel<List<AssetActivityDTO>> listByOrganizationId(Integer organizationId, String requestPage) {
//        try {
//            Specification<AssetActivity> spec = (root, query, criteriaBuilder) -> {
//                return criteriaBuilder.equal(root.get("organizationId"), organizationId);
//            };
//
//            int pageRequestCount = 0;
//            if (requestPage.matches(".*\\d.*")) {
//                pageRequestCount = Integer.parseInt(requestPage);
//            }
//
//            int defaultSize = 10; // Set your default size here
//            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize);
//            Page<AssetActivity> results = assetActivityRepository.findAll(spec, pageRequest);
//
//            if (results.isEmpty()) {
//                return new ResponseModel<>(false, "No records found", null);
//            } else {
//                List<AssetActivityDTO> dtoList = results.getContent().stream()
//                        .map(activity -> new AssetActivityDTO(
//                                activity.getActivityType(),
//                                activity.getActivitySource(),
//                                activity.getActivityCause()
//                        ))
//                        .collect(Collectors.toList());
//
//                String totalCount = String.valueOf(assetActivityRepository.count());
//                String filteredCount = String.valueOf(dtoList.size());
//
//                return new ResponseModel<>(true, totalCount + " Records found & " + filteredCount + " Filtered", dtoList);
//            }
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Records not found", null);
//        }
//    }

    public ResponseModel<List<AssetActivityDTO>> listBySerialNumber(String serialNumber) {
        try {
            // SQL query to retrieve all columns from the external table using SERIAL_NUMBER
            String sql = "SELECT * FROM APPS.MTL_EAM_ASSET_ACTIVITIES_V WHERE SERIAL_NUMBER = ?";

//            System.out.println("Executing SQL: " + sql);
//            System.out.println("Parameter: serialNumber=" + serialNumber);

            // Execute the query and map each row to a result object
            List<AssetActivityDTO> results = jdbcTemplate.query(sql, new Object[]{serialNumber},
                    (rs, rowNum) -> {
                        // Map only the required fields to the DTO
                        AssetActivityDTO dto = new AssetActivityDTO();
                        dto.setActivityType(rs.getString("ACTIVITY_TYPE"));
                        dto.setActivitySource(rs.getString("ACTIVITY_SOURCE"));
                        dto.setActivityCause(rs.getString("ACTIVITY_CAUSE"));
                        dto.setActivity(rs.getString("ACTIVITY"));
                        dto.setActivityDescription(rs.getString("ACTIVITY_DESCRIPTION"));
                        dto.setAssetActivityId(rs.getString("ASSET_ACTIVITY_ID"));
                        dto.setActivityTypeCode(rs.getString("ACTIVITY_TYPE_CODE"));
                        dto.setPriority(rs.getString("PRIORITY"));
                        dto.setShutdownType(rs.getString("SHUTDOWN_TYPE"));
                        dto.setAccountingClassCode(rs.getString("ACCOUNTING_CLASS_CODE"));
                        dto.setActivityCauseCode(rs.getString("ACTIVITY_CAUSE_CODE"));
                        return dto;
                    }
            );

            // Handle the response based on whether records were found
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                String totalCount = String.valueOf(results.size());
                return new ResponseModel<>(true, totalCount + " Records found", results);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel<>(false, "Records not found", null);
        }
    }
}
