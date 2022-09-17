package com.codifi.cp2.repository;

import java.util.List;

import com.codifi.cp2.entity.ReceiverGroupUserEntity;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverGroupUserRepository extends JpaRepository<ReceiverGroupUserEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_USER WHERE RECEIVER_ID =:triggerID ", nativeQuery = true)
    ReceiverGroupUserEntity findAllByTriggerID(@Param("triggerID") String triggerID);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "RECEIVER_GROUP_USER WHERE RECEIVER_LONG =:receiverLong ", nativeQuery = true)
    ReceiverGroupUserEntity findByreceiverLong(@Param("receiverLong") String receiverLong);

    @Transactional
    @Query(value = "SELECT distinct RECEIVER_LONG FROM "
            + " (select trim(both from ((json_array_elements(user_json)->'sapLoginId')\\:\\:text), '\"') as sapLoginId,receiver_Long  from sap_cp2.receiver_group_user where ACTIVE_STATUS = 1) a where sapLoginId = :loginId ", nativeQuery = true)
    List<String> findReceiverGroupList(@Param("loginId") String loginId);

}
