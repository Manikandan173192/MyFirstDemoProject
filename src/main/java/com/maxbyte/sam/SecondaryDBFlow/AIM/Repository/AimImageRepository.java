package com.maxbyte.sam.SecondaryDBFlow.AIM.Repository;

import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.AIMImage;
import com.maxbyte.sam.SecondaryDBFlow.AIM.Entity.Aim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AimImageRepository extends JpaRepository<AIMImage, Integer> {
    List<AIMImage> findByAimNumber(String aimNo);

}
