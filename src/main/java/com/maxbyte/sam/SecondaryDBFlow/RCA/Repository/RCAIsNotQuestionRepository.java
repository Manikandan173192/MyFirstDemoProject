package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAIsNotQuestions;
import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepThree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RCAIsNotQuestionRepository extends JpaRepository<RCAIsNotQuestions, Integer> {

    List<RCAIsNotQuestions> findByQuestionId(Integer id);

}
