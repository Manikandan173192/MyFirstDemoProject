package com.maxbyte.sam.SecondaryDBFlow.RCA.Repository;

import com.maxbyte.sam.SecondaryDBFlow.RCA.Entity.RCAStepThreeQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RCAStepThreeQuestionsRepository extends JpaRepository<RCAStepThreeQuestions, Integer> {

    List<RCAStepThreeQuestions> findByRcaNumber(String rcaNumber);
    @Transactional
    void deleteByQuestionId(Integer id);
}