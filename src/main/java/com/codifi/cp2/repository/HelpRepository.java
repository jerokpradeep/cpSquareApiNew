package com.codifi.cp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.codifi.cp2.entity.HelpEntity;
import com.codifi.cp2.util.MessageConstants;

public interface HelpRepository extends JpaRepository<HelpEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "HELP_DETAILS as help WHERE help.PAGE_ID = :pageId and help.LANGUAGE_ID = :langID and help.ACTIVE_STATUS = 1", nativeQuery = true)
    List<HelpEntity> findByPageId(@Param("pageId") Long pageId, @Param("langID") Long langID);
}
