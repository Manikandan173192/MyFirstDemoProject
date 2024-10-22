package com.maxbyte.sam.SecondaryDBFlow.WRWOType.Repository;

import com.maxbyte.sam.SecondaryDBFlow.SA.Entity.SA;
import com.maxbyte.sam.SecondaryDBFlow.WRWOType.Entity.WrWoType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WrWoTypeRepository extends JpaRepository<WrWoType,Integer> , JpaSpecificationExecutor<WrWoType> {
//    Page<WrWoType> findAll(Specification<WrWoType> build, PageRequest pageRequest);

}
