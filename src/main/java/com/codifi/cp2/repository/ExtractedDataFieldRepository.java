package com.codifi.cp2.repository;

import com.codifi.cp2.entity.ExtractedDataFieldEntity;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import com.codifi.cp2.util.MessageConstants;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ExtractedDataFieldRepository extends JpaRepository<ExtractedDataFieldEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "EXTRACTED_DATA_FIELD_LIST  WHERE ACTIVE_STATUS = 1 ORDER BY S4_TABLE_NAME, S4_FIELD_NAME ", nativeQuery = true)
    List<ExtractedDataFieldEntity> findAllActive();

    @Transactional
    @Query(value = "SELECT EASY_NAME FROM " + MessageConstants.SCHEME_NAME
            + "EXTRACTED_DATA_FIELD_LIST  WHERE ACTIVE_STATUS = 1  ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<String> findAllECNames();

    @Transactional
    @Modifying
    @Query(value = "UPDATE " + MessageConstants.SCHEME_NAME
            + "EXTRACTED_DATA_FIELD_LIST SET ACTIVE_STATUS = 0, UPDATED_BY = :createdBy where ID = :id", nativeQuery = true)
    int deleteExtractedDataFieldList(@Param("id") Long id, @Param("createdBy") String createdBy);

    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "EXTRACTED_DATA_FIELD_LIST  WHERE ACTIVE_STATUS = 1 ORDER BY CREATED_ON DESC", nativeQuery = true)
    List<ExtractedDataFieldEntity> findAllDateType();
}
