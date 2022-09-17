package com.codifi.cp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.codifi.cp2.entity.TranslateEntity;
import com.codifi.cp2.util.MessageConstants;

public interface TranslateRepository extends JpaRepository<TranslateEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "LANGUAGE_TRANSLATION_DETAILS as trans WHERE trans.PAGE_ID = :pageId and trans.LANGUAGE_ID = :langID and trans.ACTIVE_STATUS = 1", nativeQuery = true)
    List<TranslateEntity> findByLangId(@Param("pageId") Long pageId, @Param("langID") Long langID);

    @Transactional
    @Query(value = "SELECT coalesce(max(text_id),0) FROM " + MessageConstants.SCHEME_NAME
            + "LANGUAGE_TRANSLATION_DETAILS as trans WHERE trans.PAGE_ID = :pageId order by trans.PAGE_ID,trans.TEXT_ID", nativeQuery = true)
    int maxTextID(@Param("pageId") Long pageId);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "LANGUAGE_TRANSLATION_DETAILS as trans WHERE trans.PAGE_ID = :pageId", nativeQuery = true)
    List<TranslateEntity> findByPageId(@Param("pageId") Long pageId);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "LANGUAGE_TRANSLATION_DETAILS as trans WHERE trans.PAGE_ID = :pageId and trans.LANGUAGE_ID = :langID and trans.TEXT_ID = :textId and trans.ACTIVE_STATUS = 1", nativeQuery = true)
    TranslateEntity findByPageLangAndTextId(@Param("pageId") Long pageId, @Param("langID") Long langID,
            @Param("textId") Long textId);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "LANGUAGE_TRANSLATION_DETAILS as trans WHERE trans.TEXT_ID = :textId and trans.PAGE_ID = :pageId and trans.ACTIVE_STATUS = 1", nativeQuery = true)
    List<TranslateEntity> findByTextandPageId(@Param("textId") Long textId, @Param("pageId") Long pageId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "LANGUAGE_TRANSLATION_DETAILS SET TEXT_NAME = null, description = null, UPDATED_BY = :CREATED_BY where ID = :id", nativeQuery = true)
    int deleteTranslate(@Param("id") Long id, @Param("CREATED_BY") String CREATED_BY);
}