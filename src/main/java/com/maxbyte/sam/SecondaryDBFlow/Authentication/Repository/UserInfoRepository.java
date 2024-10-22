package com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository;

import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> , JpaSpecificationExecutor {
    Optional<UserInfo> findByuserName(String name);
    Optional<UserInfo> findByUserEmail(String userEmail);
    List<UserInfo> findByisActive(boolean isActive);
    @Query("SELECT u FROM UserInfo u WHERE "
            + "(:department IS NULL OR u.department LIKE %:department%) AND "
            + "(:organizationCode IS NULL OR u.organizationCode = :organizationCode) AND "
            + "(:userType IS NULL OR u.userType = :userType) AND "
            + "(:userName IS NULL OR u.userName LIKE %:userName% )")
    Page<UserInfo> findByFilters(@Param("department") String department,
                                 @Param("organizationCode") String organizationCode,
                                 @Param("userType") UserType userType,
                                 @Param("userName")  String userName, Pageable pageable);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUserName(String userName);

    UserInfo findByUserNameAndOrganizationCode(String username,String organizationCode);

    Optional<UserInfo> findByEmpCodeAndUserEmail(String empCode, String emailId);
}
