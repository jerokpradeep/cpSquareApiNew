package com.codifi.cp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.codifi.cp2.entity.PageDetailsEntity;
import com.codifi.cp2.util.MessageConstants;

public interface PageDetailsRepository extends JpaRepository<PageDetailsEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM " + MessageConstants.SCHEME_NAME
            + "PAGE_DETAILS as page WHERE page.ID = :pageId", nativeQuery = true)
    PageDetailsEntity findByPageId(@Param("pageId") Long pageId);

}
