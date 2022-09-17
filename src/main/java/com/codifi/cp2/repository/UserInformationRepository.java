package com.codifi.cp2.repository;

import com.codifi.cp2.entity.UserInformationEntity;
import com.codifi.cp2.util.MessageConstants;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<UserInformationEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "USER_INFORMATION as usr WHERE usr.USER_ID = :userId and usr.PASSWORD = :pass", nativeQuery = true)
    UserInformationEntity findByUserIdAndPassword(@Param("userId") String userId, @Param("pass") String pass);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "USER_INFORMATION as usr WHERE usr.USER_ID = :userId", nativeQuery = true)
    UserInformationEntity findByUserId(@Param("userId") String userId);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "USER_INFORMATION as usr WHERE usr.EMAIL_ADDRESS = :emailId", nativeQuery = true)
    UserInformationEntity findByMailId(@Param("emailId") String emailId);

}
