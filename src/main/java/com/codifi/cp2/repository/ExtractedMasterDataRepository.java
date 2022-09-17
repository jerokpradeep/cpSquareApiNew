package com.codifi.cp2.repository;

import com.codifi.cp2.entity.ExtractedMasterDataEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractedMasterDataRepository extends JpaRepository<ExtractedMasterDataEntity, Long> {
    // @Transactional
    // @Query(value = "SELECT :columnName FROM " + MessageConstants.SCHEME_NAME
    //         + "EXTRACTION_MASTER_TABLE as  WHERE ACTIVE_STATUS = 1 group by :columnName order by :columnName", nativeQuery = true)
    // List<String> findbyColumnName(@Param("columnName") String columnName);

}
