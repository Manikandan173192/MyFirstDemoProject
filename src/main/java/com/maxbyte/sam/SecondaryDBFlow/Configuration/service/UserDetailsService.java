package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;

import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserType;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Role;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.AssetSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.RoleSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Specification.UserInfoSpecificationBuilder;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService extends CrudService<UserInfo,Integer> {

    @Autowired
    private UserInfoRepository userDetailRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${pagination.default-size}")
    private int defaultSize;

    @Override
    public CrudRepository repository() {
        return this.userDetailRepository;
    }

    @Override
    public void validateAdd(UserInfo data) {
        try{
        }
        catch(Error e){
            throw new Error(e);
        }

    }

    @Override
    public void validateEdit(UserInfo data) {
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

    public ResponseModel<List<UserInfo>> list(Boolean isActive, String role,String organizationCode,String department, UserType userType,String userName, String requestPage) {
        try {

            UserInfoSpecificationBuilder builder = new UserInfoSpecificationBuilder();
            if(isActive!=null)builder.with("isActive","==",isActive);
            if(role!=null)builder.with("role","==",role);
            if(department!=null)builder.with("department",":",department);
            if(organizationCode!=null)builder.with("organizationCode","==",organizationCode);
            if(userName!=null)builder.with("userName","==",userName);
            if(userType!=null)builder.with("userType",":",userType);
            //List<UserInfo> results = userDetailRepository.findAll(builder.build());
            var pageRequestCount = 0;

            if(requestPage.matches(".*\\d.*")){
                pageRequestCount = Integer.parseInt(requestPage);
            }else{
                pageRequestCount = 0;
            }

            // Create a PageRequest for pagination
            PageRequest pageRequest = PageRequest.of(pageRequestCount, defaultSize, Sort.by("createdOn").descending());
            Page<UserInfo> results = userDetailRepository.findAll(builder.build(), pageRequest);
            List<UserInfo> userList = userDetailRepository.findAll();
            var totalCount = String.valueOf(userList.size());
            var filteredCount = String.valueOf(results.getContent().size());
            if (results.isEmpty()) {
                return new ResponseModel<>(false, "No records found", null);
            } else {
                return new ResponseModel<>(true, totalCount+" Records found & "+filteredCount+ " Filtered", results.getContent());
            }

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }

    public ResponseModel<String> resetPassword(String newPassword,String confirmedPassword, String userEmail) {
        try {

            if (!newPassword.equals(confirmedPassword)) {
                return new ResponseModel<>(false, "New password and confirmed password do not match.", null);
            }
            Optional<UserInfo> userData = userDetailRepository.findByUserEmail(userEmail);

            if (userData.isPresent()) {
                UserInfo user = userData.get();
                String encodedPassword = passwordEncoder.encode(newPassword);
                user.setUserPassword(encodedPassword);
                userDetailRepository.save(user);

                return new ResponseModel<>(true, "Password reset successful.", null);
            } else {
                return new ResponseModel<>(false, "Invalid or expired reset token.", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to reset password.", null);
        }
    }

    /*public ResponseModel<List<UserInfo>> list(Boolean isActive) {
        try {
            UserInfoSpecificationBuilder builder = new UserInfoSpecificationBuilder();
            if(isActive!=null)builder.with("isActive",":",isActive);

            List<UserInfo> results = userDetailRepository.findAll(builder.build());
            return new ResponseModel<>(true, "Records Found",results);

        }catch (Exception e){
            return new ResponseModel<>(false, "Records not found",null);
        }
    }*/

//    public ResponseModel<UserInfo> addUser(UserInfo userInfo) {
//        try {
////            if(userDetailRepository.existsByUserName(userInfo.getUserName())){
////                return new ResponseModel<>(false, "UserName already exists", null);
////            }
//            // Check if the email already exists
////            if (userDetailRepository.existsByUserEmail(userInfo.getUserEmail())) {
////                return new ResponseModel<>(false, "Email already exists", null);
////            }
//
//            // If email doesn't exist, proceed with user creation
//            userInfo.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
//            UserInfo savedUser = userDetailRepository.save(userInfo);
//            return new ResponseModel<>(true, "User Added Successfully", savedUser);
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Failed to add user: " + e.getMessage(), null);
//        }
//    }

//    public ResponseModel<UserInfo> addUser(UserInfo userInfo) {
//        try {
//            userInfo.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
//            userDetailRepository.save(userInfo);
//            return new ResponseModel<>(true, "User Added Successfully",userInfo);
//        }catch (Exception e){
//            return new ResponseModel<>(false, "Failed to add",null);
//        }
//    }

        public ResponseModel<UserInfo> addUser(UserInfo userInfo) {
        try {
            // Encode the password first
            userInfo.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));

            // Check if the email already exists
            if (userDetailRepository.existsByUserEmail(userInfo.getUserEmail())) {
                return new ResponseModel<>(false, "Email already exists", null);
            }

            // If email doesn't exist, proceed with user creation
            UserInfo savedUser = userDetailRepository.save(userInfo);
            return new ResponseModel<>(true, "User Added Successfully", savedUser);
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to add user: " + e.getMessage(), null);
        }
    }


//    public ResponseModel<UserInfo> addUser(UserInfo userInfo) {
//        try {
//            // Check if the user exists by ID
//            Optional<UserInfo> existingUserOptional = userDetailRepository.findById(userInfo.getId());
//
//            if (existingUserOptional.isPresent()) {
//                // User with the given ID already exists, update it
//                UserInfo existingUser = existingUserOptional.get();
//
//                // Update the existing user with the new information
//                existingUser.setUserName(userInfo.getUserName());
//                existingUser.setUserEmail(userInfo.getUserEmail());
//                existingUser.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
//                existingUser.setUserId(userInfo.getUserId());
//                existingUser.setUserPhoneNumber(userInfo.getUserPhoneNumber());
//                existingUser.setDepartmentId(userInfo.getDepartmentId());
//                existingUser.setDepartment(userInfo.getDepartment());
//                existingUser.setOrganizationId(userInfo.getOrganizationId());
//                existingUser.setOrganizationCode(userInfo.getOrganizationCode());
//                existingUser.setRoleId(userInfo.getRoleId());
//                existingUser.setRole(userInfo.getRole());
//                existingUser.setPlantName(userInfo.getPlantName());
//                existingUser.setUserType(userInfo.getUserType());
//                existingUser.setCreatedOn(userInfo.getCreatedOn());
//                existingUser.setUpdatedOn(userInfo.getUpdatedOn());
//                existingUser.setActive(us);
//
//                UserInfo updatedUser = userDetailRepository.save(existingUser);
//                return new ResponseModel<>(true, "User Updated Successfully", updatedUser);
//            } else {
//                // User with the given ID does not exist, create a new user
//                if (userDetailRepository.existsByUserName(userInfo.getUserName())){
//                    return new ResponseModel<>(false, "UserName already exists", null);
//                }
//                // Check if the email already exists
//                if (userDetailRepository.existsByUserEmail(userInfo.getUserEmail())) {
//                    return new ResponseModel<>(false, "Email already exists", null);
//                }
//
//                // Set the encoded password
//                userInfo.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
//
//                UserInfo savedUser = userDetailRepository.save(userInfo);
//                return new ResponseModel<>(true, "User Added Successfully", savedUser);
//            }
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Failed to add/update user: " + e.getMessage(), null);
//        }
//
//
//    }

    //********************************  New code for Listing *************************
//    public ResponseModel<List<UserInfo>> listUserDetails(String department, String organizationCode, UserType userType, String userName) {
//        try {
//            Pageable pageable = PageRequest.of(0, 25);
//            Page<UserInfo> pageResults = userDetailRepository.findByFilters(department, organizationCode, userType,userName,pageable);
//            List<UserInfo> results = pageResults.getContent();
//            return new ResponseModel<>(true, "Records Found", results.reversed());
//        } catch (Exception e) {
//            return new ResponseModel<>(false, "Records not found", null);
//        }
//    }


}
