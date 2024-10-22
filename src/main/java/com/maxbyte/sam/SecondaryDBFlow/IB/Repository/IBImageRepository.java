package com.maxbyte.sam.SecondaryDBFlow.IB.Repository;

import com.maxbyte.sam.SecondaryDBFlow.IB.Entity.IBImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBImageRepository extends JpaRepository<IBImage, Integer> {

    void deleteByImageUploadId(Integer imageUploadId);

    List<IBImage> findByIbNumber(String ibNumber);
}
