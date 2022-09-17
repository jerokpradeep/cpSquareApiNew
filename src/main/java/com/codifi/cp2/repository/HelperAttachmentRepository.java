package com.codifi.cp2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.codifi.cp2.entity.HelperAttachmentEntity;
import com.codifi.cp2.model.response.HelperRespModel;
import com.codifi.cp2.util.MessageConstants;

public interface HelperAttachmentRepository extends JpaRepository<HelperAttachmentEntity, Long> {

    @Transactional
    @Query(value = "SELECT id,description,name,language_id,page_id,active_status FROM " + MessageConstants.SCHEME_NAME
            + "helper_attachment_details as help WHERE help.PAGE_ID = :pageId and help.LANGUAGE_ID = :langID and help.ACTIVE_STATUS = 1", nativeQuery = true)
    List<HelperRespModel> findByPageId(@Param("pageId") Long pageId, @Param("langID") Long langID);

}
