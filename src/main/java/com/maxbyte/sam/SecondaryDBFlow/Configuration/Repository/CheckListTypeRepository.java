package com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.CheckListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckListTypeRepository extends JpaRepository<CheckListType, Integer>, JpaSpecificationExecutor<CheckListType> {
}
