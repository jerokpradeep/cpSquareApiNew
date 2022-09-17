package com.codifi.cp2.repository;

import com.codifi.cp2.entity.StreamTriggerEventEntity;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StreamTriggerEventRepository extends JpaRepository<StreamTriggerEventEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "STREAM_TRIGGER_EVENT WHERE RECEIVER_ID =:triggerID ", nativeQuery = true)
    StreamTriggerEventEntity findAllByTriggerID(@Param("triggerID") String triggerID);

}
