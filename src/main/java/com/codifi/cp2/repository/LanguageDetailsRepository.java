package com.codifi.cp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.codifi.cp2.entity.LanguageDetailsEntity;
import com.codifi.cp2.util.MessageConstants;

public interface LanguageDetailsRepository extends JpaRepository<LanguageDetailsEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "LANGUAGE_DETAILS as country WHERE country.ID = :id", nativeQuery = true)
    LanguageDetailsEntity findByLangId(@Param("id") Long id);
}
