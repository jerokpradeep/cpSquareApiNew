package com.codifi.cp2.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codifi.cp2.entity.RequestResponseLogEntity;
import com.codifi.cp2.util.MessageConstants;

public interface RequestResponseLogRepository extends JpaRepository<RequestResponseLogEntity, Long> {
    @Transactional
    @Query(value = "SELECT TOP(500) * FROM " + MessageConstants.SCHEME_NAME
            + "REQUEST_RESPONSE_LOG requestUnits WHERE requestUnits.URL = :url order by CREATED_ON desc ", nativeQuery = true)
    List<RequestResponseLogEntity> requestListByUrl(@Param("url") String url);

    @Transactional
    @Query(value = "SELECT TOP(500) * FROM " + MessageConstants.SCHEME_NAME
            + "REQUEST_RESPONSE_LOG requestUnits WHERE requestUnits.USER_ID = :user_id order by CREATED_ON desc ", nativeQuery = true)
    List<RequestResponseLogEntity> requestListByUser(@Param("user_id") String user_id);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "REQUEST_RESPONSE_LOG requestUnits WHERE URL NOT LIKE '%swagger%' order by id desc limit :limits offset :offsets",nativeQuery = true)
    List<RequestResponseLogEntity> requestListByPagination(@Param("limits") int limits, @Param("offsets") int offsets);

    @Transactional
    @Query(value = "SELECT count(*) FROM " + MessageConstants.SCHEME_NAME
            + "REQUEST_RESPONSE_LOG requestUnits WHERE URL NOT LIKE '%swagger%'",nativeQuery = true)
    int getCount();
}
