package com.maxbyte.sam.OracleDBFlow.Repository;

import com.maxbyte.sam.OracleDBFlow.Entity.MasterDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterDepartmentRepository extends JpaRepository<MasterDepartment, Integer> {
    Optional<MasterDepartment> findByDepartmentId(Integer department);
}

