package com.codifi.cp2.repository;

import java.util.List;

import com.codifi.cp2.entity.NoteListEntity;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NoteListRepository extends JpaRepository<NoteListEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "NOTE_LIST WHERE MESSAGE_ID = :messageId and TYPE = :type and ACTIVE_STATUS = 1  ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<NoteListEntity> findByMsgId(@Param("messageId") String messageId, @Param("type") String type);

}
