package com.maxbyte.sam.OracleDBFlow.Repository;

import com.maxbyte.sam.OracleDBFlow.Entity.MasterUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterUserInfoRepository extends JpaRepository<MasterUserInfo, Integer> {
    Optional<MasterUserInfo> findByEmpCodeAndEmailId(String employee, String email);
}
