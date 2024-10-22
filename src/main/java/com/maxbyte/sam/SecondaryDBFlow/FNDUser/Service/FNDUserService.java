package com.maxbyte.sam.SecondaryDBFlow.FNDUser.Service;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Role;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.RoleSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.CrudService;
import com.maxbyte.sam.SecondaryDBFlow.FNDUser.APIRequest.FNDUserRequest;
import com.maxbyte.sam.SecondaryDBFlow.FNDUser.Entity.FNDUser;
import com.maxbyte.sam.SecondaryDBFlow.FNDUser.Repository.FNDUserRepository;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FNDUserService extends CrudService<FNDUser,Integer> {

    @Autowired
    private FNDUserRepository fndUserRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public CrudRepository repository() {
        return this.fndUserRepository;
    }

    @Override
    public void validateAdd(FNDUser data) {

    }

    @Override
    public void validateEdit(FNDUser data) {

    }

    @Override
    public void validateDelete(Integer id) {

    }
    @Value("${pagination.default-size}")
    private int defaultSize;

    public ResponseModel<List<FNDUser>> list(String userName, String requestPage) {
        try {


            // List<Role> results = roleRepository.findAll(builder.build());
            var pageRequestCount = 0;

            if (requestPage.matches(".*\\d.*")) {
                pageRequestCount = Integer.parseInt(requestPage);
            } else {
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize);

//            var count = departmentRepository.findAll().size();
            Page<FNDUser> results = fndUserRepository.findByUserName(userName, pageRequest);

            List<FNDUser> fndUserList= fndUserRepository.findAll();
            var totalCount = String.valueOf(fndUserList.size());
            var filteredCount = String.valueOf(results.getContent().size());
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount+ " Records found & " + filteredCount + " Filtered", results.getContent().reversed());
            }
        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }


//    public ResponseModel<List<FNDUserRequest>> listDD(String userName) {
//        String sql = "SELECT * FROM APPS.XXHIL_EAM_FND_USER_V WHERE USER_NAME = ? ";
//
//
//        List<FNDUserRequest> results = jdbcTemplate.query(sql, new Object[]{userName},
//                (rs, rowNum) -> {
//                    FNDUserRequest material = new FNDUserRequest();
//                    material.setUserId(rs.getInt("USER_ID"));
//                    material.setUserName(rs.getString("USER_NAME"));
//                    material.setEMAIL_ADDRESS(rs.getString("EMAIL_ADDRESS"));
//
//                    return material;
//                }
//        );
//
//        return new ResponseModel<>(true, "Success", results);
//    }

