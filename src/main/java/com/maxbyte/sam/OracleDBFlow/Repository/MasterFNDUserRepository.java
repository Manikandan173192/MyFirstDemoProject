package com.maxbyte.sam.OracleDBFlow.Repository;

import com.maxbyte.sam.OracleDBFlow.Entity.MasterFNDUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterFNDUserRepository extends JpaRepository<MasterFNDUser, Integer> {
    Optional<MasterFNDUser> findByUserId(Integer userId);
}
