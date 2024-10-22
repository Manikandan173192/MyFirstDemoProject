package com.maxbyte.sam.OracleDBFlow.Repository;

import com.maxbyte.sam.OracleDBFlow.Entity.MasterWorkRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterWorkRequestRepository extends JpaRepository<MasterWorkRequest, Integer> {
    Optional<MasterWorkRequest> findByWorkRequestId(Integer workRequestId);

    boolean existsByWorkRequestNumber(String workRequestNumber);

    Optional<MasterWorkRequest> findTopByOrderByCreationDateDesc();

//    Optional<MasterWorkRequest> findTopByOrderByUpdateDateDesc();

    Optional<MasterWorkRequest> findTopByOrderByLastUpdateDateDesc();
}
