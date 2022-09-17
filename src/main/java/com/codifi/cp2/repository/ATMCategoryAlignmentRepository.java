package com.codifi.cp2.repository;

import com.codifi.cp2.entity.ATMCategoryAlignmentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.codifi.cp2.util.MessageConstants;

public interface ATMCategoryAlignmentRepository extends JpaRepository<ATMCategoryAlignmentEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_FIELD_CATEGORY_ALIGNMENT  WHERE AUTOMATED_TRIGGER_ID =:triggerID ", nativeQuery = true)
    ATMCategoryAlignmentEntity findAllByTriggerID(@Param("triggerID") String triggerID);
}
