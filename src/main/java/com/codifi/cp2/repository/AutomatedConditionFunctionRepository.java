package com.codifi.cp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.codifi.cp2.entity.AutomatedConditionFunctionEntity;
import com.codifi.cp2.util.MessageConstants;

public interface AutomatedConditionFunctionRepository extends JpaRepository<AutomatedConditionFunctionEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_FUNCTION  WHERE AUTOMATED_TRIGGER_ID =:triggerID ", nativeQuery = true)
    AutomatedConditionFunctionEntity findAllByTriggerID(@Param("triggerID") String triggerID);
}
