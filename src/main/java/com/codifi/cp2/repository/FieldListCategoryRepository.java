package com.codifi.cp2.repository;

import com.codifi.cp2.entity.FieldListCategoryEntity;
import com.codifi.cp2.util.MessageConstants;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FieldListCategoryRepository extends JpaRepository<FieldListCategoryEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "FIELD_LIST_CATEGORY as flc WHERE flc.ACTIVE_STATUS = 1 ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<FieldListCategoryEntity> findAllActive();

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "FIELD_LIST_CATEGORY WHERE CATEGORY_TECHNICAL_NAME = :categoryTechnicalName and  ACTIVE_STATUS = 1 ;", nativeQuery = true)
    FieldListCategoryEntity findByCategoryTechnicalName(@Param("categoryTechnicalName") String categoryTechnicalName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "FIELD_LIST_CATEGORY SET ACTIVE_STATUS = 0, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int deleteFieldListCategory(@Param("id") Long id, @Param("createdBy") String createdBy);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "FIELD_LIST_CATEGORY SET CATEGORY_TECHNICAL_NAME = :categoryTechnicalName, CATEGORY_NAME_ON_MESSAGE = :categoryNameOnMessage, TRIGGER_EVENT = :triggerEvent, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int updateFieldListCategoryDetail(@Param("categoryTechnicalName") String categoryTechnicalName,
            @Param("categoryNameOnMessage") String categoryNameOnMessage, @Param("triggerEvent") String triggerEvent,
            @Param("createdBy") String createdBy, @Param("id") Long id);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "FIELD_LIST_CATEGORY WHERE TRIGGER_EVENT in (:triggerEvent) and ACTIVE_STATUS = 1;", nativeQuery = true)
    List<FieldListCategoryEntity> getCategoryListByTriggerEvent(@Param("triggerEvent") List<String> triggerEvent);
}
