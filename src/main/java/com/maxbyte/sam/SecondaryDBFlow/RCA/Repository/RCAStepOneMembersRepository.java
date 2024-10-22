package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCA;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOne;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepOneTeams;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepThreeQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RCAStepOneMembersRepository extends JpaRepository<RCAStepOneTeams, Integer> {

    List<RCAStepOneTeams> findByRcaNumber(String rcaNumber);
    @Modifying
    @Transactional
    void deleteByTeamId(Integer id);

}
