package com.codifi.cp2.repository;

import java.util.Date;
import java.util.List;

import com.codifi.cp2.entity.FreeTextMessageEntity;
import com.codifi.cp2.model.response.FTWAttachmentModel;
import com.codifi.cp2.model.response.GetAllTriggerEvent;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FreeTextMessageRepository extends JpaRepository<FreeTextMessageEntity, Long> {
    @Transactional
    @Query(value = "select DISTINCT sender_approved_status from " + MessageConstants.SCHEME_NAME
            + "free_text_message where sender_approved_status is not null", nativeQuery = true)
    List<String> getDistinctStatusList();

    @Transactional
    @Query(value = "select DISTINCT display_id from " + MessageConstants.SCHEME_NAME
            + "free_text_message where display_id is not null", nativeQuery = true)
    List<String> getAllDisplayIdList();

    @Transactional
    @Query(value = "select DISTINCT approved_by from " + MessageConstants.SCHEME_NAME
            + "free_text_message where approved_by is not null", nativeQuery = true)
    List<String> getAllApprovedByList();

    @Transactional
    @Query(value = "select DISTINCT created_by from " + MessageConstants.SCHEME_NAME
            + "free_text_message where created_by is not null", nativeQuery = true)
    List<String> getAllCreatedByList();

    @Transactional
    @Query(value = "select detail.free_text_trigger_id as triggerId , concat(detail.free_text_trigger_id,' - ',detail.FREE_TEXT_TRIGGER_NAME) as triggerName, "
            + " count(data.message_number) as FilteredMessageCount from " + MessageConstants.SCHEME_NAME
            + "free_text_trigger_detail detail left join " + MessageConstants.SCHEME_NAME
            + "free_text_message data on detail.free_text_trigger_id = data.free_text_event where  detail.status in ('Active','Inactive') and  data.ACTIVE_STATUS = 1 "
            + "group by detail.free_text_trigger_id,detail.FREE_TEXT_TRIGGER_NAME order by detail.free_text_trigger_id;", nativeQuery = true)
    List<GetAllTriggerEvent> findAllTriggerEvents();

    @Transactional
    @Query(value = "select count(message.message_number) as TotalMessageCount from " + MessageConstants.SCHEME_NAME
            + "free_text_trigger_detail detail left join " + MessageConstants.SCHEME_NAME
            + "free_text_message message on detail.free_text_trigger_id = message.free_text_event "
            + "where  detail.status in ('Active','Inactive') and detail.free_text_trigger_id =:triggerID and  message.ACTIVE_STATUS = 1 ;", nativeQuery = true)
    int findFTMTotalMessageCount(@Param("triggerID") String triggerID);

    @Transactional
    @Query(value = "select * from " + MessageConstants.SCHEME_NAME
            + "free_text_message where FREE_TEXT_EVENT = :triggerID and sender_approved_status != 'Awaiting Approval' and ACTIVE_STATUS = 1 order by created_on desc ;", nativeQuery = true)
    List<FreeTextMessageEntity> findByTriggerId(@Param("triggerID") String triggerID);

    @Transactional
    @Query(value = "select * from " + MessageConstants.SCHEME_NAME
            + "free_text_message where FREE_TEXT_EVENT = :triggerID and sender_approved_status = 'Awaiting Approval' and ACTIVE_STATUS = 1 order by created_on desc;", nativeQuery = true)
    List<FreeTextMessageEntity> getPendingApprovalMsgs(@Param("triggerID") String triggerID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "free_text_message SET sender_approved_status = :status, UPDATED_BY = :createdBy, UPDATED_ON = :updatedOn where MESSAGE_NUMBER = :messageIds ", nativeQuery = true)
    int updateSendApprove(@Param("status") String status, @Param("createdBy") String createdBy,
            @Param("updatedOn") Date updatedOn, @Param("messageIds") String messageIds);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "free_text_message SET sender_approved_status = :status, APPROVED_BY = :approvedBy ,APPROVED_ON = :approvedOn,  UPDATED_BY = :createdBy, UPDATED_ON = :updatedOn where MESSAGE_NUMBER = :messageIds ", nativeQuery = true)
    int updateApprove(@Param("status") String status, @Param("createdBy") String createdBy,
            @Param("approvedBy") String approvedBy, @Param("approvedOn") String approvedOn,
            @Param("updatedOn") Date updatedOn, @Param("messageIds") String messageIds);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "free_text_message SET ACTIVE_STATUS = 0  where MESSAGE_NUMBER = :messageId", nativeQuery = true)
    int deleteMessage(@Param("messageId") String messageId);

    @Transactional
    @Query(value = "select * from " + MessageConstants.SCHEME_NAME
            + "free_text_message where MESSAGE_NUMBER  = :messageId and ACTIVE_STATUS = 1 ", nativeQuery = true)
    FreeTextMessageEntity getMsgByMsgId(@Param("messageId") String messageId);

    @Transactional
    @Query(value = "select ATTACHMENTS as attachments,ATTACHMENT_NAME as attachmentName from "
            + MessageConstants.SCHEME_NAME
            + "free_text_message where MESSAGE_NUMBER  = :messageId and ACTIVE_STATUS = 1 ", nativeQuery = true)
    FTWAttachmentModel getAttachment(@Param("messageId") String messageId);

    @Transactional
    @Query(value = "select MAX(id) from " + MessageConstants.SCHEME_NAME + "free_text_message", nativeQuery = true)
    Long getMaxId();

}