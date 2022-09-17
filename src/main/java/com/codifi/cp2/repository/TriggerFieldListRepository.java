package com.codifi.cp2.repository;

import java.util.List;

import com.codifi.cp2.entity.TriggerFieldListEntity;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TriggerFieldListRepository extends JpaRepository<TriggerFieldListEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "TRIGGER_FIELD_LIST WHERE ACTIVE_STATUS = 1", nativeQuery = true)
    List<TriggerFieldListEntity> findAllActive();
}
