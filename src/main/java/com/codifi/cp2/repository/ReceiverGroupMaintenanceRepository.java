package com.codifi.cp2.repository;

import com.codifi.cp2.entity.ReceiverGroupMaintenanceEntity;
import com.codifi.cp2.util.MessageConstants;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ReceiverGroupMaintenanceRepository extends JpaRepository<ReceiverGroupMaintenanceEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DETAIL WHERE ACTIVE_STATUS = 1 ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<ReceiverGroupMaintenanceEntity> findAllActive();

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DETAIL WHERE ID =:id ", nativeQuery = true)
    ReceiverGroupMaintenanceEntity findDataById(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DETAIL WHERE RECEIVER_ID =:triggerID ", nativeQuery = true)
    ReceiverGroupMaintenanceEntity findAllByTriggerID(@Param("triggerID") String triggerID);

    @Transactional
    @Query(value = "SELECT id FROM " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DETAIL WHERE RECEIVER_ID =:triggerID ", nativeQuery = true)
    int getIdByTriggerID(@Param("triggerID") String triggerID);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DETAIL WHERE RECEIVER_LONG =:triggerName ", nativeQuery = true)
    ReceiverGroupMaintenanceEntity getDataByTriggerName(@Param("triggerName") String triggerName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DETAIL SET STATUS = :status, date_activated = :activatedOn, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int updateStatusActive(@Param("id") Long id, @Param("status") String status,
            @Param("activatedOn") String activatedOn, @Param("createdBy") String CREATED_BY);

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DETAIL SET STATUS = :status, date_deactivated = :deActivatedOn, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int updateStatusInActiveAndUD(@Param("id") Long id, @Param("status") String status,
            @Param("deActivatedOn") String deActivatedOn, @Param("createdBy") String createdBy);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_DETAIL SET RECEIVER_LONG = :receiverName, RECEIVER_GROUP_EMAIL = :receiverGroupEmail, CONTACT_PERSON = :contactPerson, CONTACT_EMAIL = :contactEmail, MESSAGE_DELIVERY = :messageDelivery, EXCEL_CONSOLIDATION = :excelConsolidation, WEEKDAY = :weekday, HOUSEKEEPING_TIME = :housekeepingTime, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int updateReceiverGroupDetail( @Param("receiverName") String receiverName, @Param("receiverGroupEmail") String receiverGroupEmail,
            @Param("contactPerson") String contactPerson, @Param("contactEmail") String contactEmail,
            @Param("messageDelivery") String messageDelivery, @Param("excelConsolidation") String excelConsolidation,
            @Param("weekday") String weekday, @Param("housekeepingTime") int housekeepingTime,
            @Param("createdBy") String createdBy, @Param("id") Long id);
}
