package com.codifi.cp2.repository;

import com.codifi.cp2.entity.TemplateFieldListEntity;
import com.codifi.cp2.model.response.FTWTemplateDataModel;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TemplateFieldListRepository extends JpaRepository<TemplateFieldListEntity, Long> {
    @Transactional
    @Query(value = " SELECT TEMPLATE.FIELD_NAME as fieldName ,TEMPLATE.TEMPLATE_NAME as templateName FROM  " + MessageConstants.SCHEME_NAME
            + " FREE_TEXT_MESSAGE MESSAGE INNER JOIN " + MessageConstants.SCHEME_NAME
            + " FREE_TEXT_TRIGGER_DETAIL DETAIL ON DETAIL.FREE_TEXT_TRIGGER_ID=MESSAGE.FREE_TEXT_EVENT INNER JOIN "
            + MessageConstants.SCHEME_NAME
            + " TEMPLATE_FIELD_LIST TEMPLATE ON TEMPLATE.FIELD_NAME=DETAIL.TEMPLATE_TYPE "
            + " WHERE DETAIL.FREE_TEXT_TRIGGER_ID= :triggerID GROUP BY TEMPLATE.FIELD_NAME,TEMPLATE.TEMPLATE_NAME ;", nativeQuery = true)
    FTWTemplateDataModel findTemplateType(@Param("triggerID") String triggerID);

}
