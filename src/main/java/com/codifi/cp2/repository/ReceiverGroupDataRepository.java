package com.codifi.cp2.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.codifi.cp2.entity.ReceiverGroupDataEntity;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.json.JSONObject;

public interface ReceiverGroupDataRepository extends JpaRepository<ReceiverGroupDataEntity, Long> {
    @Transactional
    @Query(value = "select count(data.message_number) as TotalMessageCount from " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA data left join " + MessageConstants.SCHEME_NAME
            + "automated_trigger_detail detail on detail.automated_trigger_id = data.trigger_event_id ;", nativeQuery = true)
    int findATMTotalMessageCount();

    @Transactional
    @Query(value = "select count(data.message_number) as TotalMessageCount from " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA data left join " + MessageConstants.SCHEME_NAME
            + "free_text_trigger_detail detail on detail.free_text_trigger_id = data.trigger_event_id ;", nativeQuery = true)
    int findFTMTotalMessageCount();

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA WHERE TRIGGER_EVENT_ID = :triggerId", nativeQuery = true)
    List<ReceiverGroupDataEntity> findByTriggerId(@Param("triggerId") String triggerId);

    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA WHERE MESSAGE_NUMBER = :messageId", nativeQuery = true)
    ReceiverGroupDataEntity isdataByMessageId(@Param("messageId") String messageId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA SET ASSIGNED_BY = :createdBy, ASSIGNED_TO = :assignedTo, UPDATED_BY = :createdBy, UPDATED_ON = :updatedOn, CLAIMED_BY = null, IS_CLAIMED = false  where MESSAGE_NUMBER = :messageIds", nativeQuery = true)
    int updateStatusAssign(@Param("assignedTo") String assignedTo, @Param("messageIds") String messageIds,
            @Param("createdBy") String createdBy, @Param("updatedOn") Date updatedOn);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA SET ASSIGNED_BY = null, ASSIGNED_TO = null, CLAIMED_BY =:createdBy, IS_CLAIMED = true, UPDATED_ON = :updatedOn, UPDATED_BY = :createdBy where MESSAGE_NUMBER  = :messageIds", nativeQuery = true)
    int updateStatusClaim(@Param("messageIds") String messageIds, @Param("createdBy") String createdBy,
            @Param("updatedOn") Date updatedOn);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA SET ASSIGNED_BY = null, ASSIGNED_TO = null, CLAIMED_BY = null, IS_CLAIMED = false, UPDATED_ON = :updatedOn, UPDATED_BY = :createdBy where MESSAGE_NUMBER  = :messageIds", nativeQuery = true)
    int updateStatusReset(@Param("messageIds") String messageIds, @Param("createdBy") String createdBy,
            @Param("updatedOn") Date updatedOn);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA SET POSTPONE_DATE = :postponeDate, POSTPONE_TIME = :postponeTime, STATUS_MESSAGE = 'Postponed', UPDATED_ON = :updatedOn, UPDATED_BY = :createdBy where MESSAGE_NUMBER  = :messageIds", nativeQuery = true)
    int updateStatusPostponed(@Param("messageIds") String messageIds, @Param("createdBy") String createdBy,
            @Param("postponeDate") String postponeDate, @Param("postponeTime") String postponeTime,
            @Param("updatedOn") Date updatedOn);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA SET POSTPONE_DATE = null, POSTPONE_TIME = null, STATUS_MESSAGE = :status, UPDATED_ON = :updatedOn, UPDATED_BY = :createdBy where MESSAGE_NUMBER  = :messageIds", nativeQuery = true)
    int updateStartAndComplete(@Param("status") String status, @Param("messageIds") String messageIds,
            @Param("createdBy") String createdBy, @Param("updatedOn") Date updatedOn);

    @Transactional
    @Query(value = "select distinct(assigned_to) from " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA where assigned_to is not null;", nativeQuery = true)
    List<String> userList();

    @Transactional
    @Query(value = "select DISTINCT display_id from " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA where display_id is not null", nativeQuery = true)
    List<String> getAllDisplayIdList();

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DATA SET POSTPONE_DATE = :postponeDate, POSTPONE_TIME = :postponeTime, ASSIGNED_TO =:assignedTo,  UPDATED_BY = :createdBy, ATTACHMENT =:attachment, ATTACHMENTS_YN =:isAttachment, EMBEDDED_ATTACHMENTS = :fileName where MESSAGE_NUMBER = :messageId ", nativeQuery = true)
    int editMessage(@Param("messageId") String messageId, @Param("createdBy") String createdBy,
            @Param("postponeDate") String postponeDate, @Param("postponeTime") String postponeTime,
            @Param("assignedTo") String assignedTo, @Param("attachment") String attachment,
            @Param("isAttachment") boolean isAttachment, @Param("fileName") String fileName);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME + "RECEIVER_GROUP_DATA"
            + " WHERE TRIGGER_EVENT_ID = :triggerId" + " AND ((:displayId) is null or DISPLAY_ID in(:displayId))"
            + " AND ((:assignedTo) is null or ASSIGNED_TO in(:assignedTo))"
            + " AND ((:productHierarchyCode) is null or PRODUCT_HIERARCHY_CODE in(:productHierarchyCode))"
            + " AND ((:aldiCommodityGroup) is null or ALDI_COMMODITY_GROUP in(:aldiCommodityGroup))"
            + " AND ((:articleType) is null or ARTICLE_TYPE in(:articleType))"
            + " AND ((:dcArticleStatus) is null or DC_ARTICLE_STATUS in(:dcArticleStatus))"
            + " AND (:stockPlannerCode is null or STOCK_PLANNER_CODE LIKE '%:stockPlannerCode%' )"
            + " AND ((:orderAreaDesc) is null or ORDER_AREA_DESCRIPTION in(:orderAreaDesc))"
            + " AND ((:status) is null or STATUS_MESSAGE in(:status))"
            + " AND ((:listingDateFrom) is null or DC_LISTING_START_DATE BETWEEN :listingDateFrom AND :listingDateTo)"
            + " AND ((:postDateFrom) is null or POSTED_DATE BETWEEN :postDateFrom AND :postDateTo)", nativeQuery = true)
    List<ReceiverGroupDataEntity> findAllByFilters(@Param("triggerId") String triggerId,
            @Param("displayId") Integer displayId, @Param("assignedTo") String assignedTo,
            @Param("productHierarchyCode") String productHierarchyCode,
            @Param("aldiCommodityGroup") String aldiCommodityGroup, @Param("articleType") String articleType,
            @Param("dcArticleStatus") String dcArticleStatus, @Param("stockPlannerCode") String stockPlannerCode,
            @Param("orderAreaDesc") String orderAreaDesc, @Param("status") String status,
            @Param("listingDateFrom") String listingDateFrom, @Param("listingDateTo") String listingDateTo,
            @Param("postDateFrom") String postDateFrom, @Param("postDateTo") String postDateTo);

}
