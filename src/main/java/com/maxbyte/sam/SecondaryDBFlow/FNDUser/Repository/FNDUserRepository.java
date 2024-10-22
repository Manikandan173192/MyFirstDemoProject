package com.maxbyte.sam.SecondaryDBFlow.FNDUser.Repository;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Asset;
import com.maxbyte.sam.SecondaryDBFlow.FNDUser.Entity.FNDUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FNDUserRepository extends JpaRepository<FNDUser, Integer> {

    Optional<FNDUser> findByUserId(Integer userId);
//    @Query("SELECT f FROM FNDUser f WHERE (:userName IS NULL OR f.userName LIKE %:userName%)")
    @Query("SELECT a FROM FNDUser a WHERE LOWER(a.userName) LIKE LOWER(CONCAT('%', :userName, '%'))")
    Page<FNDUser> findByUserName(String userName, PageRequest pageRequest);
}