//    public ResponseModel<List<FNDUserRequest>> listDD(String userName, int page, int size) {
//        int startRow = (page - 1) * size + 1;
//        int endRow = page * size;
//
//        // SQL query with optional filtering
//        String sql = "SELECT * FROM ( " +
//                "  SELECT a.*, ROW_NUMBER() OVER (ORDER BY USER_ID) AS rn " +
//                "  FROM APPS.XXHIL_EAM_FND_USER_V a " +
//                "  WHERE (:userName IS NULL OR USER_NAME LIKE %:userName%) " + ") WHERE rn BETWEEN ? AND ?";
//
//        // Prepare parameters based on whether userName is provided
//        List<Object> params = new ArrayList<>();
//        if (userName != null && !userName.isEmpty()) {
//            params.add(userName);
//            params.add("%" + userName + "%");  // Add wildcard characters
//        } else {
//            params.add(null);
//            params.add(null);  // No filter applied
//        }
//        params.add(startRow);
//        params.add(endRow);
//
//        // Convert list to array
//        Object[] paramArray = params.toArray();
//
//        List<FNDUserRequest> results = jdbcTemplate.query(sql, paramArray,
//                (rs, rowNum) -> {
//                    FNDUserRequest material = new FNDUserRequest();
//                    material.setUserId(rs.getInt("USER_ID"));
//                    material.setUserName(rs.getString("USER_NAME"));
//                    material.setEMAIL_ADDRESS(rs.getString("EMAIL_ADDRESS"));
//                    return material;
//                }
//        );
//
//        return new ResponseModel<>(true, "Success", results);
//    }

   /* public ResponseModel<List<FNDUserRequest>> listDD(String userName, int pageNumber, int pageSize) {
        // Calculate the starting row and ending row
        int startRow = (pageNumber - 1) * pageSize + 1;
        int endRow = pageNumber * pageSize;

        // SQL query with pagination for Oracle 11g and earlier
        String sql = "SELECT * FROM ( " +
                "    SELECT USER_ID, USER_NAME, EMAIL_ADDRESS, " +
                "           ROWNUM AS rnum " +
                "    FROM ( " +
                "        SELECT USER_ID, USER_NAME, EMAIL_ADDRESS " +
                "        FROM APPS.XXHIL_EAM_FND_USER_V " +
                "        WHERE USER_NAME = ? " +
                "        ORDER BY USER_ID " +
                "    ) " +
                "    WHERE ROWNUM <= ? " +
                ") " +
                "WHERE rnum >= ?";

        // Execute the query with pagination parameters
        List<FNDUserRequest> results = jdbcTemplate.query(sql, new Object[]{userName, endRow, startRow},
                (rs, rowNum) -> {
                    FNDUserRequest material = new FNDUserRequest();
                    material.setUserId(rs.getInt("USER_ID"));
                    material.setUserName(rs.getString("USER_NAME"));
                    material.setEMAIL_ADDRESS(rs.getString("EMAIL_ADDRESS"));

                    return material;
                }
        );

        // Return the results within a response model
        return new ResponseModel<>(true, "Success", results);
    }*/

    public ResponseModel<List<FNDUserRequest>> listDD(String userName, int pageNumber) {
        // Calculate the starting row and ending row
        int startRow = (pageNumber - 1) * defaultSize + 1;
        int endRow = pageNumber * defaultSize;

        // Base SQL query
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM ( ")
                .append("    SELECT USER_ID, USER_NAME, EMAIL_ADDRESS, ")
                .append("           ROWNUM AS rnum ")
                .append("    FROM ( ")
                .append("        SELECT USER_ID, USER_NAME, EMAIL_ADDRESS ")
                .append("        FROM APPS.XXHIL_EAM_FND_USER_V ");

        // Conditionally add the WHERE clause based on the userName parameter
        if (userName != null && !userName.trim().isEmpty()) {
            sqlBuilder.append("         WHERE UPPER(USER_NAME) LIKE UPPER(?) ");
        }

        // Finish the SQL query
        sqlBuilder.append("        ORDER BY USER_ID ")
                .append("    ) ")
                .append("    WHERE ROWNUM <= ? ")
                .append(") ")
                .append("WHERE rnum >= ?");

        // Convert the StringBuilder to a String for the query
        String sql = sqlBuilder.toString();

        // Parameters for the query
        Object[] params;
        if (userName != null && !userName.trim().isEmpty()) {
            // If userName is provided, include it with wildcard in the parameters
            params = new Object[]{'%' + userName.toUpperCase() + '%', endRow, startRow};
        } else {
            // If userName is not provided, exclude it from the parameters
            params = new Object[]{endRow, startRow};
        }

        // Execute the query with pagination parameters
        List<FNDUserRequest> results = jdbcTemplate.query(sql, params,
                (rs, rowNum) -> {
                    FNDUserRequest material = new FNDUserRequest();
                    material.setUserId(rs.getInt("USER_ID"));
                    material.setUserName(rs.getString("USER_NAME"));
                    material.setEMAIL_ADDRESS(rs.getString("EMAIL_ADDRESS"));

                    return material;
                }
        );

        // Return the results within a response model
        return new ResponseModel<>(true, "Success", results);
    }





}
