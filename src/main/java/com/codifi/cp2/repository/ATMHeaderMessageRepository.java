package com.codifi.cp2.repository;

import com.codifi.cp2.entity.ATMHeaderMessageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.codifi.cp2.util.MessageConstants;

public interface ATMHeaderMessageRepository extends JpaRepository<ATMHeaderMessageEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_HEADER_MESSAGE  WHERE AUTOMATED_TRIGGER_ID =:triggerID ", nativeQuery = true)
    ATMHeaderMessageEntity findAllByTriggerID(@Param("triggerID") String triggerID);
}
