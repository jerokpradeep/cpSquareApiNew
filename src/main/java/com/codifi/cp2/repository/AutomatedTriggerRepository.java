package com.codifi.cp2.repository;

import java.util.List;

import com.codifi.cp2.entity.AutomatedTriggerEntity;
import com.codifi.cp2.model.response.DevlopmentProgressModel;
import com.codifi.cp2.model.response.GetAllTriggerEvent;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AutomatedTriggerRepository extends JpaRepository<AutomatedTriggerEntity, Long> {

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL as atm WHERE atm.ACTIVE_STATUS = 1 ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<AutomatedTriggerEntity> findAllActive();

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL as atm WHERE atm.AUTOMATED_TRIGGER_ID =:triggerId", nativeQuery = true)
    AutomatedTriggerEntity findByTriggerId(@Param("triggerId") String triggerId);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL as atm WHERE ID = :id", nativeQuery = true)
    AutomatedTriggerEntity findByIdValue(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL as atm WHERE atm.TRIGGER_NAME_ENGLISH = :triggerName ", nativeQuery = true)
    AutomatedTriggerEntity findByTriggerName(@Param("triggerName") String triggerName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL SET STATUS = :status, date_activated = :activatedOn, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int updateStatusActive(@Param("id") Long id, @Param("status") String status,
            @Param("activatedOn") String activatedOn, @Param("createdBy") String CREATED_BY);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL SET STATUS = :status, DEVELOPMENT_PROGRESS = :devProgress where ID = :id", nativeQuery = true)
    int updateStatusAndDevProgress(@Param("id") Long id, @Param("status") String status,
            @Param("devProgress") int devProgress);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL SET STATUS = :status, date_deactivated = :deActivatedOn, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int updateStatusInActiveAndUD(@Param("id") Long id, @Param("status") String status,
            @Param("deActivatedOn") String deActivatedOn, @Param("createdBy") String createdBy);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL SET ACTIVE_STATUS = 0, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int deleteATM(@Param("id") Long id, @Param("createdBy") String createdBy);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL SET AUTOMATED_TRIGGER = :description, TRIGGER_NAME_ENGLISH =:triggerNameEnglish, TRIGGER_NAME_GERMAN =:triggerNameGerman,  LEAD_TIME_AVAILABLE = :leadTimeAvailable, EASY_NAME = :easyName, S4_TABLE_FIELD_NAME = :fieldName, LEAD_TIME_DATA_S4_TABLE_NAME = :tableName, CATEGORY_ALIGNMENT = :categoryAlignment, CONDITIONAL_FUNCTION = :conditionalFunction, UPDATED_BY = :createdBy, STATUS = :status,DEVELOPMENT_PROGRESS=:devProgress  where ID = :id", nativeQuery = true)
    int updateATMDetails(@Param("description") String description,
            @Param("conditionalFunction") boolean conditionalFunction,
            @Param("categoryAlignment") boolean categoryAlignment,
            @Param("leadTimeAvailable") boolean leadTimeAvailable, @Param("easyName") String easyName,
            @Param("tableName") String tableName, @Param("fieldName") String fieldName,
            @Param("createdBy") String createdBy, @Param("id") Long id, @Param("status") String status,
            @Param("devProgress") int devProgress, @Param("triggerNameEnglish") String triggerNameEnglish,
            @Param("triggerNameGerman") String triggerNameGerman);

    @Transactional
    @Query(value = "select detail.automated_trigger_id as triggerId , concat(detail.automated_trigger_id,' - ',detail.TRIGGER_NAME_ENGLISH) as triggerName, "
            + " count(data.message_number) as FilteredMessageCount " + " from " + MessageConstants.SCHEME_NAME
            + "automated_trigger_detail detail " + " left join " + MessageConstants.SCHEME_NAME
            + "receiver_group_data data on detail.automated_trigger_id = data.trigger_event_id where detail.status in ('Active','Inactive') and detail.ACTIVE_STATUS = 1 "
            + " group by detail.automated_trigger_id,detail.TRIGGER_NAME_ENGLISH order by detail.automated_trigger_id; ", nativeQuery = true)
    List<GetAllTriggerEvent> findAllTriggerEvents();

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "AUTOMATED_TRIGGER_DETAIL WHERE ACTIVE_STATUS = 1 AND DEVELOPMENT_PROGRESS = 100 AND STATUS = 'Active';", nativeQuery = true)
    List<AutomatedTriggerEntity> findActiveTriggerDetails();

    @Transactional
    @Query(value = "SELECT detail.category_alignment as categoryAlignment,detail.conditional_function as condFunc,detail.status as status, "
            + "case when cdhdr.cdpos_objectid_json is null then 0 else 1 end as IsCdObj, "
            + "case when cdhdr.s4_cdpos_json is null then 0 else 1 end as IsCdPos, "
            + "case when cdhdr.s4_cdhdr_json is null then 0 else 1 end as IsHdr, "
            + "case when cdhdr.cdpos_tabkey_json is null then 0 else 1 end as IsTab, "
            + "case when cat.category_json is null then 0 else 1 end as IsCategory, "
            + "case when func.condition_function_json is null then 0 else 1 end as IsCond, "
            + "case when var.message_header_json is null then 0 else 1 end as IsVariable from "
            + MessageConstants.SCHEME_NAME + "automated_trigger_detail detail inner join "
            + MessageConstants.SCHEME_NAME
            + "automated_trigger_cd_table cdhdr  on detail.automated_trigger_id = cdhdr.automated_trigger_id "
            + "inner join " + MessageConstants.SCHEME_NAME
            + "automated_trigger_field_category_alignment cat  on detail.automated_trigger_id = cat.automated_trigger_id "
            + "inner join " + MessageConstants.SCHEME_NAME
            + "automated_trigger_function func on detail.automated_trigger_id = func.automated_trigger_id "
            + "inner join " + MessageConstants.SCHEME_NAME
            + "automated_trigger_header_message var  on detail.automated_trigger_id = var.automated_trigger_id where detail.id = :id ;", nativeQuery = true)
    DevlopmentProgressModel findDataForDevProgress(@Param("id") Long id);

}
