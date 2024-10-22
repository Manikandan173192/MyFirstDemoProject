package com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Service;

import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.AddMaterialRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetMaterialList;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.MaterialInvenoryItemRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.MaterialInventoryListRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.Material;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity.MaterialChild;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.MaterialRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository.MaterialChildRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Specification.MaterialSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialChildRepository materialChildRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ResponseModel<List<Material>> list(String workOrderNumber) {
        try {

            MaterialSpecificationBuilder builder=new MaterialSpecificationBuilder();
            if(workOrderNumber!=null)builder.with("workOrderNumber",":",workOrderNumber);

            List<Material> results = materialRepository.findAll(builder.build());
            return new ResponseModel<>(true, "Records found",results);

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }

    public ResponseModel<String> addMaterial(AddMaterialRequest addMaterialRequest) {
        try {
            List<Material> materialOneList = materialRepository.findByWorkOrderNumber(addMaterialRequest.getWorkOrderNumber());
            var actionApi = 0;
            if(!materialOneList.isEmpty()) {
                materialOneList.getFirst().setWorkOrderNumber(addMaterialRequest.getWorkOrderNumber());
                materialRepository.save(materialOneList.getFirst());

                var addedTableList = materialChildRepository.findByWorkOrderNumber(materialOneList.getFirst().getWorkOrderNumber());
                if (!addedTableList.isEmpty()) {
                    for (MaterialChild items : addedTableList) {
                        if (items.getWorkOrderNumber().equals(materialOneList.getFirst().getWorkOrderNumber())) {
                            materialChildRepository.deleteById(items.getId());
                        }
                    }
                }
                for (MaterialChild items : addMaterialRequest.getMaterialList()) {
                    var materialTwo = new MaterialChild();

                    materialTwo.setWorkOrderNumber(items.getWorkOrderNumber());
                    materialTwo.setWipEntityId(items.getWipEntityId());
                    materialTwo.setOperationId(items.getOperationId());
                    materialTwo.setOperationSequence(items.getOperationSequence());
                    materialTwo.setItemId(items.getItemId());
                    materialTwo.setItemType(items.getItemType());
                    materialTwo.setItemCode(items.getItemCode());
                    materialTwo.setItemDescription(items.getItemDescription());
                    materialTwo.setRequiredQuantity(items.getRequiredQuantity());
                    materialTwo.setUom(items.getUom());
                    materialTwo.setDateRequired(items.getDateRequired());
                    materialTwo.setMaterialTransactionType(items.getMaterialTransactionType());
//                    materialTwo.setCreatedOn(LocalDateTime.now());

                    materialChildRepository.save(materialTwo);

                }
                actionApi = 2;
            }else {
                var materialOne = new Material();
                materialOne.setWorkOrderNumber(addMaterialRequest.getWorkOrderNumber());
                materialOne.setMaterialList(addMaterialRequest.getMaterialList());
                materialRepository.save(materialOne);


                for (MaterialChild items : addMaterialRequest.getMaterialList()) {
                    var materialTwo = new MaterialChild();

                    materialTwo.setWorkOrderNumber(addMaterialRequest.getWorkOrderNumber());
                    materialTwo.setWipEntityId(items.getWipEntityId());
                    materialTwo.setOperationId(items.getOperationId());
                    materialTwo.setOperationSequence(items.getOperationSequence());
                    materialTwo.setItemId(items.getItemId());
                    materialTwo.setItemType(items.getItemType());
                    materialTwo.setItemCode(items.getItemCode());
                    materialTwo.setItemDescription(items.getItemDescription());
                    materialTwo.setRequiredQuantity(items.getRequiredQuantity());
                    materialTwo.setUom(items.getUom());
                    materialTwo.setDateRequired(items.getDateRequired());
                    materialTwo.setMaterialTransactionType(items.getMaterialTransactionType());
//                    materialTwo.setCreatedOn(LocalDateTime.now());

                    materialChildRepository.save(materialTwo);

                }
                actionApi = 1;
            }

            return new ResponseModel<>(true, actionApi == 1?"Material Added Successfully":"Material updated successfully", null);

        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add", null);
        }
    }

   /* public ResponseModel<List<MaterialInventoryListRequest>> listDD(String itemType, String organizationId) {
        String sql = "SELECT * FROM APPS.XXHIL_EAM_WO_ITEMS_V WHERE ITEM_TYPE = ? AND ORGANIZATION_ID = ?";
        System.out.println("sql"+ sql);
        List<MaterialInventoryListRequest> results = jdbcTemplate.query(sql, new Object[]{itemType, organizationId},
                (rs, rowNum) -> {
                    MaterialInventoryListRequest material = new MaterialInventoryListRequest();
                    material.setItemType(rs.getString("ITEM_TYPE"));
                    material.setItemCode(rs.getString("ITEM_CODE"));
                    material.setItemDescription(rs.getString("ITEM_DESCRIPTION"));
                    material.setInventoryItemId(rs.getString("INVENTORY_ITEM_ID"));
                    material.setOrganizationId(rs.getString("ORGANIZATION_ID"));
                    material.setPrimaryUomCode(rs.getString("PRIMARY_UOM_CODE"));
                    material.setSubinventoryCode(rs.getString("SUBINVENTORY_CODE"));
                    material.setOnhandQuantity(rs.getString("ONHAND_QUANTITY"));
                    material.setAvailableQuantity(rs.getString("AVAILABLE_QUANTITY"));
                    return material;
                }
        );

        return new ResponseModel<>(true, "Success", results);
    }*/

    public ResponseModel<List<MaterialInventoryListRequest>> listDD(String itemType, String organizationId, String itemCode) {
        // Base SQL query with mandatory filters
        StringBuilder sql = new StringBuilder("SELECT * FROM APPS.XXHIL_EAM_WO_ITEMS_V WHERE ITEM_TYPE = ? AND ORGANIZATION_ID = ?");

        // List to hold query parameters
        List<Object> params = new ArrayList<>();
        params.add(itemType);
        params.add(organizationId);

        // Add the itemCode filter using LIKE if it's not null or empty
        if (itemCode != null && !itemCode.trim().isEmpty()) {
            sql.append(" AND ITEM_CODE LIKE ?");
            params.add("%" + itemCode.trim() + "%"); // Using % for partial matches
        }

        // Add ordering and limit to fetch top 50 records based on ITEM_CODE
        sql.append(" ORDER BY ITEM_CODE FETCH FIRST 100 ROWS ONLY");

        // Execute the query and map results
        List<MaterialInventoryListRequest> results = jdbcTemplate.query(sql.toString(), params.toArray(),
                (rs, rowNum) -> {
                    MaterialInventoryListRequest material = new MaterialInventoryListRequest();
                    material.setItemType(rs.getString("ITEM_TYPE"));
                    material.setItemCode(rs.getString("ITEM_CODE"));
                    material.setItemDescription(rs.getString("ITEM_DESCRIPTION"));
                    material.setInventoryItemId(rs.getString("INVENTORY_ITEM_ID"));
                    material.setOrganizationId(rs.getString("ORGANIZATION_ID"));
                    material.setPrimaryUomCode(rs.getString("PRIMARY_UOM_CODE"));
                    material.setSubinventoryCode(rs.getString("SUBINVENTORY_CODE"));
                    material.setOnhandQuantity(rs.getString("ONHAND_QUANTITY"));
                    material.setAvailableQuantity(rs.getString("AVAILABLE_QUANTITY"));
                    return material;
                }
        );

        return new ResponseModel<>(true, "Success", results);
    }


    public ResponseModel<List<MaterialInvenoryItemRequest>> listInvenoryItem(String inventoryItemId) {
        String sql = "SELECT * FROM APPS.XXHIL_EAM_WO_ITEMS_V WHERE INVENTORY_ITEM_ID = ?";

        List<MaterialInvenoryItemRequest> results = jdbcTemplate.query(sql, new Object[]{inventoryItemId},
                (rs, rowNum) -> {
                    MaterialInvenoryItemRequest material = new MaterialInvenoryItemRequest();
                    material.setItemType(rs.getString("ITEM_TYPE"));
                    material.setItemCode(rs.getString("ITEM_CODE"));
                    material.setItemDescription(rs.getString("ITEM_DESCRIPTION"));
                    material.setInventoryItemId(rs.getString("INVENTORY_ITEM_ID"));
                    material.setOrganizationId(rs.getString("ORGANIZATION_ID"));
                    material.setPrimaryUomCode(rs.getString("PRIMARY_UOM_CODE"));
                    material.setSubinventoryCode(rs.getString("SUBINVENTORY_CODE"));
                    material.setOnhandQuantity(rs.getString("ONHAND_QUANTITY"));
                    material.setAvailableQuantity(rs.getString("AVAILABLE_QUANTITY"));
                    return material;
                }
        );

        return new ResponseModel<>(true, "Success", results);
    }

    public ResponseModel<List<MaterialInvenoryItemRequest>> listMaterialItemTypeList(String inventoryItemId, String organizationId) {
        String sql = "SELECT * FROM APPS.XXHIL_EAM_WO_ITEMS_V WHERE INVENTORY_ITEM_ID = ? AND ORGANIZATION_ID = ?";

        List<MaterialInvenoryItemRequest> results = jdbcTemplate.query(sql, new Object[]{inventoryItemId, organizationId},
                (rs, rowNum) -> {
                    MaterialInvenoryItemRequest material = new MaterialInvenoryItemRequest();
                    material.setItemType(rs.getString("ITEM_TYPE"));
                    material.setItemCode(rs.getString("ITEM_CODE"));
                    material.setItemDescription(rs.getString("ITEM_DESCRIPTION"));
                    material.setInventoryItemId(rs.getString("INVENTORY_ITEM_ID"));
                    material.setOrganizationId(rs.getString("ORGANIZATION_ID"));
                    material.setPrimaryUomCode(rs.getString("PRIMARY_UOM_CODE"));
                    material.setSubinventoryCode(rs.getString("SUBINVENTORY_CODE"));
                    material.setOnhandQuantity(rs.getString("ONHAND_QUANTITY"));
                    material.setAvailableQuantity(rs.getString("AVAILABLE_QUANTITY"));
                    return material;
                }
        );

        return new ResponseModel<>(true, "Success", results);
    }


    public ResponseModel<List<GetMaterialList>> listMaterial(String wipEntityId) {

        String sql = "SELECT * FROM " +
                "APPS.WIP_REQUIREMENT_OPERATIONS_V " +
                "WHERE WIP_ENTITY_ID = ? ";


        List<GetMaterialList> results = jdbcTemplate.query(sql, new Object[]{wipEntityId},
                (rs, rowNum) -> {

                    GetMaterialList materialList = new GetMaterialList();

                    materialList.setWipEntityId(rs.getString("WIP_ENTITY_ID"));
                    materialList.setOperationSequence(rs.getString("OPERATION_SEQ_NUM"));
                    materialList.setOrganizationId(rs.getString("ORGANIZATION_ID"));
                    materialList.setItemId(rs.getString("INVENTORY_ITEM_ID"));
                    materialList.setItemType(rs.getString("WIP_ENTITY_ID"));
                    materialList.setItemCode(rs.getString("WIP_ENTITY_ID"));
                    materialList.setItemDescription(rs.getString("ITEM_DESCRIPTION"));
                    materialList.setRequiredQuantity(rs.getString("REQUIRED_QUANTITY"));
                    materialList.setUom(rs.getString("ITEM_PRIMARY_UOM_CODE"));
                    materialList.setDateRequired(rs.getString("DATE_REQUIRED"));


                    return materialList;
                }
        );

        System.out.println("Material List = " + results.size());

        return new ResponseModel<>(true, "Success", results);
    }

}
