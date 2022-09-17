package com.codifi.cp2.repository;

import com.codifi.cp2.entity.FreeTextMaintenanceEntity;
import com.codifi.cp2.model.response.GetAllTriggerEvent;
import com.codifi.cp2.model.response.ReceiverFreeTextModel;
import com.codifi.cp2.util.MessageConstants;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FreeTextMaintenanceRepository extends JpaRepository<FreeTextMaintenanceEntity, Long> {

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "FREE_TEXT_TRIGGER_DETAIL as ftm WHERE ftm.ACTIVE_STATUS = 1 ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<FreeTextMaintenanceEntity> findAllActive();

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "FREE_TEXT_TRIGGER_DETAIL WHERE FREE_TEXT_TRIGGER_ID = :triggerId", nativeQuery = true)
    FreeTextMaintenanceEntity findByTriggerId(@Param("triggerId") String triggerId);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "FREE_TEXT_TRIGGER_DETAIL as ftm WHERE ID = :id", nativeQuery = true)
    FreeTextMaintenanceEntity findByIds(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "FREE_TEXT_TRIGGER_DETAIL as ftm WHERE ftm.FREE_TEXT_TRIGGER_NAME = :triggerName ", nativeQuery = true)
    FreeTextMaintenanceEntity findByTriggerName(@Param("triggerName") String triggerName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "FREE_TEXT_TRIGGER_DETAIL SET STATUS = :status, date_activated = :activatedOn, ACTIVATED_BY = :createdBy, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int updateStatusActive(@Param("id") Long id, @Param("status") String status,
            @Param("activatedOn") String activatedOn, @Param("createdBy") String CREATED_BY);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "FREE_TEXT_TRIGGER_DETAIL SET STATUS = :status, date_deactivated = :deActivatedOn, DEACTIVATED_BY = :createdBy, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int updateStatusInActiveAndUD(@Param("id") Long id, @Param("status") String status,
            @Param("deActivatedOn") String deActivatedOn, @Param("createdBy") String createdBy);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "FREE_TEXT_TRIGGER_DETAIL SET ACTIVE_STATUS = 0, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int deleteFTM(@Param("id") Long id, @Param("createdBy") String createdBy);

    @Transactional
    @Query(value = "select detail.free_text_trigger_id as triggerId , concat(detail.free_text_trigger_id,' - ',detail.FREE_TEXT_TRIGGER_NAME) as triggerName, "
            + " count(data.message_number) as FilteredMessageCount " + " from " + MessageConstants.SCHEME_NAME
            + "free_text_trigger_detail detail " + " left join " + MessageConstants.SCHEME_NAME
            + "receiver_group_data data on detail.free_text_trigger_id = data.trigger_event_id where detail.status in ('Active','Inactive') and detail.ACTIVE_STATUS = 1 "
            + " group by detail.free_text_trigger_id,detail.FREE_TEXT_TRIGGER_NAME order by detail.free_text_trigger_id; ", nativeQuery = true)
    List<GetAllTriggerEvent> findAllTriggerEvents();

    @Transactional
    @Query(value = " Select distinct free_text_trigger_id as triggerId,free_text_trigger_name as engName,trigger_name_german as germanName,status,development_progress as devProgress from "
            + " (Select trim(both from ((json_array_elements(group_name)->'id')\\:\\:text), '\"') as receiver_id ,free_text_trigger_id,free_text_trigger_name,trigger_name_german,status,development_progress "
            + " from sap_cp2.free_text_trigger_detail where ACTIVE_STATUS = 1) a where receiver_id =:receiverID ;", nativeQuery = true)
    List<ReceiverFreeTextModel> getMappedFreeTextList(@Param("receiverID") String receiverID);

    @Transactional
    @Query(value = "SELECT TEMPLATE_TYPE FROM " + MessageConstants.SCHEME_NAME
            + "FREE_TEXT_TRIGGER_DETAIL WHERE FREE_TEXT_TRIGGER_ID = :triggerId ; ", nativeQuery = true)
    String findTemplateTypeByTriggerId(@Param("triggerId") String triggerId);
}
