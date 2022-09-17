package com.codifi.cp2.repository;

import com.codifi.cp2.entity.ODataRequestResponseLogEntity;
import com.codifi.cp2.util.MessageConstants;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ODataRequestResponseLogRepository extends JpaRepository<ODataRequestResponseLogEntity, Long> {

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "ODATA_REQUEST_RESPONSE_LOG requestUnits order by id desc limit :limits offset :offsets",nativeQuery = true)
    List<ODataRequestResponseLogEntity> requestListByPagination(@Param("limits") int limits, @Param("offsets") int offsets);

    @Transactional
    @Query(value = "SELECT count(*) FROM " + MessageConstants.SCHEME_NAME
            + "ODATA_REQUEST_RESPONSE_LOG requestUnits",nativeQuery = true)
    int getCount();
    
}
